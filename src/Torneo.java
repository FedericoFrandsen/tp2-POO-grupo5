import java.util.ArrayList;

public class Torneo {
    private String nombre;
    private OrganizadorDePartidos organizadorDePartidos;
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private Equipo equipoGanador = null;
    private Jugador maximoGoleador;
    private Jugador maximoAsistidor;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.organizadorDePartidos = new OrganizadorDePartidos(this);
    }

    public void agregarEquipo(Equipo equipo) {
        if (equipos.size() == 8) {
            System.out.println("El torneo ya tiene 8 equipos, no se pueden agregar más.");
            return;
        }
        if (equipos.contains(equipo)) {
            System.out.println("El equipo " + equipo.getNombre() +  " ya está en el torneo.");
            return;
        }
        equipos.add(equipo);
    }

    public void iniciarTorneo() throws RuntimeException {
        if (equipos.size() == 8) {
            organizadorDePartidos.setEquiposParaSortear(equipos);

        } else {
            throw new RuntimeException("El torneo no puede iniciar, se necesitan 8 equipos");

        }
    }

    public void eliminarEquipo(Equipo equipo) {

        equipos.remove(equipo);
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
        return this.equipoGanador;
    }

    public OrganizadorDePartidos getOrganizadorDePartidos() {
        return organizadorDePartidos;
    }

    public void setEquipoGanador(Equipo equipoGanador) throws RuntimeException {
        if(this.organizadorDePartidos.hayPartidosPendientes()) {
            throw new RuntimeException("No se puede definir un equipo ganador si hay partidos pendientes.");

        }

        this.equipoGanador = equipoGanador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumEquipos() {
        return equipos.size();
    }
}

