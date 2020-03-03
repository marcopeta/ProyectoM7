package es.gracia.marcos.proyectom7.ui.consejos;

public class Consejo {
    private String titol, text, autor,trastorno;
    private String id;

    public Consejo(String titol, String text, String autor, String trastorno, String uid) {
        this.titol = titol;
        this.text = text;
        this.autor = autor;
        this.trastorno = trastorno;
        this.id = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTrastorno() {
        return trastorno;
    }

    public void setTrastorno(String trastorno) {
        this.trastorno = trastorno;
    }

    public String getTextCurt(){

        int len = text.length();
        if (len > 15){
            len = 15;
        }
        String textCurt = text.substring(0,len);
        return textCurt;
    }

    @Override
    public String toString() {
        return "Consejo{" +
                "titol='" + titol + '\'' +
                ", text='" + text + '\'' +
                ", autor=" + autor +
                ", trastorno=" + trastorno + '\''+
                '}';
    }
}
