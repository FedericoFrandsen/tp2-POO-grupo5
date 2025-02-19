public class Jugador extends Persona implements TieneId {

    private int goles = 0;
    private int asistencias = 0;
    private int id;


    public Jugador(int id, String nombre, String apellido, int edad) {
        super(nombre, apellido, edad);
        this.id = id;
    }

    public Jugador(int id,String nombre, String apellido, int edad, int goles, int asistencias) {
        this(id, nombre, apellido, edad);
        this.id = id;
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

        var atributosMap = Utilidades.stringToMap(jugadorString);

        return new Jugador(
                Integer.parseInt(atributosMap.get("id")),
                atributosMap.get("nombre"),
                atributosMap.get("apellido"),
                Integer.parseInt(atributosMap.get("edad")),
                Integer.parseInt(atributosMap.get("goles")),
                Integer.parseInt(atributosMap.get("asistencias"))
        );
    }

    /**
     * Devuelve una representación en String del objeto Jugador para almacenar en un archivo.
     * @return String
     */
    public String toFileString() {

        return "id:" + this.getId() +
                ";nombre:" +
                this.getNombre() +
                ";apellido:" +
                this.getApellido() +
                ";edad:" +
                this.getEdad() +
                ";goles:" +
                this.getGoles() +
                ";asistencias:" +
                this.getAsistencias() +
                "\n";
    }


    public String toString() {
        return this.getId() + ". " + this.getNombre() +
                " " +
                this.getApellido() +
                " Edad: " +
                this.getEdad() +
                " Goles:" +
                this.getGoles() +
                " Asistencias: " +
                this.getAsistencias();
    }

    public void incrementarGoles() {
        this.goles++;
    }

    public void incrementarAsistencias() {
        this.asistencias++;
    }

    // Getters y Setters
    @Override
    public int getId() {
        return id;
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
