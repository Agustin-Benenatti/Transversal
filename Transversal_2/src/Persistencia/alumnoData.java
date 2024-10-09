/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Alumno;
import java.sql.SQLException;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;



/**
 *
 * @author aguse
 */
public class alumnoData {
    
    private Connection red = null;
    
    public alumnoData() throws SQLException{
        red = conecxion.getConexion();
    }
    
    public void guardar(Alumno a){
        
        String g = "INSERT INTO alumno(dni, apellido, nombre, fecha_nacimiento, estado) VALUES (?,?,?,?,?)";
        
        try{
            PreparedStatement ps = red.prepareStatement(g,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, a.getDni());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getNombre());
            ps.setDate(4, Date.valueOf(a.getFecha_nacimiento()));
            ps.setBoolean(5, a.isEstado());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            if( rs.next()){
                a.setId_alumno(rs.getInt(1));
                
                JOptionPane.showMessageDialog(null, "Se ha añadido un nuevo alumno.");
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno");
        }
    }
    
    public void actualizarAlumno (Alumno alumno){
        
        String sql = "UPDATE alumno SET  apellido= ? , nombre= ? ,fecha_nacimiento= ?,estado= ? WHERE dni ?";
        
        try {
            PreparedStatement ps = red.prepareStatement(sql);
            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFecha_nacimiento()));
            ps.setBoolean(4, true);
            ps.setInt(5, alumno.getDni());
            
            int i = ps.executeUpdate();
            
            if( i == 1){
                JOptionPane.showMessageDialog(null, "Se han modificado con exito  los datos del alumno.");
            }
          
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla alumnos");
        }
        
    }
    
    public Alumno buscarDni (int dni){
        Alumno alumno =null;
        
        String buscar = "SELECT  apellido, nombre, fecha_nacimiento, estado FROM alumno WHERE dni = ?";
        
        try {
            PreparedStatement ps = red.prepareStatement(buscar);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                    alumno = new Alumno();
                    alumno.setApellido(rs.getString("apellido"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    alumno.setEstado(rs.getBoolean("estado"));
            }else{
                JOptionPane.showMessageDialog(null, "No se encontro el alumno.");
            }
                    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla alumnos.");
        }
    return alumno;
    }
    
    public void bajaLogica (int dni){
    
        String sql = "UPDATE alumno SET estado= 0 WHERE dni = ? ";
        
        try {
            PreparedStatement ps = red.prepareStatement(sql);
            
            ps.setInt(1, dni);
            int i = ps.executeUpdate();
            if(i ==1){
                JOptionPane.showMessageDialog(null, "Se ha dado de baja al alumno.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla alumnos.");
            
        }
    }
    
    public void altaLogica (int dni){
        String sql = "UPDATE alumno SET estado= 1 WHERE dni = ?";
        
        try {
            PreparedStatement ps = red.prepareStatement(sql);
            
            ps.setInt(1, dni);
            int i = ps.executeUpdate();
            
            if( i == 1){
                JOptionPane.showMessageDialog(null, "El alumno ha sido dado de alta.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla alumnos.");
        }
    }
    
    public void eliminarAlumno (int dni){
        
        String sql = "DELETE FROM alumno WHERE dni = ?";
        
        try {
            PreparedStatement ps = red.prepareStatement(sql);
            
            ps.setInt(1, dni);
            int i = ps.executeUpdate();
            
            if( i ==1){
            JOptionPane.showMessageDialog(null, "El alumno ha sido eliminado.");
            }else{
                JOptionPane.showMessageDialog(null, "El alumno no se encuentra en la tabla. "+dni);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla.");
        }
    
    }
    
    
    
}
