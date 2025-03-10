
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utilidades {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";


    private static final Random random = new Random();

    /**
     * Genera un ID único para un objeto dentro de la lista pasada como parametro
     *
     * @param lista Lista de objetos que implementan la interfaz `TieneId`
     * @param <T>   Cualquier clase que implemente la interfaz `TieneId`.
     * @return int: ID no repetido en la lista pasada como argumento.
     */
    public static <T extends TieneId> int generarIdUnicaEn(ArrayList<T> lista) {
        // T es un parametro generico de Tipo, es decir establece condiciones para el tipo de dato que se puede recibir,
        // en este caso estamos diciendo que T debe ser una clase que implemente la interfaz TieneId. Y como el parametro que pedimos es un
        // ArrayList<T> quiere decir que aceptamos una lista de cualquier clase que implemente TieneId.

        do {
            int newId = random.nextInt(1000);
            // revisamos que ninguno de los objetos presentes en la lista tenga el ID creado.
            if (lista.stream().noneMatch(e -> e.getId() == newId)) {
                return newId;
            }
        } while (true);
    }

    /**
     * Busca un objeto en una lista por su ID.
     *
     * @param lista Lista de objetos que implementan la interfaz `TieneId`
     * @param id    ID del objeto a buscar
     * @param <T>   Cualquier clase que implemente la interfaz `TieneId`.
     * @return Objeto de la lista con el ID pasado como parametro.
     * @throws RuntimeException en caso de que no se encuentre el objeto con el ID pasado como parametro.
     */
    public static <T extends TieneId> T buscarPorIdEnLista(ArrayList<T> lista, int id) throws RuntimeException {
        T encontrado = null;
        for (T elemento : lista) {
            if (elemento.getId() == id) {
                encontrado = elemento;
                break;
            }
        }

        if (encontrado == null) {
            throw new RuntimeException("No se encontró el elemento con id: " + id);
        }

        return encontrado;
    }

    /**
     * Funcion de utilidad para agregar color al texto a ser impreso por consola.
     * @param texto texto a imprimir
     * @param color color a agregar
     * @return String con el color agregado.
     */
    public static String agregarColor(String texto, String color) {
        return color + texto + ANSI_RESET;
    }

    /**
     * Escribe un archivo con el contenido pasado como parametro. (Siempre sobreescribe el contenido anterior)
     * @param direccionArchivo path al archivo
     * @param contenido string a escribir en el archivo
     * @param agregar booleano que indica si agregar el contenido al archivo o sobreescribirlo.
     * @throws RuntimeException en caso de que haya un error al escribir el archivo.
     */
    public static void escribirArchivo(String direccionArchivo, String contenido, boolean agregar) throws RuntimeException {
        File archivo = new File(direccionArchivo);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(archivo, agregar))) {
            fileWriter.write(contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Lee un archivo y devuelve un Stream de Strings con cada línea del archivo.
     * @param direccionArchivo path del archivo a leer
     * @return Stream<String> con cada línea del archivo.
     * @throws RuntimeException en caso de que no exista el archivo, o haya un error al leerlo.
     */
    public static ArrayList<String> leerArchivo(String direccionArchivo) throws FileNotFoundException {
        if (direccionArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del archivo no puede ser nula o vacía");
        }

        File archivo = new File(direccionArchivo);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + direccionArchivo);
        }

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            // aca estamos asignando a la variable `line` el valor de la siguiente línea del archivo.
            // y al mismo tiempo nos fijamos que no sea null, permitiendo iterar por todas las líneas no nulas del archivo.
            while ((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    // si la línea esta vacia no la agregamos a la lista porque va a generar errores en el codigo.
                    continue;
                }

                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new UncheckedIOException("Error al leer el archivo: " + direccionArchivo, e);
        }
    }


    /**
     * Convierte un string con un formato especial en un HashMap<String, String> donde las claves son los atributos y los valores son los valores de los atributos.
     * @param cadena string con formato `nombreDeAtributo`:`valorDeAtributo`;`nombreDeAtributo`:`valorDeAtributo`;...
     * @return HashMap<String, String> con los atributos y valores del string pasado como parametro.
     */
    public static HashMap<String, String> stringToMap(String cadena) {
        if (cadena.trim().isEmpty()) {
            throw new IllegalArgumentException("El string no puede ser nulo o vacío");
        }

        // El string llega en este formato -> `nombreDeAtributo`:`valorDeAtributo`;`nombreDeAtributo`:`valorDeAtributo`;
        String[] atributos = cadena.split(";");
        // atributos -> [ "nombreDeAtributo:valorAtributo", ... ]

        // creamos un hashMap para guardar los datos que extraemos del string

        HashMap<String, String> atributosMap = new HashMap<>();

        for (String atributo : atributos) {
            // Verificar que haya al menos un ":" en el atributo
            int index = atributo.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Formato incorrecto en el atributo: " + atributo);
            }
            // Los atributos estan en este formato `nombreDeAtributo:valorDeAtributo`
            // agarramos el nombre del atributo, que es todo lo que esta antes de los dos puntos
            // para hacerlo usamos substring que recibe dos indices, el primero desde que parte del string y el segundo hasta donde corta.
            String nombreDelAtributo = atributo.substring(0, atributo.indexOf(":"));
            // Hacemos lo mismo con el valor del atributo, pero en este caso queremos todo lo que esta despues de los dos puntos.
            String valorDelAtributo = atributo.substring(atributo.indexOf(":") + 1);

            // guardamos los atributos en el hashMap.
            atributosMap.put(nombreDelAtributo, valorDelAtributo);
        }

        return atributosMap;
    }

    public static <T> T seleccionarRandomEnLista(ArrayList<T> lista) {
        return lista.get(random.nextInt(lista.size()));
    }
}
