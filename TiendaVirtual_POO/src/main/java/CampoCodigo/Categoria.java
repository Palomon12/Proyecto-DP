package CampoCodigo;

import java.util.ArrayList;
import java.util.List;

public class Categoria  {
    private static List<Categoria> listaCategorias = new ArrayList<>(); 
    private int id_Categoria;
    private String nombre;

    // Constructor
    public Categoria(int idCategoria, String nombre) {
        this.id_Categoria = idCategoria;
        this.nombre = nombre;
    }
    
    // G y S
    public int getIdCategoria() {
        return id_Categoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.id_Categoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    @Override
    public String toString() {
        return "--Categoría-- \n.ID: " + id_Categoria + "\n.Nombre: '" + nombre + "'";
    }
}
    

