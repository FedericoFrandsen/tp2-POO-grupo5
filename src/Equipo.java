public class Equipo {
    private String nombre;
    private Jugador[] jugadores;
    private int numJugadores;
    private int golesMarcados;
    private int golesRecibidos;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new Jugador[10]; // Suponiendo un máximo de 10 jugadores por equipo
        this.numJugadores = 0;
        this.golesMarcados = 0;
        this.golesRecibidos = 0;
    }

    public void agregarJugador(Jugador jugador) {
        if (numJugadores < jugadores.length) {
            jugadores[numJugadores++] = jugador;
        }
    }

    public void eliminarJugador(Jugador jugador) {
        for (int i = 0; i < numJugadores; i++) {
            if (jugadores[i].equals(jugador)) {
                jugadores[i] = jugadores[--numJugadores];
                jugadores[numJugadores] = null;
                break;
            }
        }
    }

    public String formacion() {
        StringBuilder formacion = new StringBuilder("Formación del equipo " + nombre + ":\n");
        for (int i = 0; i < numJugadores; i++) {
            formacion.append(jugadores[i].getNombre()).append(" ").append(jugadores[i].getApellido()).append("\n");
        }
        return formacion.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador[] getJugadores() {
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
