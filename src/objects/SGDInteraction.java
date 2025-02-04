package objects;

import java.util.ArrayList;

/**
 *
 * @author Thanet (Knack) Praneenararat, Department of Computational Biology, The University of Tokyo
 */
public class SGDInteraction {
    protected Object bait;
    protected Object hit;
    protected int numEvidence = 1;
    protected ArrayList<String> experimentList = new ArrayList<String>();
    protected ArrayList<String> interactionList = new ArrayList<String>();
    protected ArrayList<String> sourceList = new ArrayList<String>();
    protected ArrayList<String> curationList = new ArrayList<String>();
    protected ArrayList<String> notesList = new ArrayList<String>();
    protected ArrayList<String> phenotypeList = new ArrayList<String>();
    protected ArrayList<String> referenceList = new ArrayList<String>();
    protected ArrayList<String> citationList = new ArrayList<String>();

    public SGDInteraction(Object bait, Object hit) {
        this.bait = bait;
        this.hit = hit;
    }

    public SGDInteraction() {
    }
    
    /**
     * Get the value of citationList
     *
     * @return the value of citationList
     */
    public ArrayList<String> getCitationList() {
        return citationList;
    }

    /**
     * Set the value of citationList
     *
     * @param citationList new value of citationList
     */
    public void setCitationList(ArrayList<String> citationList) {
        this.citationList = citationList;
    }


    /**
     * Get the value of referenceList
     *
     * @return the value of referenceList
     */
    public ArrayList<String> getReferenceList() {
        return referenceList;
    }

    /**
     * Set the value of referenceList
     *
     * @param referenceList new value of referenceList
     */
    public void setReferenceList(ArrayList<String> referenceList) {
        this.referenceList = referenceList;
    }


    /**
     * Get the value of phenotypeList
     *
     * @return the value of phenotypeList
     */
    public ArrayList<String> getPhenotypeList() {
        return phenotypeList;
    }

    /**
     * Set the value of phenotypeList
     *
     * @param phenotypeList new value of phenotypeList
     */
    public void setPhenotypeList(ArrayList<String> phenotypeList) {
        this.phenotypeList = phenotypeList;
    }


    /**
     * Get the value of notesList
     *
     * @return the value of notesList
     */
    public ArrayList<String> getNotesList() {
        return notesList;
    }

    /**
     * Set the value of notesList
     *
     * @param notesList new value of notesList
     */
    public void setNotesList(ArrayList<String> notes) {
        this.notesList = notes;
    }


    /**
     * Get the value of curationList
     *
     * @return the value of curationList
     */
    public ArrayList<String> getCurationList() {
        return curationList;
    }

    /**
     * Set the value of curationList
     *
     * @param curationList new value of curationList
     */
    public void setCurationList(ArrayList<String> curationList) {
        this.curationList = curationList;
    }


    /**
     * Get the value of sourceList
     *
     * @return the value of sourceList
     */
    public ArrayList<String> getSourceList() {
        return sourceList;
    }

    /**
     * Set the value of sourceList
     *
     * @param sourceList new value of sourceList
     */
    public void setSourceList(ArrayList<String> source) {
        this.sourceList = source;
    }


    /**
     * Get the value of interactionList
     *
     * @return the value of interactionList
     */
    public ArrayList<String> getInteractionList() {
        return interactionList;
    }

    /**
     * Set the value of interactionList
     *
     * @param interactionList new value of interactionList
     */
    public void setInteractionList(ArrayList<String> interactionList) {
        this.interactionList = interactionList;
    }

    /**
     * Get the value of numEvidence
     *
     * @return the value of numEvidence
     */
    public int getNumEvidence() {
        return numEvidence;
    }

    /**
     * Set the value of numEvidence
     *
     * @param numEvidence new value of numEvidence
     */
    public void setNumEvidence(int numEvidence) {
        this.numEvidence = numEvidence;
    }

    /**
     * Get the value of experimentList
     *
     * @return the value of experimentList
     */
    public ArrayList<String> getExperimentList() {
        return experimentList;
    }

    /**
     * Set the value of experimentList
     *
     * @param experimentList new value of experimentList
     */
    public void setExperimentList(ArrayList<String> experimentList) {
        this.experimentList = experimentList;
    }

    /**
     * Get the value of hit
     *
     * @return the value of hit
     */
    public Object getHit() {
        return hit;
    }

    /**
     * Set the value of hit
     *
     * @param hit new value of hit
     */
    public void setHit(Object hit) {
        this.hit = hit;
    }

    /**
     * Get the value of bait
     *
     * @return the value of bait
     */
    public Object getBait() {
        return bait;
    }

    /**
     * Set the value of bait
     *
     * @param bait new value of bait
     */
    public void setBait(Object bait) {
        this.bait = bait;
    }


    @Override
    public String toString() {
        return bait+"<->"+hit;
    }

}
