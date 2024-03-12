package ec.edu.espe.producto.dao;

import ec.edu.espe.producto.domain.Producto;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {

    Producto findById(String id);
    List<ClienteNatural> findByTipoIdentificacionAndNumeroIdentificacion(String tipoIdentificacion, String numeroIdentificacion);

}
