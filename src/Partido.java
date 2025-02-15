import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un partido entre dos equipos, guarda la siguiente información:
 *  Los equipos que participan y los goles realizados en el partido.
 *  ¿El ganador del partido?
 *
 */
public class Partido {
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private List<Gol> golesVisitante;
    private List<Gol> golesLocal;



    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = new ArrayList<>();
        this.golesVisitante = new ArrayList<>();
    }

    public void agregarGolLocal(Jugador goleador, Jugador asistidor) {
        golesLocal.add(new Gol(goleador, asistidor));
        goleador.incrementarGoles();
        asistidor.incrementarAsistencias();
        }
    public void agregarGolVisitante(Jugador goleador, Jugador asistidor) {
        golesVisitante.add(new Gol(goleador, asistidor));
        goleador.incrementarGoles();
        asistidor.incrementarAsistencias();
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
}


