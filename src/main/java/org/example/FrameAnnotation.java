package org.example;

import javax.persistence.*;

/** Represents a Eyewear.
 Java annotation is used for the mapping. */
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


    /** Empty constructor */
    public FrameAnnotation(){
    }

    //Getters and setters
    public int getId() {
        return id;
    }
//    public int getEyewear_Id() {
//        return eyewear_id;
//    }
    public String getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setEyewear_id(EyewearAnnotation eyewear_id) {
        this.eyewear_id = eyewear_id;
    }


    public void setSize(String size) {
        this.size = size;
    }

    /** Returns a String description of the class */
    public String toString(){
        String str = "Frame. id: " + id + "; eyewear_id: " + eyewear_id + "; size: " +
                size;
        return str;
    }
}
