import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JTextField user;
    private JPasswordField password;
    private JButton iniciarSesiónButton;
    private JComboBox eleccion;
    private JPanel panel1;
    private JButton salirDelSistemaButton;

    public void IniciarLogin(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public Login() {
        super("Iniciar Sesión");
        setContentPane(panel1);
        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuOpcionesAdmin ventana2 = new MenuOpcionesAdmin();
                ventana2.IniciarMenu();
                dispose();
            }
        });
        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
