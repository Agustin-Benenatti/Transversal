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
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author aguse
 */
public class alumnoData {
    
    private Connection red = null;
    
    public alumnoData() {
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
        
        String sql = "UPDATE alumno SET  apellido = ? , nombre = ? ,fecha_nacimiento = ?,estado = ? WHERE dni = ?";
        
        try {
            PreparedStatement ps = red.prepareStatement(sql);
            ps.setString(1, alumno.getApellido());
            ps.setString(2, alumno.getNombre());
            ps.setDate(3, Date.valueOf(alumno.getFecha_nacimiento()));
            ps.setBoolean(4, alumno.isEstado());
            ps.setInt(5, alumno.getDni());
            
            int i = ps.executeUpdate();
            
            if( i == 1){
//                JOptionPane.showMessageDialog(null, "Se han modificado con exito  los datos del alumno.");
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
                //JOptionPane.showMessageDialog(null, "No se encontro el alumno.");
            }
                    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla alumnos.");
        }
    return alumno;
    }
    
    public Alumno buscarId (int id){
      
        
          String sql="SELECT dni, apellido, nombre, fecha_nacimiento FROM alumno WHERE id_alumno = ? AND estado = 1";

        Alumno alumno= null;
        try {
            PreparedStatement ps=red.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                alumno=new Alumno();
                alumno.setId_alumno(id);
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(true);


            }
            ps.close();

        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error al acceder a la tabla  alumno ");
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
    
    public List<Alumno> listaDeAlumnos(){
        String sql = "SELECT id_alumno, dni, apellido, nombre, fecha_nacimiento, estado FROM alumno ";

        ArrayList<Alumno> estudiantes = new ArrayList<>();

        try {
            PreparedStatement ps = red.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                Alumno alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));

                estudiantes.add(alumno);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno");
        }
        return estudiantes;
    }
     public List<Alumno> obtenerAlumnosPorMateria(int idMateria) {
        ArrayList<Alumno> alumnos = new ArrayList<>();

        String sql = "SELECT a.id_alumno, dni, apellido, nombre, fecha_nacimiento, estado " +
                     "FROM inscripcion i, alumno a WHERE i.id_alumno = a.id_alumno AND id_materia = ?";
        
        try {
            PreparedStatement ps = red.prepareCall(sql);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Alumno a = new Alumno();
                a.setId_alumno(rs.getInt("id_alumno"));
                a.setDni(rs.getInt("dni"));
                a.setApellido(rs.getString("apellido"));
                a.setNombre(rs.getString("nombre"));
                a.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                a.setEstado(rs.getBoolean("estado"));

                alumnos.add(a);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Error al conectar a la tabla inscripcion");
        }
        return alumnos;    
    }
}
    

