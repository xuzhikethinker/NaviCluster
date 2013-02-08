/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.HashSet;
import java.util.Set;

/**
 * 2009/11/2    Add "parents" field and its corresponding methods
 *              Add equal and hashcode methods
 *              Make all fields "private"
 * 2010/06/1    Change Namespace from enum to class
 *
 * @author Knacky
 */
public class PropertyTerm extends Object{
    private String id = "";
    private String name = "";
    private String shortName = "";
    // Weight specified here is like "depth" for terms in any ontology hierarchy
    private int weight = 0;
//    private NameSpace namespace = NameSpace.ALL;
    private NameSpace namespace = new NameSpace();
    private Set<String> parents = new HashSet<String>();

    public NameSpace getNamespace() {
        return namespace;
    }

    public void setNamespace(NameSpace namespace) {
        this.namespace = namespace;
    }
    
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public Set<String> getParents() {
        return parents;
    }

    public void setParents(Set<String> parents) {
        this.parents = parents;
    }

    public void addParent(String parent){
        this.parents.add(parent);
    }

    public void removeParent(String parent){
        this.parents.remove(parent);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyTerm other = (PropertyTerm) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }



    public PropertyTerm(String id){
        this.id = id;
    }
    public PropertyTerm(String id, int depth){
        this.id = id;
        this.weight = depth;
    }
    public PropertyTerm(){
        
    }
    @Override
    public String toString(){
        return id;
    }
    
    public static PropertyTerm findTerm(Set<PropertyTerm> GOSet, String id){
        for (PropertyTerm term : GOSet){
            if (id.equals(term.id))
                return term;
        }
        return null;
    }
}