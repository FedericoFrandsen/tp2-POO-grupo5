import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String nombre;
    private List<Jugador> jugadores = new ArrayList<>();

    private int golesMarcados = 0;
    private int golesRecibidos = 0;

    public Equipo(String nombre) {
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
        Equipo equipo = new Equipo("Nombre por default, nunca deberia figurar");
        for (String atributo : atributos) {
            String nombreDelAtributo = atributo.substring(0, atributo.indexOf(":"));
            String valorDelAtributo = atributo.substring(atributo.indexOf(":") + 1);

            switch (nombreDelAtributo) {
                case "nombre" -> equipo = new Equipo(valorDelAtributo);
                case "golesMarcados" -> equipo.setGolesMarcados(Integer.parseInt(valorDelAtributo));
                case "golesRecibidos" -> equipo.setGolesRecibidos(Integer.parseInt(valorDelAtributo));
            }

        }


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


}
