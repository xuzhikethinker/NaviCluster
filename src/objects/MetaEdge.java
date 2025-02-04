package objects;

import objects.BioEdge;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class MetaEdge<E> {

    private String name = "";
    private Set<E> edgeSetBundled = new HashSet<E>();
    private int numEdgeSetBundled = 0;
    private double weightedNumEdges = 0.0D;

    //Intentionally not clone or copy. Just refer to the edgeSet
    public MetaEdge(String s, Set<E> edgeSet) {
        name = s;
        edgeSetBundled = edgeSet;
        numEdgeSetBundled = edgeSetBundled.size();
    }

    public MetaEdge(String s, E edge) {
        name = s;
        this.addEdge(edge);
    }

    public MetaEdge(String s) {
        name = s;
    }

    public double getWeightedNumEdges() {
        return weightedNumEdges;
    }

    public int getNumEdgeSetBundled() {
        return numEdgeSetBundled;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public Set<E> getEdgeSetBundled() {
        return edgeSetBundled;
    }

    public void setEdgeSetBundled(Set<E> edgeSet) {
        edgeSetBundled = edgeSet;
        numEdgeSetBundled = edgeSetBundled.size();
        weightedNumEdges = 0;
        for (E e : edgeSet) {
            if (e instanceof BioEdge) {
                weightedNumEdges += ((BioEdge) e).getWeight();
            }
        }
    }

    public void addEdge(E edge) {
        edgeSetBundled.add(edge);
        numEdgeSetBundled += 1;
        if (edge instanceof BioEdge) {
            weightedNumEdges += ((BioEdge) edge).getWeight();
        } else {
            weightedNumEdges += 1.0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MetaEdge<E> other = (MetaEdge<E>) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.edgeSetBundled != other.edgeSetBundled && (this.edgeSetBundled == null || !this.edgeSetBundled.equals(other.edgeSetBundled))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.edgeSetBundled != null ? this.edgeSetBundled.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Num edge: " + numEdgeSetBundled + "; " + edgeSetBundled;
    }
}