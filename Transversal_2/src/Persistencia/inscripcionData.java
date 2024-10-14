/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Alumno;
import Modelo.Inscripcion;
import Modelo.Materia;
import Persistencia.alumnoData;
import Persistencia.conecxion;
import Persistencia.materiaData;
import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author aguse
 */
public class inscripcionData {
    
    private Connection red = null;
    
    private materiaData md = new materiaData();
    private alumnoData ad = new alumnoData();
    
    
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
      
        public List<Inscripcion> obtenerInscripciones(){
        
            String sql = "SELECT * FROM inscripcion";
                    
            ArrayList<Inscripcion> cursada = new ArrayList<>();
            
            try {
                PreparedStatement ps = red.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    
                    Inscripcion insc =new Inscripcion();
                    insc.setId_inscripcion(rs.getInt("id_inscripcion"));
                    Alumno alu = ad.buscarId(rs.getInt("id_alumno"));
                    Materia m = md.buscarMateria(rs.getInt("id_materia"));
                    insc.setAlumno(alu);
                    insc.setMateria(m);
                    insc.setNota(rs.getDouble("nota"));
                    
                    cursada.add(insc);
                    
                    
                }
                ps.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar con la tabla inscripcion");
            }
            return cursada;
            
        }
        
        public void actualizarNota(int id_alumno, int id_materia,double nota){
        
            String sql = "UPDATE inscripcion SET nota = ? WHERE id_alumno = ? AND id_materia = ?";
            try {
                PreparedStatement ps = red.prepareStatement(sql);
                ps.setDouble(1, nota);
                ps.setInt(2, id_alumno);
                ps.setInt(3, id_materia);
                
                int i =ps.executeUpdate();
                
                if(i == 1){
                    
                JOptionPane.showMessageDialog(null, "La nota ha sido actualizada");
                }
                ps.close();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al accceder a la tabla inscripcion");
            }
        }
}
        
    
    

