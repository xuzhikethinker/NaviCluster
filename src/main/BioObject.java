
package main;

import java.util.ArrayList;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class BioObject extends Object {

    private int id = 0;
    protected String databaseID = "";
    protected String databaseName = "";
    protected String name = "";
    // name 2 is the standard name in sgd database
    protected String standardName = "";
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
    
    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    /**
     * Get the value of standardName
     * if standardName is empty string then return name
     * @return the value of standardName
     */
    public String getStandardName() {
        if (standardName.length() == 0) {
            return name;
        } else {
            return standardName;
        }
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public ArrayList<String> getAspectList() {
        return aspectList;
    }

    public void setAspectList(ArrayList<String> aspectList) {
        this.aspectList = aspectList;
    }

    public ArrayList<String> getWithOrFromList() {
        return withOrFromList;
    }

    public void setWithOrFromList(ArrayList<String> withOrFromList) {
        this.withOrFromList = withOrFromList;
    }

    public ArrayList<String> getEviCodeList() {
        return eviCodeList;
    }

    public void setEviCodeList(ArrayList<String> eviCodeList) {
        this.eviCodeList = eviCodeList;
    }

    public ArrayList<String> getRefList() {
        return refList;
    }

    public void setRefList(ArrayList<String> refList) {
        this.refList = refList;
    }

    public ArrayList<String> getNotModifierList() {
        return notModifierList;
    }

    public void setNotModifierList(ArrayList<String> notModifierList) {
        this.notModifierList = notModifierList;
    }

    public ArrayList<String> getPropTermList() {
        return propTermList;
    }

    public void setPropTermList(ArrayList<String> goIdList) {
        this.propTermList = goIdList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof BioObject) {
            BioObject bioObj = (BioObject) obj;
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
            return false;

        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.databaseID != null ? this.databaseID.hashCode() : 0);
        hash = 23 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        if (standardName.length() > 0) {
            return standardName;
        }
        return name;
    }
}
