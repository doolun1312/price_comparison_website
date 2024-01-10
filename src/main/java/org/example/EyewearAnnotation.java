package org.example;

import javax.persistence.*;

/**
 * Represents a Eyewear.
 * Java annotation is used for the mapping.
 */
@Entity
@Table(name="eyewear")
public class EyewearAnnotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;
    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "brand")
    private String brand;


    /**
     * Empty constructor
     */
    public EyewearAnnotation(){
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets image url.
     *
     * @param image_url the image url
     */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }



    /** Returns a String description of the class */
    public String toString(){
        String str = "Eyewear. id: " + id + "; name: " + name + "; description: " +
                description + "; image_url: " + image_url + "; brand: " + brand + "; model: " + model;
        return str;
    }
}
