package ec.edu.espe.producto.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import ec.edu.espe.producto.dao.ProductoRepository;
import ec.edu.espe.producto.domain.Producto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(productoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarTodo() {
        log.info("Se va a obtener todos los productos");
        List<Producto> dtos = new ArrayList<>();
        for (Producto producto : this.productoRepository.findAll()) {
            if ("ACT".equals(producto.getExistencia())) {
            dtos.add(Producto);
            }
        }
        return dtos;
    }

    public Producto obtenerPorIdentificacion(String tipoIdentificacion, String numeroIdentificacion) {
        log.info("Se va a obtener Producto por TipoIdentificacion: {} y NumeroIdentificacion: {}", tipoIdentificacion,numeroIdentificacion);
        List<Producto> Productos = this.productoRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
        if (Productos != null && !Productos.isEmpty()) {
            if ("ACT".equals(Productos.get(0).getExistencia())) {
                log.debug("Producto obtenido: {}", Productos.get(0));
                return Productos.get(0);
            } else {
                throw new RuntimeException("Producto con id: " + id
                     + " no se encuentra activo");
            }
        } else {
            throw new RuntimeException("No existe el Producto con id: " + id);
        }
    }

    public Producto obtenerPorId(String id) {
        log.info("Se va a obtener el Producto con ID: {}", id);
        Producto Producto = this.productoRepository.findByIdProducto(id);
        if ("ACT".equals(Producto.getExistencia())) {
            log.debug("Producto obtenido: {}", Producto);
            return producto;
        } else {
            throw new RuntimeException("Producto con ID: " + id + " no se encuentra activo");
        }
    }
    
    @Transactional
    public void crear(Producto Producto) {
        try {
            Producto.setExitencia("1");
            Producto.setIdProducto(new DigestUtils("MD2").digestAsHex(Producto.getTipoIdentificacion()+Producto.getNumeroIdentificacion()));
            log.debug("ID Producto generado: {}", Producto.getIdProducto());
            Producto.setFechaCreacion(new Date());
            this.productoRepository.save(Producto);
            log.info("Se creo el Producto: {}", Producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el Producto.", e);
        }
    }

    @Transactional
    public void actualizar(Producto Producto) {
        try {
            Producto ProductoAux = this.productoRepository.findByIdProducto(Producto.getIdProducto());
            if ("ACT".equals(ProductoAux.getEstado())) {
                Producto.setExitencia("ACT");
                this.productoRepository.save(Producto);
                log.info("Se actualizaron los datos del Producto: {}", Producto);
            } else {
                log.error("No se puede actualizar, Producto: {} se encuentra INACTIVO", ProductoAux);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el Producto.", e);
        }
    }

    @Transactional
    public void desactivar(String idProducto) {
        log.info("Se va a desactivar el Producto: {}", idProducto);
        try {
            Producto Producto = this.productoRepository.findByIdProducto(idProducto);
            log.debug("Desactivando Producto: {}, estado: 0", idProducto);
            Producto.setEstado("INA");
            this.productoRepository.save(Producto);
            log.info("Se desactivo el Producto: {}", idProducto);
        } catch (Exception e) {
            throw new RuntimeException("Error al desactivar Producto: " + idProducto, e);
        }
    }
}

