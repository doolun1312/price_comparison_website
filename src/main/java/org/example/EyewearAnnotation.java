package org.example;

import javax.persistence.*;

/** Represents a Eyewear.
 Java annotation is used for the mapping. */
@Entity
@Table(name="eyewear")
public class EyewearAnnotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "brand")
    private String brand;


    /** Empty constructor */
    public EyewearAnnotation(){
    }

    //Getters and setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getImage_url() {
        return image_url;
    }
    public String getBrand() {
        return brand;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /** Returns a String description of the class */
    public String toString(){
        String str = "Eyewear. id: " + id + "; name: " + name + "; description: " +
                description + "; image_url: " + image_url + "; brand: " + brand;
        return str;
    }
}
