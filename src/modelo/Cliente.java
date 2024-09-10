/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Juanjo SR
 */
public class Cliente extends Persona{
    private int id;
    private String nit;
    Conexion cn;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
   
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "SELECT idclientes as id,nit,nombres,apellidos,direccion,telefono,fecha_nacimiento from clientes;";
            ResultSet consulta =  cn.conexionBD.createStatement().executeQuery(query);
            
            String encabezado[] = {"ID","NIT","NOMBRES","APELLIDOS","DIRECCION","TELEFONO","NACIMIENTO"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[7];
            while(consulta.next()){
                datos[0] = consulta.getString("ID");
                datos[1] = consulta.getString("NIT");
                datos[2] = consulta.getString("NOMBRES");
                datos[3] = consulta.getString("APELLIDOS");
                datos[4] = consulta.getString("DIRECCION");
                datos[5] = consulta.getString("TELEFONO");
                datos[6] = consulta.getString("FECHA_NACIMIENTO");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public void agregar(){
        try{
            PreparedStatement parametro;
            String query = "INSERT INTO clientes(nit,nombres,apellidos,direccion,telefono,fecha_nacimiento) VALUES (?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getNit());
            parametro.setString(2, this.getNombre());
            parametro.setString(3, this.getApellido());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFechanacimiento());
            
            
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Ingresado","Agregar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    } 
    public void actualizar(){
        try{
            PreparedStatement parametro;
            String query = "update clientes set nit = ?,nombres = ?,apellidos = ?,direccion = ?,telefono = ?,fecha_nacimiento = ? where idclientes = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getNit());
            parametro.setString(2, this.getNombre());
            parametro.setString(3, this.getApellido());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFechanacimiento());
            parametro.setInt(7, this.getId());
            
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Ingresado","Agregar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }        
    }
    public void eliminar(){
        try{
            PreparedStatement parametro;
            String query = "delete from clientes where idclientes = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);

            parametro.setInt(1, this.getId());
            
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Eliminado","Agregar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    }


}
