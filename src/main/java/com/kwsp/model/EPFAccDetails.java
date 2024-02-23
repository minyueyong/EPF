package com.kwsp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EPFAccDetails")
@TypeAlias("")


public class EPFAccDetails {
    private String FullName;

    private String DASTS;
    private String DAGEM;
    private String DSTTL;
    private String DFSA2;
    private List<RelatedDoc> relatedDocs;

    // Constructors, getters, and setters
    // Constructor
    public EPFAccDetails() {}

    // Getters and setters for each field
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


    public String getDASTS() {
        return DASTS;
    }

    public void setDASTS(String DASTS) {
        this.DASTS = DASTS;
    }

    public String getDAGEM() {
        return DAGEM;
    }

    public void setDAGEM(String DAGEM) {
        this.DAGEM = DAGEM;
    }

    public String getDSTTL() {
        return DSTTL;
    }

    public void setDSTTL(String DSTTL) {
        this.DSTTL = DSTTL;
    }

    public String getDFSA2() {
        return DFSA2;
    }

    public void setDFSA2(String DFSA2) {
        this.DFSA2 = DFSA2;
    }

    public List<RelatedDoc> getRelatedDocs() {
        return relatedDocs;
    }

    public void setRelatedDocs(List<RelatedDoc> relatedDocs) {
        this.relatedDocs = relatedDocs;
    }
}

// Inner class representing related documents
class RelatedDoc {
    private String DSUBS;
    private String DTBAL;

    // Constructors, getters, and setters
    // Constructor
    public RelatedDoc() {}

    // Getters and setters for each field
    public String getDSUBS() {
        return DSUBS;
    }

    public void setDSUBS(String DSUBS) {
        this.DSUBS = DSUBS;
    }

    public String getDTBAL() {
        return DTBAL;
    }

    public void setDTBAL(String DTBAL) {
        this.DTBAL = DTBAL;
    }
}
