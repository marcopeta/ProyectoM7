package es.gracia.marcos.proyectom7.ui.alimentos;

public class Alimento {
    private String nombre;
    private String marca;
    private Float cantidad;
    private String unidad;
    private Float grasas;
    private Float hidratos;
    private Float proteinas;
    private int calorias;


    public Alimento(String nombre, String marca, Float cantidad, String unidad, Float grasas, Float hidratos, Float proteinas, int calorias) {
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.grasas = grasas;
        this.hidratos = hidratos;
        this.proteinas = proteinas;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Float getGrasas() {
        return grasas;
    }

    public void setGrasas(Float grasas) {
        this.grasas = grasas;
    }

    public Float getHidratos() {
        return hidratos;
    }

    public void setHidratos(Float hidratos) {
        this.hidratos = hidratos;
    }

    public Float getProteinas() {
        return proteinas;
    }

    public void setProteinas(Float proteinas) {
        this.proteinas = proteinas;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
