/**
 *  Esta clase representa un gol en un partido de fútbol.
 *  Guarda que jugador lo hizo, si hubo asistencia, quien la realizó y el equipo.
 *
 */
class Gol {
    private Jugador goleador;
    private Jugador asistidor;
    private Equipo equipo;

    public Gol(Jugador goleador, Jugador asistidor) {
        this.goleador = goleador;
        this.asistidor = asistidor;
    }

    // Getters y Setters
    public Jugador getGoleador() {
        return goleador;
    }

    public Jugador getAsistidor() {
        return asistidor;
    }
}