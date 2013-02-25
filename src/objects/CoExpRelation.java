
package objects;

import objects.BioObject;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class CoExpRelation extends Object {
    private BioObject bioObj1 = null;
    private BioObject bioObj2 = null;
    private double mutualRank = -1;
    private double pearson = -1;

    public CoExpRelation(){

    }
    
    /**
     * Constructor of CoExpRelation
     * @param obj1 BioObject 2
     * @param obj2 BioObject 1
     * @param mr mutual rank
     * @param pr Pearson's correlation
     */
    public CoExpRelation(BioObject obj1, BioObject obj2, double mr, double pr){
        bioObj1 = obj1;
        bioObj2 = obj2;
        mutualRank = mr;
        pearson = pr;
    }

    public BioObject getBioObj1() {
        return bioObj1;
    }

    public void setBioObj1(BioObject bioObj) {
        this.bioObj1 = bioObj;
    }

    public BioObject getBioObj2() {
        return bioObj2;
    }

    public void setBioObj2(BioObject bioObj) {
        this.bioObj2 = bioObj;
    }

    public double getMutualRank() {
        return mutualRank;
    }

    public void setMutualRank(double mutualRank) {
        this.mutualRank = mutualRank;
    }

    public double getPearson() {
        return pearson;
    }

    public void setPearson(double pearson) {
        this.pearson = pearson;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof CoExpRelation))
            return false;
        CoExpRelation cer = (CoExpRelation)obj;
        if ((bioObj1.equals(cer.getBioObj1()) && bioObj2.equals(cer.getBioObj2())) ||
                (bioObj2.equals(cer.getBioObj1())) && bioObj1.equals(cer.getBioObj2())) {
            return true;
        }
        return false;
    }

    public boolean equals(CoExpRelation cer){
        return equals((Object)cer);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.bioObj1 != null ? this.bioObj1.hashCode() : 0);
        hash = 83 * hash + (this.bioObj2 != null ? this.bioObj2.hashCode() : 0);
        return hash;
    }

    public String toString(){
        return bioObj1.getName()+" : "+bioObj2.getName()+" with MR = "+mutualRank+" & PR = "+pearson;
    }
}
