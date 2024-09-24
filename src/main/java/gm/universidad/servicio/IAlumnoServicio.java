package gm.universidad.servicio;

import gm.universidad.modelo.Alumno;

import java.util.List;

public interface IAlumnoServicio {

    public List<Alumno> listarAlumnos();
    public Alumno buscarAlumnoPorId(Integer idAlumno);
    public void guardarAlumno (Alumno alumno);
    public void eliminarAlumno (Alumno alumno);
    boolean existeMatricula(Integer matricula);

}
