/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Materia;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author aguse
 */
public class materiaData {
    
    private Connection red = null;
    
    public materiaData(){
        red = conecxion.getConexion();
    }
    
    public void guardarMateria (Materia m){
        
    String sql = "INSERT INTO materia (id_materia, nombre_materia, cuatrimestre, estado) VALUES (?, ?, ?, ?)";
    
    try {
        PreparedStatement ps = red.prepareStatement(sql);
        
        ps.setInt(1, m.getId_materia());
        ps.setString(2, m.getNombre_materia());
        ps.setString(3, m.getCuatrimestre());
        ps.setBoolean(4, m.isEstado());
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Se añadió una nueva materia");
    
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
    }
}
    
    
    
   public void actualizarMateria (Materia materia){
   
       String sql ="UPDATE materia SET nombre_materia = ? , cuatrimestre = ? , estado = ? WHERE id_materia";
       
       try {
           PreparedStatement ps = red.prepareStatement(sql);
           
           ps.setString(1, materia.getNombre_materia());
           ps.setString(2, materia.getCuatrimestre());
           ps.setBoolean(3, true);
           
           int i =ps.executeUpdate();
           
           if( i == 1){
               JOptionPane.showMessageDialog(null, "Modificado exitosamente!");
           }else{
               JOptionPane.showMessageDialog(null, "La materia no existe!");
           
           }
           
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
           
       }
   }
   
   public void eliminarMateria (int id){
   String sql = "DELETE FROM materia WHERE id_materia = ?";
   
       try {
           PreparedStatement ps = red.prepareStatement(sql);
           
           ps.setInt(1, id);
           
           int i = ps.executeUpdate();
           
           if( i == 1){
               JOptionPane.showMessageDialog(null, "La materia se elimino con exito!");
           }else{
               JOptionPane.showMessageDialog(null, "No se encontro la materia!");
           }
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
                   
       }
   }
   
   public Materia buscarMateria(int id){
   
       Materia materia = null;
       
       String sql = "SELECT id_materia,nombre_materia, cuatrimestre, estado FROM materia WHERE id_materia = ?";
       
       try {
           PreparedStatement ps = red.prepareStatement(sql);
           ps.setInt(1, id);
           
           ResultSet rs = ps.executeQuery();
           
           if(rs.next()){
               materia = new Materia();
               materia.setId_materia(rs.getInt("id_materia"));
               materia.setNombre_materia(rs.getString("nombre_materia"));
               materia.setCuatrimestre(rs.getString("cuatrimestre"));
               materia.setEstado(rs.getBoolean("estado"));
               JOptionPane.showMessageDialog(null, "Materia encontrada");
           }else{
               JOptionPane.showMessageDialog(null, "No se encontro la materia");
           
           }
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
       }
       return materia;
   }
   
   public void bajaLogica(int id){
   
       String sql = "UPDATE materia SET estado = 0  WHERE id_materia = ?";
       
       try {
           PreparedStatement ps = red.prepareStatement(sql);
           
           ps.setInt(1, id);
           int i = ps.executeUpdate();
           
           if(i==1){
               JOptionPane.showMessageDialog(null, "La materia ha sido dada de baja");
           
           }
           
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
       }
   }
   
   public void altaLogica(int id){
   
       String sql = "UPDATE materia SET estado = 1  WHERE id_materia = ?";
       
       try {
           PreparedStatement ps = red.prepareStatement(sql);
           
           ps.setInt(1, id);
           int i = ps.executeUpdate();
           
           if(i==1){
               JOptionPane.showMessageDialog(null, "La materia ha sido dada de baja");
           
           }
           
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia");
       }
   }
    
}
