import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    private static ArrayList<Equipo> equipos = new ArrayList<>();
    private static ArrayList<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        try {

            ArrayList<String> lineasJugadores = Utilidades.leerArchivo("./jugadores.txt");

            for (String line : lineasJugadores) {
                Jugador jugador = Jugador.fromString(line);
                jugadores.add(jugador);
            }

            ArrayList<String> lineasEquipos = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineasEquipos) {
                Equipo equipo = Equipo.fromString(line, jugadores);
                equipos.add(equipo);
            }



        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

        System.out.println(equipos);
        System.out.println(jugadores);
        System.out.println("Bienvenido al sistema de torneos de fútbol");



        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Elegi que recurso queres administrar:");
            System.out.println("1. Torneos");
            System.out.println("2. Equipos");
            System.out.println("3. Jugadores");
            System.out.println("4. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de línea que se produce al presionar enter

            switch (option) {
                case 1:
                    System.out.println("1. Ver historial de torneos");
                    System.out.println("2. Empezar nuevo torneo");
                    int torneoOption = scanner.nextInt();

                    switch (torneoOption) {
                        case 1:
                            verHistorialDeTorneos();
                            break;
                        case 2:
                            empezarNuevoTorneo(scanner);
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }

                    break;
                case 2:
                    System.out.println("1. Ver equipos");
                    System.out.println("2. Crear equipo");
                    System.out.println("3. Modificar Equipo");
                    crearEquipo(scanner);
                    break;
                case 3:
                    System.out.println("1. Ver jugadores");
                    System.out.println("2. Crear jugador");


                    ingresarJugadores(equipos.getFirst());
                    break;
                case 4:
                    empezarNuevoTorneo(scanner); // falta implementar la cargar de resultados
                case 5:
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
                System.out.println("No hay suficientes equipos para iniciar el torneo. Por favor agregue más equipos.");
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
                System.out.println(equipo.getNombre());
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
                                if (equipoQueHizoElGol == 1) {
                                    System.out.println("¿Quién hizo el gol?");
                                    System.out.println("Jugadores:");
                                    //  Falta validar que ingrese un ID valido.
                                    for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                        System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                    }
                                    int idJugadorGoleador = scanner.nextInt();
                                    Jugador goleador = partido.getEquipoLocal().getJugadorPorId(idJugadorGoleador);
                                    System.out.println("¿Quién asistio en el gol?");
                                    System.out.println("Jugadores:");
                                    for (Jugador jugador : partido.getEquipoLocal().getJugadores()) {
                                        System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                    }
                                    Jugador asistidor = partido.getEquipoLocal().getJugadorPorId(scanner.nextInt());
                                    partido.agregarGolLocal(goleador, asistidor);
                                } else if (equipoQueHizoElGol == 2) {
                                    System.out.println("¿Quién hizo el gol?");
                                    System.out.println("Jugadores:");
                                    //  Falta validar que ingrese un ID valido.
                                    for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                        System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                    }
                                    int idJugadorGoleador = scanner.nextInt();
                                    Jugador goleador = partido.getEquipoVisitante().getJugadorPorId(idJugadorGoleador);
                                    System.out.println("¿Quién asistio en el gol?");
                                    System.out.println("Jugadores:");
                                    for (Jugador jugador : partido.getEquipoVisitante().getJugadores()) {
                                        System.out.println(jugador.getId() + ") " + jugador.getNombre() + " " + jugador.getApellido());
                                    }
                                    Jugador asistidor = partido.getEquipoVisitante().getJugadorPorId(scanner.nextInt());
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


                // Calcular cuantos goles metieron, cuantos recibierons, etc. Ver qué jugador fue el maximo goleador.

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }


        }


    private static void crearEquipo(Scanner scanner) {

        System.out.println("Ingrese el nombre del equipo:");
        String nombre = scanner.nextLine();



        for (Equipo equipo : equipos) {

            if (equipo.getNombre().equals(nombre)) {
                System.out.println("El equipo ya existe");
                return;
            }
        }


        /* creo el equipo */
        Equipo equipo = new Equipo(Utilidades.generarId(), nombre);
        equipos.add(equipo);
        System.out.println("Equipo creado: " + equipo);

          /*  try (BufferedWriter writer = new BufferedWriter(new FileWriter("./equipos.txt", true))) {
                writer.write(equipo.toString());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
    }


    public static void ingresarJugadores(Equipo equipo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Cuántos jugadores deseas agregar?");
        int numJugadores = scanner.nextInt();
        scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de linea que se produce al presionar enter

        for (int i = 0; i < numJugadores; i++) {
            System.out.println("Ingrese el nombre del jugador:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el apellido del jugador:");
            String apellido = scanner.nextLine();
            System.out.println("Ingrese la edad del jugador:");
            int edad = scanner.nextInt();
            System.out.println("Ingrese la posicion del jugador (arquero, defensor, delantero):");
            String posicion = scanner.nextLine();


            Jugador jugador = new Jugador(Utilidades.generarId(), nombre, apellido, edad, 0, 0, posicion);
            equipo.agregarJugador(jugador);
        }

        System.out.println("Jugadores agregados:");
        System.out.println(equipo.formacion());
    }
}



