import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class Torneo {
    private String nombre;
    private OrganizadorDePartidos organizadorDePartidos;
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private Equipo equipoGanador = null;
    private Jugador maximoGoleador;
    private Jugador maximoAsistidor;
    private ArrayList<Arbitro> arbitros = new ArrayList<>();

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.organizadorDePartidos = new OrganizadorDePartidos(this);


        // Los arbitros por ahora quedan hardcodeados.
        this.arbitros.add(new Arbitro("Juan", "Perez", 30, 0));
        this.arbitros.add(new Arbitro("Pedro", "Gomez", 35, 0));
        this.arbitros.add(new Arbitro("Carlos", "Rodriguez", 40, 0));
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
            // aca hacemos una copia de la lista porque el organizador de partidos la va a modificar
            // y no queremos que se modifique la lista original
            // el (ArrayList<Equipo>) es un cast, porque el metodo .clone() devuelve un tipo Object
            organizadorDePartidos.setEquiposParaSortear((ArrayList<Equipo>) equipos.clone());

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

            torneoStringBuilder.append(maximoAsistidor.getNombre()).append(" ").append(maximoAsistidor.getApellido());

        }

        torneoStringBuilder.append(";equiposIds:[");

        for (Equipo equipo : equipos) {

            torneoStringBuilder.append(equipo.getId());

            if (equipos.indexOf(equipo) != equipos.size() - 1) {

                torneoStringBuilder.append(",");

            }
        }

        torneoStringBuilder.append("]\n");

        return torneoStringBuilder.toString();
    }

    public void finalizarTorneo() {
        ArrayList<Partido> partidosJugados = this.organizadorDePartidos.getPartidosJugados();

        ArrayList<Gol> golesTotales = new ArrayList<>();

        for (Partido partido : partidosJugados) {
            golesTotales.addAll( partido.getGolesTotales());
        }

        HashMap<Integer,Integer> goleadores = new HashMap<>();
        HashMap<Integer,Integer> asistidores = new HashMap<>();

        for (Gol gol : golesTotales) {
            goleadores.putIfAbsent(gol.getGoleador().getId(), 1);
            goleadores.computeIfPresent(gol.getGoleador().getId(), (k,v) -> v + 1);

            if (gol.getAsistidor() != null) {
                asistidores.putIfAbsent(gol.getAsistidor().getId(), 1);
                asistidores.computeIfPresent(gol.getAsistidor().getId(), (k,v) -> v + 1);
            }
        }


        int maximoGoleadorId = -1;
        int maximoAsistidorId = -1;

        if(!goleadores.isEmpty()) {
            maximoGoleadorId = Collections.max(goleadores.entrySet(), Map.Entry.comparingByValue()).getKey();
        }

        if (!asistidores.isEmpty()) {
            maximoAsistidorId = Collections.max(asistidores.entrySet(), Map.Entry.comparingByValue()).getKey();
        }


        System.out.println(maximoGoleadorId);
        System.out.println(maximoAsistidorId);


        for (Equipo equipo : this.equipos) {
            equipo.incrementarTorneosJugados();

            if (maximoGoleadorId != -1 && maximoAsistidorId != -1) {
                try {

                    this.maximoGoleador = Utilidades.buscarPorIdEnLista(equipo.getJugadores(), maximoGoleadorId);
                    this.maximoAsistidor = Utilidades.buscarPorIdEnLista(equipo.getJugadores(), maximoAsistidorId);

                } catch (RuntimeException _) {
                    System.out.println("Catch aca en finalizar torneo.");
                }
            }
        }


        this.equipoGanador = this.organizadorDePartidos.getEquiposParaSortear().getFirst();

        this.equipoGanador.incrementarTorneosGanados();
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

    public void setEquipoGanador(Equipo equipoGanador) throws RuntimeException {
        if (this.organizadorDePartidos.hayPartidosPendientes()) {
            throw new RuntimeException("No se puede definir un equipo ganador si hay partidos pendientes.");

        }

        this.equipoGanador = equipoGanador;
    }

    public OrganizadorDePartidos getOrganizadorDePartidos() {
        return organizadorDePartidos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDeEquipos() {
        return equipos.size();
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public ArrayList<Arbitro> getArbitros() {
        return arbitros;
    }

    public void setArbitros(ArrayList<Arbitro> arbitros) {
        this.arbitros = arbitros;
    }
}

