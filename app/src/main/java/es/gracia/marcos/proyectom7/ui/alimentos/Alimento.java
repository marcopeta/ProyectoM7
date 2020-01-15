package es.gracia.marcos.proyectom7.ui.alimentos;

public class Alimento {
    private String nombre;
    private int calorias;

    public Alimento(String nombre, int calorias) {
        this.nombre = nombre;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
