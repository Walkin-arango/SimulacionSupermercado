// Cajera.java
import javax.swing.JTextArea;

public class Cajera implements Runnable {
    private String nombre;
    private Cliente cliente;
    private JTextArea areaTexto;

    public Cajera(String nombre, Cliente cliente, JTextArea areaTexto) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.areaTexto = areaTexto;
    }

    public void agregarProductoAlCliente(Producto producto) {
        cliente.agregarProducto(producto);
        areaTexto.append("Producto: " + producto.getNombre() + " | Precio: $" + producto.getPrecio() + "\n");
    }

    @Override
    public void run() {
        procesarCompra();
    }

    private void procesarCompra() {
        areaTexto.append("Procesando compra para Cliente: " + cliente.getNombre() + "\n");
        cliente.getProductos().forEach(producto -> {
            areaTexto.append("Producto: " + producto.getNombre() + " | Precio: $" + producto.getPrecio() + "\n");
        });
        areaTexto.append("Total a pagar: $" + cliente.calcularTotal() + "\n\n");
    }
}
