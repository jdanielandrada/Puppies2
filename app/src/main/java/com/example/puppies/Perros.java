package com.example.puppies;

public class Perros {
    private String nombre;
    private String desc;
    private String foto;

    public Perros(String nombre, String desc, String foto) {
        this.nombre = nombre;
        this.desc = desc;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}