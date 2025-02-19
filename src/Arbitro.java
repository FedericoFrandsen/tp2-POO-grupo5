public class Arbitro extends Persona {
    private int partidosDirigidos;


    public Arbitro(String nombre, String apellido, int edad, int partidosDirigidos) {
        super(nombre, apellido, edad);
        this.partidosDirigidos = partidosDirigidos;
    }


    public int getPartidosDirigidos() {
        return partidosDirigidos;
    }

    public void setPartidosDirigidos(int partidosDirigidos) {
        this.partidosDirigidos = partidosDirigidos;
    }

    public void incrementarPartidosDirigidos() {
        this.partidosDirigidos++;
    }

}
