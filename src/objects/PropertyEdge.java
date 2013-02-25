package objects;

import kmean.DataVector;
import kmean.KMeanClusterer;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class PropertyEdge {

    private String name = "";
    private DataVector dv1, dv2;
    private double innerProduct = 0.0;

    public PropertyEdge(String name, DataVector dv1, DataVector dv2) {
        this.name = name;
        this.dv1 = dv1;
        this.dv2 = dv2;
        innerProduct = KMeanClusterer.similarity(this.dv1, this.dv2);
    }

    public double getInnerProduct() {
        return innerProduct;
    }

    public DataVector getDv1() {
        return dv1;
    }

    public void setDv1(DataVector dv1) {
        this.dv1 = dv1;
        innerProduct = KMeanClusterer.similarity(this.dv1, this.dv2);
    }

    public DataVector getDv2() {
        return dv2;
    }

    public void setDv2(DataVector dv2) {
        this.dv2 = dv2;
        innerProduct = KMeanClusterer.similarity(this.dv1, this.dv2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Inner Product: " + innerProduct;
    }
}
