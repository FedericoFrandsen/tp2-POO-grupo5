public class Jugador {
    private String nombre;
    private String apellido;
    private int edad;
    private boolean esTitular;
    private int goles;
    private int asistencias;

    public Jugador(String nombre, String apellido, int edad, boolean esTitular) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.esTitular = esTitular;
        this.goles = 0;
        this.asistencias = 0;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

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
