import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionStockAdmin extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton buscarButton;
    private JTextField textField1;
    private JButton actualizarPrecioButton;
    private JButton actualizarNombreButton;
    private JButton actualizarStockButton;
    private JTable table1;
    private JButton agregarProductoButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton volverAlMenúButton;

    public GestionStockAdmin(){
        super("Gestión del Stock de los Productos");
        volverAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuOpcionesAdmin ventana2 = new MenuOpcionesAdmin();
                ventana2.IniciarMenu();
                dispose();
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
}
