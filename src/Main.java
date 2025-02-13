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

        torneo.sortearEnfrentamientos();

        // Simulación de partidos y demás lógica del torneo
        // Ejemplo de finalizar un partido
        Partido partido = torneo.getPartidos()[0];
        torneo.finalizarPartido(partido, equipo1); // Suponiendo que equipo1 ganó el partido

        // Continuar con la lógica del torneo hasta que haya un campeón
    }
        }

