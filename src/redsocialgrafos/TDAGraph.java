/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocialgrafos;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Nelson Cardenas
 */
public class TDAGraph {

    SparseMultigraph<String, MyEdge> grafo;
    
    
    public TDAGraph() {
        grafo = new SparseMultigraph<String, MyEdge>();
        init();
    }

    private void init() {
        System.out.println("cargarvertices");
        cargarVertices();
        System.out.println("cargar aristas");
        cargarAristas();
    }

    private void cargarVertices() {
        try {
            File archivo = new File(".\\personas.ncr");
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temporal;
            while ((temporal = bufferedReader.readLine()) != null) {
                grafo.addVertex(temporal);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pueden cargar los vertices");
        }
    }

    private void cargarAristas() {
        try {
            File archivo = new File(".\\relaciones.ncr");
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temporal, persona1, persona2, relacion;
            StringTokenizer tokens = null;
            while ((temporal = bufferedReader.readLine()) != null) {
                tokens = new StringTokenizer(temporal, "@");
                while (tokens.hasMoreTokens()) {
                    System.out.println(temporal);
                    persona1 = tokens.nextToken();
                    persona2 = tokens.nextToken();
                    relacion = tokens.nextToken();
                    grafo.addEdge(new MyEdge(Integer.parseInt(relacion), persona1, persona2), persona1, persona2, EdgeType.DIRECTED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "No se pueden cargar los vertices");
        }
    }

    public ArrayList calcularCamino(Queue<String> personas) {//inicio calcular Camino
        System.out.println("entra al metodo calcular caminos");
        ArrayList retVal = new ArrayList();
        org.apache.commons.collections15.Transformer<MyEdge, Integer> optimusPrime = new org.apache.commons.collections15.Transformer<MyEdge, Integer>() {
            public Integer transform(MyEdge arista) {
                return arista.getRelacion();
            }
        };
        String tmp1, tmp2;
        tmp1 = personas.poll();
        tmp2 = personas.element();
        System.out.println("temp1:"+tmp1+" temp2:"+tmp2);
        DijkstraShortestPath<String, MyEdge> dijkstra = new DijkstraShortestPath(grafo, optimusPrime);
        List<MyEdge> lista = dijkstra.getPath(tmp1, tmp2);
        
        System.out.println("lista:"+lista);
        retVal.add(lista);
        retVal.add(dijkstra.getDistance(tmp1, tmp2));
        while ((tmp1 = personas.poll()) != null && (tmp2 = personas.peek()) != null) {
            Number distancia = dijkstra.getDistance(tmp1, tmp2);
            ((List<MyEdge>) retVal.get(0)).addAll(dijkstra.getPath(tmp1, tmp2));
            retVal.set(1, (distancia.intValue() + Integer.parseInt(retVal.get(1).toString())));
        }
        System.out.println(retVal);
        System.out.println("fin del metodo calcular camino");
        return retVal;
    }//fin calcular Camino

}
