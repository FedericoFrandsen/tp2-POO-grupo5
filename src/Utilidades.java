
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Utilidades {

    private static final Random random = new Random();

    /**
     * Genera un ID único para un objeto dentro de la lista pasada como argumento
     *
     * @param lista Lista de objetos que implementan la interfaz `ConId`
     * @param <T>   Cualquier clase que implemente la interfaz `ConId`.
     * @return int: id no repetido en la lista pasada como argumento.
     */
    public static <T extends ConId> int generarIdUnicaEn(ArrayList<T> lista) {

        do {
            int newId = random.nextInt(1000);
            // revisamos que ninguno de los objetos presentes en la lista tenga el ID creado.
            if (lista.stream().noneMatch(e -> e.getId() == newId)) {
                return newId;
            }
        } while (true);
    }

    /**
     * Escribe un archivo con el contenido pasado como argumento. (Siempre sobreescribe el contenido anterior)
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
    public static Stream<String> leerArchivo(String direccionArchivo) throws RuntimeException {
        File archivo = new File(direccionArchivo);

        if (archivo.exists()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(archivo))) {
                return fileReader.lines();

            } catch (IOException e) {
                System.err.println(e.toString());
                throw new RuntimeException(e);
            }

        } else {
            throw new RuntimeException("El archivo no existe, direccion de archivo: " + direccionArchivo);
        }


    }
}
