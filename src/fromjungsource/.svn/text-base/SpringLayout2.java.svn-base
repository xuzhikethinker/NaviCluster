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
import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;

/**
 * The SpringLayout package represents a visualization of a set of nodes. The
 * SpringLayout, which is initialized with a Graph, assigns X/Y locations to
 * each node. When called <code>relax()</code>, the SpringLayout moves the
 * visualization forward one step.
 *
 *
 *
 * @author Danyel Fisher
 * @author Joshua O'Madadhain
 */
public class SpringLayout2<V, E> extends SpringLayout<V,E>
{
    protected int currentIteration;
    protected int averageCounter;
    protected int loopCountMax = 70;
    protected boolean done;

    protected Point2D averageDelta = new Point2D.Double();

    /**
     * Constructor for a SpringLayout for a raw graph with associated
     * dimension--the input knows how big the graph is. Defaults to the unit
     * length function.
     */
    @SuppressWarnings("unchecked")
    public SpringLayout2(Graph<V,E> g) {
        super(g);
//        this.stretch = 0.8;
    }

    /**
     * Constructor for a SpringLayout for a raw graph with associated component.
     *
     * @param g the {@code Graph} to lay out
     * @param length_function provides a length for each edge
     */
    public SpringLayout2(Graph<V,E> g, Transformer<E, Integer> length_function)
    {
        super(g, length_function);
//        this.stretch = 0.98;
    }

    /**
     * Relaxation step. Moves all nodes a smidge.
     */
    @Override
    public void step() {
        super.step();
    	currentIteration++;
    	testAverageDeltas();
    }

    private void testAverageDeltas() {
    	double dx = this.averageDelta.getX();
    	double dy = this.averageDelta.getY();
//    	if(Math.abs(dx) < .0001 && Math.abs(dy) < .0001) {
//        if(Math.abs(dx) < 1E-10 && Math.abs(dy) < 1E-10) {
//    		done = true;
//    		System.err.println("done, dx="+dx+", dy="+dy);
//    	}
        if(currentIteration > loopCountMax) {
        	this.averageDelta.setLocation(0,0);
        	averageCounter = 0;
        	currentIteration = 0;
            done = true;
        }
    }

    @Override
    protected void moveNodes() {
        synchronized (getSize()) {
            try {
                for (V v : getGraph().getVertices()) {
                    if (isLocked(v)) continue;
                    SpringVertexData vd = springVertexData.get(v);
                    if(vd == null) continue;
                    Point2D xyd = transform(v);

                    vd.dx += vd.repulsiondx + vd.edgedx;
                    vd.dy += vd.repulsiondy + vd.edgedy;

//                    int currentCount = currentIteration % this.loopCountMax;
//                    System.err.println(averageCounter+" --- vd.dx="+vd.dx+", vd.dy="+vd.dy);
//                    System.err.println("averageDelta was "+averageDelta);

                    averageDelta.setLocation(
                    		((averageDelta.getX() * averageCounter) + vd.dx) / (averageCounter+1),
                    		((averageDelta.getY() * averageCounter) + vd.dy) / (averageCounter+1)
                    		);
//                    System.err.println("averageDelta now "+averageDelta);
//                    System.err.println();
                    averageCounter++;

                    // keeps nodes from moving any faster than 5 per time unit
//                    xyd.setLocation(xyd.getX()+Math.max(-5, Math.min(5, vd.dx)),
//                    		xyd.getY()+Math.max(-5, Math.min(5, vd.dy)));
//                    xyd.setLocation(xyd.getX()+ vd.dx,	xyd.getY()+  vd.dy);
                    xyd.setLocation(xyd.getX()+Math.max(-70, Math.min(70, vd.dx)),
                    		xyd.getY()+Math.max(-70, Math.min(70, vd.dy)));

                    Dimension d = getSize();
                    int width = d.width;
                    int height = d.height;

//                    if (xyd.getX() < 0) {
//                        xyd.setLocation(0, xyd.getY());//                     setX(0);
//                    } else if (xyd.getX() > width) {
//                        xyd.setLocation(width, xyd.getY());             //setX(width);
//                    }
//                    if (xyd.getY() < 0) {
//                        xyd.setLocation(xyd.getX(),0);//setY(0);
//                    } else if (xyd.getY() > height) {
//                        xyd.setLocation(xyd.getX(), height);      //setY(height);
//                    }
                    if (xyd.getX()-nodeSize/2 < 0) {
                        xyd.setLocation(0+nodeSize/2+5, xyd.getY());
                    } else if (xyd.getX()+nodeSize/2 > width) {
                        xyd.setLocation(width-nodeSize/2-5, xyd.getY());
                    }
                    if (xyd.getY()-nodeSize/2 < 0) {
                        xyd.setLocation(xyd.getX(), 0+nodeSize/2+5);
                    } else if (xyd.getY()+nodeSize/2 > height) {
                        xyd.setLocation(xyd.getX(), height-nodeSize/2-5);
                    }

                }
            } catch(ConcurrentModificationException cme) {
                moveNodes();
            }
        }
    }

    @Override
    public boolean done() {
        return done;
    }

}