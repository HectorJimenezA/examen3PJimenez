package ec.edu.espe.producto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Field("fechaVenta")
    private String fechaVenta;

    @Field("nombreProducto")
    private String NombreProducto;

    @Field("precioUnitario")
    private String precioUnitario;

    @Field("cantidad")
    private String Cantidad;

    @Field("valorTotal")
    private String valorTotal;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
    
    
   


    

}
