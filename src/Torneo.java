import java.util.ArrayList;
import java.util.List;

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
            // estoy casi seguro que vamos a tener un problema con el tema de referencias de listas, pero ya veremos.
            organizadorDePartidos.setEquiposParaSortear(equipos);

        } else {
            throw new RuntimeException("El torneo no puede iniciar, se necesitan 8 equipos");

        }
    }


    public String toFileString() {
        StringBuilder torneoStringBuilder = new StringBuilder("torneo:" + nombre)
                .append(";equipoGanador:")
                .append(equipoGanador)
                .append(";maximoGoleador:")
                .append(maximoGoleador)
                .append(";maximoAsistidor:")
                .append(maximoAsistidor)
                .append(";equipos:[");

        for (Equipo equipo : equipos) {
            torneoStringBuilder.append(equipo.toFileString().replace(";", "|"));
            if(equipos.indexOf(equipo) != equipos.size() - 1) {
                torneoStringBuilder.append(",");
            }
        }

        torneoStringBuilder.append("]");


        return torneoStringBuilder.toString();
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
    public List<Equipo> getEquipos() {
        return equipos;
}

}

