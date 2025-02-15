import java.util.ArrayList;
import java.util.List;

public class Torneo {
    private String nombre;
    private List<Equipo> equipos;
    private List<Partido> partidos;
    // Los partidos tendrían que tener una estructura algo asi:
    //  Partido 1: Equipo 1 vs. Equipo 2
    //  Partido 2: Equipo 3 vs. Equipo 4
    //  Partido 3: Equipo 5 vs. Equipo 6
    //  Partido 4: Equipo 7 vs. Equipo 8
    //      Partido 5: Ganador 1 vs. Ganador 2
    //      Partido 6: Ganador 3 vs. Ganador 4
    //          Partido 7: Ganador 5 vs. Ganador 6
    private int numEquipos;
    private int numPartidos;
    private Jugador maximoGoleador;
    private Jugador maximoAsistidor;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.numEquipos = 0;
        this.numPartidos = 0;
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);

    }

    public void eliminarEquipo(Equipo equipo) {
        equipos.remove(equipo);
    }

    public void sortearEnfrentamientos() {
        partidos.clear();
        for (int i = 0; i < equipos.size(); i += 2) {
            if (i + 1 < equipos.size()) {
                partidos.add(new Partido(equipos.get(i), equipos.get(i + 1)));
            }
        }
    }

    public void finalizarPartido(Partido partido, Equipo ganador) {
        eliminarEquipo(partido.getEquipoLocal().equals(ganador) ? partido.getEquipoVisitante() : partido.getEquipoLocal());
        if (equipos.size() > 1) {
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
        if (equipos.size() == 1) {
            return equipos.get(0);
        }
        return null;
    }
    public List<Partido> getPartidos() {
        return partidos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumEquipos() {
        return equipos.size();
    }
}

