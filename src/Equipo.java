import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String nombre;
    private List<Jugador> jugadores;
    private int numJugadores;
    private int golesMarcados;
    private int golesRecibidos;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
        this.numJugadores = 0;
        this.golesMarcados = 0;
        this.golesRecibidos = 0;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        }



public void eliminarJugador(Jugador jugador) {
    jugadores.remove(jugador);

    }

    public String formacion() {
        StringBuilder formacion = new StringBuilder("Formación del equipo " + nombre + ":\n");
        for (Jugador jugador : jugadores) {
            formacion.append(jugador.getNombre()).append(" ").append(jugador.getApellido()).append("\n");
        }
        return formacion.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public List <Jugador> getJugadores() {
        return jugadores;
    }

    public int getGolesMarcados() {
        return golesMarcados;
    }

    public void setGolesMarcados(int golesMarcados) {
        this.golesMarcados = golesMarcados;
    }

    public int getGolesRecibidos() {
        return golesRecibidos;
    }

    public void setGolesRecibidos(int golesRecibidos) {
        this.golesRecibidos = golesRecibidos;
    }


}
