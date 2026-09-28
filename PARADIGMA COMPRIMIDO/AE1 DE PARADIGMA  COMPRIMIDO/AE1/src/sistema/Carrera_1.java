/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package sistema;

import java.util.ArrayList;
import java.util.List;

public class Carrera {

    private final String nombre;
    private final int duracion;
    private final Coordinador coordinador;
    private final double precioInscripcion;
    private final double precioCuota;

    private final List<Materia> materias;
    private final List<Alumno> alumnos;

    public Carrera(String nombre,
                   int duracion,
                   Coordinador coordinador,
                   double precioInscripcion,
                   double precioCuota) {

        this.nombre = nombre;
        this.duracion = duracion;
        this.coordinador = coordinador;
        this.precioInscripcion = precioInscripcion;
        this.precioCuota = precioCuota;

        this.materias = new ArrayList<>();
        this.alumnos = new ArrayList<>();
    }

    public boolean agregarMateria(Materia materia) {

        if (materia == null) {
            return false;
        }

        for (Materia m : materias) {
            if (m.getNombre().equalsIgnoreCase(materia.getNombre())) {
                return false;
            }
        }

        materias.add(materia);
        return true;
    }

    public boolean matricularAlumno(Alumno alumno) {

        if (alumno == null) {
            return false;
        }

        for (Alumno a : alumnos) {
            if (a.getLegajo() == alumno.getLegajo()) {
                return false;
            }
        }

        alumnos.add(alumno);
        return true;
    }

    public boolean tieneMateria(Materia materia) {
        return materias.contains(materia);
    }

    public boolean estaMatriculado(Alumno alumno) {

        for (Alumno a : alumnos) {
            if (a.getLegajo() == alumno.getLegajo()) {
                return true;
            }
        }

        return false;
    }

    public Materia buscarMateria(String nombreMateria) {

        for (Materia materia : materias) {

            if (materia.getNombre().equalsIgnoreCase(nombreMateria)) {
                return materia;
            }
        }

        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public double getPrecioInscripcion() {
        return precioInscripcion;
    }

    public double getPrecioCuota() {
        return precioCuota;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public String toString() {
        return nombre
                + " - Duracion: " + duracion + " años"
                + " - Coordinador: " + coordinador;
    }
}
