/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocialgrafos;

/**
 *
 * @author Nelson Cardenas
 */
class MyEdge {

    private int relacion;
    private String persona1;
    private String persona2;

    public MyEdge() {
    }

    public MyEdge(int relacion, String persona1, String persona2) {
        this.relacion = relacion;
        this.persona1 = persona1;
        this.persona2 = persona2;
    }

    public int getRelacion() {
        return relacion;
    }

    public void setRelacion(int relacion) {
        this.relacion = relacion;
    }

    public String getPersona1() {
        return persona1;
    }

    public void setPersona1(String persona1) {
        this.persona1 = persona1;
    }

    public String getPersona2() {
        return persona2;
    }

    public void setPersona2(String persona2) {
        this.persona2 = persona2;
    }

    @Override
    public String toString() {
        return persona1 + "-" + relacion + "-" + persona2 + "-";
    }

}
