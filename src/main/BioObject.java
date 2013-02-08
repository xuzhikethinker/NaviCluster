/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class BioObject extends Object {

    private int id = 0;
    protected String databaseID = "";
    protected String databaseName = "";
    protected String name = "";
    // name 2 is the standard name in sgd database
    protected String name2 = "";
    protected ArrayList<String> synonym = new ArrayList<String>();
    protected String type = "";
    //for now date is the same for all annotations of one object
    protected String date = "";
    protected String taxon = "";
    protected ArrayList<String> notModifierList = new ArrayList<String>();
    protected ArrayList<String> propTermList = new ArrayList<String>();
    protected ArrayList<String> refList = new ArrayList<String>();
    protected ArrayList<String> eviCodeList = new ArrayList<String>();
    protected ArrayList<String> withOrFromList = new ArrayList<String>();
    protected ArrayList<String> aspectList = new ArrayList<String>();
    protected String objName = "";

//    dame resp. to heat not connected
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 43 * hash + this.id;
//        hash = 43 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 43 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
//        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 43 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        hash = 43 * hash + (this.propTermList != null ? this.propTermList.hashCode() : 0);
//        return hash;
//    }

//    cla4 not good, protein folding
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 47 * hash + this.id;
//        hash = 47 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 47 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
//        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 47 * hash + (this.propTermList != null ? this.propTermList.hashCode() : 0);
//        return hash;
//    }

    
//    gimon protein folding quite good
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 59 * hash + this.id;
//        hash = 59 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 59 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
//        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 59 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

//    gimon protein folding
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 37 * hash + this.id;
//        hash = 37 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 37 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
//        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
//        return hash;
//    }

    
//  gimon
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 71 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 71 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
//        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 71 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

//    Currently Used!
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
        hash = 23 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    
//    gimon
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 97 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }


//    dame both figures bad
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 43 * hash + this.id;
//        hash = 43 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        return hash;
//    }

//    dame both figures bad
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 23 * hash + this.id;
//        hash = 23 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 23 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

//    gimon
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 67 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 67 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }
    
    // Manual
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 71 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
//        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 71 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

//      gimon quite good
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 89 * hash + this.id;
//        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 89 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

//      gimon, quite good, protein folding instead of cellular response to heat?
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 37 * hash + this.id;
//        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 37 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }

    // dame. CLA4 good, but three proteins very bad
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 79 * hash + this.id;
//        return hash;
//    }

    public boolean equals(Object obj) {
        if (obj instanceof BioObject) {
            BioObject bioObj = (BioObject) obj;
//            if (this.databaseID.equals(bioObj.getDatabaseID()))
            if (this.id == bioObj.getId())
            {
                return true;
            }
            if (this.getStandardName().equals(bioObj.getStandardName())) {
                return true;
            }
            if (this.getName().equals(bioObj.getName())) {
                return true;
            }
            if (this.getStandardName().equals(bioObj.getName())) {
                return true;
            }
//            if (this.getName().equals(bioObj.getStandardName())) {
//                return true;
//            }
            return false;

//            else if (this.name2.equals(bioObj.getStandardName()))
//                return true;
//            {
//                if (this.name2.equals(bioObj.getName()))
//                    return true;
//
//                return false;
//            }
        }
        return false;
    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 37 * hash + this.id;
//        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
//        return hash;
//    }


//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 71 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
////        hash = 71 * hash + this.id;
//        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
//        hash = 71 * hash + (this.name2 != null ? this.name2.hashCode() : 0);
//        return hash;
//    }
//
////    @Override
////    public int hashCode() {
////        int hash = 7;
////        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
////        return hash;
////    }
//
//    @Override
//    public boolean equals(Object obj){
//        if (obj instanceof BioObject){
//            BioObject bioObj = (BioObject) obj;
//            if (this.databaseID.equals(bioObj.getDatabaseID()))
////            if (this.id == bioObj.getId())
//                return true;
//            if (this.getStandardName().equals(bioObj.getStandardName()))
//                return true;
//            if (this.getName().equals(bioObj.getName()))
//                return true;
//            if (this.getStandardName().equals(bioObj.getName()))
//                return true;
//            if (this.getName().equals(bioObj.getStandardName()))
//                return true;
//            return false;
//
////            else if (this.name2.equals(bioObj.getStandardName()))
////                return true;
////            {
////                if (this.name2.equals(bioObj.getName()))
////                    return true;
////
////                return false;
////            }
//        }
//        return false;
//    }
    /**
     * Get the value of objName
     *
     * @return the value of objName
     */
    public String getObjName() {
        return objName;
    }

    /**
     * Set the value of objName
     *
     * @param objName new value of objName
     */
    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Override
    public String toString() {
//        return name;
        if (name2.length() > 0) {
            return name2;
        }
        return name;
    }

    /**
     * Get the value of name2
     * if name2 is empty string then return name
     * @return the value of name2
     */
    public String getStandardName() {
        if (name2.length() == 0) {
            return name;
        } else {
            return name2;
        }
    }

    /**
     * Set the value of name2
     *
     * @param name2 new value of name2
     */
    public void setStandardName(String standardName) {
        this.name2 = standardName;
    }

    /**
     * Get the value of aspectList
     *
     * @return the value of aspectList
     */
    public ArrayList<String> getAspectList() {
        return aspectList;
    }

    /**
     * Set the value of aspectList
     *
     * @param aspectList new value of aspectList
     */
    public void setAspectList(ArrayList<String> aspectList) {
        this.aspectList = aspectList;
    }

    /**
     * Get the value of withOrFromList
     *
     * @return the value of withOrFromList
     */
    public ArrayList<String> getWithOrFromList() {
        return withOrFromList;
    }

    /**
     * Set the value of withOrFromList
     *
     * @param withOrFromList new value of withOrFromList
     */
    public void setWithOrFromList(ArrayList<String> withOrFromList) {
        this.withOrFromList = withOrFromList;
    }

    /**
     * Get the value of eviCodeList
     *
     * @return the value of eviCodeList
     */
    public ArrayList<String> getEviCodeList() {
        return eviCodeList;
    }

    /**
     * Set the value of eviCodeList
     *
     * @param eviCodeList new value of eviCodeList
     */
    public void setEviCodeList(ArrayList<String> eviCodeList) {
        this.eviCodeList = eviCodeList;
    }

    /**
     * Get the value of refList
     *
     * @return the value of refList
     */
    public ArrayList<String> getRefList() {
        return refList;
    }

    /**
     * Set the value of refList
     *
     * @param refList new value of refList
     */
    public void setRefList(ArrayList<String> refList) {
        this.refList = refList;
    }

    /**
     * Get the value of notModifierList
     *
     * @return the value of notModifierList
     */
    public ArrayList<String> getNotModifierList() {
        return notModifierList;
    }

    /**
     * Set the value of notModifierList
     *
     * @param notModifierList new value of notModifierList
     */
    public void setNotModifierList(ArrayList<String> notModifierList) {
        this.notModifierList = notModifierList;
    }

    /**
     * Get the value of propTermList
     *
     * @return the value of propTermList
     */
    public ArrayList<String> getPropTermList() {
        return propTermList;
    }

    /**
     * Set the value of propTermList
     *
     * @param propTermList new value of propTermList
     */
    public void setPropTermList(ArrayList<String> goIdList) {
        this.propTermList = goIdList;
    }

    public BioObject() {
    }

    public BioObject(int id) {
        this.id = id;
    }

    public BioObject(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public BioObject(int id, String databaseID, String name) {
        this.id = id;
        this.name = name;
        this.databaseID = databaseID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String sgdId) {
        this.databaseID = sgdId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public ArrayList<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(ArrayList<String> synonym) {
        this.synonym = synonym;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}