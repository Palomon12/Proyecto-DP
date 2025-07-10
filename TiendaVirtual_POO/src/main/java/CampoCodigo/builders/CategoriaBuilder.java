package CampoCodigo.builders;

import CampoCodigo.Categoria;

public class CategoriaBuilder {
    private int id_categoria;
    private String nombre;

    public CategoriaBuilder setId(int id) {
        this.id_categoria = id;
        return this;
    }

    public CategoriaBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Categoria build() {
        return new Categoria(id_categoria, nombre);
    }
}
