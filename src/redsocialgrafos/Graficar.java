/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocialgrafos;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Nelson Cardenas
 */
public class Graficar {

    private static DirectedGraph<Vertice, Arista> Grafo;

    public Graficar() {
    }

    public Graficar(DirectedGraph<Vertice, Arista> Grafo) {
        Grafo = new DirectedSparseMultigraph<>();
        this.Grafo = Grafo;
    }

    public void paintGraph(DirectedGraph X) {

        Layout<Vertice, Arista> layout = new FRLayout(X);
        layout.setSize(new Dimension(800, 600));
        BasicVisualizationServer<Vertice, Arista> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(800, 600));
        Transformer<Vertice, Paint> vertexPaint = new Transformer<Vertice, Paint>() {

            @Override
            public Paint transform(Vertice i) {
                if (i.getID().equals("Nelson Cardenas")) {
                    return Color.CYAN;
                } else {
                    return Color.GREEN;
                }
            }
        };

        Transformer<Vertice, Shape> vertexSize = new Transformer<Vertice, Shape>() {
            public Shape transform(Vertice i) {
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                return circle;
            }
        };
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

        Transformer<Arista, Stroke> edgeStrokeTransformer = new Transformer<Arista, Stroke>() {
            public Stroke transform(Arista s) {
                return edgeStroke;
            }
        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        JFrame frame = new JFrame("Relaciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
    private static Vertice FINAL;
    private static ArrayList<Vertice> Ruta = new ArrayList<>();
    ;
    private static ArrayList<Integer> Trip = new ArrayList<>();
    ;
    private static DirectedGraph<Vertice, Arista> Ruta1;

    public void getShortestPath(DirectedGraph<Vertice, Arista> Grafo, Vertice Start, Vertice... Destiny) {
        this.Grafo = Grafo;
        Vertice Back = null;
        int i = -1;
        while (!CheckVisited(Destiny, Start)) {
            ResetVisitWeight(Start);
            i++;
            if (i >= Destiny.length) {
                break;
            }
            Vertice StartNode = Destiny[i];
            FINAL = StartNode;
            while (!StartNode.equals(Start)) {
                Arista[] InEdges = Grafo.getInEdges(StartNode).toArray(new Arista[0]);
                if (InEdges.length == 0) {
                    StartNode.setVisited(true);
                    Trip.remove(Trip.size() - 1);
                    StartNode = Ruta.get(Ruta.size() - 1);
                    StartNode.setVisited(false);
                }
                while (true) {
                    if (StartNode.isVisited()) {
                        break;
                    }

                    StartNode.setVisited(true);
                    if (Ruta.size() >= 1) {
                        if (!StartNode.equals(Ruta.get(Ruta.size() - 1))) {
                            Ruta.add(StartNode);
                        }
                    } else {
                        Ruta.add(StartNode);
                    }

                    StartNode = getShortestNode(ArraytoArrayList(InEdges), Destiny, Start);

                    if ((StartNode) == null) {
                        Ruta.remove(Ruta.size() - 1);
                        Trip.remove(Trip.size() - 1);
                        
                        StartNode = Ruta.get(Ruta.size() - 1);
                        StartNode.setVisited(false);

                        InEdges = Grafo.getInEdges(StartNode).toArray(new Arista[0]);
                    }
                    System.out.println(StartNode.getID() + " Despues");
                    if (StartNode != null) {
                        if (StartNode.equals(Start)) {
                            Start.setVisited(true);
                        }
                        break;
                    }
                }
                if (StartNode == null) {
                    break;
                }
            }
        }
    }

    private static boolean CheckVisited(Vertice[] Destiny, Vertice Start) {
        if (!Start.isVisited()) {
            return false;
        }

        for (int i = 0; i < Destiny.length; i++) {
            if (!Destiny[i].isVisited()) {
                return false;
            }
        }

        return true;
    }

    private static void ResetVisitWeight(Vertice Start) {
        for (int i = 0; i < Grafo.getVertices().size(); i++) {
            Grafo.getVertices().toArray(new Vertice[0])[i].setVisited(false);
        }
        Start.setVisited(false);
        while (!Trip.isEmpty()) {
            Trip.remove(0);
        }
        while (!Ruta.isEmpty()) {
            Ruta.remove(0);
        }
    }

    private static ArrayList<Arista> ArraytoArrayList(Arista[] A) {
        ArrayList<Arista> retVal = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {
            retVal.add(A[i]);
        }
        return retVal;
    }

    private static Vertice getShortestNode(ArrayList<Arista> SendEdges, Vertice[] TravelTo, Vertice Start) {

        ArrayList<Vertice> sendNode = new ArrayList<>();
        for (int i = 0; i < SendEdges.size(); i++) {
            sendNode.add(Grafo.getSource(SendEdges.get(i)));
            if (sendNode.get(i).equals(Start)) {
                Trip.add(SendEdges.get(i).getPeso());
                SendEdges.remove(i);
                return sendNode.get(i);
            }
        }

        for (int i = 0; i < TravelTo.length; i++) {
            for (int j = 0; j < sendNode.size(); j++) {
                if (TravelTo[i].equals(sendNode.get(j)) && !TravelTo[i].isVisited()) {
                    Trip.add(SendEdges.get(j).getPeso());
                    SendEdges.remove(j);
                    return sendNode.get(j);
                }
            }
        }

        int weigth = 99999999;
        int Position = -1;
        for (int i = 0; i < SendEdges.size(); i++) {
            if (weigth > SendEdges.get(i).getPeso() && !sendNode.get(i).isVisited()) {
                weigth = SendEdges.get(i).getPeso();
                Position = i;
            }
        }

        if (weigth != 99999999) {
            Trip.add(weigth);
        } else {
        }
        if (Position == -1) {
            return null;
        }
        SendEdges.remove(Position);
        return sendNode.get(Position);

    }

    private static void fillGraph(DirectedGraph G, ArrayList<Vertice> V, ArrayList<Integer> E) {
        for (int i = V.size() - 1; i >= 1; i--) {
            Vertice V1 = V.get(i);
            Vertice V2 = V.get(i - 1);
            G.addVertex(V1);
            G.addVertex(V2);
            G.addEdge(new Arista(G.getEdgeCount() + 1, E.get(i)), V1, V2);
        }
    }

    public void ruta() {
        Ruta1 = new DirectedSparseMultigraph<>();
        fillGraph(Ruta1, Ruta, Trip);
        paintGraph(Ruta1);
    }
}
