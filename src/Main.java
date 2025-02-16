import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main {

    private static ArrayList<Equipo> equipos = new ArrayList<>();

    public static void main(String[] args) {


//        Torneo torneo = new Torneo("Torneo 1");
//
//        Equipo equipo = new Equipo(1, "Equipo 1");
//        equipo.agregarJugador(new Jugador(1, "Jugador 1", "Apellido 1", 20, 0, 0, "arquero"));
//        torneo.agregarEquipo(equipo);
//        torneo.agregarEquipo(new Equipo(2, "Equipo 2"));
//
//        Utilidades.escribirArchivo("./torneos.txt", torneo.toFileString(), true);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Leer archivo de equipos");
            System.out.println("2. Crear equipo");
            System.out.println("3. Crear y asginar jugador");
            System.out.println("4. Crear torneo ");
            System.out.println("5. Iniciar torneo");
            System.out.println("6. Carga de resultados");
            System.out.println("7. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de linea que se produce al presionar enter

            switch (option) {
                case 1:
                    leerArchivoDeEquipos();
                    break;
                case 2:
                    crearEquipo(scanner);
                    break;
                case 3:
                    ingresarJugadores(equipos.getFirst());
                    break;
                case 4:
                    crearTorneo();
                    break;
                case 5:
                    iniciarTorneo();
                    break;
                case 6:
                    cargaDeResultados();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void cargaDeResultados() {
    }

    private static void iniciarTorneo() {

    }

    private static void crearTorneo() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el nombre del torneo:");
            String nombre = scanner.nextLine();

            Torneo torneo = new Torneo(nombre);

            ArrayList<Equipo> equiposDisponibles = new ArrayList<>();
            try {
                ArrayList<String> lineas = Utilidades.leerArchivo("./equipos.txt");
                for (String line : lineas) {
                    Equipo equipo = Equipo.fromString(line);
                    equiposDisponibles.add(equipo);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Equipos disponibles para seleccionar:");
            for (Equipo equipo : equiposDisponibles) {
                System.out.println("ID: " + equipo.getId() + " - " + equipo.getNombre());
            }

            System.out.println("Ingrese los IDs de los equipos que desea agregar al torneo, separados por comas:");
            String[] ids = scanner.nextLine().split(",");
            for (String idStr : ids) {
                int equipoId = Integer.parseInt(idStr.trim());
                boolean equipoEncontrado = false;
                for (Equipo equipo : equiposDisponibles) {
                    if (equipo.getId() == equipoId) {
                        torneo.agregarEquipo(equipo);
                        equipoEncontrado = true;
                        break;
                    }
                }
                if (!equipoEncontrado) {
                    System.out.println("ID de equipo no válido: " + equipoId);
                }
            }

            System.out.println("Torneo creado: " + torneo.getNombre());
            System.out.println("Equipos en el torneo:");
            for (Equipo equipo : torneo.getEquipos()) {
                System.out.println(equipo.getNombre());
            }

       /* Escribir datos en el archivo??
           try (BufferedWriter writer = new BufferedWriter(new FileWriter("./torneos.txt", true))) {
                writer.write(torneo.toFileString());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
        }


    private static void leerArchivoDeEquipos() {
        try {

            ArrayList<String> lineas = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineas) {

                Equipo equipo = Equipo.fromString(line);

                System.out.println(equipo);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void crearEquipo(Scanner scanner) {

        System.out.println("Ingrese el nombre del equipo:");
        String nombre = scanner.nextLine();

        /* comparar el nombre del equipo con los nombres de los equipos existentes */
        try {

            ArrayList<String> lineas = Utilidades.leerArchivo("./equipos.txt");

            for (String line : lineas) {

                Equipo equipo = Equipo.fromString(line);

                if (equipo.getNombre().equals(nombre)) {
                    System.out.println("El equipo ya existe");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /* creo el equipo */
        Equipo equipo = new Equipo(Utilidades.generarId(), nombre);
        equipos.add(equipo);
        System.out.println("Equipo creado: " + equipo);

          /*  try (BufferedWriter writer = new BufferedWriter(new FileWriter("./equipos.txt", true))) {
                writer.write(equipo.toString());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
    }


    public static void ingresarJugadores(Equipo equipo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Cuántos jugadores deseas agregar?");
        int numJugadores = scanner.nextInt();
        scanner.nextLine(); // es para que el siguiente ingreso no tome el salto de linea que se produce al presionar enter

        for (int i = 0; i < numJugadores; i++) {
            System.out.println("Ingrese el nombre del jugador:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el apellido del jugador:");
            String apellido = scanner.nextLine();
            System.out.println("Ingrese la edad del jugador:");
            int edad = scanner.nextInt();
            System.out.println("Ingrese la posicion del jugador (arquero, defensor, delantero):");
            String posicion = scanner.nextLine();


            Jugador jugador = new Jugador(Utilidades.generarId(), nombre, apellido, edad, 0, 0, posicion);
            equipo.agregarJugador(jugador);
        }

        System.out.println("Jugadores agregados:");
        System.out.println(equipo.formacion());
    }
}



