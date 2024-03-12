

public class Productos {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("id_producto")
    private String codigo;

    @Field("nombre")
    private String nombre;

    @Field("decripcion")
    private String descripcion;

    @Field("precio")
    private String precio;

    @Field("existencia")
    private String existencia;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Productos other = (Productos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
