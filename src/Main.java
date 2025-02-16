import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main {

    private static ArrayList<Equipo> equipos = new ArrayList<>();

    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Leer archivo de equipos");
            System.out.println("2. Crear equipo");
            System.out.println("3. Crear y asginar jugador");
            System.out.println("4. Crear torneo ");
            System.out.println("5. Iniciar torneo");
            System.out.println("6. Carga de resultados");
            System.out.println("7. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de linea que se produce al presionar enter

            switch (option) {
                case 1:
                    leerArchivoDeEquipos();
                    break;
                case 2:
                    crearEquipo(scanner);
                    break;
                case 3:
                    ingresarJugadores(equipo);
                    break;
                case 4:
                    crearTorneo();
                    break;
                case 5:
                    iniciarTorneo();
                    break;
                case 6:
                    cargaDeResultados();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    private static void leerArchivoDeEquipos() {
        try {

            ArrayList<String> lineas = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineas) {

                Equipo equipo = Equipo.fromString(line);

                System.out.println(equipo);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        private static void crearEquipo(Scanner scanner) {

            System.out.println("Ingrese el nombre del equipo:");
            String nombre = scanner.nextLine();

            /* comparar el nombre del equipo con los nombres de los equipos existentes */
            try {

                ArrayList<String> lineas = Utilidades.leerArchivo("./equipos.txt");

                for (String line : lineas) {

                    Equipo equipo = Equipo.fromString(line);

                    if (equipo.getNombre().equals(nombre)) {
                        System.out.println("El equipo ya existe");
                        return;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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

        }

        Torneo torneo = new Torneo("Torneo de Futbol 5");

        Equipo equipo1 = new Equipo(0,"Equipo 1");
        equipo1.agregarJugador(new Jugador(1,"Juan", "Perez", 25,  5, 3, "arquero"));
        equipo1.agregarJugador(new Jugador(2,"Carlos", "Gomez", 22,  3, 2, "defensa"));

        Equipo equipo2 = new Equipo(1,"Equipo 2");
        equipo2.agregarJugador(new Jugador(3,"Luis", "Martinez", 28,  4, 4, "arquero"));
        equipo2.agregarJugador(new Jugador(4,"Pedro", "Lopez", 24,  2, 1, "defensa"));

        Equipo equipo3 = new Equipo(2,"Equipo 3");
        equipo3.agregarJugador(new Jugador(5,"Miguel", "Sanchez", 30,  6, 5, "arquero"));
        equipo3.agregarJugador(new Jugador(6,"Jorge", "Ramirez", 27,  3, 2, "defensa"));

        Equipo equipo4 = new Equipo(3,"Equipo 4");
        equipo4.agregarJugador(new Jugador(7,"Roberto", "Diaz", 26,  4, 3, "arquero"));
        equipo4.agregarJugador(new Jugador(8,"Fernando", "Gonzalez", 23,  2, 1, "defensa"));

        ArrayList<Equipo> equipos = new ArrayList<>();
        equipos.add(equipo1);
        equipos.add(equipo2);
        equipos.add(equipo3);
        equipos.add(equipo4);

        int id = Utilidades.generarIdUnicaEn(equipos);
        System.out.println("El id generado es: " + id);

        torneo.agregarEquipo(equipo1);
        torneo.agregarEquipo(equipo2);
        torneo.agregarEquipo(equipo3);
        torneo.agregarEquipo(equipo4);

        torneo.iniciarTorneo();

        int maxPartidos = (torneo.getNumEquipos() * 2) - 1;

        OrganizadorDePartidos organizadorDePartidos = torneo.getOrganizadorDePartidos();

        organizadorDePartidos.sortearEnfrentamientos();

        boolean hayPartidosPendientes = organizadorDePartidos.hayPartidosPendientes();

//        System.out.println("Hay partidos pendientes: " + hayPartidosPendientes);

//        while (organizadorDePartidos.hayPartidosPendientes()) {
//
//
//            System.out.println("Partidos Pendientes:");
//            System.out.println( organizadorDePartidos.getPartidosPendientes());
//            System.out.println("Partidos Jugados:");
//            System.out.println( organizadorDePartidos.getPartidosJugados());
//            System.out.println("Equipos para sortear:");
//            System.out.println( organizadorDePartidos.getEquiposParaSortear());
//
//            Partido partido = organizadorDePartidos.iniciarSiguientePartido();
//
//
//
//
//            System.out.println("Partido " + (organizadorDePartidos.getPartidosJugados().size() + 1) + ": " + partido.getEquipoLocal().getNombre() + " vs. " + partido.getEquipoVisitante().getNombre());
//            partido.agregarGolLocal(partido.getEquipoLocal().getJugadores().get(0), partido.getEquipoLocal().getJugadores().get(1));
//            partido.agregarGolVisitante(partido.getEquipoVisitante().getJugadores().get(0), partido.getEquipoVisitante().getJugadores().get(1));
//            System.out.println("Goles del equipo local: " + partido.getGolesLocal().size());
//            System.out.println("Goles del equipo visitante: " + partido.getGolesVisitante().size());
//            System.out.println("Ganador: " + partido.determinarGanador().getNombre());
//            System.out.println();
//            organizadorDePartidos.finalizarPartidoActual( partido.determinarGanador());
//        }
    }

    public static void pruebasDeArchivos() {
        File archivo = new File("./equipos.txt");

        if (archivo.exists()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(archivo))) {

                for (Iterator<String> it = fileReader.lines().iterator(); it.hasNext(); ) {
                    String line = it.next();

                    Equipo equipo = Equipo.fromString(line);

                    System.out.println(equipo);

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("El archivo no existe");
        }
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


                Jugador jugador = new Jugador(Utilidades.generarId(),nombre, apellido, edad, 0, 0, posicion);
                equipo.agregarJugador(jugador);
            }

            System.out.println("Jugadores agregados:");
            System.out.println(equipo.formacion());
        }



