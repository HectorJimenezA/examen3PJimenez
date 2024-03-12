public class Venta {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("fecha_venta")
    private String fechaVenta;

    @Field("codigo_unico")
    private String codigoUnico;

    @Field("nombre")
    private String nombre;

    @Field("precio_unitario")
    private String precioUnitario;

    @Field("cantidad")
    private String cantidad;

    @Field("valor_total")
    private String valorTotal;

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
        Venta other = (Venta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
