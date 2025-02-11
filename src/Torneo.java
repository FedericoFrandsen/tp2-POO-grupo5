public class Torneo {
    private String nombre;
    private Equipo[] equipos;
    private Partido[] partidos;
    private int numEquipos;
    private int numPartidos;
    private Jugador maximoGoleador;
    private Jugador maximoAsistidor;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equipos = new Equipo[10]; // Suponiendo un máximo de 10 equipos por torneo
        this.partidos = new Partido[10]; // Suponiendo un máximo de 10 partidos por torneo
        this.numEquipos = 0;
        this.numPartidos = 0;
    }

    public void agregarEquipo(Equipo equipo) {
        if (numEquipos < equipos.length) {
            equipos[numEquipos++] = equipo;
        }
    }

    public void eliminarEquipo(Equipo equipo) {
        for (int i = 0; i < numEquipos; i++) {
            if (equipos[i].equals(equipo)) {
                equipos[i] = equipos[--numEquipos];
                equipos[numEquipos] = null;
                break;
            }
        }
    }

    public void sortearEnfrentamientos() {
        numPartidos = 0;
        for (int i = 0; i < numEquipos; i += 2) {
            if (i + 1 < numEquipos) {
                partidos[numPartidos++] = new Partido(equipos[i], equipos[i + 1]);
            }
        }
    }

    public void ingresarGoles(Partido partido, Jugador goleador, Jugador asistidor) {
        goleador.setGoles(goleador.getGoles() + 1);
        asistidor.setAsistencias(asistidor.getAsistencias() + 1);
        partido.agregarGol(goleador, asistidor);
    }

    public void finalizarPartido(Partido partido, Equipo ganador) {
        eliminarEquipo(partido.getEquipo1().equals(ganador) ? partido.getEquipo2() : partido.getEquipo1());
        if (numEquipos > 1) {
            sortearEnfrentamientos();
        }
    }

    public Jugador obtenerMaximoGoleador() {
        // Implementación para obtener el máximo goleador
        return maximoGoleador;
    }

    public Jugador obtenerMaximoAsistidor() {
        // Implementación para obtener el máximo asistidor
        return maximoAsistidor;
    }

    public Equipo obtenerEquipoCampeon() {
        if (numEquipos == 1) {
            return equipos[0];
        }
        return null;
    }
    public Partido[] getPartidos() {
        return partidos;
    }

    public String getNombre() {
        return nombre;
    }
}

