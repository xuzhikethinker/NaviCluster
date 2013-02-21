
package main;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class BioEdge {
    protected BioObject node1;
    protected BioObject node2;
    protected double weight = 1.0;
    protected String type = "";

    public BioEdge(BioObject node1, BioObject node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public BioEdge(BioObject node1, BioObject node2, double weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public BioObject getNode1() {
        return node1;
    }

    public void setNode1(BioObject node1) {
        this.node1 = node1;
    }

    public BioObject getNode2() {
        return node2;
    }

    public void setNode2(BioObject node2) {
        this.node2 = node2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BioEdge other = (BioEdge) obj;
        if (this.node1 == other.node1 || (this.node1 != null && this.node1.equals(other.node1))){
            if (this.node2 == other.node2 || (this.node2 != null && this.node2.equals(other.node2))){
                return true;
            }
            
        } else if (this.node1 == other.node2 || (this.node1 != null && this.node1.equals(other.node2))) {
            if (this.node2 == other.node1 || (this.node2 != null && this.node2.equals(other.node1))){
                return true;
            }
            
        }
        if (this.node1 != other.node1 && (this.node1 == null || !this.node1.equals(other.node1))) {
            return false;
        }
        if (this.node2 != other.node2 && (this.node2 == null || !this.node2.equals(other.node2))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.node1 != null ? this.node1.hashCode() : 0);
        hash = 89 * hash + (this.node2 != null ? this.node2.hashCode() : 0);
        return hash;
    }
    
     public String toString(){
        return node1+"<->"+node2;
    }


}
