/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lucas
 */
package sistema;
public class Inscripcion {
    private int inasistencias;
    private final SituacionFinal situacionfinal;
    private final Alumno alumno;
    private final Materia materia;
    
    public Inscripcion(Alumno alumno, Materia materia){
        this.inasistencias = 0;
        this.situacionfinal = new SituacionFinal();
        this.alumno = alumno;
        this.materia = materia;
    }
    public void registrarAsistencia(){
        System.out.println ("Asistencia registrada con éxito");
    }
    public void registrarInasistencia(){
        this.inasistencias++;
    }
    public void cargarSituacionFinal(String estadoElegido){
        this.situacionfinal.setEstado(estadoElegido);
    }
    public boolean estaFinalizada(){
        return this.situacionfinal.getEstado() != null;
    }
    public Alumno getAlumno(){
        return this.alumno;
    }
    public Materia getMateria(){
        return this.materia;
    }
    public int getInasistencias(){
        return this.inasistencias;
    }
    public String getEstadoFinal(){
        return this.situacionfinal.getEstado();
    }
}
