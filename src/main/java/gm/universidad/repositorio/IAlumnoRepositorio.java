package gm.universidad.repositorio;

import gm.universidad.modelo.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAlumnoRepositorio extends JpaRepository<Alumno, Integer> {
    Optional<Alumno> findByMatricula(Integer matricula);

}
