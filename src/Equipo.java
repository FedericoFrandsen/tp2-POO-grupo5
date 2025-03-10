import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Equipo implements TieneId {
    private String nombre;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private int id;
    private int golesMarcados = 0;
    private int golesRecibidos = 0;
    private int torneosJugados = 0;
    private int torneosGanados = 0;

    public Equipo(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Equipo(int id, String nombre, int golesMarcados, int golesRecibidos, int torneosJugados, int torneosGanados) {
        this(id, nombre);
        this.golesMarcados = golesMarcados;
        this.golesRecibidos = golesRecibidos;
        this.torneosJugados = torneosJugados;
        this.torneosGanados = torneosGanados;

    }

    /**
     * Devuelve un objeto Equipo a partir de un String con un formato específico -> "[nombreDelAtributo]:[valorDelAtributo];"
     *     Para el equipo sería algo asi:
     *     nombre:Equipo 1;golesMarcados:7;golesRecibidos:1
     * Este metodo lo usamos para convertir un string en un objeto Equipo, por ejemplo cuando leemos un archivo y queremos convertir los datos en Equipos.
     * @param equipoString String con los atributos del equipo.
     * @param jugadores Lista de jugadores para poder asignarlos al equipo sin crear instancias duplicadas.
     */
    public static Equipo fromString(String equipoString, ArrayList<Jugador> jugadores) {


        var atributosMap = Utilidades.stringToMap(equipoString);

        // Creamos una instancia del equipo con los valores extraidos del string.
        Equipo equipo = new Equipo(
                Integer.parseInt(atributosMap.get("id")),
                atributosMap.get("nombre"),
                Integer.parseInt(atributosMap.get("golesMarcados")),
                Integer.parseInt(atributosMap.get("golesRecibidos")),
                Integer.parseInt(atributosMap.get("torneosJugados")),
                Integer.parseInt(atributosMap.get("torneosGanados"))
        );


        String jugadoresString = atributosMap.get("jugadoresIds");

        // Agarramos solo los ids de los jugadores, que estan en una string con este formato [id1,id2,id3,...]
        // Entonces extraemos primero todo lo que esta entre corchetes ([]) y luego separamos los ids por comas.
        jugadoresString = jugadoresString.substring(jugadoresString.indexOf("[") + 1, jugadoresString.indexOf("]"));

        for (String jugadorId : jugadoresString.split(",")) {

            try {
                Jugador jugador = Utilidades.buscarPorIdEnLista(jugadores, Integer.parseInt(jugadorId));

                equipo.agregarJugador(jugador);

            } catch (RuntimeException e) {
                System.out.println("Error al agregar jugador al equipo " + equipo.getNombre() + ": " + e.getMessage());

            }


        }


        return equipo;
    }

    /**
     * Devuelve una representación en String del objeto Equipo para almacenar en un archivo.
     * @return String
     */
    public String toFileString() {
        StringBuilder equipoStringBuilder = new StringBuilder("id:" + this.getId())
                .append(";nombre:")
                .append(this.nombre)
                .append(";golesMarcados:")
                .append(this.golesMarcados)
                .append(";golesRecibidos:")
                .append(this.golesRecibidos)
                .append(";torneosJugados:")
                .append(this.torneosJugados)
                .append(";torneosGanados:")
                .append(this.torneosGanados)
                .append(";jugadoresIds:[");

        for (Jugador jugador : jugadores) {
            equipoStringBuilder.append(jugador.getId());

            if(jugadores.indexOf(jugador) != jugadores.size() - 1) {
                equipoStringBuilder.append(",");
            }
        }

        equipoStringBuilder.append("]\n");

        return equipoStringBuilder.toString();
    }

    public boolean agregarJugador(Jugador jugador) {

        if (jugadores.size() == 5) {
            System.out.println("El equipo ya tiene 5 jugadores, no se pueden agregar más.");
            return false;
        }

        if (jugadores.contains(jugador)) {
            System.out.println("El jugador " + jugador.getNombre() + " " + jugador.getApellido() + " ya está en el equipo.");
            return false;
        }

        jugadores.add(jugador);

        return true;
    }

    public void eliminarJugador(Jugador jugador) {

        jugadores.remove(jugador);

    }

    public String formacion() {
        StringBuilder formacion = new StringBuilder();
        formacion.append("============================================\n");
        formacion.append(String.format("Formación de " + Utilidades.agregarColor("%s\n", Utilidades.ANSI_PURPLE), nombre));
        formacion.append(String.format("%-20s %-20s\n", "Nombre", "Apellido"));
        formacion.append(String.format("%-20s %-20s\n", "--------------------", "--------------------"));
        for (Jugador jugador : jugadores) {
            formacion.append(String.format("%-20s %-20s\n", jugador.getNombre(), jugador.getApellido()));
        }
        formacion.append("--------------------------------------------\n");
        return formacion.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Jugador> getJugadores() {
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

    public void incrementarGolesMarcados() {
        this.golesMarcados++;
    }

    public void incrementarGolesRecibidos() {
        this.golesRecibidos++;
    }

    public int getId() {
        return id;
    }

    public int getTorneosJugados() {
        return torneosJugados;
    }

    public void incrementarTorneosJugados() {
        this.torneosJugados++;
    }

    public void setTorneosJugados(int torneosJugados) {
        this.torneosJugados = torneosJugados;
    }

    public int getTorneosGanados() {
        return torneosGanados;
    }

    public void setTorneosGanados(int torneosGanados) {
        this.torneosGanados = torneosGanados;
    }

    public void incrementarTorneosGanados() {
        this.torneosGanados++;
    }

    public void setNombre(String nombre) {this.nombre = nombre;}

    public Jugador getJugadorPorId(int id) throws RuntimeException {
        return Utilidades.buscarPorIdEnLista(this.jugadores, id);
    }

}

