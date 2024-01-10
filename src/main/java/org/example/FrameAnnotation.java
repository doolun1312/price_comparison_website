package org.example;

import javax.persistence.*;

/**
 * Represents a Eyewear.
 * Java annotation is used for the mapping.
 */
@Entity
@Table(name="frame")
public class FrameAnnotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "eyewear_id", nullable = false)
    private EyewearAnnotation eyewear_id;

    @Column(name = "size")
    private String size;

    @Column(name = "website")
    private String website;

    /**
     * Empty constructor
     */
    public FrameAnnotation(){
    }

    /**
     * Gets id.
     *
     * @return the id
     */
//Getters and setters
    public int getId() {
        return id;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
//    public int getEyewear_Id() {
//        return eyewear_id;
//    }
    public String getSize() {
        return size;
    }

    /**
     * Gets website.
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets eyewear id.
     *
     * @param eyewear_id the eyewear id
     */
    public void setEyewear_id(EyewearAnnotation eyewear_id) {
        this.eyewear_id = eyewear_id;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Sets website.
     *
     * @param website the website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /** Returns a String description of the class */
    public String toString(){
        String str = "Frame. id: " + id + "; eyewear_id: " + eyewear_id + "; size: " +
                size + "Website: " + website;
        return str;
    }
}
