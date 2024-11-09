// Producto.java
public class Producto {
    private String nombre;
    private double precio;
    private int tiempoProceso;

    public Producto(String nombre, double precio, int tiempoProceso) {
        this.nombre = nombre;
        this.precio = precio;
        this.tiempoProceso = tiempoProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getTiempoProceso() {
        return tiempoProceso;
    }
}
