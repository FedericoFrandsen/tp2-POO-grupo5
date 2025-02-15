import java.util.ArrayList;
import java.util.Random;

public class OrganizadorDePartidos {


    private Partido partidoActual = null;


    private ArrayList<Partido> partidosPendientes = new ArrayList<>();
    private ArrayList<Partido> partidosJugados = new ArrayList<>();
    private ArrayList<Equipo> equiposParaSortear = null;
    private Torneo torneo;
    private final Random generator = new Random();

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
    public void sortearEnfrentamientos() {
        // Implementación para sortear los enfrentamientos

        if (this.equiposParaSortear == null) {
            throw new RuntimeException("Antes de sortear los enfrentamientos, se deben agregar los equipos.");

        }

        while(!this.equiposParaSortear.isEmpty()) {
            //  Si solo queda un equipo para sortear quiere decir que este es el ganador del torneo.
            if ( this.equiposParaSortear.size() == 1){
                System.out.println("El equipo ganador es: " + this.equiposParaSortear.getFirst().getNombre());
                break;
            }
            //  Seleccionamos dos equipos random para que se enfrenten
            Equipo equipo1 = this.seleccionarEquipoRandom();
            Equipo equipo2 = this.seleccionarEquipoRandom();

            //  Creamos el partido y lo agregamos a la lista de partidos
            Partido partido = new Partido(equipo1, equipo2);
            partidosPendientes.add(partido);
        }

    }

    /**
     * Inicia el siguiente partido disponible.
     */
    public void iniciarSiguientePartido() {

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
                    break;
                }

                this.sortearEnfrentamientos();
            } else {
                this.partidoActual = partidosPendientes.removeFirst();
            }
        } while (this.partidoActual == null);

    }

    /**
     * Finaliza el partido actual, lo agrega a la lista de partidos jugados
     * y agrega al equipo ganador a la lista de equipos para sortear.
     */
    public void finalizarPartidoActual(Equipo ganador) {
        this.equiposParaSortear.add(ganador);
        this.partidosJugados.add(this.partidoActual);
        this.partidoActual = null;
    }

    public Partido getPartidoActual() {
        return partidoActual;
    }

    /**
     * Selecciona y remueve un equipo del listado de pendientes para sortear.
     * @return equipo seleccionado al azar.
     */
    private Equipo seleccionarEquipoRandom() {
        int index = generator.nextInt(equiposParaSortear.size());
        return equiposParaSortear.remove(index);
    }

}
