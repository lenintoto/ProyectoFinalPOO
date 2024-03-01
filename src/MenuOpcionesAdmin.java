import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuOpcionesAdmin extends JFrame{
    private JPanel panel1;
    private JButton gestiónDeStockButton;
    private JButton historialDeTransaccionesButton;
    private JButton gestiónDeUsuariosButton;
    private JButton cerrarSesiónButton;

    public MenuOpcionesAdmin() {
        super("Menú de Opciones (Administrador)");
        gestiónDeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionStockAdmin ventana = new GestionStockAdmin();
                ventana.IniciarTransaccion();
                dispose();
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login ventanaLogin = new Login();
                ventanaLogin.IniciarLogin();
                dispose();
            }
        });
        gestiónDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionUsuariosAdmin ventanaGesUs = new GestionUsuariosAdmin();
                ventanaGesUs.IniciarGestion();
                dispose();
            }
        });
        historialDeTransaccionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistorialTransaccionAdmin ventana = new HistorialTransaccionAdmin();
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
