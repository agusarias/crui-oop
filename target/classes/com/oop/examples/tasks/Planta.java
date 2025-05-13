public class Planta {
    String nombre;
    boolean necesitaAgua;

    public Planta(String nombre, boolean necesitaAgua) {
        this.nombre = nombre;
        this.necesitaAgua = necesitaAgua;
    }

    public void mostrarInformacion() {
        System.out.println("Nombre de la planta: " + nombre);
        System.out.println("¿Necesita agua frecuentemente?: " + (necesitaAgua ? "Sí" : "No"));
    }

    public void crecer() {
        System.out.println(nombre + " está creciendo.");
    }
}
