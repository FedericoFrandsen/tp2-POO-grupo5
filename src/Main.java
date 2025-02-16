import javax.xml.transform.Source;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        Utilidades.escribirArchivo("./equipos.txt", "nombre:Equipo 8;golesMarcados:7;golesRecibidos:1\nnombre:Equipo 12;golesMarcados:42;golesRecibidos:12");

        Torneo torneo = new Torneo("Torneo de Futbol 5");

        Equipo equipo1 = new Equipo(0,"Equipo 1");
        equipo1.agregarJugador(new Jugador("Juan", "Perez", 25,  5, 3));
        equipo1.agregarJugador(new Jugador("Carlos", "Gomez", 22,  3, 2));

        Equipo equipo2 = new Equipo(1,"Equipo 2");
        equipo2.agregarJugador(new Jugador("Luis", "Martinez", 28,  4, 4));
        equipo2.agregarJugador(new Jugador("Pedro", "Lopez", 24,  2, 1));

        Equipo equipo3 = new Equipo(2,"Equipo 3");
        equipo3.agregarJugador(new Jugador("Miguel", "Sanchez", 30,  6, 5));
        equipo3.agregarJugador(new Jugador("Jorge", "Ramirez", 27,  3, 2));

        Equipo equipo4 = new Equipo(3,"Equipo 4");
        equipo4.agregarJugador(new Jugador("Roberto", "Diaz", 26,  4, 3));
        equipo4.agregarJugador(new Jugador("Fernando", "Gonzalez", 23,  2, 1));

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
            scanner.nextLine(); // Consumir la nueva línea ?? Para que es esto?

            for (int i = 0; i < numJugadores; i++) {
                System.out.println("Ingrese el nombre del jugador:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el apellido del jugador:");
                String apellido = scanner.nextLine();
                System.out.println("Ingrese la edad del jugador:");
                int edad = scanner.nextInt();


                Jugador jugador = new Jugador(nombre, apellido, edad, 0, 0);
                equipo.agregarJugador(jugador);
            }

            System.out.println("Jugadores agregados:");
            System.out.println(equipo.formacion());
        }
    }


