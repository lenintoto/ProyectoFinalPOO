import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * en esta clase se procedió a hacer la conexión con la base de datos ya se de manera local
 * o en la nube
 */
public class ConexionDB {
    //Funcion para establecer la conexion con la base de datos
    public static Connection conectar(){
        String url = "jdbc:mysql://ukud0rhkbotxad3e:gl0uERVOwId0SFsZnbxI@b9kesxzshx0znsteocxi-mysql.services.clever-cloud.com:3306/b9kesxzshx0znsteocxi";
        String usuarioDB = "ukud0rhkbotxad3e";
        String contrasenaBD = "gl0uERVOwId0SFsZnbxI";

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(url, usuarioDB, contrasenaBD);
            //JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos.");

        }catch (SQLException error){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + error.getMessage());
        }
        return conexion;
    }
}
