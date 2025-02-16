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
            System.out.println("El equipo " + equipo.getNombre() + " ya está en el torneo.");
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
        if (equipoGanador == null) {
            throw new RuntimeException("El torneo no ha finalizado, no se puede guardar en archivo.");
        }

        StringBuilder torneoStringBuilder = new StringBuilder("nombre:" + nombre)
                .append(";equipoGanadorId:")
                .append(equipoGanador.getId())
                .append(";maximoGoleador:");

        if (maximoGoleador == null) {

            torneoStringBuilder.append("No hubo goles");

        } else {

            torneoStringBuilder.append(maximoGoleador.getNombre()).append(" ").append(maximoGoleador.getApellido());
        }

        torneoStringBuilder.append(";maximoAsistidor:");

        if (maximoAsistidor == null) {

            torneoStringBuilder.append("No hubo asistencias");

        } else {

            torneoStringBuilder.append(maximoAsistidor.toFileString()).append(" ").append(maximoAsistidor.getApellido());

        }

        torneoStringBuilder.append(";equiposIds:[");

        for (Equipo equipo : equipos) {

            torneoStringBuilder.append(equipo.getId());

            if (equipos.indexOf(equipo) != equipos.size() - 1) {

                torneoStringBuilder.append(",");

            }
        }

        torneoStringBuilder.append("]");

        return torneoStringBuilder.toString();
    }

    public void finalizarTorneo() {
        ArrayList<Partido> partidosJugados = this.organizadorDePartidos.getPartidosJugados();

        for (Partido partido : partidosJugados) {
            for (Gol gol : partido.getGolesLocal()) {
                if (maximoGoleador == null || gol.getGoleador().getGoles() > maximoGoleador.getGoles()) {
                    maximoGoleador = gol.getGoleador();
                }
            }
            for (Gol gol : partido.getGolesVisitante()) {
                if (maximoGoleador == null || gol.getGoleador().getGoles() > maximoGoleador.getGoles()) {
                    maximoGoleador = gol.getGoleador();
                }
            }
        }

        for (Partido partido : partidosJugados) {
            for (Gol gol : partido.getGolesLocal()) {
                if (maximoAsistidor == null || gol.getAsistidor().getAsistencias() > maximoAsistidor.getAsistencias()) {
                    maximoAsistidor = gol.getAsistidor();
                }
            }
            for (Gol gol : partido.getGolesVisitante()) {
                if (maximoAsistidor == null || gol.getAsistidor().getAsistencias() > maximoAsistidor.getAsistencias()) {
                    maximoAsistidor = gol.getAsistidor();
                }
            }
        }

        this.equipoGanador = this.organizadorDePartidos.getEquiposParaSortear().getFirst();
    }


    public void guardarEnArchivo() {

        Utilidades.escribirArchivo("torneos.txt", this.toFileString(), true);
    }


    public Jugador obtenerMaximoGoleador() {


        // Implementación para obtener el máximo goleador
        return maximoGoleador;
    }

    public Jugador obtenerMaximoAsistidor() {
        // Implementación para obtener el máximo asistidor
        return maximoAsistidor;
    }

    public Equipo getEquipoGanador() {
        return this.equipoGanador;
    }

    public OrganizadorDePartidos getOrganizadorDePartidos() {
        return organizadorDePartidos;
    }

    public void setEquipoGanador(Equipo equipoGanador) throws RuntimeException {
        if (this.organizadorDePartidos.hayPartidosPendientes()) {
            throw new RuntimeException("No se puede definir un equipo ganador si hay partidos pendientes.");

        }

        this.equipoGanador = equipoGanador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDeEquipos() {
        return equipos.size();
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

}

