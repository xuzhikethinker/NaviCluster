/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fromjungsource;

/*
 Copyright (c) 2007, Trampoline Systems ltd.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of the Trampoline Systems nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author David R. MacIver
 */
public class FRLayout3<V, E> implements Layout<V, E>, IterativeContext {
  private static final Comparator<Point2D> ORDER_BY_X = new Comparator<Point2D>() {
    public int compare(Point2D o1, Point2D o2) {
      return Double.compare(o1.getX(), o2.getX());
    }
  };

  private static final Comparator<Point2D> ORDER_BY_Y = new Comparator<Point2D>() {
    public int compare(Point2D o1, Point2D o2) {
      return Double.compare(o1.getY(), o2.getY());
    }
  };

  private static final int NEIGHBOURHOOD = 100;

  private double temperature;
  private double optimalDistance;
  private double optimalDistance2;
  private boolean valid = false;
  private double distanceMultiplier = 100;
  private Graph<V, E> graph;
  private Dimension size;
  private HashMap<V, TaggedPoint> elements;
  private FRLayout3.TaggedPoint[] xSorted;
  private FRLayout3.TaggedPoint[] ySorted;
  private FRLayout3.TaggedPoint[] edges;
  private Transformer<V, Point2D> initializer;
  private int iterations = 0;
  private int maxIterations = 500;


  public FRLayout3(Graph<V, E> graph, Dimension size) {
    this.graph = graph;
    this.size = size;
    this.initializer = new RandomLocationTransformer<V>(size);
    initialize();
  }

  // Layout methods
  public Transformer<V, Point2D> getInitializer() {
    return initializer;
  }

  public void setInitializer(final Transformer<V, Point2D> initialiser) {
    this.initializer = initialiser;
  }

  public Graph<V, E> getGraph() {
    return this.graph;
  }

  public void setGraph(final Graph<V, E> graph) {
    this.graph = graph;
    reset();
  }

  public Dimension getSize() {
    return this.size;
  }

  public void setSize(final Dimension dimension) {
    this.size = dimension;
  }

  public boolean isLocked(final V v) {
    return elements.get(v).isLocked();
  }

  public void lock(final V v, final boolean b) {
    elements.get(v).setLocked(b);
  }

  public int getMaxIterations() {
    return maxIterations;
  }

  public void setMaxIterations(final int maxIterations) {
    this.maxIterations = maxIterations;
  }

  public void initialize() {
    iterations = 0;
    temperature = 1000;
    this.elements = new HashMap<V, TaggedPoint>();

    this.optimalDistance = Math.min(100, distanceMultiplier * Math
        .sqrt(this.getSize().getWidth() * this.getSize().getHeight() / this.getGraph().getVertexCount()));
    cacheCalculations();

    this.xSorted = new FRLayout3.TaggedPoint[graph.getVertexCount()];
    this.ySorted = new FRLayout3.TaggedPoint[graph.getVertexCount()];
    this.edges = new FRLayout3.TaggedPoint[graph.getEdgeCount() * 2];
    int i = 0;

    for (V vertex : graph.getVertices()) {
      final TaggedPoint taggedPoint = new TaggedPoint(vertex, initializer.transform(vertex));
      elements.put(vertex, taggedPoint);
      xSorted[i] = taggedPoint;
      ySorted[i] = taggedPoint;
      i++;
    }

    int j = 0;
    for (E edge : graph.getEdges()) {
      Pair<V> endpoints = graph.getEndpoints(edge);
      edges[j++] = elements.get(endpoints.getFirst());
      edges[j++] = elements.get(endpoints.getSecond());
    }
  }

  private void cacheCalculations() {
    this.optimalDistance2 = optimalDistance * optimalDistance;
  }

  public void reset() {
    initialize();
  }

  public void setLocation(final V v, final Point2D point2D) {
    elements.get(v).setLocation(point2D);
  }

  public Point2D transform(final V v) {
    return elements.get(v);
  }

  // IterativeContext methods.
  public void repelFrom(TaggedPoint pointToMove, TaggedPoint other) {
    double deltaX = other.getX() - pointToMove.getX();
    double deltaY = other.getY() - pointToMove.getY();

    double distance2 = deltaX * deltaX + deltaY * deltaY;

    double inverseDistance2 = 1.0 / distance2;

    double force = -optimalDistance2 * inverseDistance2;
    pointToMove.add(deltaX * force, deltaY * force);
  }

  public void attractTo(TaggedPoint pointToMove, TaggedPoint other) {
    double deltaX = other.getX() - pointToMove.getX();
    double deltaY = other.getY() - pointToMove.getY();

    pointToMove.add(0.9 * deltaX, 0.9 * deltaY);
  }

  private void handleRepulsions(TaggedPoint[] elements) {
    for (int i = 0; i < elements.length; i++) {
      int cap = Math.min(i + NEIGHBOURHOOD, elements.length - 1);
      for (int j = i + 1; j <= cap; j++) {
        repelFrom(elements[i], elements[j]);
        repelFrom(elements[j], elements[i]);
      }
    }
  }

  public void step() {
    cool();
    sort();

    iterations++;

    handleRepulsions(xSorted);
    handleRepulsions(ySorted);

    // Calculate attractive forces.
    for (int i = 0; i < edges.length; i += 2) {
      attractTo(edges[i], edges[i + 1]);
      attractTo(edges[i + 1], edges[i]);
    }

    for (TaggedPoint taggedPoint : xSorted) {
      taggedPoint.limit(temperature);
      taggedPoint.move();
    }
  }

  private synchronized void sort() {
    if (!valid) {
      Arrays.sort(xSorted, ORDER_BY_X);
      Arrays.sort(ySorted, ORDER_BY_Y);
      valid = true;
    }
  }

  private void cool() {
    if (iterations <= 3) temperature = 10000.0;
    else temperature = 10000.0 / (1 + iterations);
  }

  public double temperature() {
    return temperature;
  }

  public boolean done() {
    return iterations >= maxIterations;
  }

  public void setIterations(final int iterations) {
    this.iterations = iterations;
  }

  private class TaggedPoint extends Point2D.Double {
    private final V value;

    private double dx;
    private double dy;
    private boolean locked;

    public void add(double dx, double dy) {
      this.dx += dx;
      this.dy += dy;
    }

    @Override
    public void setLocation(double x, double y) {
      if (!isLocked()) {
        valid = false;
        super.setLocation(x, y);
      }
    }

    public void move() {
      setLocation(x + dx, y + dy);
      dx = 0;
      dy = 0;
    }

    public void limit(double cap) {
      dx = Math.signum(dx) * Math.min(Math.abs(dx), cap);
      dy = Math.signum(dy) * Math.min(Math.abs(dy), cap);
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ")";
    }

    public TaggedPoint(V value, Point2D point) {
      this(value, point.getX(), point.getY());
    }

    public TaggedPoint(V value, double x, double y) {
      super(x, y);
      this.value = value;
    }

    public V getValue() {
      return value;
    }

    public void setLocked(boolean locked) {
      this.locked = locked;
    }

    public boolean isLocked() {
      return locked;
    }
  }

  // Spatial methods
  public double getMinX() {
    sort();
    return xSorted[0].getX();
  }

  public double getMaxX() {
    sort();
    return xSorted[xSorted.length - 1].getX();
  }

  public double getMinY() {
    sort();
    return ySorted[0].getY();
  }

  public double getMaxY() {
    sort();
    return ySorted[ySorted.length - 1].getY();
  }
}


