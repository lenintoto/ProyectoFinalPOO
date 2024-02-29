import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionUsuariosAdmin extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton agregarUsuarioButton;
    private JButton salirAlMenúButton;
    private JButton limpiarCamposButton;

    public GestionUsuariosAdmin(){
        super("Gestión de Usuarios (Administrador)");
        salirAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuOpcionesAdmin ventana2 = new MenuOpcionesAdmin();
                ventana2.IniciarMenu();
                dispose();
            }
        });
    }
    public void IniciarGestion(){
        setContentPane(panel1);
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
