/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Juanjo SR
 */
public class Conexion {
    public Connection conexionBD;
    public final String bd = "db_empresa";
    public final String urlConexion = String.format("jdbc:mysql://localhost:3306/%s",bd);
    public final String user = "usr_empresa";
    public final String pass = "Empres@123";
    public final String jdbc = "com.mysql.cj.jdbc.Driver";
    
    public void abrir_conexion (){
        try{
            Class.forName(jdbc);
            conexionBD = DriverManager.getConnection(urlConexion, user, pass);
            //JOptionPane.showMessageDialog(null, "conexion exitosa...", "Exito",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            System.out.println("Error..."+ ex.getMessage()); 
        }
    }
    public void cerrar_conexion(){
        try{
            conexionBD.close();
        }catch(SQLException ex){
            System.out.println("Error..."+ ex.getMessage()); 
        }
    }
}
