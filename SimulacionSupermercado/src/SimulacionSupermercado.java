// SimulacionSupermercado.java
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulacionSupermercado extends JFrame {
    private JTextArea areaTextoCajera1;
    private JTextArea areaTextoCajera2;
    private JTextField nombreCliente1;
    private JTextField nombreCliente2;
    private JTextField nombreProducto1;
    private JTextField precioProducto1;
    private JTextField nombreProducto2;
    private JTextField precioProducto2;
    private Cliente cliente1;
    private Cliente cliente2;
    private Cajera cajera1;
    private Cajera cajera2;
    private ExecutorService executor;

    public SimulacionSupermercado() {
        setTitle("Simulación de Cobro en Supermercado");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        // Panel para la cajera 1
        JPanel panelCajera1 = crearPanelCajera("Cajera 1", true);
        areaTextoCajera1 = new JTextArea();
        panelCajera1.add(new JScrollPane(areaTextoCajera1), BorderLayout.CENTER);

        // Panel para la cajera 2
        JPanel panelCajera2 = crearPanelCajera("Cajera 2", false);
        areaTextoCajera2 = new JTextArea();
        panelCajera2.add(new JScrollPane(areaTextoCajera2), BorderLayout.CENTER);

        add(panelCajera1);
        add(panelCajera2);

        executor = Executors.newFixedThreadPool(2);
    }

    private JPanel crearPanelCajera(String nombreCajera, boolean esCajera1) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(nombreCajera));

        JPanel formulario = new JPanel(new GridLayout(5, 2));

        formulario.add(new JLabel("Nombre Cliente:"));
        JTextField nombreCliente = new JTextField();
        formulario.add(nombreCliente);

        formulario.add(new JLabel("Producto:"));
        JTextField nombreProducto = new JTextField();
        formulario.add(nombreProducto);

        formulario.add(new JLabel("Precio:"));
        JTextField precioProducto = new JTextField();
        formulario.add(precioProducto);

        JButton agregarProductoBtn = new JButton("Agregar Producto");
        agregarProductoBtn.addActionListener(e -> {
            if (nombreCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre del cliente.");
                return;
            }
            if (nombreProducto.getText().isEmpty() || precioProducto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa nombre y precio del producto.");
                return;
            }
            String clienteNombre = nombreCliente.getText();
            Cliente cliente = esCajera1 ? cliente1 : cliente2;
            if (cliente == null || !cliente.getNombre().equals(clienteNombre)) {
                cliente = new Cliente(clienteNombre);
                if (esCajera1) {
                    cliente1 = cliente;
                    cajera1 = new Cajera(nombreCajera, cliente1, areaTextoCajera1);
                } else {
                    cliente2 = cliente;
                    cajera2 = new Cajera(nombreCajera, cliente2, areaTextoCajera2);
                }
            }
            double precio = Double.parseDouble(precioProducto.getText());
            Producto producto = new Producto(nombreProducto.getText(), precio, 500);
            if (esCajera1) {
                cajera1.agregarProductoAlCliente(producto);
            } else {
                cajera2.agregarProductoAlCliente(producto);
            }
            nombreProducto.setText("");
            precioProducto.setText("");
        });

        JButton procesarCompraBtn = new JButton("Procesar Compra");
        procesarCompraBtn.addActionListener(e -> {
            if (esCajera1 && cajera1 != null) {
                executor.submit(cajera1);
            } else if (cajera2 != null) {
                executor.submit(cajera2);
            }
        });

        formulario.add(agregarProductoBtn);
        formulario.add(procesarCompraBtn);

        panel.add(formulario, BorderLayout.NORTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulacionSupermercado ventana = new SimulacionSupermercado();
            ventana.setVisible(true);
        });
    }
}
