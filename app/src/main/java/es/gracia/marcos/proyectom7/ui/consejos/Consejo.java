package es.gracia.marcos.proyectom7.ui.consejos;

public class Consejo {
    private String titol, text, autor;
    private int id;

    public Consejo(String titol, String text, String autor) {
        this.titol = titol;
        this.text = text;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTextConsejo() {
        return text;
    }

    public void setTextConsejo(String text) {
        this.text = text;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    @Override
    public String toString() {
        return "Consejo{" +
                "titol='" + titol + '\'' +
                ", text='" + text + '\'' +
                ", autor=" + autor +
                '}';
    }
}
