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
        
         public List<Inscripcion> obtenerInscripcionesPorAlumno(int idalumno){
        
            String sql = "SELECT * FROM inscripcion WHERE id_alumno = ?";
                    
            ArrayList<Inscripcion> cursada = new ArrayList<>();
            
            try {
                PreparedStatement ps = red.prepareStatement(sql);
                ps.setInt(1, idalumno);
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
         
//         public List<Materia> obtenerMateriasCursadas(Alumno alumno){
//         
////             ArrayList<Materia> materias = new ArrayList();
//             
//             String sql = "SELECT inscripcion.id_materia, materia.nombre_materia, materia.cuatrimestre, materia.nota " + 
//                                "FROM inscripcion " + 
//                                    "INNER JOIN materia ON inscripcion.id_materia = materia.id_materia " + 
//                                    "WHERE inscripcion.id_alumno = ?";
//             
//             try {
//                 PreparedStatement ps = red.prepareStatement(sql);
//                 ps.setInt(1, alumno.getId_alumno());
//                 ResultSet rs = ps.executeQuery();
//                 
//                 while(rs.next()){
//                 
//                     Materia m = new Materia();
//                     
//                     m.setId_materia(rs.getInt("id_materia"));
//                     m.setNombre_materia(rs.getString("nombre_materia"));
//                     m.setCuatrimestre(rs.getString("cuatrimestre"));
//                     
//                     materias.add(m);
//                     
//                 }
//                 ps.close();
//             } catch (SQLException e) {
//                 JOptionPane.showConfirmDialog(null, "Error al conectar a la tabla inscripcion");
//             }
//             return materias;
//         }
         
         public List<Inscripcion> obtenerMateriasCursadas(Alumno alumno) {
    ArrayList<Inscripcion> inscripciones = new ArrayList();

    
    String sql = "SELECT inscripcion.id_inscripcion, inscripcion.id_materia, materia.nombre_materia, materia.cuatrimestre, inscripcion.nota " +
                 "FROM inscripcion " +
                 "INNER JOIN materia ON inscripcion.id_materia = materia.id_materia " +
                 "WHERE inscripcion.id_alumno = ?";

    try {
        PreparedStatement ps = red.prepareStatement(sql);
        ps.setInt(1, alumno.getId_alumno());
        ResultSet rs = ps.executeQuery();

        
        while (rs.next()) {
            Materia materia = new Materia();
            materia.setId_materia(rs.getInt("id_materia"));
            materia.setNombre_materia(rs.getString("nombre_materia"));
            materia.setCuatrimestre(rs.getString("cuatrimestre"));

            double nota = rs.getDouble("nota");

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setId_inscripcion(rs.getInt("id_inscripcion"));
            inscripcion.setMateria(materia);
            inscripcion.setNota(nota);
            inscripcion.setAlumno(alumno);

            inscripciones.add(inscripcion);
        }
        ps.close();
    } catch (SQLException e) {
        JOptionPane.showConfirmDialog(null, "Error al conectar a la tabla inscripcion: " + e.getMessage());
    }

    return inscripciones;
}
         
         public List<Materia> obtenerMateriasNoCursadas(int idalumno){
             
             ArrayList<Materia> materias = new ArrayList();
             
             String sql = "SELECT * FROM materia WHERE estado = 1 AND id_materia not in " + "(SELECT id_materia FROM inscripcion WHERE id_alumno = ?)";
             
             try {
                 PreparedStatement ps = red.prepareStatement(sql);
                 ps.setInt(1, idalumno);
                 ResultSet rs = ps.executeQuery();
                 
                 while(rs.next()){
                 
                     Materia m = new Materia();
                     
                     m.setId_materia(rs.getInt("id_materia"));
                     m.setNombre_materia(rs.getString("nombre_materia"));
                     m.setCuatrimestre(rs.getString("cuatrimestre"));
                     materias.add(m);
                     
                 }
                 ps.close();
             } catch (SQLException e) {
                 JOptionPane.showConfirmDialog(null, "Error al conectar a la tabla inscripcion");
             }
             
             return materias;
         }
         
         public List<Alumno> obtenerAlumnosPorMateria(int idmateria){
             ArrayList<Alumno> alumnos = new ArrayList();
             
             String sql = "SELECT a.id_alumno, dni, apellido,nombre, fecha_nacimiento, estado " + "FROM inscripcion i, alumno a WHERE i.id_alumno = a.id_alumno AND id_materia = ?";
             
             try {
                  PreparedStatement ps = red.prepareCall(sql);
                  
                  ps.setInt(1, idmateria);
                  ResultSet rs = ps.executeQuery();
                  
                  while(rs.next()){
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
         
         public void borrarInscripcion(int idalumno, int idmateria){
             
             String sql = "DELATE FROM inscripcion WHERE id_alumno = ? AND id_materia = ?";
             
             try {
                 PreparedStatement ps = red.prepareStatement(sql);
                 ps.setInt(1, idalumno);
                 ps.setInt(2, idmateria);
                 
                 int i = ps.executeUpdate();
                 
                 if( i == 1){
                     JOptionPane.showMessageDialog(null, "Inscripcion borrada con exito!");
                 }
                 ps.close();
                 
                 
             } catch (SQLException e) {
                 
                 JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion!");
                 
             }
         }
}
        
    
    

