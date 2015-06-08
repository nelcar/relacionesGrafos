/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocialgrafos;

/**
 *
 * @author Nelson Cardenas
 */
public class Vertice {

    private String ID;
    private String relacion;
    private boolean Visited;

    public Vertice(String ID, String relacion) {
        Visited = false;
        this.ID = ID;
        this.relacion = relacion;
    }

    public Vertice() {
        Visited = false;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean Visited) {
        this.Visited = Visited;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public boolean equals(Vertice E) {
        return E.getID().equals(this.ID);
    }

    @Override
    public String toString() {
        return ID + "";
    }

}
