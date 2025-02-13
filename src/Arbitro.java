public class Arbitro extends Persona{
    public int partidosDirigidos;

    //const
    public Arbitro(String nombre, String apellido, int edad, int partidosDirigidos) {
        super(nombre, apellido, edad);
        this.partidosDirigidos = partidosDirigidos;
    }

    //get set
    public int getPartidosDirigidos() {
        return partidosDirigidos;
    }

    public void setPartidosDirigidos(int partidosDirigidos) {
        this.partidosDirigidos = partidosDirigidos;
    }

}
