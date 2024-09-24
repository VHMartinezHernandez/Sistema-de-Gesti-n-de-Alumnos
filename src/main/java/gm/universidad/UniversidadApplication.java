package gm.universidad;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import gm.universidad.modelo.Alumno;
import gm.universidad.servicio.IAlumnoServicio;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class UniversidadApplication implements CommandLineRunner {

	@Autowired
	private IAlumnoServicio iAlumnoServicio;

	private static final Logger logger = LoggerFactory.getLogger(UniversidadApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la Aplicacion");
		// Levantar la fabrica de spring
		SpringApplication.run(UniversidadApplication.class, args);
		logger.info("Aplicacion finalizada!");
	}

	@Override
	public void run(String... args) throws Exception {
		universidadApp();
	}

	private void universidadApp(){
		var salir = false;
		var consola = new Scanner(System.in);
		while(!salir){
			var opcion = mostraMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private int mostraMenu(Scanner consola){
		logger.info("""
			\n*** Aplicacion Universidad ***
			1. Listar Clientes
			2. Buscar Cliente
			3. Agregar Cliente
			4. Modificar Cliente
			5. Eliminar Cliente
			6. Salir
		""");
		System.out.print("Elige una opcion: ");
		return Integer.parseInt(consola.nextLine());
	}


	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch (opcion) {
			case 1: {
				logger.info(nl + "--- Listado de Clientes ---" + nl);
				List<Alumno> clientes = iAlumnoServicio.listarAlumnos();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
				break;
			}
			case 2: {
				logger.info(nl + "--- Buscar Cliente por Id ---" + nl);
				logger.info("Id Cliente a buscar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Alumno cliente = iAlumnoServicio.buscarAlumnoPorId(idCliente);
				if (cliente != null)
					logger.info("Cliente encontrado: " + cliente + nl);
				else
					logger.info("Cliente NO encontrado" + nl);
				break;
			}

			case 3 : {
				logger.info("--- Agregar Cliente ---" + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Matricula: ");
				var matricula = Integer.parseInt(consola.nextLine());
				var cliente = new Alumno();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMatricula(matricula);
				iAlumnoServicio.guardarAlumno(cliente);
				logger.info("Cliente agregado: " + cliente + nl);
				break;
			}
			case 4 : {
				logger.info("--- Modificar Cliente ---" + nl);
				logger.info("Id Cliente: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Alumno cliente = iAlumnoServicio.buscarAlumnoPorId(idCliente);
				if(cliente != null){
					logger.info("Nombre: " );
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Matricula: ");
					var matricula = Integer.parseInt(consola.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMatricula(matricula);
					iAlumnoServicio.guardarAlumno(cliente);
					logger.info("Cliente modificado: " + cliente + nl);
				}
				else
					logger.info("cliente NO encontrado: " + cliente + nl);
				break;
			}

			case 5 : {
				logger.info("--- Eliminar Cliente ---" + nl);
				logger.info("Id Cliente: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente = iAlumnoServicio.buscarAlumnoPorId(idCliente);
				if(cliente != null){
					iAlumnoServicio.eliminarAlumno(cliente);
					logger.info("Cliente eliminado: " + cliente + nl);
				}
				else
					logger.info("Cliente No encontrado: " + cliente + nl);
				break;
			}
			case 6: {
				logger.info("Saliendo de la aplicación...");
				salir = true;
				break;
			}
			default: {
				logger.info("Opción no válida, por favor elige otra.");
				break;
			}
		}
		return salir;
	}

}
