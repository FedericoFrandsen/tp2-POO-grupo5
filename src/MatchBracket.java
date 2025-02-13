import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que se encarga de manejar el orden de los partidos del torneo.
 */
public class MatchBracket {
    private Partido partido1;
    private Partido partido2;
    private Partido partido3;
    private Partido partido4;

    private ArrayList<Equipo> equipos;
    private ArrayList<Partido> partidos;
    private final Random generator = new Random();

    // 12 equipos
    // Los partidos tendrían que tener una estructura algo asi:
    //  Partido 1: Equipo 1 vs. Equipo 2
    //  Partido 2: Equipo 3 vs. Equipo 4
    //  Partido 3: Equipo 5 vs. Equipo 6
    //  Partido 4: Equipo 7 vs. Equipo 8
    //      Partido 5: Ganador 1 vs. Ganador 2
    //      Partido 6: Ganador 3 vs. Ganador 4
    //          Partido 7: Ganador 5 vs. Ganador 6
    public MatchBracket(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
        this.partidos = new ArrayList<>();

        while (this.equipos.size() > 1) {
            Equipo equipo1 = elegirEquipoAlAzar();
            Equipo equipo2 = elegirEquipoAlAzar();
            Partido partido = new Partido(equipo1, equipo2);
            this.partidos.add(partido);
        }

    }

    public void obtenerSiguientePartido() {
        if (this.partidos.size() > 1) {
            Partido partido = this.partidos.removeFirst();

        }
    }

    /**
     * Método que se encarga de elegir y remover un equipo al azar
     * de la lista de equipos que participan en el torneo.
     * @return equipo elegido al azar.
     */
    private Equipo elegirEquipoAlAzar() {
        int indiceEquipo = generator.nextInt(this.equipos.size());
        return equipos.remove(indiceEquipo);

    }

}
