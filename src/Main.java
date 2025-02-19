import javax.swing.event.MouseInputListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    private static ArrayList<Equipo> equipos = new ArrayList<>();
    private static ArrayList<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        try {
            // En este bloque de codigo cargamos los jugadores guardados en un archivo en memoria para poder acceder a ellos.
            ArrayList<String> lineasJugadores = Utilidades.leerArchivo("./jugadores.txt");

            for (String line : lineasJugadores) {

                Jugador jugador = Jugador.fromString(line);
                jugadores.add(jugador);
            }

            // Aca cargamos los equipos guardados en un archivo en memoria para poder acceder a ellos en el programa.
            ArrayList<String> lineasEquipos = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineasEquipos) {

                // Aca me encantaria poder hacer esto de otra forma (Pero agregaria demasiada complejidad), pero para poder mantener actualizados los datos a lo largo del programa
                // lo hacemos asi y pasamos los jugadores como parametro para poder asignarlos al equipo, y no estar creando nuevas instancias diferentes de los jugadores.
                Equipo equipo = Equipo.fromString(line, jugadores);
                equipos.add(equipo);
            }


        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());

            throw new RuntimeException(e);

        }


        System.out.println(Utilidades.agregarColor("+--------------------------------------------+\n| Bienvenido al sistema de torneos de fútbol |\n+--------------------------------------------+", Utilidades.ANSI_BLUE));



        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("+------------------------------------------+");
            System.out.println("|                                          |");
            System.out.println("|  Elige qué recurso quieres administrar:  |");
            System.out.println("|                                          |");
            System.out.println("|   1. Torneos                             |");
            System.out.println("|   2. Jugadores                           |");
            System.out.println("|   3. Equipos                             |");
            System.out.println("|   4. Salir                               |");
            System.out.println("|                                          |");
            System.out.println("+------------------------------------------+");

            int option = scanner.nextInt();
            scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de línea que se produce al presionar enter

            switch (option) {
                case 1:
                    administrarTorneos(scanner);
                    break;
                case 2:
                    administrarJugadores(scanner);
                    break;
                case 3:
                    administrarEquipos(scanner);
                    break;
                case 4:

                    System.out.println("Muchas gracias por usar nuestro programa.");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    System.out.println(Utilidades.agregarColor("Opción no válida. Intente de nuevo.", Utilidades.ANSI_RED));
            }
        }
    }

    private static void administrarEquipos(Scanner scanner) {


        boolean continuar = true;
        while (continuar) {
            System.out.println("+------------------------------------+");
            System.out.println("|   1. Ver equipos                   |");
            System.out.println("|   2. Agregar equipo                |");
            System.out.println("|   3. Modificar Equipo              |");
            System.out.println("|   4. Volver atras                  |");
            System.out.println("+------------------------------------+");

            int equipoOption = scanner.nextInt();
            scanner.nextLine();


            switch (equipoOption) {
                case 1:
                    mostrarEquipos();
                    break;
                case 2:
                    crearEquipo(scanner);
                    break;
                case 3:
                    modificarEquipo(scanner);
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println(Utilidades.agregarColor( "Opción no válida. Intente de nuevo.", Utilidades.ANSI_RED ));
                    break;
            }
        }

    }

    private static void mostrarEquipos() {
        System.out.println("Equipos:");
        for (Equipo equipo : equipos) {
            System.out.println(equipo.formacion());
            System.out.println("Historial del equipo:");
            System.out.println("Torneos jugados: " + equipo.getTorneosJugados());
            System.out.println("Torneos ganados: " + equipo.getTorneosGanados());
            System.out.println("Goles marcados: " + equipo.getGolesMarcados());
            System.out.println("Goles recibidos: " + equipo.getGolesRecibidos());
            System.out.println("\n============================================\n");

        }
    }

    private static void modificarEquipo(Scanner scanner) {
        System.out.println("Equipos disponibles:");
        for (Equipo equipo : equipos) {
            System.out.println("ID: " + equipo.getId() + " - " + equipo.getNombre());
        }
        System.out.println("Ingrese el ID del equipo que desea modificar:");

        int equipoId = scanner.nextInt();
        scanner.nextLine();
        Equipo equipoAModificar;
        try {

            equipoAModificar = Utilidades.buscarPorIdEnLista(equipos, equipoId);

        } catch (RuntimeException e) {

            System.out.println(Utilidades.agregarColor( "ID de equipo no válido." , Utilidades.ANSI_RED));
            return;
        }

        boolean continuar = true;
        boolean huboCambios = false;


        while (continuar) {


            System.out.println("+-------------------------------------------+");
            System.out.println("| Equipo encontrado: " + equipoAModificar.getNombre());
            System.out.println("+-------------------------------------------+");
            System.out.println("| 1. Modificar nombre del equipo            |");
            System.out.println("| 2. Agregar jugador al equipo              |");
            System.out.println("| 3. Eliminar jugador del equipo            |");
            System.out.println("| 4. Volver atras                           |");
            System.out.println("+-------------------------------------------+");

            int modificarOption = scanner.nextInt();
            scanner.nextLine();


            switch (modificarOption) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre del equipo:");

                    String nuevoNombre = scanner.nextLine();

                    equipoAModificar.setNombre(nuevoNombre);

                    huboCambios = true;

                    System.out.println("Nombre del equipo actualizado a: " + Utilidades.agregarColor(nuevoNombre, Utilidades.ANSI_GREEN));

                    break;
                case 2:
                    huboCambios = agregarJugadorAEquipo(scanner, equipoAModificar);
                    break;
                case 3:
                    huboCambios = eliminarJugadorDeEquipo(scanner, equipoAModificar);
                    break;
                case 4:
                    // Si alguno de los datos del equipo fue modificado, actualizamos el archivo de equipos.
                    if (huboCambios) {
                        StringBuilder equiposActualizados = new StringBuilder();

                        for (Equipo equipo : equipos) {
                            equiposActualizados.append(equipo.toFileString());
                        }

                        Utilidades.escribirArchivo("./equipos.txt", equiposActualizados.toString(), false);

                        System.out.println(Utilidades.agregarColor("Equipo actualizado correctamente.", Utilidades.ANSI_GREEN));
                        huboCambios = false;
                    }

                    continuar = false;
                    break;
                default:
                    System.out.println(Utilidades.agregarColor("Opción no válida. Intente de nuevo.", Utilidades.ANSI_RED));
                    break;
            }
        }
    }

    private static boolean agregarJugadorAEquipo(Scanner scanner, Equipo equipoAModificar) {
        if (equipoAModificar.getJugadores().size() == 5) {
            System.out.println(Utilidades.agregarColor("El equipo ya tiene 5 jugadores, no se pueden agregar más. Elimine un jugador antes de agregar uno nuevo.", Utilidades.ANSI_RED));
            return false;
        }

        System.out.println("Jugadores disponibles para agregar:");

        for (Jugador jugador : jugadores) {
            // Nos fijamos que el jugador en la lista general no este en el equipo,
            // SI no está mostramos sus datos como disponible para agregar al equipo.
            if (!equipoAModificar.getJugadores().contains(jugador)) {

                System.out.println(jugador.getId() + ". " + jugador.getNombre() + " " + jugador.getApellido());

            }

        }
        System.out.println("Ingrese el ID del jugador que desea agregar:");

        int jugadorIdAgregar = scanner.nextInt();
        scanner.nextLine();

        try {
            Jugador jugadorAAgregar = Utilidades.buscarPorIdEnLista(jugadores, jugadorIdAgregar);

            // El metodo devuelve true si se agregó el jugador por eso lo ponemos directo en el if.
            if (equipoAModificar.agregarJugador(jugadorAAgregar)) {

                System.out.println("Jugador agregado: " + Utilidades.agregarColor( jugadorAAgregar.getNombre() + " " + jugadorAAgregar.getApellido(), Utilidades.ANSI_BLUE));
                return true;

            }

            return false;
        } catch (RuntimeException e) {

            System.out.println(Utilidades.agregarColor("No se encontro el jugador con el ID: " + jugadorIdAgregar, Utilidades.ANSI_RED));

            return false;

        }


    }

    private static boolean eliminarJugadorDeEquipo(Scanner scanner, Equipo equipoAModificar) {
        System.out.println("Jugadores en el equipo:");

        for (Jugador jugador : equipoAModificar.getJugadores()) {

            System.out.println(jugador.getId() + ". " + jugador.getNombre() + " " + jugador.getApellido());

        }

        System.out.println("Ingrese el ID del jugador que desea eliminar:");

        int jugadorIdEliminar = scanner.nextInt();
        scanner.nextLine();

        try {

            Jugador jugadorEliminar = equipoAModificar.getJugadorPorId(jugadorIdEliminar);

            equipoAModificar.eliminarJugador(jugadorEliminar);



            System.out.println("Jugador eliminado: " + Utilidades.agregarColor(jugadorEliminar.getNombre() + " " + jugadorEliminar.getApellido(), Utilidades.ANSI_RED));

            return true;

        } catch (RuntimeException e) {

            System.out.println(Utilidades.agregarColor("ID de jugador no válido.", Utilidades.ANSI_RED));

            return false;

        }

    }

    private static void administrarJugadores(Scanner scanner) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("+---------------------------+");
            System.out.println("| 1. Ver jugadores          |");
            System.out.println("| 2. Agregar jugador        |");
            System.out.println("| 3. Volver atras           |");
            System.out.println("+---------------------------+");

            int jugadorOption = scanner.nextInt();
            scanner.nextLine();

            switch (jugadorOption) {
                case 1:
                    mostrarJugadores();
                    break;
                case 2:
                    agregarNuevoJugador(scanner);
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println(Utilidades.agregarColor("Opción no válida. Intente de nuevo.", Utilidades.ANSI_RED));
                    break;
            }
        }

    }

    private static void mostrarJugadores() {
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|                 Lista de Jugadores                          |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.printf("|%-10s | %-10s |%-10s | %-10s |%-10s |%n", "ID", "Apellido", "Nombre", "Asist.", "Goles");
        System.out.printf("|%-10s | %-10s |%-10s | %-10s |%-10s |%n", "=======", "=======", "========", "========", "========");
        for (Jugador jugador : jugadores) {
            System.out.printf("|%-10s | %-10s |%-10s | %-10s |%-10s |%n",  jugador.getId(), jugador.getApellido(), jugador.getNombre(), jugador.getAsistencias(), jugador.getGoles());
        }
        System.out.println("+-------------------------------------------------------------+");
    }

    private static void agregarNuevoJugador(Scanner scanner) {
        System.out.println("+-----------------------------------+");
        System.out.println("|         Agregar Nuevo Jugador     |");
        System.out.println("+-----------------------------------+");
        System.out.println("| Ingrese el nombre del jugador:    |");
        System.out.print("| > ");
        String nombre = scanner.nextLine();
        System.out.println("| Ingrese el apellido del jugador:  |");
        System.out.print("| > ");
        String apellido = scanner.nextLine();
        System.out.println("| Ingrese la edad del jugador:      |");
        System.out.print("| > ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea


        Jugador nuevoJugador = new Jugador(Utilidades.generarIdUnicaEn(jugadores), nombre, apellido, edad);
        jugadores.add(nuevoJugador);
        System.out.println("Jugador agregado: " + Utilidades.agregarColor(nuevoJugador.getNombre() + " " + nuevoJugador.getApellido(), Utilidades.ANSI_BLUE));

        // Guardar el nuevo jugador en el archivo
        Utilidades.escribirArchivo("./jugadores.txt", nuevoJugador.toFileString(), true);
    }

    private static void administrarTorneos(Scanner scanner) throws InterruptedException {

        boolean continuar = true;

        while (continuar) {

            System.out.println("+------------------------------------+");
            System.out.println("|   1. Empezar nuevo torneo          |");
            System.out.println("|   2. Ver historial de torneos      |");
            System.out.println("|   3. Volver atras                  |");
            System.out.println("+------------------------------------+");

            int torneoOption = scanner.nextInt();
            scanner.nextLine();

            switch (torneoOption) {
                case 1:
                    empezarNuevoTorneo(scanner);
                    break;
                case 2:
                    verHistorialDeTorneos();
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println(Utilidades.agregarColor("Opción no válida. Por favor selecciona otra opcion.", Utilidades.ANSI_RED));
            }
        }

    }

    private static void verHistorialDeTorneos() {
        try {
            ArrayList<String> lineas = Utilidades.leerArchivo("./torneos.txt");
            for (String line : lineas) {

                HashMap<String, String> torneoMap = Utilidades.stringToMap(line);

                try {
                    Equipo equipoGanador = Utilidades.buscarPorIdEnLista(equipos, Integer.parseInt(torneoMap.get("equipoGanadorId")));

                    System.out.println("Nombre del torneo: " + torneoMap.get("nombre"));
                    System.out.println("Equipo ganador: " + equipoGanador.getNombre());
                    System.out.println("Máximo goleador: " + torneoMap.get("maximoGoleador"));
                    System.out.println("Máximo asistidor: " + torneoMap.get("maximoAsistidor"));
                    System.out.println();

                } catch (RuntimeException e) {
                    System.out.println(Utilidades.agregarColor("No se encontro el equipo ganador: " + torneoMap.get("equipoGanadorId"), Utilidades.ANSI_RED));
                }



            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void empezarNuevoTorneo(Scanner scanner) throws InterruptedException {

        System.out.println("Ingrese el nombre del torneo:");
        String nombre = scanner.nextLine();

        Torneo torneo = new Torneo(nombre);


        ArrayList<Equipo> equiposDisponibles = new ArrayList<>();

        for (Equipo equipo : equipos) {

            if (equipo.getJugadores().size() != 5) {
                System.out.println(Utilidades.agregarColor("El equipo " + equipo.getNombre() + " no tiene 5 jugadores, no puede participar en el torneo.", Utilidades.ANSI_YELLOW));
                continue;
            }

            equiposDisponibles.add(equipo);
        }


        if (equiposDisponibles.size() < 8) {
            System.out.println(Utilidades.agregarColor("No hay suficientes equipos completos para iniciar el torneo. Por favor agregue más equipos.", Utilidades.ANSI_RED));
            return;
        }

        System.out.println("Equipos disponibles para seleccionar: \n");

        for (Equipo equipo : equiposDisponibles) {
            System.out.println("ID: " + equipo.getId() + " - " + equipo.getNombre());
        }


        while (torneo.getCantidadDeEquipos() < 8) {
            System.out.println("Ingresa el ID del equipo que deseas agregar al torneo: \n");
            int equipoId = scanner.nextInt();


            try {
                Equipo equipo = Utilidades.buscarPorIdEnLista(equiposDisponibles, equipoId);

                torneo.agregarEquipo(equipo);
                System.out.println("Equipo agregado: " + Utilidades.agregarColor(equipo.getNombre(), Utilidades.ANSI_BLUE));
            } catch (RuntimeException e) {
                System.out.println(Utilidades.agregarColor("ID de equipo no válido: " + equipoId, Utilidades.ANSI_RED));

            }


        }


        System.out.println("Torneo creado: " + Utilidades.agregarColor(torneo.getNombre(), Utilidades.ANSI_PURPLE));
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
                    System.out.println("Partido: " +
                            Utilidades.agregarColor( partido.getEquipoLocal().getNombre(), Utilidades.ANSI_BLUE) +
                            " vs " +
                            Utilidades.agregarColor(partido.getEquipoVisitante().getNombre(), Utilidades.ANSI_PURPLE));
                    System.out.println("Árbitro: " + Utilidades.agregarColor( partido.getArbitro().getNombre() + " " + partido.getArbitro().getApellido(), Utilidades.ANSI_YELLOW));
                    System.out.println("Elegi una de las opciones:\n");
                    System.out.println("1) Ingresa gol");
                    System.out.println("2) Finalizar partido");
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("¿Que equipo hizo el gol?\n");
                            System.out.println("1) " + partido.getEquipoLocal().getNombre());
                            System.out.println("2) " + partido.getEquipoVisitante().getNombre());
                            int equipoQueHizoElGol = scanner.nextInt();
                            Jugador goleador = null;
                            Jugador asistidor = null;
                            if (equipoQueHizoElGol == 1) {
                                System.out.println("¿Quién hizo el gol? ");
                                System.out.println("Jugadores: \n");

                                for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                    System.out.println(jugador.getId() + " - "+ jugador.getNombre() + " " + jugador.getApellido());
                                }

                                while (goleador == null) {
                                    int idJugadorGoleador = scanner.nextInt();

                                    try {
                                        goleador = partido.getEquipoLocal().getJugadorPorId(idJugadorGoleador);
                                    } catch (RuntimeException e) {
                                        System.out.println(Utilidades.agregarColor("ID de jugador no válido. Intente de nuevo.", Utilidades.ANSI_RED));
                                    }

                                }
                                System.out.println("¿Quién asistio en el gol?");
                                System.out.println("Jugadores: \n");
                                for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                    System.out.println(jugador.getId() + " - " + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (asistidor == null) {
                                    int idJugadorAsistidor = scanner.nextInt();
                                    try {
                                        asistidor = partido.getEquipoLocal().getJugadorPorId(idJugadorAsistidor);
                                    } catch (RuntimeException e) {
                                        System.out.println(Utilidades.agregarColor("ID de jugador no válido. Intente de nuevo.", Utilidades.ANSI_RED));
                                    }

                                }
                                partido.agregarGolLocal(goleador, asistidor);

                            } else if (equipoQueHizoElGol == 2) {
                                System.out.println("¿Quién hizo el gol?");
                                System.out.println("Jugadores: \n");
                                for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                    System.out.println(jugador.getId() + " - " + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (goleador == null) {
                                    int idJugadorGoleador = scanner.nextInt();
                                    try {
                                        goleador = partido.getEquipoVisitante().getJugadorPorId(idJugadorGoleador);
                                    } catch (RuntimeException e) {
                                        System.out.println(Utilidades.agregarColor("ID de jugador no válido. Intente de nuevo.", Utilidades.ANSI_RED));
                                    }

                                }

                                System.out.println("¿Quién asistio en el gol?");
                                System.out.println("Jugadores: \n");
                                for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                    System.out.println(jugador.getId() + " - " + jugador.getNombre() + " " + jugador.getApellido());
                                }
                                while (asistidor == null) {
                                    int idJugadorAsistidor = scanner.nextInt();
                                    try {
                                        asistidor = partido.getEquipoVisitante().getJugadorPorId(idJugadorAsistidor);
                                    } catch (RuntimeException e) {
                                        System.out.println(Utilidades.agregarColor("ID de jugador no válido. Intente de nuevo.", Utilidades.ANSI_RED));
                                    }

                                }
                                partido.agregarGolVisitante(goleador, asistidor);
                            } else {
                                System.out.println(Utilidades.agregarColor("Opción no válida", Utilidades.ANSI_RED));
                            }
                            break;
                        case 2:
                            Equipo ganador = partido.determinarGanador();
                            System.out.println("El ganador del partido " +
                                    Utilidades.agregarColor( partido.getEquipoLocal().getNombre(), Utilidades.ANSI_BLUE) +
                                    " vs " +
                                    Utilidades.agregarColor(partido.getEquipoVisitante().getNombre(), Utilidades.ANSI_PURPLE) +
                                    " es: " +
                                    Utilidades.agregarColor(ganador.getNombre() + "\n", Utilidades.ANSI_GREEN));

                            organizadorDePartidos.finalizarPartidoActual(ganador);
                            break;
                        default:
                            System.out.println(Utilidades.agregarColor("Opción no válida", Utilidades.ANSI_RED));
                            break;
                    }
                }
            }

            torneo.finalizarTorneo();

            System.out.println("Torneo finalizado");
            System.out.println("Equipo ganador: " + Utilidades.agregarColor( torneo.getEquipoGanador().getNombre(), Utilidades.ANSI_GREEN_BACKGROUND + Utilidades.ANSI_BLACK));


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
                System.out.println(Utilidades.agregarColor("El equipo ya existe, por favor elegir otro nombre.", Utilidades.ANSI_RED));
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

            System.out.println("\nIngresa el ID del jugador que deseas agregar al equipo:");
            int jugadorId = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea


            try {
                Jugador jugador = Utilidades.buscarPorIdEnLista(jugadores, jugadorId);

                if (equipo.agregarJugador(jugador)) {
                    System.out.println("Jugador agregado: " + jugador.getNombre() + " " + jugador.getApellido());

                }

            } catch (RuntimeException e) {
                System.out.println(Utilidades.agregarColor("ID de jugador no válido.", Utilidades.ANSI_RED));

            }


        }


        System.out.println(Utilidades.agregarColor("\nEquipo completo!\n", Utilidades.ANSI_GREEN));
        System.out.println(equipo.formacion());

        Utilidades.escribirArchivo("./equipos.txt", equipo.toFileString(), true);
    }
}




