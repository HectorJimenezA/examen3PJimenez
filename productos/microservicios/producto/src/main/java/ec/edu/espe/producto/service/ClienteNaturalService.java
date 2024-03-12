package com.banquito.core.clientes.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import com.banquito.core.clientes.dao.ClienteNaturalRepository;
import com.banquito.core.clientes.domain.ClienteNatural;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteNaturalService {

    private final ClienteNaturalRepository clienteNaturalRepository;

    public ClienteNaturalService(ClienteNaturalRepository clienteNaturalRepository) {
        this.clienteNaturalRepository = clienteNaturalRepository;
    }

    public List<ClienteNatural> listarTodo() {
        log.info("Se va a obtener todos los clientes naturales");
        List<ClienteNatural> dtos = new ArrayList<>();
        for (ClienteNatural cliente : this.clienteNaturalRepository.findAll()) {
            if ("ACT".equals(cliente.getEstado())) {
            dtos.add(cliente);
            }
        }
        return dtos;
    }

    public ClienteNatural obtenerPorIdentificacion(String tipoIdentificacion, String numeroIdentificacion) {
        log.info("Se va a obtener cliente por TipoIdentificacion: {} y NumeroIdentificacion: {}", tipoIdentificacion,numeroIdentificacion);
        List<ClienteNatural> clientes = this.clienteNaturalRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
        if (clientes != null && !clientes.isEmpty()) {
            if ("ACT".equals(clientes.get(0).getEstado())) {
                log.debug("Cliente obtenido: {}", clientes.get(0));
                return clientes.get(0);
            } else {
                throw new RuntimeException("Cliente con TipoIdentificacion: " + tipoIdentificacion
                        + " y NumeroIdentificacion: " + numeroIdentificacion + " no se encuentra activo");
            }
        } else {
            throw new RuntimeException("No existe el cliente con TipoIdentificacion: " + tipoIdentificacion
                    + " y NumeroIdentificacion: " + numeroIdentificacion);
        }
    }

    public ClienteNatural obtenerPorId(String id) {
        log.info("Se va a obtener el cliente con ID: {}", id);
        ClienteNatural cliente = this.clienteNaturalRepository.findByIdCliente(id);
        if ("ACT".equals(cliente.getEstado())) {
            log.debug("Cliente obtenido: {}", cliente);
            return cliente;
        } else {
            throw new RuntimeException("Cliente con ID: " + id + " no se encuentra activo");
        }
    }
    
    @Transactional
    public void crear(ClienteNatural cliente) {
        try {
            cliente.setEstado("ACT");
            cliente.setIdCliente(new DigestUtils("MD2").digestAsHex(cliente.getTipoIdentificacion()+cliente.getNumeroIdentificacion()));
            log.debug("ID Cliente generado: {}", cliente.getIdCliente());
            cliente.setFechaCreacion(new Date());
            this.clienteNaturalRepository.save(cliente);
            log.info("Se creo el cliente: {}", cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el cliente.", e);
        }
    }

    @Transactional
    public void actualizar(ClienteNatural cliente) {
        try {
            ClienteNatural clienteAux = this.clienteNaturalRepository.findByIdCliente(cliente.getIdCliente());
            if ("ACT".equals(clienteAux.getEstado())) {
                cliente.setEstado("ACT");
                this.clienteNaturalRepository.save(cliente);
                log.info("Se actualizaron los datos del cliente: {}", cliente);
            } else {
                log.error("No se puede actualizar, Cliente: {} se encuentra INACTIVO", clienteAux);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el cliente.", e);
        }
    }

    @Transactional
    public void desactivar(String idCliente) {
        log.info("Se va a desactivar el cliente: {}", idCliente);
        try {
            ClienteNatural cliente = this.clienteNaturalRepository.findByIdCliente(idCliente);
            log.debug("Desactivando cliente: {}, estado: INA", idCliente);
            cliente.setEstado("INA");
            this.clienteNaturalRepository.save(cliente);
            log.info("Se desactivo el cliente: {}", idCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al desactivar cliente: " + idCliente, e);
        }
    }
}

