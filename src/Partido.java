public class Partido {
    private Equipo equipo1;
    private Equipo equipo2;
    private Gol[] goles;
    private int numGoles;

    public Partido(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.goles = new Gol[20]; // Suponiendo un máximo de 20 goles por partido
        this.numGoles = 0;
    }

    public void agregarGol(Jugador goleador, Jugador asistidor) {
        if (numGoles < goles.length) {
            goles[numGoles++] = new Gol(goleador, asistidor);
        }
    }


    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }
}


