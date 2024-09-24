package gm.universidad.servicio;

import gm.universidad.modelo.Alumno;
import gm.universidad.repositorio.IAlumnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicio implements IAlumnoServicio {

    @Autowired
    private IAlumnoRepositorio iAlumnoRepositorio;


    @Override
    public List<Alumno> listarAlumnos() {
        List<Alumno> alumnos = iAlumnoRepositorio.findAll();
        return alumnos;
    }

    @Override
    public Alumno buscarAlumnoPorId(Integer idAlumno) {
        Alumno alumno = iAlumnoRepositorio.findById(idAlumno).orElse(null);
        return alumno;
    }

    @Override
    public void guardarAlumno(Alumno alumno) {
        iAlumnoRepositorio.save(alumno);

    }

    @Override
    public void eliminarAlumno(Alumno alumno) {
        iAlumnoRepositorio.delete(alumno);

    }

    @Override
    public boolean existeMatricula(Integer matricula) {
        return iAlumnoRepositorio.findByMatricula(matricula).isPresent(); // o el equivalente en tu caso
    }
}
