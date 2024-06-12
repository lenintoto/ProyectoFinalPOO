import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * En esta clase el Cajero podrá hacer uso de las consultas para
 * imprimir sus ventas realizadas a los clientes
 */
public class HistorialTransaccionCajero extends JFrame {
    private JButton imprimirButton;
    private JButton regresarAlMenúButton;
    private JPanel panel_historial;
    private JTextArea idtra;
    private JTextArea nombre;
    private JTextArea fecha;
    private JTextArea produ;
    private JTextArea cantidad;
    private JTextArea total;

    private MenuOpcionesCajero datosmenucajero;
    Connection conexion;
    private String usuario;

    public HistorialTransaccionCajero( MenuOpcionesCajero datosmenucajero, Connection conexion, String usuario){
    super("Historial de transacciones");
    this.datosmenucajero=datosmenucajero;
    this.conexion=conexion;
    this.usuario=usuario;
    setSize(900,600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel_historial);

        try {
            String consultar_id = "SELECT ID FROM Usuarios WHERE usuario=?";
            PreparedStatement consulta3 = conexion.prepareStatement(consultar_id);
            consulta3.setString(1, usuario);
            ResultSet resultado3 = consulta3.executeQuery();

            if (resultado3.next()) {
                int id_obtenido = resultado3.getInt("ID");

                String consultar_transacciones = "SELECT idTra,nombre_cajero,fecha,idProducto,cantidad,totalProducto FROM transacciones WHERE FK_id_Cajero=?";
                PreparedStatement consulta4 = conexion.prepareStatement(consultar_transacciones);
                consulta4.setInt(1, id_obtenido);
                ResultSet resultadoTransacciones = consulta4.executeQuery();

                while (resultadoTransacciones.next()) {
                    idtra.append(resultadoTransacciones.getString("idTra")+"\n");
                    nombre.append(resultadoTransacciones.getString("nombre_cajero")+"\n");

                    fecha.append(resultadoTransacciones.getString("fecha") + "\n");
                    produ.append(resultadoTransacciones.getString("idProducto") + "\n");
                    cantidad.append(resultadoTransacciones.getString("cantidad") + "\n");
                    total.append(resultadoTransacciones.getString("totalProducto") + "\n");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error al encontrar el ID");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + ex.getMessage());
        }
        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarPDF();
            }
        });
        regresarAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                datosmenucajero.IniciarMenu();
                dispose();
            }
        });
    }
    private void generarPDF() {
        // Crear un objeto PageFormat
        PageFormat pageFormat = new PageFormat();
        // Establecer la orientación de la página
        pageFormat.setOrientation(PageFormat.LANDSCAPE);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Títulos de los campos
                g2d.drawString("Historial de transacciones", 100, 100);
                g2d.drawString("Fechas y horas:", 5, 120);
                g2d.drawString("IDs de transacción:", 130, 120); // Alinear con la columna de fechas
                g2d.drawString("Nombre Cajero:", 250, 120); // Alinear con la columna de IDs de transacción
                g2d.drawString("Producto:", 350, 120); // Alinear con la columna de tipos de transacción
                g2d.drawString("Cantidad:", 450, 120);
                g2d.drawString("Total:", 550, 120);
                // Contenido de los campos
                String[] fechas = fecha.getText().split("\n");
                String[] idsTransaccion = idtra.getText().split("\n");
                String[] nombrecajero = nombre.getText().split("\n");
                String[] producto = produ.getText().split("\n");
                String[]  cantidadp = cantidad.getText().split("\n");
                String[] totalp = total.getText().split("\n");

                int yPos = 140; // Posición inicial Y para las fechas
                for (int i = 0; i < fechas.length; i++) {
                    g2d.drawString(fechas[i], 5, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < idsTransaccion.length; i++) {
                    g2d.drawString(idsTransaccion[i], 130, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < nombrecajero.length; i++) {
                    g2d.drawString(nombrecajero[i], 250, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < producto.length; i++) {
                    g2d.drawString(producto[i], 350, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }
                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < cantidadp.length; i++) {
                    g2d.drawString(cantidadp[i], 450, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }
                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < totalp.length; i++) {
                    g2d.drawString(totalp[i], 550, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                return Printable.PAGE_EXISTS;
            }
        }, pageFormat);

        try {
            job.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir: " + e.getMessage());
        }
    }
    public void IniciarHistorial() {
        setContentPane(panel_historial);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}




