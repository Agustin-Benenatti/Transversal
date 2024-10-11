/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Inscripcion;
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
public class inscripcionData {
    
    private Connection red = null;
    
    public inscripcionData(){
    red = conecxion.getConexion();
    }
    
    public void guardarInscripcion(Inscripcion i){
         String sql = "INSERT INTO inscripcion(id_alumno, id_materia, nota) VALUES (?,?,?)";
         
         try {
            PreparedStatement ps = red.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, i.getAlumno().getId_alumno());
            ps.setInt(2, i.getMateria().getId_materia());
            ps.setDouble(3, i.getNota());
            
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                i.setId_inscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Se ha generado una nueva inscripcion!");
                
            }
            ps.close();
         }catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Error al conectar con la tabla inscripcion");
             } 
        }
      
    }
    

