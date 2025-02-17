import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    private static ArrayList<Equipo> equipos = new ArrayList<>();
    private static ArrayList<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // En este bloque de codigo cargamos los jugadores guardados en un archivo en memoria para poder acceder a ellos.
            ArrayList<String> lineasJugadores = Utilidades.leerArchivo("./jugadores.txt");

            for (String line : lineasJugadores) {

                if (line.isEmpty()) {
                    continue;
                }

                Jugador jugador = Jugador.fromString(line);
                jugadores.add(jugador);
            }

            // Aca cargamos los equipos guardados en un archivo en memoria para poder acceder a ellos en el programa..
            ArrayList<String> lineasEquipos = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineasEquipos) {

                if (line.isEmpty()) {
                    continue;
                }

                Equipo equipo = Equipo.fromString(line, jugadores);
                equipos.add(equipo);
            }


        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }
        System.out.println("------------------------------------");
        System.out.println("Bienvenido al sistema de torneos de fútbol");
        System.out.println("------------------------------------");


        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("+-----------------------------------+");
            System.out.println("|                                   |");
            System.out.println("|  Elige qué recurso quieres administrar:  |");
            System.out.println("|                                   |");
            System.out.println("|   1. Torneos                     |");
            System.out.println("|   2. Jugadores                   |");
            System.out.println("|   3. Equipos                     |");
            System.out.println("|   4. Salir                       |");
            System.out.println("|                                   |");
            System.out.println("+-----------------------------------+");

            int option = scanner.nextInt();
            scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de línea que se produce al presionar enter

            switch (option) {
                case 1:
                    System.out.println("------------------------------------");
                    System.out.println("|   1. Empezar nuevo torneo   |");
                    System.out.println("|   2. Ver historial de torneos   |");
                    System.out.println("------------------------------------");
                    int torneoOption = scanner.nextInt();
                    scanner.nextLine();

                    switch (torneoOption) {
                        case 1:
                            empezarNuevoTorneo(scanner);
                            break;
                        case 2:
                            verHistorialDeTorneos();
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor selecciona otra opcion.");
                    }

                    break;
                case 2:
                    System.out.println("+---------------------------+");
                    System.out.println("| 1. Ver jugadores          |");
                    System.out.println("| 2. Agregar jugador        |");
                    System.out.println("+---------------------------+");

                    int jugadorOption = scanner.nextInt();
                    scanner.nextLine();

                    switch (jugadorOption) {
                        case 1:
                            System.out.println("+-----------------------------------+");
                            System.out.println("|          Lista de Jugadores        |");
                            System.out.println("+-----------------------------------+");
                            System.out.printf("|%-10s || %-10s |%-10s |%-10s | %-10s |%-10s |%n","ID", "Apellido", "Nombre","Posición","Asistencias","Goles");
                            System.out.printf("|%-10s || %-10s |%-10s |%-10s | %-10s |%-10s |%n","=======",  "=======", "========","========","========","========");
                            for (Jugador jugador : jugadores) {
                                System.out.printf("|%-10s || %-10s |%-10s |%-10s | %-10s |%-10s |%n",jugador.getId(), jugador.getApellido(),jugador.getNombre(), jugador.getPosicion(), jugador.getAsistencias(), jugador.getGoles());
                            }
                            System.out.println("+-----------------------------------+");
                            break;
                        case 2:
                            System.out.println("+-----------------------------------+");
                            System.out.println("|         Agregar Nuevo Jugador      |");
                            System.out.println("+-----------------------------------+");
                            System.out.println("| Ingrese el nombre del jugador:    |");
                            System.out.print("| > ");
                            String nombre = scanner.nextLine();
                            System.out.println("| Ingrese el apellido del jugador:   |");
                            System.out.print("| > ");
                            String apellido = scanner.nextLine();
                            System.out.println("| Ingrese la edad del jugador:       |");
                            System.out.print("| > ");
                            int edad = scanner.nextInt();
                            scanner.nextLine(); // Consumir el salto de línea
                            System.out.println("| Ingrese la posición del jugador:   |");
                            System.out.println("| (arquero, defensor, delantero)     |");
                            System.out.print("| > ");
                            String posicion = scanner.nextLine().toLowerCase();

                            // Validamos que la posición sea una de las permitidas
                            while (!posicion.equals("arquero") &&
                                    !posicion.equals("defensor") &&
                                    !posicion.equals("delantero")) {
                                System.out.println("Error: Posición no válida. Debe ser arquero, defensor o delantero.");
                                System.out.println("Ingrese nuevamente la posición:");
                                System.out.print("| > ");
                                posicion = scanner.nextLine().toLowerCase();
                            }

                            Jugador nuevoJugador = new Jugador(Utilidades.generarIdUnicaEn(jugadores), nombre, apellido, edad, 0, 0, posicion);
                            jugadores.add(nuevoJugador);
                            System.out.println("Jugador agregado: " + nuevoJugador.getNombre() + " " + nuevoJugador.getApellido());

                            // Guardar el nuevo jugador en el archivo
                            Utilidades.escribirArchivo("./jugadores.txt", nuevoJugador.toFileString() + "\n", true);
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                            break;
                    }


                    break;
                case 3:
                    System.out.println("------------------------------------");
                    System.out.println("|   1. Ver equipos   |");
                    System.out.println("|   2. Agregar equipo   |");
                    System.out.println("|   3. Modificar Equipo   |");
                    System.out.println("------------------------------------");
                    int equipoOption = scanner.nextInt();
                    scanner.nextLine();

                    switch (equipoOption) {
                        case 1:
                            System.out.println("Equipos:");
                            for (Equipo equipo : equipos) {
                                System.out.println(equipo.formacion());
                            }
                            break;
                        case 2:
                            crearEquipo(scanner);
                            break;
                        case 3:
                            System.out.println("Equipos disponibles:");
                            for (Equipo equipo : equipos) {
                                System.out.println("ID: " + equipo.getId() + " - " + equipo.getNombre());
                            }
                            System.out.println("Ingrese el ID del equipo que desea modificar:");

                            int equipoId = scanner.nextInt();
                            scanner.nextLine();
                            Equipo equipoAModificar = null;

                            for (Equipo equipo : equipos) {
                                if (equipo.getId() == equipoId) {
                                    equipoAModificar = equipo;
                                    break;
                                }
                            }

                            if (equipoAModificar == null) {
                                System.out.println("ID de equipo no válido.");
                                break;
                            }

                            System.out.println("+-------------------------------------------+");
                            System.out.println("| Equipo encontrado: " + equipoAModificar.getNombre());
                            System.out.println("+-------------------------------------------+");
                            System.out.println("| 1. Modificar nombre del equipo            |");
                            System.out.println("| 2. Agregar jugador al equipo              |");
                            System.out.println("| 3. Eliminar jugador del equipo            |");
                            System.out.println("+-------------------------------------------+");

                            int modificarOption = scanner.nextInt();
                            scanner.nextLine();

                            switch (modificarOption) {
                                case 1:
                                    System.out.println("Ingrese el nuevo nombre del equipo:");
                                    String nuevoNombre = scanner.nextLine();
                                    equipoAModificar.setNombre(nuevoNombre);
                                    System.out.println("Nombre del equipo actualizado a: " + nuevoNombre);
                                    break;
                                case 2:
                                    if (equipoAModificar.getJugadores().size() == 5) {
                                        System.out.println("El equipo ya tiene 5 jugadores, no se pueden agregar más. Elimine un jugador antes de agregar uno nuevo.");
                                        break;
                                    }

                                    System.out.println("Jugadores disponibles para agregar:");
                                    for (Jugador jugador : jugadores) {
                                        if (!equipoAModificar.getJugadores().contains(jugador)) {
                                            System.out.println(jugador.getId() + ". " + jugador.getNombre() + " " + jugador.getApellido());
                                        }
                                    }
                                    System.out.println("Ingrese el ID del jugador que desea agregar:");
                                    int jugadorIdAgregar = scanner.nextInt();
                                    scanner.nextLine();
                                    Jugador jugadorAgregar = null;
                                    for (Jugador jugador : jugadores) {
                                        if (jugador.getId() == jugadorIdAgregar) {
                                            jugadorAgregar = jugador;
                                            break;
                                        }
                                    }
                                    if (jugadorAgregar != null && equipoAModificar.agregarJugador(jugadorAgregar)) {
                                        System.out.println("Jugador agregado: " + jugadorAgregar.getNombre() + " " + jugadorAgregar.getApellido());
                                    } else {
                                        System.out.println("No se pudo agregar el jugador.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Jugadores en el equipo:");
                                    for (Jugador jugador : equipoAModificar.getJugadores()) {
                                        System.out.println(jugador.getId() + ". " + jugador.getNombre() + " " + jugador.getApellido());
                                    }
                                    System.out.println("Ingrese el ID del jugador que desea eliminar:");
                                    int jugadorIdEliminar = scanner.nextInt();
                                    scanner.nextLine();
                                    Jugador jugadorEliminar = equipoAModificar.getJugadorPorId(jugadorIdEliminar);
                                    if (jugadorEliminar != null) {
                                        equipoAModificar.eliminarJugador(jugadorEliminar);
                                        System.out.println("Jugador eliminado: " + jugadorEliminar.getNombre() + " " + jugadorEliminar.getApellido());
                                    } else {
                                        System.out.println("No se pudo eliminar el jugador.");
                                    }
                                    break;
                                default:
                                    System.out.println("Opción no válida. Intente de nuevo.");
                                    break;
                            }
                            break;

                    }
                case 4:

                    System.out.println("Muchas gracias por usar nuestro programa.");
                    exit = true;
                    scanner.close();
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void verHistorialDeTorneos() {
        try {
            ArrayList<String> lineas = Utilidades.leerArchivo("./torneos.txt");
            for (String line : lineas) {
                HashMap<String, String> torneoMap = Utilidades.stringToMap(line);

                Equipo equipoGanador = null;

                for (Equipo equipo : equipos) {
                    if (equipo.getId() == Integer.parseInt(torneoMap.get("equipoGanadorId"))) {
                        equipoGanador = equipo;
                        break;
                    }
                }

                if (equipoGanador == null) {
                    throw new RuntimeException("No se encontró el equipo ganador");
                }

                System.out.println("Nombre del torneo: " + torneoMap.get("nombre"));
                System.out.println("Equipo ganador: " + equipoGanador.getNombre());
                System.out.println("Máximo goleador: " + torneoMap.get("maximoGoleador"));
                System.out.println("Máximo asistidor: " + torneoMap.get("maximoAsistidor"));
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void empezarNuevoTorneo(Scanner scanner) {

        System.out.println("Ingrese el nombre del torneo:");
        String nombre = scanner.nextLine();

        Torneo torneo = new Torneo(nombre);

        ArrayList<Equipo> equiposDisponibles = new ArrayList<>();

        for (Equipo equipo : equipos) {

            if (equipo.getJugadores().size() != 5) {
                System.out.println("El equipo " + equipo.getNombre() + " no tiene 5 jugadores, no puede participar en el torneo.");
                continue;
            }

            equiposDisponibles.add(equipo);
        }


        if (equiposDisponibles.size() < 8) {
            System.out.println("No hay suficientes equipos completos para iniciar el torneo. Por favor agregue más equipos.");
            return;
        }

        System.out.println("Equipos disponibles para seleccionar:");

        for (Equipo equipo : equiposDisponibles) {
            System.out.println("ID: " + equipo.getId() + " - " + equipo.getNombre());
        }


        while (torneo.getCantidadDeEquipos() < 8) {
            System.out.println("Ingresa el ID del equipo que deseas agregar al torneo:");
            int equipoId = scanner.nextInt();
            boolean equipoEncontrado = false;
            for (Equipo equipo : equiposDisponibles) {
                if (equipo.getId() == equipoId) {
                    System.out.println("Equipo agregado: " + equipo.getNombre());
                    torneo.agregarEquipo(equipo);
                    equipoEncontrado = true;
                    break;
                }
            }
            if (!equipoEncontrado) {
                System.out.println("ID de equipo no válido: " + equipoId);
            }
        }


        System.out.println("Torneo creado: " + torneo.getNombre());
        System.out.println("Equipos en el torneo: \n");
        for (Equipo equipo : torneo.getEquipos()) {
            System.out.println(equipo.formacion());
            System.out.println();
        }

        try {
            torneo.iniciarTorneo();

            OrganizadorDePartidos organizadorDePartidos = torneo.getOrganizadorDePartidos();


            while (organizadorDePartidos.hayPartidosPendientes()) {
                Partido partido = organizadorDePartidos.iniciarSiguientePartido();

                while (organizadorDePartidos.getPartidoActual() != null) {
                    System.out.println("Partido: " + partido.getEquipoLocal().getNombre() + " vs " + partido.getEquipoVisitante().getNombre());
                    System.out.println("Elegi una de las opciones:");
                    System.out.println("1) Ingresa gol");
                    System.out.println("2) Finalizar partido");
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("¿Quién hizo el gol?");
                            System.out.println("1) " + partido.getEquipoLocal().getNombre());
                            System.out.println("2) " + partido.getEquipoVisitante().getNombre());
                            int equipoQueHizoElGol = scanner.nextInt();
                            Jugador goleador = null;
                            Jugador asistidor = null;
                            if (equipoQueHizoElGol == 1) {
                                System.out.println("¿Quién hizo el gol?");
                                System.out.println("Jugadores:");

                                for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                    System.out.println("ID." + jugador.getId() + jugador.getNombre() + " " + jugador.getApellido());
                                }

                                while (goleador == null) {
                                    int idJugadorGoleador = scanner.nextInt();
                                    goleador = partido.getEquipoLocal().getJugadorPorId(idJugadorGoleador);
                                    if (goleador == null) {
                                        System.out.println("ID de jugador no válido. Intente de nuevo.");
                                    }
                                }
                                System.out.println("¿Quién asistio en el gol?");
                                System.out.println("Jugadores:");
                                for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                    System.out.println("ID." + jugador.getId() + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (asistidor == null) {
                                    int idJugadorAsistidor = scanner.nextInt();
                                    asistidor = partido.getEquipoLocal().getJugadorPorId(idJugadorAsistidor);
                                    if (asistidor == null) {
                                        System.out.println("ID de jugador no válido. Intente de nuevo.");
                                    }
                                }
                                partido.agregarGolLocal(goleador, asistidor);

                            } else if (equipoQueHizoElGol == 2) {
                                System.out.println("¿Quién hizo el gol?");
                                System.out.println("Jugadores:");
                                for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                    System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (goleador == null) {
                                    int idJugadorGoleador = scanner.nextInt();
                                    goleador = partido.getEquipoVisitante().getJugadorPorId(idJugadorGoleador);
                                    if (goleador == null) {
                                        System.out.println("ID de jugador no válido. Intente de nuevo.");
                                    }
                                }

                                System.out.println("¿Quién asistio en el gol?");
                                System.out.println("Jugadores:");
                                for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                    System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (asistidor == null) {
                                    int idJugadorAsistidor = scanner.nextInt();
                                    asistidor = partido.getEquipoVisitante().getJugadorPorId(idJugadorAsistidor);
                                    if (asistidor == null) {
                                        System.out.println("ID de jugador no válido. Intente de nuevo.");
                                    }
                                }
                                partido.agregarGolVisitante(goleador, asistidor);
                            } else {
                                System.out.println("Opción no válida");
                            }
                            break;
                        case 2:
                            Equipo ganador = partido.determinarGanador();
                            System.out.println("El ganador del partido " + partido.getEquipoLocal().getNombre() + " vs. " + partido.getEquipoVisitante().getNombre() + " es: " + ganador.getNombre());

                            organizadorDePartidos.finalizarPartidoActual(ganador);
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                }
            }

            torneo.finalizarTorneo();

            System.out.println("Torneo finalizado");
            System.out.println("Equipo ganador: " + torneo.getEquipoGanador().getNombre());


            torneo.guardarEnArchivo();

            StringBuilder jugadoresActualizados = new StringBuilder();

            for (Jugador jugador : jugadores) {
                jugadoresActualizados.append(jugador.toFileString());
            }

            Utilidades.escribirArchivo("./jugadores.txt", jugadoresActualizados.toString(), false);


            StringBuilder equiposActualizados = new StringBuilder();

            for (Equipo equipo : equipos) {
                equiposActualizados.append(equipo.toFileString());
            }

            Utilidades.escribirArchivo("./equipos.txt", equiposActualizados.toString(), false);


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        }


    }


    private static void crearEquipo(Scanner scanner) {

        System.out.println("Ingrese el nombre del equipo:");
        System.out.println();

        String nombre = scanner.nextLine();

        for (Equipo equipo : equipos) {

            if (equipo.getNombre().equals(nombre)) {
                System.out.println("El equipo ya existe, por favor elegir otro nombre.");
                return;
            }
        }


        /* creo el equipo */
        Equipo equipo = new Equipo(Utilidades.generarIdUnicaEn(equipos), nombre, 0, 0, 0, 0);
        equipos.add(equipo);
        System.out.println("Equipo creado: " + equipo.getNombre());

        System.out.println("\n¿Que jugadores queres agregar al equipo?\n ");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getId() + ". " + jugador.getNombre() + " " + jugador.getApellido());
        }

        while (equipo.getJugadores().size() < 5) {
            boolean jugadorAgregado = false; // Bandera para saber si se agregó un jugador

            while (!jugadorAgregado) {
                System.out.println("\nIngresa el ID del jugador que deseas agregar al equipo:");
                int jugadorId = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                boolean jugadorEncontrado = false;

                for (Jugador jugador : jugadores) {
                    if (jugador.getId() == jugadorId) {
                        jugadorEncontrado = true;
                        boolean fueAgregado = equipo.agregarJugador(jugador);
                        if (fueAgregado) {
                            System.out.println("Jugador agregado: " + jugador.getNombre() + " " + jugador.getApellido());
                            jugadorAgregado = true;
                        } else {
                            System.out.println("No se pudo agregar el jugador. Verifica si ya pertenece al equipo o si cumple las restricciones.");
                        }
                        break;
                    }
                }
                if (!jugadorEncontrado) {
                    System.out.println("Error: No existe un jugador con el ID " + jugadorId + ". Por favor, ingresa un ID válido.");
                }
            }
        }


        System.out.println("\nEquipo completado!\n");
        System.out.println(equipo.formacion());

        Utilidades.escribirArchivo("./equipos.txt", equipo.toFileString(), true);
    }
}




