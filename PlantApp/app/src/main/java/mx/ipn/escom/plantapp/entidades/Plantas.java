package mx.ipn.escom.plantapp.entidades;

import android.graphics.Bitmap;

public class Plantas {

    private Integer id;
    private String especie;
    private String edad;
    private String detalles;
    private Bitmap imagen;
    private Boolean maceta;


    public Plantas(Integer id, String especie, String edad, String detalles, Bitmap imagen, Boolean maceta) {
        this.id = id;
        this.especie = especie;
        this.edad = edad;
        this.detalles = detalles;
        this.imagen = imagen;
        this.maceta = maceta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Boolean getMaceta() {
        return maceta;
    }

    public void setMaceta(Boolean maceta) {
        this.maceta = maceta;
    }
}
