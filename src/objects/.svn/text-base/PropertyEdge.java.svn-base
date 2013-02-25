/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import kmean.DataVector;
import kmean.KMeanClusterer;

/**
 *
 * @author Knacky
 */
public class PropertyEdge extends Object{
    String name = "";
    DataVector dv1, dv2;
    double innerProduct = 0;
    public PropertyEdge(String name,DataVector dv1, DataVector dv2){
        this.name = name;
        this.dv1 = dv1;
        this.dv2 = dv2;
        innerProduct = KMeanClusterer.similarity(dv1, dv2);
    }

    public double getInnerProduct() {
        return innerProduct;
    }

    public void setInnerProduct(double innerProduct) {
        this.innerProduct = innerProduct;
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
