package com.banquito.core.clientes.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Productos")
@CompoundIndexes({
        @CompoundIndex(name = "idxu_codigo", def = "{'codigo': 1, 'codigo': 1}", unique = true)
})
public class Producto {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("id_producto")
    private String codigo;

    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @Field("precio")
    private String precio;

    @Field("existencia")
    private String existencia;
    
    
   


    

}
