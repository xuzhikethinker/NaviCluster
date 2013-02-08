/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Color;

/**
 *
 * @author Knacky
 */
public class NameSpace {
    private String name = "ALL";
    private double weight = 1.0;
    private Color color = new Color(0, 0, 0);
    public NameSpace(String name, double weight){
        this.name = name;
        this.weight = weight;
    }

    public NameSpace(String name) {
        this.name = name;
    }

    public NameSpace(){
        
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NameSpace other = (NameSpace) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    


//    public static NameSpace getNameSpace(String s) {
//        if (s.equals("biological_process")) {
//            return BP;
//        } else if (s.equals("molecular_function")) {
//            return MF;
//        } else {
//            return CC;
//        }
//    }
}
