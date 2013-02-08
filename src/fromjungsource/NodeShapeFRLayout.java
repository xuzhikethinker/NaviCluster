/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fromjungsource;

/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 */
import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
import java.util.Collection;
import java.util.Set;
import main.BioEdge;
import main.BioObject;
import main.MetaEdge;
import main.PropertyEdge;
import main.SGDInteraction;

/**
 * Implements the Fruchterman-Reingold algorithm for node layout.
 * Same as FRLayout but with a little bit of legwork to try and avoid
 * overlapping.
 *
 * @author Scott White, Yan-Biao Boey, Danyel Fisher
 */
public class NodeShapeFRLayout<V, E> extends AbstractLayout<V, E> implements IterativeContext {

    private double forceConstant;
    private double temperature;
    private int currentIteration;
//    private int mMaxIterations = 300;
//    private int mMaxIterations = 400;
    private int mMaxIterations = 50;
    private Map<V, FRVertexData> frVertexData =
            LazyMap.decorate(new HashMap<V, FRVertexData>(), new Factory<FRVertexData>() {

        public FRVertexData create() {
            return new FRVertexData();
        }
    });
    private double attraction_multiplier = 0.75;
    private double attraction_constant;
    private double repulsion_multiplier = 0.75;
    private double repulsion_constant;
    private double max_dimension;
    private AbstractVertexShapeTransformer<V> shapeTransformer;
    private double maxMetaWeight = 100.0;
    private double minMetaWeight = 1.0;
    private double maxEdgeWeight = 10.0;
    private double minEdgeWeight = 0.0;
    private int nodeSize = 85;
    private int minNodeSize = 85;
    private int maxNumMem = 2;
    private int minNumMem = 1;
    private double sizeMultiplier = 0.3;

    public double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public void setSizeMultiplier(double sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }

    public int getMinNodeSize() {
        return minNodeSize;
    }

    public void setMinNodeSize(int minNodeSize) {
        this.minNodeSize = minNodeSize;
    }

    public int getMaxNumMem() {
        return maxNumMem;
    }

    public void setMaxNumMem(int maxNumMem) {
        this.maxNumMem = maxNumMem;
    }

    public int getMinNumMem() {
        return minNumMem;
    }

    public void setMinNumMem(int minNumMem) {
        this.minNumMem = minNumMem;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }

    public NodeShapeFRLayout(Graph<V, E> g, AbstractVertexShapeTransformer<V> shapeTransformer) {
        super(g);
        this.shapeTransformer = shapeTransformer;
    }

    public NodeShapeFRLayout(Graph<V, E> g, AbstractVertexShapeTransformer<V> shapeTransformer, Dimension d) {
        super(g, new RandomLocationTransformer<V>(d), d);
        initialize();
        max_dimension = Math.max(d.height, d.width);
        this.shapeTransformer = shapeTransformer;
    }

    /* (non-Javadoc)
     * @see edu.uci.ics.jung.visualization.layout.AbstractLayout#setSize(java.awt.Dimension)
     */
    @Override
    public void setSize(Dimension size) {
        setInitializer(new RandomLocationTransformer<V>(size));
        super.setSize(size);
        max_dimension = Math.max(size.height, size.width);
    }

    public void setAttractionMultiplier(double attraction) {
        this.attraction_multiplier = attraction;
    }

    public void setRepulsionMultiplier(double repulsion) {
        this.repulsion_multiplier = repulsion;
    }

    public double getMaxMetaWeight() {
        return maxMetaWeight;
    }

    public void setMaxMetaWeight(double maxMetaWeight) {
        this.maxMetaWeight = maxMetaWeight;
    }

    public double getMinMetaWeight() {
        return minMetaWeight;
    }

    public void setMinMetaWeight(double minMetaWeight) {
        this.minMetaWeight = minMetaWeight;
    }

    public double getMaxEdgeWeight() {
        return maxEdgeWeight;
    }

    public void setMaxEdgeWeight(double maxEdgeWeight) {
        this.maxEdgeWeight = maxEdgeWeight;
    }

    public double getMinEdgeWeight() {
        return minEdgeWeight;
    }

    public void setMinEdgeWeight(double minEdgeWeight) {
        this.minEdgeWeight = minEdgeWeight;
    }

    public void reset() {
        doInit();
    }

    public void initialize() {
        doInit();
    }

    private void doInit() {
        Graph<V, E> graph = getGraph();
        Dimension d = getSize();
        if (graph != null && d != null) {
            currentIteration = 0;
            temperature = d.getWidth() / 10;

            forceConstant =
                    Math.sqrt(d.getHeight() * d.getWidth() / graph.getVertexCount());

            attraction_constant = attraction_multiplier * forceConstant;
            repulsion_constant = repulsion_multiplier * forceConstant;
        }
    }
//private double EPSILON = 0.000001D;
    private double EPSILON = 0.0001D;

    /**
     * Moves the iteration forward one notch, calculation attraction and
     * repulsion between vertices and edges and cooling the temperature.
     */
    public synchronized void step() {
        currentIteration++;

        /**
         * Calculate repulsion
         */
        while (true) {

            try {
                for (V v1 : getGraph().getVertices()) {
// if (isLocked(v1)) continue;
                    calcRepulsion(v1);
                }
                break;
            } catch (ConcurrentModificationException cme) {
            }
        }

        /**
         * Calculate attraction
         */
        while (true) {
            try {
                for (E e : getGraph().getEdges()) {

                    calcAttraction(e);
                }
                break;
            } catch (ConcurrentModificationException cme) {
            }
        }


        while (true) {
            try {
                for (V v : getGraph().getVertices()) {
                    if (isLocked(v)) {
                        continue;
                    }
                    calcPositions(v);
// fireStateChanged();
                }
                break;
            } catch (ConcurrentModificationException cme) {
            }
        }
        cool();
// fireStateChanged();
    }

    public synchronized void calcPositions(V v) {
        FRVertexData fvd = getFRData(v);
        if (fvd == null) {
            return;
        }
        Point2D xyd = transform(v);
        double deltaLength = Math.max(EPSILON, Math.sqrt(fvd.disp.zDotProduct(fvd.disp)));

        double newXDisp = fvd.getXDisp() / deltaLength * Math.min(deltaLength, temperature);

        if (Double.isNaN(newXDisp)) {
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [xdisp]");
        }

        double newYDisp = fvd.getYDisp() / deltaLength * Math.min(deltaLength, temperature);
        xyd.setLocation(xyd.getX() + newXDisp, xyd.getY() + newYDisp);

        double borderWidth = getSize().getWidth() / 50.0;
        double newXPos = xyd.getX();
        if (newXPos < borderWidth) {
            newXPos = borderWidth + Math.random() * borderWidth * 2.0;
        } else if (newXPos > (getSize().getWidth() - borderWidth)) {
            newXPos = getSize().getWidth() - borderWidth - Math.random() * borderWidth * 2.0;
        }

        double newYPos = xyd.getY();
        if (newYPos < borderWidth) {
            newYPos = borderWidth + Math.random() * borderWidth * 2.0;
        } else if (newYPos > (getSize().getHeight() - borderWidth)) {
            newYPos = getSize().getHeight() - borderWidth - Math.random() * borderWidth * 2.0;
        }

        xyd.setLocation(newXPos, newYPos);
        int marginDummy = 60;
//        int marginDummy = 20;
//        if (v instanceof BioObject){
//            nodeSize = 50;
//        }
//        else if (v instanceof Set){
//            int numMember = ((Set)v).size();
//            nodeSize = (int)(minNodeSize+((double)(numMember-minNumMem)/(maxNumMem-minNumMem))*minNodeSize*sizeMultiplier);
//        }
//        else
//            nodeSize = minNodeSize;
        nodeSize = (int) shapeTransformer.transform(v).getBounds().getWidth();

        if (xyd.getX() - nodeSize / 2 < 0) {
            xyd.setLocation(0 + nodeSize / 2 + marginDummy, xyd.getY());
        } else if (xyd.getX() + nodeSize / 2 > getSize().width) {
            xyd.setLocation(getSize().width - nodeSize / 2 - marginDummy, xyd.getY());
        }
        if (xyd.getY() - nodeSize / 2 < 0) {
            xyd.setLocation(xyd.getX(), 0 + nodeSize / 2 + marginDummy);
        } else if (xyd.getY() + nodeSize / 2 > getSize().height) {
            xyd.setLocation(xyd.getX(), getSize().height - nodeSize / 2 - marginDummy);
        }


    }

    public void calcAttraction(E e) {
        Pair<V> endpoints = getGraph().getEndpoints(e);
        V v1 = endpoints.getFirst();
        V v2 = endpoints.getSecond();
        boolean v1_locked = isLocked(v1);
        boolean v2_locked = isLocked(v2);

        if (v1_locked && v2_locked) {
// both locked, do nothing
            return;
        }
        Point2D p1Center = transform(v1);
        Point2D p2Center = transform(v2);

        if (p1Center == null || p2Center == null) {
            return;
        }

// Get the line from v1's box to v2's box
        Line2D link = findNewLink(v1, v2);
        Point2D p1 = link.getP1();
        Point2D p2 = link.getP2();

// Overlap case
        if (Math.sqrt(Math.pow((p1Center.getX() - p2.getX()), 2) + Math.pow(p1Center.getY() - p2.getY(), 2))
                <= Math.sqrt(Math.pow((p1Center.getX() - p1.getX()), 2) + Math.pow(p1Center.getY() - p1.getY(), 2))) {
            p1 = p1Center;
        }

        double xDelta = p1.getX() - p2.getX();
        double yDelta = p1.getY() - p2.getY();

        double deltaLength = Math.max(EPSILON, Math.sqrt((xDelta * xDelta) + (yDelta * yDelta)));

        double force = (deltaLength * deltaLength) / attraction_constant;
        if (Double.isNaN(force)) {
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [force] 1");
        }
        force *= attrMul(e);
        if (Double.isNaN(force)) {
            throw new IllegalArgumentException(
                    "Unexpected mathematical result in FRLayout:calcPositions [force]");
        }

        double dx = (xDelta / deltaLength) * force;
        double dy = (yDelta / deltaLength) * force;
        if (v1_locked == false) {
            FRVertexData fvd1 = getFRData(v1);
            fvd1.decrementDisp(dx, dy);
        }
        if (v2_locked == false) {
            FRVertexData fvd2 = getFRData(v2);
            fvd2.incrementDisp(dx, dy);
        }
    }

    public void calcRepulsion(V v1) {
        FRVertexData fvd1 = getFRData(v1);
        if (fvd1 == null) {
            return;
        }
        fvd1.setDisp(0, 0);

        try {
            for (V v2 : getGraph().getVertices()) {

// if (isLocked(v2)) continue;
                if (v1 != v2) {
                    Point2D p1Center = transform(v1);
                    Point2D p2Center = transform(v2);

                    if (p1Center == null || p2Center == null) {
                        continue;
                    }

// Get the line from v1's box to v2's box
                    Line2D link = findNewLink(v1, v2);
                    Point2D p1 = link.getP1();
                    Point2D p2 = link.getP2();
//                    System.out.println("p1 x " + p1.getX());
//                    System.out.println("p2 x " + p2.getX());

                    int extraRepel = 1;

// Overlap case
                    if (Math.sqrt(Math.pow((p1Center.getX() - p2.getX()), 2) + Math.pow(p1Center.getY() - p2.getY(), 2))
                            <= Math.sqrt(Math.pow((p1Center.getX() - p1.getX()), 2) + Math.pow(p1Center.getY() - p1.getY(), 2))) {
                        p1 = p1Center;
                    }

                    double xDelta = p1.getX() - p2.getX();
                    double yDelta = p1.getY() - p2.getY();

                    double deltaLength = Math.max(EPSILON, Math.sqrt((xDelta * xDelta) + (yDelta * yDelta)));
//                    System.out.println("xDelta " + xDelta);
//                    System.out.println("yDelta " + yDelta);
//                    System.out.println("deltaLength " + deltaLength);
                    double force = (repulsion_constant * repulsion_constant) / deltaLength * extraRepel;
                    Collection<E> edgeSet = getGraph().findEdgeSet(v1, v2);
//                    System.out.println("Force " + force);
//                    if (Double.isNaN(force)) {
//                        throw new IllegalArgumentException(
//                                "Unexpected mathematical result in FRLayout:calcPositions [force] 1");
//                    }
//                    System.out.println("repulmul " + repulMul(edgeSet));
                    force *= repulMul(edgeSet);

                    if (extraRepel != 1) {
                        System.out.println(v1 + " " + v2 + " " + force);
                    }

                    if (Double.isNaN(force)) {
                        throw new RuntimeException(
                                "Unexpected mathematical result in FRLayout:calcPositions [repulsion]");
                    }

                    fvd1.incrementDisp((xDelta / deltaLength) * force,
                            (yDelta / deltaLength) * force);
                }
            }
        } catch (ConcurrentModificationException cme) {
            calcRepulsion(v1);
        }
    }

    private Line2D findNewLink(V v1, V v2) {
// First get center (exact) location of vertex
        Point2D p1 = transform(v1);
        Point2D p2 = transform(v2);

// Line drawn between the two centers
        Line2D connection = new Line2D.Double(p1, p2);

// These are the halved dimensions of the vertices
        double width1 = (double) shapeTransformer.transform(v1).getBounds().width / 2,
                width2 = (double) shapeTransformer.transform(v2).getBounds().width / 2,
                height1 = (double) shapeTransformer.transform(v1).getBounds().height / 2,
                height2 = (double) shapeTransformer.transform(v2).getBounds().height / 2;
//        System.out.println("width1 "+width1);
//        System.out.println("width2 "+width2);
//        if (width2 == 0){
//            System.out.println("v2 "+v2);
//        }

// Bounds of first vertex icon
        Line2D rect1[] = {new Line2D.Double(p1.getX() - width1, p1.getY() - height1, p1.getX() + width1, p1.getY() - height1),
            new Line2D.Double(p1.getX() + width1, p1.getY() - height1, p1.getX() + width1, p1.getY() + height1),
            new Line2D.Double(p1.getX() + width1, p1.getY() + height1, p1.getX() - width1, p1.getY() + height1),
            new Line2D.Double(p1.getX() - width1, p1.getY() + height1, p1.getX() - width1, p1.getY() - height1)};

// Bounds of second vertex icon
        Line2D rect2[] = {new Line2D.Double(p2.getX() - width2, p2.getY() - height2, p2.getX() + width2, p2.getY() - height2),
            new Line2D.Double(p2.getX() + width2, p2.getY() - height2, p2.getX() + width2, p2.getY() + height2),
            new Line2D.Double(p2.getX() + width2, p2.getY() + height2, p2.getX() - width2, p2.getY() + height2),
            new Line2D.Double(p2.getX() - width2, p2.getY() + height2, p2.getX() - width2, p2.getY() - height2)};

// In some cases (icon overlap) there won't be an intersection point so
// just stick with the center on default
        Point2D int1 = p1, int2 = p2;

        for (int i = 0; i < rect1.length; i++) {
// Found an intersection for the first icon box
            if (connection.intersectsLine(rect1[i])) {
                double x1 = connection.getX1(), y1 = connection.getY1(),
                        x2 = connection.getX2(), y2 = connection.getY2(),
                        x3 = rect1[i].getX1(), y3 = rect1[i].getY1(),
                        x4 = rect1[i].getX2(), y4 = rect1[i].getY2();

                double intx = det(det(x1, y1, x2, y2), x1 - x2,
                        det(x3, y3, x4, y4), x3 - x4)
                        / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
                double inty = det(det(x1, y1, x2, y2), y1 - y2,
                        det(x3, y3, x4, y4), y3 - y4)
                        / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);

                int1 = new Point2D.Double(intx, inty);
            }
// Found an intersection for the second icon box
            if (connection.intersectsLine(rect2[i])) {
                double x1 = connection.getX1(), y1 = connection.getY1(),
                        x2 = connection.getX2(), y2 = connection.getY2(),
                        x3 = rect2[i].getX1(), y3 = rect2[i].getY1(),
                        x4 = rect2[i].getX2(), y4 = rect2[i].getY2();

                double intx = det(det(x1, y1, x2, y2), x1 - x2,
                        det(x3, y3, x4, y4), x3 - x4)
                        / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
                double inty = det(det(x1, y1, x2, y2), y1 - y2,
                        det(x3, y3, x4, y4), y3 - y4)
                        / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);

                int2 = new Point2D.Double(intx, inty);
            }
        }


        return new Line2D.Double(int1, int2);
    }

    public double repulMul(Collection<E> edgeSet) {
        double maxMetaEdgeVal = 1, maxPropVal = 1, maxSGDINt = 1;
        double metaEdgeVal = 0, propEdgeVal = 0, SGDIntVal = 0, bioEdgeVal = 0;
        double value = 1;
        for (E edge : edgeSet) {
            if (edge instanceof MetaEdge) {
                MetaEdge metaedge = (MetaEdge) edge;
//                int edgeweight = metaedge.getNumEdgeSetBundled();
                double edgeweight = metaedge.getWeightedNumEdges();
//            System.out.println("edgeweight "+edgeweight);
//                metaEdgeVal = ((double) (maxMetaWeight - edgeweight) / (maxMetaWeight - minMetaWeight)) * 0.55 + 0.45;
                metaEdgeVal = 0.1+(1.0-((double) (edgeweight - minMetaWeight) / (maxMetaWeight - minMetaWeight)) * 0.55);
//                System.out.println("value " + metaEdgeVal);
                
//            return value;
            } else if (edge instanceof BioEdge) {
                BioEdge bioedge = (BioEdge) edge;
                double edgeweight = bioedge.getWeight();
                bioEdgeVal = 0.1+(1.0-((double) (edgeweight - minEdgeWeight) / (maxEdgeWeight - minEdgeWeight)) * 0.45);
//                if (bioedge.getWeight() <= 2) {
//                    SGDIntVal = 1;
//                } else if (bioedge.getWeight() <= 5) {
//                    SGDIntVal = 0.75;
//                } else if (bioedge.getWeight() <= 8) {
//                    SGDIntVal = 0.6;
//                } else {
//                    SGDIntVal = 0.45;
//                }
//            return value;
                
            } else if (edge instanceof PropertyEdge) {
                PropertyEdge propedge = (PropertyEdge) edge;

                if (propedge.getInnerProduct() < 0) {
                    propEdgeVal = 1;
                } else if (propedge.getInnerProduct() < 0.2) {
                    propEdgeVal = 0.9;
                } else if (propedge.getInnerProduct() < 0.4) {
                    propEdgeVal = 0.8;
                } else if (propedge.getInnerProduct() < 0.6) {
                    propEdgeVal = 0.7;
                } else if (propedge.getInnerProduct() < 0.8) {
                    propEdgeVal = 0.6;
                } else {
                    propEdgeVal = 0.5;
                }
//            return value;
            }  else if (edge instanceof SGDInteraction) {
                SGDInteraction sgdint = (SGDInteraction) edge;

                if (sgdint.getNumEvidence() <= 2) {
                    SGDIntVal = 1;
                } else if (sgdint.getNumEvidence() <= 5) {
                    SGDIntVal = 0.75;
                } else if (sgdint.getNumEvidence() <= 8) {
                    SGDIntVal = 0.6;
                } else {
                    SGDIntVal = 0.45;
                }
//            return value;
            }
        }
//        value = (metaEdgeVal + propEdgeVal + SGDIntVal)/(maxMetaEdgeVal+maxPropVal+maxSGDINt);
//        value = (metaEdgeVal / maxMetaEdgeVal * 0.8 + propEdgeVal / maxPropVal + SGDIntVal / maxSGDINt * 0.8) / 2.6;
        if (metaEdgeVal > 0.0)
            return metaEdgeVal;
        else if (bioEdgeVal > 0.0)
            return bioEdgeVal;
        else if (propEdgeVal > 0.0)
            return propEdgeVal;

        return value;
    }

    public double attrMul(E edge) {
        if (edge instanceof MetaEdge) {
            MetaEdge metaedge = (MetaEdge) edge;
//            int edgeweight = metaedge.getNumEdgeSetBundled();
            double edgeweight = metaedge.getWeightedNumEdges();
//            System.out.println("max edgeweight "+maxMetaWeight);
//            System.out.println("min edgeweight "+minMetaWeight);
            double value = ((double) (edgeweight-minMetaWeight) / (maxMetaWeight - minMetaWeight)) * 0.55 + 1;
//            System.out.println("value "+value);
            return value;
        } else if (edge instanceof PropertyEdge) {
            PropertyEdge propedge = (PropertyEdge) edge;
            double value = 1;
            if (propedge.getInnerProduct() < 0) {
                value = 1 / 5;
            } else if (propedge.getInnerProduct() < 0.2) {
                value = 1;
            } else if (propedge.getInnerProduct() < 0.4) {
                value = 1.1;
            } else if (propedge.getInnerProduct() < 0.6) {
                value = 1.25;
            } else if (propedge.getInnerProduct() < 0.8) {
                value = 1.4;
            } else {
                value = 1.5;
            }
            return value;
        } else if (edge instanceof BioEdge) {
            BioEdge bioedge = (BioEdge) edge;
//            double value = 0;
            double edgeweight = bioedge.getWeight();
            double value = ((double) (edgeweight-minEdgeWeight) / (maxEdgeWeight - minEdgeWeight)) * 0.45 + 1;
//            if (bioedge.getWeight() <= 2) {
//                value = 1.2;
//            } else if (bioedge.getWeight() <= 5) {
//                value = 1.4;
//            } else if (bioedge.getWeight() <= 8) {
//                value = 1.5;
//            } else {
//                value = 1.7;
//            }
            return value;
        } else if (edge instanceof SGDInteraction) {
            SGDInteraction sgdint = (SGDInteraction) edge;
            double value = 0;
            if (sgdint.getNumEvidence() <= 2) {
                value = 1.2;
            } else if (sgdint.getNumEvidence() <= 5) {
                value = 1.4;
            } else if (sgdint.getNumEvidence() <= 8) {
                value = 1.5;
            } else {
                value = 1.7;
            }
            return value;
        }
        return 1;
    }

    private void cool() {
        temperature *= (1.0 - currentIteration / (double) mMaxIterations);
    }

    public void setMaxIterations(int maxIterations) {
        mMaxIterations = maxIterations;
    }

    public FRVertexData getFRData(V v) {
        return frVertexData.get(v);
    }

    /**
     * This one is an incremental visualization.
     */
    public boolean isIncremental() {
        return true;
    }

    /**
     * Returns true once the current iteration has passed the maximum count,
     * <tt>MAX_ITERATIONS</tt>.
     */
    public boolean done() {
        if (currentIteration > mMaxIterations || temperature < 1.0 / max_dimension) {
            return true;
        }
        return false;
    }

    static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    public static class FRVertexData {

        private DoubleMatrix1D disp;

        public FRVertexData() {
            initialize();
        }

        public void initialize() {
            disp = new DenseDoubleMatrix1D(2);
        }

        public double getXDisp() {
            return disp.get(0);
        }

        public double getYDisp() {
            return disp.get(1);
        }

        public void setDisp(double x, double y) {
            disp.set(0, x);
            disp.set(1, y);
        }

        public void incrementDisp(double x, double y) {
            disp.set(0, disp.get(0) + x);
            disp.set(1, disp.get(1) + y);
        }

        public void decrementDisp(double x, double y) {
            disp.set(0, disp.get(0) - x);
            disp.set(1, disp.get(1) - y);
        }
    }
}
