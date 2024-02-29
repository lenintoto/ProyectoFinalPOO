import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistorialTransaccionAdmin extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JButton IDTransacciónButton;
    private JTable table1;
    private JButton IDCajeroButton;
    private JButton nombreCajeroButton;
    private JButton fechaTransacciónButton;
    private JButton regresarAlMenúButton;
    private JButton productoButton;

    public HistorialTransaccionAdmin() {
        super("Historial de Transacciones");
        regresarAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuOpcionesAdmin ventanaMenu = new MenuOpcionesAdmin();
                ventanaMenu.IniciarMenu();
                dispose();
            }
        });
    }
    public void IniciarHistorial(){
        setContentPane(panel1);
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
