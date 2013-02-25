/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Knacky
 */
public class CoExpRelation extends Object {
    private BioObject gene1 = null;
    private BioObject gene2 = null;
    private double mutualRank = -1;
    private double pearson = -1;

    public CoExpRelation(){

    }
    public CoExpRelation(BioObject g1, BioObject g2, double mr, double pr){
        gene1 = g1;
        gene2 = g2;
        mutualRank = mr;
        pearson = pr;
    }

    public BioObject getGene1() {
        return gene1;
    }

    public void setGene1(BioObject gene1) {
        this.gene1 = gene1;
    }

    public BioObject getGene2() {
        return gene2;
    }

    public void setGene2(BioObject gene2) {
        this.gene2 = gene2;
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
        if ((gene1.equals(cer.getGene1()) && gene2.equals(cer.getGene2())) ||
                (gene2.equals(cer.getGene1())) && gene1.equals(cer.getGene2())) {
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
        hash = 83 * hash + (this.gene1 != null ? this.gene1.hashCode() : 0);
        hash = 83 * hash + (this.gene2 != null ? this.gene2.hashCode() : 0);
        return hash;
    }

    public String toString(){
        return gene1.getName()+" : "+gene2.getName()+" with MR = "+mutualRank+" & PR = "+pearson;
    }
}
