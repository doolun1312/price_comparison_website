package org.example;

import javax.persistence.*;

/**
 * Represents a Eyewear.
 * Java annotation is used for the mapping.
 */
@Entity
@Table(name="comparison")
public class ComparisonAnnotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "frame_id", nullable = false)
    private FrameAnnotation frame_id;

    @Column(name = "url")
    private String url;

    @Column(name = "price")
    private String price;


    /**
     * Empty constructor
     */
    public ComparisonAnnotation(){
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
     * Gets url.
     *
     * @return the url
     */
//    public int getFrame_Id() {
//        return frame_id.getId();
//    }
    public String getUrl() {
        return url;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
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
     * Sets frame id.
     *
     * @param frame_id the frame id
     */
    public void setFrame_id(FrameAnnotation frame_id) {
        this.frame_id = frame_id;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }
    /** Returns a String description of the class */
    public String toString(){
        String str = "Comparison. id: " + id + "; frame_id: " + frame_id + "; url: " +
                url + " price: " + price;
        return str;
    }
}
