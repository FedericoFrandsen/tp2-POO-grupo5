import javax.xml.transform.Source;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Torneo torneo = new Torneo("Torneo de Futbol 5");

        Equipo equipo1 = new Equipo("Equipo 1");
        equipo1.agregarJugador(new Jugador("Juan", "Perez", 25, true, 5, 3));
        equipo1.agregarJugador(new Jugador("Carlos", "Gomez", 22, true, 3, 2));

        Equipo equipo2 = new Equipo("Equipo 2");
        equipo2.agregarJugador(new Jugador("Luis", "Martinez", 28, true, 4, 4));
        equipo2.agregarJugador(new Jugador("Pedro", "Lopez", 24, true, 2, 1));

        Equipo equipo3 = new Equipo("Equipo 3");
        equipo3.agregarJugador(new Jugador("Miguel", "Sanchez", 30, true, 6, 5));
        equipo3.agregarJugador(new Jugador("Jorge", "Ramirez", 27, true, 3, 2));

        Equipo equipo4 = new Equipo("Equipo 4");
        equipo4.agregarJugador(new Jugador("Roberto", "Diaz", 26, true, 4, 3));
        equipo4.agregarJugador(new Jugador("Fernando", "Gonzalez", 23, true, 2, 1));

        torneo.agregarEquipo(equipo1);
        torneo.agregarEquipo(equipo2);
        torneo.agregarEquipo(equipo3);
        torneo.agregarEquipo(equipo4);

        torneo.iniciarTorneo();

        int maxPartidos = (torneo.getNumEquipos() * 2) - 1;

        OrganizadorDePartidos organizadorDePartidos = torneo.getOrganizadorDePartidos();

        organizadorDePartidos.sortearEnfrentamientos();

        boolean hayPartidosPendientes = organizadorDePartidos.hayPartidosPendientes();
        System.out.println("Hay partidos pendientes: " + hayPartidosPendientes);
        while (organizadorDePartidos.hayPartidosPendientes()) {


            System.out.println("Partidos Pendientes:");
            System.out.println( organizadorDePartidos.getPartidosPendientes());
            System.out.println("Partidos Jugados:");
            System.out.println( organizadorDePartidos.getPartidosJugados());
            System.out.println("Equipos para sortear:");
            System.out.println( organizadorDePartidos.getEquiposParaSortear());

            Partido partido = organizadorDePartidos.iniciarSiguientePartido();




            System.out.println("Partido " + (organizadorDePartidos.getPartidosJugados().size() + 1) + ": " + partido.getEquipoLocal().getNombre() + " vs. " + partido.getEquipoVisitante().getNombre());
            partido.agregarGolLocal(partido.getEquipoLocal().getJugadores().get(0), partido.getEquipoLocal().getJugadores().get(1));
            partido.agregarGolVisitante(partido.getEquipoVisitante().getJugadores().get(0), partido.getEquipoVisitante().getJugadores().get(1));
            System.out.println("Goles del equipo local: " + partido.getGolesLocal().size());
            System.out.println("Goles del equipo visitante: " + partido.getGolesVisitante().size());
            System.out.println("Ganador: " + partido.determinarGanador().getNombre());
            System.out.println();
            organizadorDePartidos.finalizarPartidoActual( partido.determinarGanador());
        }

    }
    public static void ingresarJugadores(Equipo equipo) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("¿Cuántos jugadores deseas agregar?");
            int numJugadores = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            for (int i = 0; i < numJugadores; i++) {
                System.out.println("Ingrese el nombre del jugador:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el apellido del jugador:");
                String apellido = scanner.nextLine();
                System.out.println("Ingrese la edad del jugador:");
                int edad = scanner.nextInt();
                System.out.println("¿Es titular? (true/false):");
                boolean esTitular = scanner.nextBoolean();
                scanner.nextLine(); // Consumir la nueva línea

                Jugador jugador = new Jugador(nombre, apellido, edad, esTitular, 0, 0);
                equipo.agregarJugador(jugador);
            }

            System.out.println("Jugadores agregados:");
            System.out.println(equipo.formacion());
        }
    }


