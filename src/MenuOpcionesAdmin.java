import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Este menú de opciones permitirá al administrador navegar entre las opciones para
 * poder realizar las diferentes funcionalidades (agregar usuarios, gestionar el stock, etc)
 */
public class MenuOpcionesAdmin extends JFrame{
    private JPanel panel1;
    private JButton gestiónDeStockButton;
    private JButton historialDeTransaccionesButton;
    private JButton gestiónDeUsuariosButton;
    private JButton cerrarSesiónButton;
    private Login datosLogin;

    public MenuOpcionesAdmin(Login datosLogin) {
        super("Menú de Opciones (Administrador)");
        this.datosLogin = datosLogin;
        gestiónDeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionStockAdmin ventana = new GestionStockAdmin(MenuOpcionesAdmin.this,datosLogin.getConexion());
                ventana.IniciarTransaccion();
                dispose();
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login nuevoinicio = new Login();
                nuevoinicio.IniciarLogin();
                dispose();
            }
        });
        gestiónDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionUsuariosAdmin ventanaGesUs = new GestionUsuariosAdmin(MenuOpcionesAdmin.this,datosLogin.getConexion());
                ventanaGesUs.IniciarGestion();
                dispose();
            }
        });
        historialDeTransaccionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialTransaccionAdmin ventana = new HistorialTransaccionAdmin(MenuOpcionesAdmin.this, datosLogin.getConexion());
                ventana.IniciarHistorial();
                dispose();
            }
        });
    }
    public void IniciarMenu(){
        setContentPane(panel1);
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
