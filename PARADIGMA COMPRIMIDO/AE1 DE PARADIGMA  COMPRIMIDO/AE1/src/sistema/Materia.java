/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package sistema;

public class Materia {

    private final String nombre;
    private final int curso;
    private final int cuatrimestre;
    private final Profesor profesor;

    public Materia(String nombre, int curso, int cuatrimestre, Profesor profesor) {
        this.nombre = nombre;
        this.curso = curso;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCurso() {
        return curso;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    @Override
    public String toString() {
        return nombre
                + " - Curso: " + curso
                + " - Cuatrimestre: " + cuatrimestre
                + " - Profesor: " + profesor;
    }
}
