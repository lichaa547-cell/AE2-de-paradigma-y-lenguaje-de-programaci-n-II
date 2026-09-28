package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    private List<Carrera> carreras;
    private List<Alumno> alumnos;
    private List<Profesor> profesores;
    private List<Coordinador> coordinadores;
    private List<Inscripcion> inscripciones;

    private final Scanner scanner;

    public Sistema() {
        carreras = new ArrayList<>();
        alumnos = new ArrayList<>();
        profesores = new ArrayList<>();
        coordinadores = new ArrayList<>();
        inscripciones = new ArrayList<>();

        scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);

        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        Profesor prof = new Profesor("Carlos", "Gómez", 12345678);
        Coordinador coord = new Coordinador("Ana", "Martínez", 87654321);
        Carrera carrera = new Carrera("Ing en Sistemas", 3, coord, 15000.0, 20000.0);
        Materia materia = new Materia("Programación I", 1, 1, prof);

        carrera.agregarMateria(materia);
        carreras.add(carrera);
        profesores.add(prof);
        coordinadores.add(coord);

        Profesor prof2 = new Profesor("Laura", "Fernández", 23456789);
        Coordinador coord2 = new Coordinador("Diego", "Sosa", 98765432);
        Carrera carrera2 = new Carrera("Contador Público", 5, coord2, 18000.0, 22000.0);
        Materia materia2 = new Materia("Contabilidad I", 1, 1, prof2);

        carrera2.agregarMateria(materia2);
        carreras.add(carrera2);
        profesores.add(prof2);
        coordinadores.add(coord2);
    }

    public void menu() {
        int opcion;

        do {
            System.out.println("\n--- MENU DEL SISTEMA ---");
            System.out.println("1. Matricular alumno en una carrera");
            System.out.println("2. Inscribir alumno a una materia");
            System.out.println("3. Registrar asistencia / inasistencia");
            System.out.println("4. Cargar situación final");
            System.out.println("5. Mostrar información de carreras");
            System.out.println("6. Mostrar alumnos de una materia");
            System.out.println("7. Mostrar condición y asistencias de un alumno");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> matricularAlumno();
                case 2 -> inscribirAlumno();
                case 3 -> registrarAsistencia();
                case 4 -> cargarSituacionFinal();
                case 5 -> mostrarNumerosCarrera();
                case 6 -> mostrarAlumnosMateria();
                case 7 -> mostrarCondicionAlumno();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }

    public void matricularAlumno() {
        System.out.println("\n--- MATRICULAR ALUMNO ---");

        if (carreras.isEmpty()) {
            System.out.println("No hay carreras disponibles en el sistema.");
            return;
        }

        System.out.print("Legajo del alumno: ");
        int legajo = scanner.nextInt();
        scanner.nextLine();

        Alumno alumno = buscarAlumnoPorLegajo(legajo);

        if (alumno == null) {
            System.out.print("Nombre del alumno: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido del alumno: ");
            String apellido = scanner.nextLine();
            System.out.print("DNI: ");
            int dni = scanner.nextInt();
            scanner.nextLine();

            alumno = new Alumno(nombre, apellido, dni, legajo);
            alumnos.add(alumno);
        }

        Carrera carreraActual = buscarCarreraDelAlumno(alumno);
        if (carreraActual != null) {
            System.out.println("Error: el alumno ya está matriculado en la carrera '"
                    + carreraActual.getNombre() + "'. No puede matricularse en otra carrera distinta.");
            return;
        }

        System.out.println("Seleccione la carrera a matricular:");
        for (int i = 0; i < carreras.size(); i++) {
            System.out.println((i + 1) + ". " + carreras.get(i).getNombre());
        }
        System.out.print("Opción: ");
        int opcCarrera = scanner.nextInt() - 1;

        if (opcCarrera >= 0 && opcCarrera < carreras.size()) {
            Carrera carreraElegida = carreras.get(opcCarrera);
            if (carreraElegida.matricularAlumno(alumno)) {
                System.out.println("Alumno matriculado con éxito en la carrera " + carreraElegida.getNombre());
            } else {
                System.out.println("El alumno ya estaba matriculado en esta carrera.");
            }
        } else {
            System.out.println("Opción de carrera inválida.");
        }
    }

    public void inscribirAlumno() {
        System.out.println("\n--- INSCRIBIR ALUMNO A MATERIA ---");

        System.out.print("Ingrese el legajo del alumno: ");
        int legajo = scanner.nextInt();
        scanner.nextLine();

        Alumno alumnoEncontrado = buscarAlumnoPorLegajo(legajo);
        if (alumnoEncontrado == null) {
            System.out.println("No se encontró ningún alumno con el legajo " + legajo);
            return;
        }

        System.out.print("Ingrese el nombre de la materia: ");
        String nombreMateria = scanner.nextLine();

        Carrera carreraDeLaMateria = buscarCarreraPorMateria(nombreMateria);
        if (carreraDeLaMateria == null) {
            System.out.println("No existe la materia '" + nombreMateria + "' en el sistema.");
            return;
        }

        if (!carreraDeLaMateria.estaMatriculado(alumnoEncontrado)) {
            System.out.println("El alumno no está matriculado en la carrera '"
                    + carreraDeLaMateria.getNombre() + "', a la que pertenece esa materia.");
            return;
        }

        Materia materiaEncontrada = carreraDeLaMateria.buscarMateria(nombreMateria);

        for (Inscripcion ins : inscripciones) {
            if (ins.getAlumno().getLegajo() == legajo && ins.getMateria().getNombre().equalsIgnoreCase(nombreMateria)) {
                System.out.println("El alumno ya se encuentra inscripto a esta materia.");
                return;
            }
        }

        Inscripcion nuevaInscripcion = new Inscripcion(alumnoEncontrado, materiaEncontrada);
        inscripciones.add(nuevaInscripcion);
        System.out.println("Inscripción realizada con éxito.");
    }

    public void registrarAsistencia() {
        System.out.println("\n--- REGISTRAR ASISTENCIA / INASISTENCIA ---");

        System.out.print("Legajo del alumno: ");
        int legajo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre de la materia: ");
        String materia = scanner.nextLine();

        Inscripcion ins = buscarInscripcion(legajo, materia);
        if (ins == null) {
            System.out.println("No existe una inscripción registrada para ese alumno en esa materia.");
            return;
        }

        System.out.println("1. Marcar Asistencia");
        System.out.println("2. Marcar Inasistencia");
        System.out.print("Seleccione opción: ");
        int tipo = scanner.nextInt();

        if (tipo == 1) {
            ins.registrarAsistencia();
        } else if (tipo == 2) {
            ins.registrarInasistencia();
            System.out.println("Inasistencia registrada.");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void cargarSituacionFinal() {
        System.out.println("\n--- CARGAR SITUACIÓN FINAL ---");

        System.out.print("Legajo del alumno: ");
        int legajo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre de la materia: ");
        String materia = scanner.nextLine();

        Inscripcion ins = buscarInscripcion(legajo, materia);
        if (ins == null) {
            System.out.println("No existe una inscripción registrada para ese alumno en esa materia.");
            return;
        }

        System.out.print("Ingrese el estado final (ej: Promocionado, Regular, Libre): ");
        String estado = scanner.nextLine();

        ins.cargarSituacionFinal(estado);
        System.out.println("Situación final registrada correctamente.");
    }

    public void mostrarNumerosCarrera() {
        System.out.println("\n--- CARRERAS REGISTRADAS ---");
        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return;
        }

        for (Carrera c : carreras) {
            System.out.println(c.toString());
            System.out.println(" Materias ofertadas:");
            if (c.getMaterias().isEmpty()) {
                System.out.println("   (Sin materias cargadas)");
            } else {
                for (Materia m : c.getMaterias()) {
                    System.out.println("   - " + m.toString());
                }
            }
        }
    }

    public void mostrarCondicionAlumno() {
        System.out.println("\n--- CONDICIÓN DEL ALUMNO ---");
        System.out.print("Legajo del alumno: ");
        int legajo = scanner.nextInt();
        scanner.nextLine();

        Alumno alumno = buscarAlumnoPorLegajo(legajo);
        if (alumno == null) {
            System.out.println("No se encontró ningún alumno con el legajo " + legajo);
            return;
        }

        Carrera carreraDelAlumno = buscarCarreraDelAlumno(alumno);
        System.out.println("Alumno: " + alumno.getApellido() + ", " + alumno.getNombre()
                + " (Legajo " + alumno.getLegajo() + ")");
        System.out.println("Carrera: " + (carreraDelAlumno != null
                ? carreraDelAlumno.getNombre()
                : "No matriculado en ninguna carrera"));

        List<Inscripcion> inscripcionesAlumno = new ArrayList<>();
        for (Inscripcion ins : inscripciones) {
            if (ins.getAlumno().getLegajo() == legajo) {
                inscripcionesAlumno.add(ins);
            }
        }

        if (inscripcionesAlumno.isEmpty()) {
            System.out.println("El alumno no está inscripto a ninguna materia.");
            return;
        }

        System.out.println("Materias:");
        for (Inscripcion ins : inscripcionesAlumno) {
            String estado = ins.getEstadoFinal();
            String estadoTexto = (estado == null) ? "En curso" : estado;
            System.out.println("   - " + ins.getMateria().getNombre()
                    + " | Inasistencias: " + ins.getInasistencias()
                    + " | Situación: " + estadoTexto);
        }
    }

    public void mostrarAlumnosMateria() {
        System.out.println("\n--- ALUMNOS INSCRIPTOS A MATERIA ---");
        System.out.print("Ingrese el nombre de la materia: ");
        String nombreMateria = scanner.nextLine();

        boolean huboAlumnos = false;
        System.out.println("Alumnos inscriptos en " + nombreMateria + ":");

        for (Inscripcion ins : inscripciones) {
            if (ins.getMateria().getNombre().equalsIgnoreCase(nombreMateria)) {
                Alumno a = ins.getAlumno();
                System.out.println("- Legajo: " + a.getLegajo() + " | " + a.getApellido() + ", " + a.getNombre());
                huboAlumnos = true;
            }
        }

        if (!huboAlumnos) {
            System.out.println("No hay alumnos inscriptos en esta materia.");
        }
    }

    private Alumno buscarAlumnoPorLegajo(int legajo) {
        for (Alumno a : alumnos) {
            if (a.getLegajo() == legajo) {
                return a;
            }
        }
        return null;
    }

    private Materia buscarMateriaPorNombre(String nombre) {
        for (Carrera carrera : carreras) {
            Materia m = carrera.buscarMateria(nombre);
            if (m != null) {
                return m;
            }
        }
        return null;
    }

    private Carrera buscarCarreraDelAlumno(Alumno alumno) {
        for (Carrera c : carreras) {
            if (c.estaMatriculado(alumno)) {
                return c;
            }
        }
        return null;
    }

    private Carrera buscarCarreraPorMateria(String nombreMateria) {
        for (Carrera carrera : carreras) {
            if (carrera.buscarMateria(nombreMateria) != null) {
                return carrera;
            }
        }
        return null;
    }

    private Inscripcion buscarInscripcion(int legajo, String nombreMateria) {
        for (Inscripcion ins : inscripciones) {
            if (ins.getAlumno().getLegajo() == legajo && ins.getMateria().getNombre().equalsIgnoreCase(nombreMateria)) {
                return ins;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        Sistema sistema = new Sistema();
        sistema.menu();
    }
}
