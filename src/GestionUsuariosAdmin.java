import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * En esta clase se va a proceder con lo que es la gestion de
 * usuario, (Agregar usuarios) vale recalcar que esta funcionalidad
 * solo la va a tener el rol de usuario para evitar confusiones y que los cajeros
 * no manejen esta funcionalidad
 */
public class GestionUsuariosAdmin extends JFrame{
    private JPanel panel1;
    private JTextField nombreX;
    private JTextField usuarioX;
    private JTextField contraX;
    private JTextField contraX2;
    private JComboBox modoX;
    private JButton agregarUsuarioButton;
    private JButton salirAlMenúButton;
    private JButton limpiarCamposButton;
    private Connection conexion;
    private MenuOpcionesAdmin datosMenuOp;

    public GestionUsuariosAdmin(MenuOpcionesAdmin datosMenuOp, Connection conexion){
        super("Gestión de Usuarios (Administrador)");
        this.datosMenuOp = datosMenuOp;
        this.conexion=conexion;
        salirAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datosMenuOp.IniciarMenu();
                dispose();
            }
        });
        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombreX.setText("");
                usuarioX.setText("");
                contraX.setText("");
                contraX2.setText("");
                modoX.setSelectedIndex(0);
                modoX.getSelectedIndex();
            }
        });
        agregarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PreparedStatement statement = null;
                try {

                    String sql = "INSERT INTO Usuarios (nombre, usuario, contrasenia, tipo) VALUES (?,?,?,?)";
                    statement = conexion.prepareStatement(sql);
                    statement.setString(1, nombreX.getText());
                    statement.setString(2, usuarioX.getText());
                    statement.setString(3, contraX.getText());
                    statement.setString(4, String.valueOf(modoX.getSelectedIndex()));

                    int resultado = statement.executeUpdate();
                    if(resultado>0){
                        JOptionPane.showMessageDialog(null, "Usuario agregado");
                    }else{
                        JOptionPane.showMessageDialog(null,"Problemas al agregar el usuario");
                    }
                }catch (SQLException error){
                    JOptionPane.showMessageDialog(null,"Error en la conexion "+error.getMessage());
                }
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

