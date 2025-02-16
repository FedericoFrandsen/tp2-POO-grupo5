import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Equipo implements ConId {
    private String nombre;
    private List<Jugador> jugadores = new ArrayList<>();
    private int id;
    private int golesMarcados = 0;
    private int golesRecibidos = 0;

    public Equipo(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Devuelve un objeto Equipo a partir de un String con un formato específico -> "[nombreDelAtributo]:[valorDelAtributo];"
     *     Para el equipo sería algo asi:
     *     nombre:Equipo 1;golesMarcados:7;golesRecibidos:1
     * Este metodo lo usamos para convertir un string en un objeto Equipo, por ejemplo cuando leemos un archivo y queremos convertir
     */
    public static Equipo fromString(String equipoString) {


        String[] atributos = equipoString.split(";");


        HashMap<String, String> atributosMap = new HashMap<>();

        for (String atributo : atributos) {
            String nombreDelAtributo = atributo.substring(0, atributo.indexOf(":"));
            String valorDelAtributo = atributo.substring(atributo.indexOf(":") + 1);

            atributosMap.put(nombreDelAtributo, valorDelAtributo);
        }
        Equipo equipo = new Equipo(Integer.parseInt(atributosMap.get("id")),atributosMap.get("nombre"));

        equipo.setGolesMarcados(Integer.parseInt(atributosMap.get("golesMarcados")));
        equipo.setGolesRecibidos(Integer.parseInt(atributosMap.get("golesRecibidos")));

        return equipo;
    }

    /**
     * Devuelve una representación en String del objeto Equipo
     * Este metodo lo llama automáticamente el System.out.println, y sobrescribiéndolo se puede modificar como se muestra por consola el equipo.
     * @return String
     */
    @Override
    public String toString() {
        return "nombre:" + this.nombre + ";golesMarcados:" + this.golesMarcados + ";golesRecibidos:" + this.golesRecibidos;
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

    public List<Jugador> getJugadores() {
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


    public int getId() {
        return id;
    }

}
