/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author aguse
 */
public class Materia {
    
    private int id_materia;
    private String nombre_materia;
    private String cuatrimestre;
    private boolean estado;

    public Materia(int id_materia, String nombre_materia, String cuatrimestre, boolean estado) {
        this.id_materia = id_materia;
        this.nombre_materia = nombre_materia;
        this.cuatrimestre = cuatrimestre;
        this.estado = estado;
    }

    public Materia(String nombre_materia, String cuatrimestre, boolean estado) {
        this.nombre_materia = nombre_materia;
        this.cuatrimestre = cuatrimestre;
        this.estado = estado;
    }

    public Materia() {
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Materia{" + "id_materia=" + id_materia + ", nombre_materia=" + nombre_materia + ", cuatrimestre=" + cuatrimestre + ", estado=" + estado + '}';
    }
    
    
    
}
