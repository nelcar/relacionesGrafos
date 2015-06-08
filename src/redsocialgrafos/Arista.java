/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocialgrafos;

/**
 *
 * @author Nelson Cardenas
 */
public class Arista {

    private int ID;
    private int Peso;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPeso() {
        return Peso;
    }

    public void setPeso(int Peso) {
        this.Peso = Peso;
    }

    public Arista(int ID, int Peso) {
        this.ID = ID;
        this.Peso = Peso;
    }

    public Arista() {

    }

    @Override
    public String toString() {
        return Peso + "";
    }

}
