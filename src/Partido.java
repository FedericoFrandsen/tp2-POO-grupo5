import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un partido entre dos equipos, guarda la siguiente información:
 *  Los equipos que participan y los goles realizados en el partido.
 */
public class Partido {
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private Arbitro arbitro;
    private List<Gol> golesVisitante = new ArrayList<>();
    private List<Gol> golesLocal = new ArrayList<>();

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, Arbitro arbitro) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.arbitro = arbitro;

    }

    /**
     * Método que agrega un gol al equipo local
     * @param goleador Jugador que realizó el gol
     * @param asistidor Jugador que realizó la asistencia
     */
    public void agregarGolLocal(Jugador goleador, Jugador asistidor) {
        golesLocal.add(new Gol(goleador, asistidor, this.equipoLocal));
        goleador.incrementarGoles();
        asistidor.incrementarAsistencias();

        this.equipoLocal.incrementarGolesMarcados();
        this.equipoVisitante.incrementarGolesRecibidos();
    }

    /**
     * Método que agrega un gol al equipo visitante
     * @param goleador Jugador que realizó el gol
     * @param asistidor Jugador que realizó la asistencia
     */
    public void agregarGolVisitante(Jugador goleador, Jugador asistidor) {
        golesVisitante.add(new Gol(goleador, asistidor, this.equipoVisitante));
        // Incrementamos en 1 los datos de los jugadores y los equipos
        goleador.incrementarGoles();
        asistidor.incrementarAsistencias();
        this.equipoVisitante.incrementarGolesMarcados();
        this.equipoLocal.incrementarGolesRecibidos();
    }

    public List<Gol> getGolesTotales() {
        List<Gol> goles = new ArrayList<>();
        goles.addAll(golesLocal);
        goles.addAll(golesVisitante);
        return goles;
    }

    public List<Gol> getGolesLocal() {return golesLocal;}
    public List<Gol> getGolesVisitante() {return golesVisitante;}

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    /**
     * Método que determina el ganador del partido
     * @return el equipo ganador del partido
     */
    public Equipo determinarGanador() {
        int golesLocal = this.golesLocal.size();
        int golesVisitante = this.golesVisitante.size();

        if (golesLocal > golesVisitante) {
            return equipoLocal;
        } else if (golesLocal < golesVisitante) {
            return equipoVisitante;
        } else {
            return equipoLocal;  // En caso de empate, gana el equipo local
        }
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }
}


