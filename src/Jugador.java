public class Jugador extends Persona {
    private boolean esTitular;
    private int goles;
    private int asistencias;

    public Jugador(String nombre, String apellido, int edad, boolean esTitular, int goles, int asistencias) {
       super(nombre, apellido, edad);
        this.esTitular = esTitular;
        this.goles = 0;
        this.asistencias = 0;
    }

    // Getters y Setters

    public boolean isEsTitular() {
        return esTitular;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }
}
