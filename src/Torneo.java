import java.util.ArrayList;

public class Torneo {
    private String nombre;
    private OrganizadorDePartidos organizadorDePartidos;
    private ArrayList<Equipo> equipos = new ArrayList<>();

    private Jugador maximoGoleador;
    private Jugador maximoAsistidor;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.organizadorDePartidos = new OrganizadorDePartidos(this);
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public void iniciarTorneo() {
        if (equipos.size() == 4 || equipos.size() == 8 || equipos.size() == 16) {
            organizadorDePartidos.setEquiposParaSortear(equipos);

        } else {
            System.out.println("El torneo no puede iniciar, se necesitan 8 o 16 equipos");

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
        if (equipos.size() == 1) {
            return equipos.getFirst();
        }
        return null;
    }

    public OrganizadorDePartidos getOrganizadorDePartidos() {
        return organizadorDePartidos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumEquipos() {
        return equipos.size();
    }
}

