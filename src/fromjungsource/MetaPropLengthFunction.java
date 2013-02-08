/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fromjungsource;

import main.MetaEdge;
import main.PropertyEdge;
import main.SGDInteraction;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Knacky
 */
public class MetaPropLengthFunction<E> implements Transformer<E,Integer>{
    private int nodeSize = 85;
    private int baseLen = 30;
    private double maxWeight = 100;
    private double minWeight = 1;

    public MetaPropLengthFunction(){

    }

    public MetaPropLengthFunction(int nodeSize, int baseLen, double maxWeight, double minWeight){
        this.nodeSize = nodeSize;
        this.baseLen = baseLen;
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
    }
    public Integer transform(E edge) {
        int maxLen = 200;
        if (edge instanceof MetaEdge){
            MetaEdge metaedge = (MetaEdge)edge;
//            int edgeweight = metaedge.getNumEdgeSetBundled();
            double edgeweight = metaedge.getWeightedNumEdges();
//            System.out.println("edgeweight "+edgeweight);
//            int value = baseLen+nodeSize+(int) (((double) (maxWeight-edgeweight) / (maxWeight - minWeight) * maxLen));
            int value = baseLen+nodeSize+(int) (((double) (edgeweight-minWeight) / (maxWeight - minWeight) * maxLen));
//            System.out.println("value "+value);
            return value;
        } 
        else if (edge instanceof PropertyEdge){
            PropertyEdge propedge = (PropertyEdge)edge;
            int value = 0;
            if (propedge.getInnerProduct() < 0)
                value = maxLen;
            else if (propedge.getInnerProduct() <  0.2)
                value = maxLen-50;
            else if (propedge.getInnerProduct() <  0.4)
                value = maxLen-85;
            else if (propedge.getInnerProduct() <  0.6)
                value = maxLen-150;
            else if (propedge.getInnerProduct() <  0.8)
                value = maxLen-175;
            else
                value = maxLen-190;
            return baseLen+nodeSize+value;
        } else if (edge instanceof SGDInteraction){
            SGDInteraction sgdint = (SGDInteraction)edge;
            int value = 0;
            if (sgdint.getNumEvidence() <= 2)
                value = maxLen-160;
            else if (sgdint.getNumEvidence() <= 5)
                value = maxLen-175;
            else if (sgdint.getNumEvidence() <= 8)
                value = maxLen-185;
            else
                value = maxLen-193;
            return baseLen+nodeSize+value;
        }
//        System.out.println(baseLen+nodeSize+10);
        return baseLen+nodeSize+100;
    }

    public int getBaseLen() {
        return baseLen;
    }

    public void setBaseLen(int baseLen) {
        this.baseLen = baseLen;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
//        System.out.println("new maxweight "+maxWeight);
        this.maxWeight = maxWeight;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }



}
