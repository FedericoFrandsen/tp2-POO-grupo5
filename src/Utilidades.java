
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Utilidades {

    private static final Random random = new Random();

    /**
     * Genera un ID único para un objeto dentro de la lista pasada como parametro
     *
     * @param lista Lista de objetos que implementan la interfaz `ConId`
     * @param <T>   Cualquier clase que implemente la interfaz `ConId`.
     * @return int: ID no repetido en la lista pasada como argumento.
     */
    public static <T extends TieneId> int generarIdUnicaEn(ArrayList<T> lista) {
        // T es un parametro generico de Tipo, es decir establece condiciones para el tipo de dato que se puede recibir,
        // en este caso estamos diciendo que T debe ser una clase que implemente la interfaz ConId. Y como el parametro que pedimos es un
        // ArrayList<T> quiere decir que aceptamos una lista de cualquier clase que implemente ConId.
        do {
            int newId = random.nextInt(1000);
            // revisamos que ninguno de los objetos presentes en la lista tenga el ID creado.
            if (lista.stream().noneMatch(e -> e.getId() == newId)) {
                return newId;
            }
        } while (true);
    }

    public static int generarId() {
        return random.nextInt(100000);
    }

    /**
     * Escribe un archivo con el contenido pasado como parametro. (Siempre sobreescribe el contenido anterior)
     * @param direccionArchivo path al archivo
     * @param contenido string a escribir en el archivo
     * @throws RuntimeException en caso de que haya un error al escribir el archivo.
     */
    public static void escribirArchivo(String direccionArchivo, String contenido) throws RuntimeException {
        File archivo = new File(direccionArchivo);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(archivo))) {
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
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new UncheckedIOException("Error al leer el archivo: " + direccionArchivo, e);
        }
    }
}
