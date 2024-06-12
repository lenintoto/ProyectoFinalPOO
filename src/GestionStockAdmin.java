import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La presente clase tiene por objetivo gestionar el stock de la tienda
 * por lo que hay diferentes funcionalidades tanto de consulta como de
 * agregar productos
 *
 * En el metodo constructor se encuentran los botones que van a recolectar
 * la información para poder manejar el respectivo stock
 */
public class GestionStockAdmin extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton buscarButton;
    private JTextField producBuscar;
    private JButton actualizarPrecioButton;
    private JButton actualizarNombreButton;
    private JButton actualizarStockButton;
    private JTable tablaProduc;
    private JButton agregarProductoButton;
    private JTextField nombrePro;
    private JTextField precioPro;
    private JTextField stockPro;
    private JButton volverAlMenúButton;
    private JButton fotoProductoButton;
    private JTextField nuevoStock;
    private JTextField nuevoNombre;
    private JTextField nuevoPrecio;
    private byte[] imagenData;
    private MenuOpcionesAdmin datosMenuOp;
    private String nombreUser;
    Connection conexion;

    public String getNombreUser() {
        return nombreUser;
    }

    public JTextField getNombrePro() {
        return nombrePro;
    }

    public GestionStockAdmin(MenuOpcionesAdmin datosMenuOp, Connection conexion){
        super("Gestión del Stock de los Productos");
        this.datosMenuOp = datosMenuOp;
        this.conexion=conexion;
        String nombre1 = String.valueOf(getNombrePro());
        // Configura las columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");
        tablaProduc.setModel(model);
        volverAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datosMenuOp.IniciarMenu();
                dispose();
            }
        });
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PreparedStatement statement = null;
                try {
                    String sql = "INSERT INTO Productos (nombre, precio, stock, Imagen) VALUES (?,?,?,?)";
                    statement = conexion.prepareStatement(sql);
                    statement.setString(1, nombrePro.getText());
                    statement.setFloat(2, Float.parseFloat(precioPro.getText()));
                    statement.setInt(3, Integer.parseInt(stockPro.getText()));
                    statement.setBytes(4, imagenData);

                    int resultado = statement.executeUpdate();
                    if(resultado>0){
                        JOptionPane.showMessageDialog(null, "Producto agregado");
                        nombrePro.setText("");
                        precioPro.setText("");
                        stockPro.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null,"Problemas al agregar el producto");
                    }
                }catch (SQLException error){
                    JOptionPane.showMessageDialog(null,"Error en la conexión"+error.getMessage());
                }
            }
        });
        fotoProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subirImagen();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        actualizarStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement statement = null;
                try {
                    String sql = "UPDATE Productos SET stock = ? WHERE nombre = ?";
                    statement = conexion.prepareStatement(sql);
                    statement.setInt(1, Integer.parseInt(nuevoStock.getText()));
                    statement.setString(2, producBuscar.getText());
                    // Ejecutar la actualización
                    int filasActualizadas = statement.executeUpdate();

                    // Verificar si se realizaron actualizaciones y mostrar un mensaje correspondiente
                    if (filasActualizadas > 0) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se actualizo");
                    }

                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar datos en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        actualizarNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement statement = null;
                try {
                    String sql = "UPDATE Productos SET nombre = ? WHERE nombre = ?";
                    statement = conexion.prepareStatement(sql);
                    statement.setString(1, nuevoNombre.getText());
                    statement.setString(2, producBuscar.getText());
                    // Ejecutar la actualización
                    int filasActualizadas = statement.executeUpdate();

                    // Verificar si se realizaron actualizaciones y mostrar un mensaje correspondiente
                    if (filasActualizadas > 0) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se actualizo");
                    }

                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar datos en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        actualizarPrecioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement statement = null;
                try {
                    String sql = "UPDATE Productos SET precio = ? WHERE nombre = ?";
                    statement = conexion.prepareStatement(sql);
                    statement.setFloat(1, Float.parseFloat(nuevoPrecio.getText()));
                    statement.setString(2, producBuscar.getText());
                    // Ejecutar la actualización
                    int filasActualizadas = statement.executeUpdate();

                    // Verificar si se realizaron actualizaciones y mostrar un mensaje correspondiente
                    if (filasActualizadas > 0) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se actualizo");
                    }

                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar datos en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void IniciarTransaccion(){
        setContentPane(panel1);
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public void subirImagen(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Leer la imagen como un arreglo de bytes
                FileInputStream fis = new FileInputStream(selectedFile);
                imagenData = fis.readAllBytes();
                fis.close();

                // Mostrar un diálogo de confirmación
                int option = JOptionPane.showConfirmDialog(null, "¿Desea guardar esta imagen?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Imagen subida con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Imagen no guardada");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al subir la imagen: " + ex.getMessage());
            }
        }
    }
    private void buscarProducto() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT nombre, precio, stock FROM Productos WHERE nombre = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, producBuscar.getText());
            resultSet = statement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Stock");

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                float precio = resultSet.getFloat("precio");
                int stock = resultSet.getInt("stock");

                model.addRow(new Object[]{nombre, precio, stock});
            }

            tablaProduc.setModel(model);

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base" + error.getMessage());
        }
    }
}


