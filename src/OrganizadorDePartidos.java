import java.util.ArrayList;
import java.util.Random;

public class OrganizadorDePartidos {


    private Partido partidoActual = null;
    // Los partidos tendrían que tener una estructura algo asi:
    //  Partido 1: Equipo 1 vs. Equipo 2
    //  Partido 2: Equipo 3 vs. Equipo 4
    //  Partido 3: Equipo 5 vs. Equipo 6
    //  Partido 4: Equipo 7 vs. Equipo 8
    //      Partido 5: Ganador 1 vs. Ganador 2
    //      Partido 6: Ganador 3 vs. Ganador 4
    //          Partido 7: Ganador 5 vs. Ganador 6

    public ArrayList<Partido> partidos = new ArrayList<>();
    public ArrayList<Equipo> equipos;
    private final Random generator = new Random();

    public OrganizadorDePartidos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
        // Constructor
    }

    public static void main(String[] args) {

        ArrayList<Equipo> equipos = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Equipo equipo = new Equipo("Equipo " + i);
            equipos.add(equipo);
        }


        OrganizadorDePartidos organizador = new OrganizadorDePartidos(equipos);
        organizador.sortearEnfrentamientos();
        System.out.println(organizador.equipos);
        System.out.println(organizador.partidoActual);
        System.out.println(organizador.partidos);
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        System.out.println(organizador.partidos);
        System.out.println(organizador.equipos);
        System.out.println(organizador.partidoActual);
        organizador.sortearEnfrentamientos();
        System.out.println(organizador.partidos);
        System.out.println(organizador.equipos);
        System.out.println(organizador.partidoActual);
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        System.out.println(organizador.partidos);
        System.out.println(organizador.equipos);
        System.out.println(organizador.partidoActual);
        organizador.sortearEnfrentamientos();
        System.out.println(organizador.partidos);
        System.out.println(organizador.equipos);
        System.out.println(organizador.partidoActual);
        organizador.iniciarSiguientePartido();
        organizador.finalizarPartidoActual();
        organizador.iniciarSiguientePartido();

    }

    public void sortearEnfrentamientos() {
        // Implementación para sortear los enfrentamientos

        while(!equipos.isEmpty()) {

            if ( this.equipos.size() == 1){
                System.out.println("El equipo ganador es: " + this.equipos.getFirst().getNombre());
                break;
            }

            Equipo equipo1 = this.seleccionarEquipoRandom();
            Equipo equipo2 = this.seleccionarEquipoRandom();


            Partido partido = new Partido(equipo1, equipo2);
            partidos.add(partido);
        }

    }

    public void iniciarSiguientePartido() {

        if (this.partidoActual != null) {
            // Finalizar el partido actual
            // Implementación para finalizar el partido
            throw new RuntimeException("No se puede iniciar un nuevo partido hasta que el actual haya finalizado");
        }




        do {
            if (this.partidos.isEmpty()) {
                if (this.equipos.isEmpty()) {
                    throw new RuntimeException("Por alguna razon los equipos estan vacios, esto no deberia pasar.");
                } else if (this.equipos.size() == 1) {
                    System.out.println("El equipo ganador es: " + this.equipos.getFirst().getNombre());
                    break;
                }

                this.sortearEnfrentamientos();
            } else {
                this.partidoActual = partidos.removeFirst();
            }
        } while (this.partidoActual == null);

    }

    public void finalizarPartidoActual() {
        // Aca tenemos que pensar la logica para determinar el ganador del partido supongo, o también podría estar pensada antes
        // y que el partido tenga un atributo "ganador" o algo asi.
        this.equipos.add(generator.nextBoolean() ? this.partidoActual.getEquipoLocal() : this.partidoActual.getEquipoVisitante());
        this.partidoActual = null;
    }

    public Partido getPartidoActual() {
        return partidoActual;
    }

    private Equipo seleccionarEquipoRandom() {
        int index = generator.nextInt(equipos.size());
        return equipos.remove(index);
    }

}
