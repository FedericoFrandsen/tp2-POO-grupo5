class Gol {
    private Jugador goleador;
    private Jugador asistidor;

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