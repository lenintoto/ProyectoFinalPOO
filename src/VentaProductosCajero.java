import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta clase se va a encargar de la venta de los productos
 * con la busqueda de los productos y la accion de agregar a un carito que
 * mas tarde va a ser procesada la compra.
 */
public class VentaProductosCajero extends JFrame {
    private JPanel panel1;
    private JButton IDProductoButton;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JButton nombreProductoButton;
    private JButton agregarButton;
    private JTable table1;
    private JTextField campo_insertar;
    private JButton volverAlMenúButton;
    private JLabel JLabel_Imagen;
    private JTable table2;
    private JRadioButton verProductosRadioButton;
    private DefaultTableModel modeloTabla1;
    private DefaultTableModel modeloTabla2;
    private MenuOpcionesCajero ventanaOpciones;
    Connection conexion;
    private String User;

    public VentaProductosCajero(MenuOpcionesCajero ventanaOpciones,Connection conexion,String User) {
        super("Venta de productos");
        this.ventanaOpciones=ventanaOpciones;
        this.conexion=conexion;
        this.User= User;
        setSize(510, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);

        modeloTabla1 = new DefaultTableModel();
        modeloTabla1.addColumn("ID");
        modeloTabla1.addColumn("Nombre");
        modeloTabla1.addColumn("Precio");
        modeloTabla1.addColumn("Stock");
        modeloTabla1.addRow(new Object[]{"ID", "Producto", "Precio", "Stock"});

        table1.setModel(modeloTabla1);


        modeloTabla2 = new DefaultTableModel();
        modeloTabla2.addColumn("ID");
        modeloTabla2.addColumn("Nombre");
        modeloTabla2.addColumn("Precio");
        modeloTabla2.addColumn("Cantidad");


        //odeloTabla2.addRow(new Object[]{"ID", "Producto", "Precio", "Stock"});

        table2.setModel(modeloTabla2);


        // Configurar botones de búsqueda
        IDProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorID();
            }
        });

        nombreProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorNombre();
            }
        });
        volverAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaOpciones.IniciarMenu();
                dispose();
            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeloTabla2.getRowCount() > 0) {
                    confirmarVenta();
                } else {
                    JOptionPane.showMessageDialog(VentaProductosCajero.this, "Agrega al menos un producto a la venta antes de confirmar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        verProductosRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTabla();
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAVenta();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarProductoAVenta();
            }
        });
    }

    public void iniciarVenta() {
        // Configurar la interfaz para la venta
        setContentPane(panel1);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Asignar la conexión a la base de datos

    }

    private void buscarProductoPorID() {
        try {
            // Consulta SQL para buscar productos por ID
            String consulta = "SELECT idProdu, nombre, precio, stock, Imagen FROM Productos WHERE idProdu = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, campo_insertar.getText());
            modeloTabla1.setRowCount(0);


            // Ejecutar la consulta
            ResultSet resultado = statement.executeQuery();

            // Agregar filas con los resultados de la búsqueda
            while (resultado.next()) {
                Object[] fila = {
                        resultado.getInt("idProdu"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"),
                        resultado.getInt("stock")
                };
                modeloTabla1.addRow(fila);
                byte[] imageData = resultado.getBytes("Imagen");
                if (imageData != null) {
                    // Crear un ImageIcon a partir de los datos de la imagen
                    ImageIcon imageIcon = new ImageIcon(imageData);

                    // Escalar la imagen al tamaño deseado (por ejemplo, 150x150 píxeles)
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

                    // Establecer el ImageIcon escalado en el JLabel
                    JLabel_Imagen.setIcon(scaledImageIcon);
                } else {
                    JOptionPane.showMessageDialog(this, "El producto no tiene una imagen asociada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            resultado.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar productos por ID");
        }
    }

    private void buscarProductoPorNombre() {
        try {
            // Consulta SQL para buscar productos por nombre
            String consulta = "SELECT idProdu, nombre, precio, stock, Imagen FROM Productos WHERE nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, campo_insertar.getText());

            // Limpiar la tabla antes de agregar nuevas filas
            modeloTabla1.setRowCount(0);

            // Ejecutar la consulta
            ResultSet resultado2 = statement.executeQuery();

            // Procesar los resultados y agregar filas con los resultados de la búsqueda
            while (resultado2.next()) {
                Object[] fila = {
                        resultado2.getInt("idProdu"),
                        resultado2.getString("nombre"),
                        resultado2.getDouble("precio"),
                        resultado2.getInt("stock")
                };
                modeloTabla1.addRow(fila);

                byte[] imageData = resultado2.getBytes("Imagen");
                if (imageData != null) {
                    // Crear un ImageIcon a partir de los datos de la imagen
                    ImageIcon imageIcon = new ImageIcon(imageData);

                    // Escalar la imagen al tamaño deseado (por ejemplo, 150x150 píxeles)
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

                    // Establecer el ImageIcon escalado en el JLabel
                    JLabel_Imagen.setIcon(scaledImageIcon);
                } else {
                    JOptionPane.showMessageDialog(this, "El producto no tiene una imagen asociada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultado2.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar productos por nombre");
        }
    }
    private void agregarProductoAVenta() {
        int filaSeleccionada = table1.getSelectedRow();

        // Verificar si se seleccionó una fila
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para agregar a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Obtener datos de la fila seleccionada directamente desde el modelo de la tabla1
            int id = (int) modeloTabla1.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla1.getValueAt(filaSeleccionada, 1);
            double precio = (double) modeloTabla1.getValueAt(filaSeleccionada, 2);
            int stock = (int) modeloTabla1.getValueAt(filaSeleccionada, 3);

            // Verificar si ya existe el producto en la tabla2
            boolean productoExistente = false;
            int filaExistente = -1;
            for (int i = 0; i < modeloTabla2.getRowCount(); i++) {
                if ((int) modeloTabla2.getValueAt(i, 0) == id) {
                    productoExistente = true;
                    filaExistente = i;
                    break;
                }
            }

            // Si el producto ya existe, incrementar la cantidad
            if (productoExistente) {
                int nuevaCantidad = (int) modeloTabla2.getValueAt(filaExistente, 3) + 1;
                modeloTabla2.setValueAt(nuevaCantidad, filaExistente, 3);
            } else {
                // Si no existe, agregar nueva fila con los datos del producto a la tabla2
                Object[] fila = {id, nombre, precio, 1};  // Asumiendo que siempre vendes una unidad por vez
                modeloTabla2.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EliminarProductoAVenta() {
        int filaSeleccionadaTabla2 = table2.getSelectedRow();

        // Verificar si se seleccionó una fila
        if (filaSeleccionadaTabla2 == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto al carrito para eliminarlo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {

            // Obtener datos de la fila seleccionada directamente desde el modelo de la tabla1
            int id = (int) modeloTabla2.getValueAt(filaSeleccionadaTabla2, 0);
            String nombre = (String) modeloTabla2.getValueAt(filaSeleccionadaTabla2, 1);
            double precio = (double) modeloTabla2.getValueAt(filaSeleccionadaTabla2, 2);
            int stock = (int) modeloTabla2.getValueAt(filaSeleccionadaTabla2, 3);

            // Verificar si hay suficiente stock
            if (stock > 0) {
                modeloTabla2.removeRow(filaSeleccionadaTabla2);
            } else {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock para eliminarlo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarStockEnBaseDeDatos(int idProducto, int nuevoStock) {
        try {
            // Actualizar el stock en la base de datos
            String consulta = "UPDATE Productos SET stock = ? WHERE idProdu = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, nuevoStock);
            statement.setInt(2, idProducto);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void mostrarTabla() {
        try (
                PreparedStatement statement = conexion.prepareStatement("SELECT idProdu,nombre,precio,stock FROM Productos");
                ResultSet resultSet = statement.executeQuery()) {

            // Crear el modelo de tabla con los nombres de las columnas
            DefaultTableModel tableModel = new DefaultTableModel(
                    new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);

            while (resultSet.next()) {
                int id = resultSet.getInt("idProdu");
                String nombre = resultSet.getString("nombre");
                String precio = resultSet.getString("precio");
                String stock = resultSet.getString("stock");




                // Agregar fila al modelo de tabla
                tableModel.addRow(new Object[]{id, nombre, precio,stock });
            }

            // Crear la tabla con el modelo de datos
            JTable tabla = new JTable(tableModel);

            // Crear el panel que contendrá la tabla
            JPanel panelTabla = new JPanel(new BorderLayout());
            panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

            // Crear el marco que contendrá el panel con la tabla
            JFrame frameTabla = new JFrame("Datos en Tabla");
            frameTabla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo la ventana de la tabla
            frameTabla.getContentPane().add(panelTabla);
            frameTabla.setSize(600, 400);
            frameTabla.setLocationRelativeTo(this);
            frameTabla.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener información de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void confirmarVenta() {
        try {
            // Obtener el ID y nombre del cajero actual
            int idCajero = obtenerIdCajeroActual();
            String nombreCajero = obtenerNombreCajeroActual();


            // Insertar cada producto vendido como una transacción en la tabla 'transacciones'
            for (int i = 0; i < modeloTabla2.getRowCount(); i++) {
                int idProducto = (int) modeloTabla2.getValueAt(i, 0);
                int cantidad = (int) modeloTabla2.getValueAt(i, 3);

                double totalProducto = (double) modeloTabla2.getValueAt(i, 2) * cantidad;

                // Insertar la transacción en la tabla 'transacciones'
                // Actualizar el stock en la base de datos

                 String consultar = "SELECT stock FROM Productos WHERE idProdu = ?";
                 PreparedStatement soli = conexion.prepareStatement(consultar);
                 soli.setInt(1,idProducto);
                 ResultSet resultado = soli.executeQuery();
                 if(resultado.next()){
                     int stock1 = resultado.getInt("stock");
                     if(stock1<cantidad){
                         JOptionPane.showMessageDialog(null,"Error no existe suficiente stock para este producto");
                     }else{
                         insertarTransaccion(idCajero, nombreCajero, idProducto, cantidad, totalProducto);
                         int nuevaCantidad = stock1 - cantidad;
                         actualizarStockEnBaseDeDatos(idProducto, nuevaCantidad);
                         JOptionPane.showMessageDialog(null,"Venta registrada");
                         // Limpiar la tabla2 después de confirmar la venta
                         modeloTabla2.setRowCount(0);
                     }
                 }
                 soli.close();
                 resultado.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al confirmar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerIdCajeroActual() throws SQLException {
        String consulta = "SELECT ID FROM Usuarios WHERE usuario = ?";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, User);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("ID");
        } else {
            throw new SQLException("No se encontró el ID del cajero actual.");
        }
    }

    private String obtenerNombreCajeroActual() throws SQLException {
        String consulta = "SELECT nombre FROM Usuarios WHERE usuario = ?";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, User);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("nombre");
        } else {
            throw new SQLException("No se encontró el nombre del cajero actual.");
        }
    }

    private void insertarTransaccion(int idCajero, String nombreCajero, int idProducto, int cantidad, double totalProducto) throws SQLException {
        String consulta = "INSERT INTO transacciones (FK_id_Cajero, nombre_cajero, idProducto, cantidad, totalProducto) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1, idCajero);
        statement.setString(2, nombreCajero);
        statement.setInt(3, idProducto);
        statement.setInt(4, cantidad);
        statement.setDouble(5, totalProducto);
        int resultado3=statement.executeUpdate();
    }
}
