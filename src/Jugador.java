import java.util.HashMap;
import java.util.Map;

public class Jugador extends Persona {

    private int goles;
    private int asistencias;

    public Jugador(String nombre, String apellido, int edad, int goles, int asistencias) {
        super(nombre, apellido, edad);
        this.goles = goles;
        this.asistencias = asistencias;
    }

    /**
     * Devuelve un objeto Jugador a partir de un String con un formato específico -> "[nombreDelAtributo]:[valorDelAtributo];"
     *     Para el equipo sería algo asi:
     *     nombre:Lionel;apellido:Messi;edad:33;goles:198;asistencias:100
     * Este metodo lo usamos para convertir un string en un objeto Jugador, por ejemplo cuando leemos un archivo y queremos convertir los datos en Jugadores.
     */
    public static Jugador fromString(String jugadorString) {
        String[] atributos = jugadorString.split(";");

        HashMap<String, String> atributosMap = new HashMap<>();

        for (String atributo : atributos) {
            String nombreDelAtributo = atributo.substring(0, atributo.indexOf(":"));
            String valorDelAtributo = atributo.substring(atributo.indexOf(":") + 1);

            atributosMap.put(nombreDelAtributo, valorDelAtributo);
        }

        return new Jugador(atributosMap.get("nombre"), atributosMap.get("apellido"), Integer.parseInt(atributosMap.get("edad")), Integer.parseInt(atributosMap.get("goles")), Integer.parseInt(atributosMap.get("asistencias")));
    }

    public String toString() {
        return "nombre:" + this.getNombre() + ";apellido:" + this.getApellido() + ";edad:" + this.getEdad() + ";goles:" + this.goles + ";asistencias:" + this.asistencias;
    }

    public void incrementarGoles() {
        this.goles++;
    }

    public void incrementarAsistencias() {
        this.asistencias++;
    }

    // Getters y Setters
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
