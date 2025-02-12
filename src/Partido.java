/**
 * Clase que representa un partido entre dos equipos, guarda la siguiente información:
 *  Los equipos que participan y los goles realizados en el partido.
 *  ¿El ganador del partido?
 *
 */
public class Partido {
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private Gol[] goles;
    private int numGoles;


    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.goles = new Gol[20]; // Suponiendo un máximo de 20 goles por partido
        this.numGoles = 0;
    }

    public void agregarGol(Jugador goleador, Jugador asistidor) {
        if (numGoles < goles.length) {
            goles[numGoles++] = new Gol(goleador, asistidor);
        }
    }

    public Gol[] getGoles() {
        return goles;
    }


    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
}


