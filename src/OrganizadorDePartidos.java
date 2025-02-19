import java.util.ArrayList;
import java.util.Random;

public class OrganizadorDePartidos {


    private Partido partidoActual = null;


    private ArrayList<Partido> partidosPendientes = new ArrayList<>();
    private ArrayList<Partido> partidosJugados = new ArrayList<>();
    private ArrayList<Equipo> equiposParaSortear = null;
    private Torneo torneo;

    public OrganizadorDePartidos(Torneo torneo) {
        this.torneo = torneo;
        // Constructor
    }

    public void setEquiposParaSortear(ArrayList<Equipo> equiposParaSortear) {
        this.equiposParaSortear = equiposParaSortear;
    }

    /**
     * Método que se encarga de distribuir los equipos disponibles en partidos.
     */
    public void sortearEnfrentamientos() throws RuntimeException {
        // Implementación para sortear los enfrentamientos

        if (this.equiposParaSortear == null) {
            throw new RuntimeException("Antes de sortear los enfrentamientos, se deben agregar los equipos.");

        }

        while(!this.equiposParaSortear.isEmpty()) {
            //  Si solo queda un equipo para sortear quiere decir que este es el ganador del torneo.
            if ( this.equiposParaSortear.size() == 1) {
                System.out.println("El equipo ganador es: " + this.equiposParaSortear.getFirst().getNombre());
                this.torneo.setEquipoGanador(this.equiposParaSortear.getFirst());
                break;
            }
            //  Seleccionamos dos equipos random para que se enfrenten
            Equipo equipo1 = this.seleccionarEquipoRandom();
            Equipo equipo2 = this.seleccionarEquipoRandom();

            //  Creamos el partido y lo agregamos a la lista de partidos
            Arbitro arbitro = Utilidades.seleccionarRandomEnLista(torneo.getArbitros());

            Partido partido = new Partido(equipo1, equipo2, arbitro);

            partidosPendientes.add(partido);
        }

    }

    public boolean hayPartidosPendientes() {

        return !this.partidosPendientes.isEmpty() || this.equiposParaSortear.size() > 1 ;
    }

    /**
     * Inicia el siguiente partido disponible.
     * @return Partido que se va a jugar.
     * @throws RuntimeException si hay un partido en curso o no hay equipos para sortear .
     */
    public Partido iniciarSiguientePartido() throws RuntimeException {

        //  Si hay un partido en curso, no se puede iniciar otro.
        if (this.partidoActual != null) {

            throw new RuntimeException("No se puede iniciar un nuevo partido hasta que el actual haya finalizado");
        }

        // Cuando tratamos de iniciar un nuevo partido primero revisamos si quedan partidos pendientes del último sorteo,
        // si no quedan partidos pendientes entonces volvemos a sortear los enfrentamientos. Si no hay equipos restantes
        // algo salió mal, si solo queda un equipo el torneo termino y el equipo restante es el ganador.
        do {
            if (this.partidosPendientes.isEmpty()) {
                if (this.equiposParaSortear.isEmpty()) {
                    throw new RuntimeException("Por alguna razon los equipos estan vacios, esto no deberia pasar.");
                } else if (this.equiposParaSortear.size() == 1) {
                    System.out.println("El equipo ganador es: " + this.equiposParaSortear.getFirst().getNombre());
                    this.torneo.setEquipoGanador(this.equiposParaSortear.getFirst());
                    break;
                }

                this.sortearEnfrentamientos();
            } else {
                this.partidoActual = partidosPendientes.removeFirst();
            }
        } while (this.partidoActual == null);

        return partidoActual;

    }

    /**
     * Finaliza el partido actual, lo agrega a la lista de partidos jugados
     * y agrega al equipo ganador a la lista de equipos para sortear.
     */
    public void finalizarPartidoActual(Equipo ganador) {

        this.partidoActual.getArbitro().incrementarPartidosDirigidos();
        this.equiposParaSortear.add(ganador);
        this.partidosJugados.add(this.partidoActual);
        this.partidoActual = null;
    }

    public Partido getPartidoActual() {
        return partidoActual;
    }

    public ArrayList<Partido> getPartidosPendientes() {
        return partidosPendientes;
    }

    public ArrayList<Partido> getPartidosJugados() {
        return partidosJugados;
    }

    public ArrayList<Equipo> getEquiposParaSortear() {
        return equiposParaSortear;
    }

    /**
     * Selecciona y remueve un equipo del listado de pendientes para sortear.
     * @return equipo seleccionado al azar.
     */
    private Equipo seleccionarEquipoRandom() {
        Equipo equipo = Utilidades.seleccionarRandomEnLista(equiposParaSortear);
        equiposParaSortear.remove(equipo);
        return equipo;
    }

}
