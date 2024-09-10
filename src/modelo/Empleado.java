/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.*;
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
public class Empleado extends Persona {
    private int id_puesto;
    private int id_puestoAux;
    private int id_empleado;
    private String codigo;
    Conexion cn;
    
    public int getId_puestoAux() {
        return id_puestoAux;
    }

    public void setId_puestoAux(int id_puestoAux) {
        this.id_puestoAux = id_puestoAux;
    }
    
    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }
    
    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public void leerPuestoArr(){
       String[][] puesto = new String[3][2];
       int i = 0;
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "SELECT id_puesto as id,puesto from puestos;";
            ResultSet consulta =  cn.conexionBD.createStatement().executeQuery(query);
            
            while(consulta.next()){
                puesto[i][0] = consulta.getString("ID");
                puesto[i][1] = consulta.getString("PUESTO");
                i++;
            }
            id_puestoAux = Integer.parseInt(puesto[id_puesto][0]);
            
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        
    }
    
    public DefaultComboBoxModel leerPuesto(){
        DefaultComboBoxModel puesto = new DefaultComboBoxModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "SELECT id_puesto as id,puesto from puestos;";
            ResultSet consulta =  cn.conexionBD.createStatement().executeQuery(query);
            
            String lst_puesto;
            int i= 0;
            puesto.addElement(i+") "+"ELIJA PUESTO");
            while (consulta.next()) {
                i=i+1;
                lst_puesto = consulta.getString("PUESTO");
                puesto.addElement(i+") "+lst_puesto);
            }
            
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        return puesto;
    }

        public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "select em.id_empleados as id,em.codigo as codigo, em.nombres as nombres, em.apellidos as apellidos, em.direccion as direccion, em.telefono as telefono, em.fecha_nacimiento as fecha_nacimiento, ps.puesto as puesto from empleados em inner join puestos ps on ps.id_puesto = em.id_puesto order by em.id_empleados asc;";
            ResultSet consulta =  cn.conexionBD.createStatement().executeQuery(query);

            String encabezado[] = {"ID","CODIGO","NOMBRES","APELLIDOS","DIRECCION","TELEFONO","NACIMIENTO","PUESTO"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[8];
            while(consulta.next()){
                datos[0] = consulta.getString("ID");
                datos[1] = consulta.getString("CODIGO");
                datos[2] = consulta.getString("NOMBRES");
                datos[3] = consulta.getString("APELLIDOS");
                datos[4] = consulta.getString("DIRECCION");
                datos[5] = consulta.getString("TELEFONO");
                datos[6] = consulta.getString("FECHA_NACIMIENTO");
                datos[7] = consulta.getString("PUESTO");
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
        leerPuestoArr();
        try{
            PreparedStatement parametro;
            String query = "INSERT INTO empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto) VALUES (?,?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getCodigo());
            parametro.setString(2, this.getNombre());
            parametro.setString(3, this.getApellido());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFechanacimiento());
            parametro.setInt(7, this.getId_puestoAux());
            
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Ingresado","Agregar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    } 
    
    @Override
    public void actualizar(){
        leerPuestoArr();
        try{
            PreparedStatement parametro;
            String query = "update empleados set codigo = ?,nombres = ?,apellidos = ?,direccion = ?,telefono = ?,fecha_nacimiento = ?,id_puesto = ? where id_empleados = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getCodigo());
            parametro.setString(2, this.getNombre());
            parametro.setString(3, this.getApellido());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFechanacimiento());
            parametro.setInt(7, this.getId_puestoAux());
            parametro.setInt(8, this.getId_empleado());
            
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Actualizado","Actualizar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }        
    }
    
    public void eliminar(){
        try{
            PreparedStatement parametro;
            String query = "delete from empleados where id_empleados = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);

            parametro.setInt(1, this.getId_empleado());
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar)+ " Registro Eliminado","Eliminar", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    }
}
