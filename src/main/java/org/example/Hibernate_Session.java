package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;


/**
 * Simple Hibernate example that uses annotation to specify the mapping between
 * a eyewear object and the eyeawear table in the coursework1 database.
 */
public class Hibernate_Session {

    //Creates new Sessions when we need to interact with the database
    private SessionFactory sessionFactory;


    /**
     * Empty constructor
     */
    Hibernate_Session() {
    }


    /**
     * Sets up the session factory.
     * Call this method first.
     */
    public void init(){
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                System.err.println("Session Factory build failed.");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy( registry );
            }

            //Ouput result
            System.out.println("Session factory built.");

        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }


    /**
     * Shut down.
     */
    public void shutDown(){
        sessionFactory.close();
    }

    /**
     * Adds a new eyewear to the database  @param website the website
     *
     * @param name        the name
     * @param model       the model
     * @param description the description
     * @param image_url   the image url
     * @param brand       the brand
     * @param size        the size
     * @param url         the url
     * @param price       the price
     */
    public void addEyewear(String website, String name, String model, String description, String image_url, String brand, String size, String url, String price){
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Create an instance of a EyewearAnnotation class
        EyewearAnnotation eyewear = new EyewearAnnotation();

        //Create an instance of a FrameAnnotation class
        FrameAnnotation frame = new FrameAnnotation();

        ComparisonAnnotation comparison = new ComparisonAnnotation();

        //Set values of Eyewear class that we want to add
        eyewear.setName(name);
        eyewear.setModel(model);
        eyewear.setDescription(description);
        eyewear.setImage_url(image_url);
        eyewear.setBrand(brand);
        //Start transaction
        session.beginTransaction();
        session.save(eyewear);
        //Commit transaction to save it to database
        session.getTransaction().commit();
        //Close the session and release database connection
        session.close();

        Session session1 = sessionFactory.getCurrentSession();

        frame.setEyewear_id(eyewear);
        frame.setWebsite(website);
        frame.setSize(size);
        //Start transaction
        session1.beginTransaction();
        session1.save(frame);
        //Commit transaction to save it to database
        session1.getTransaction().commit();
        //Close the session and release database connection
        session1.close();

        Session session2 = sessionFactory.getCurrentSession();
        comparison.setFrame_id(frame);
        comparison.setUrl(url);
        comparison.setPrice(price);

        session2.beginTransaction();
        session2.save(comparison);
        //Commit transaction to save it to database
        session2.getTransaction().commit();
        //Close the session and release database connection
        session2.close();



        System.out.println("Eyewear added to database with ID: " + eyewear.getId());
    }


    /**
     * Updates the values of an existing eyewear in the database
     */
    public void updateEyewear(){
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Create an instance of a Eyewear class
        EyewearAnnotation eyewear = new EyewearAnnotation();

        //Set ID of eyewear class - this controls the row in the table that we want to update
        eyewear.setId(1);

        //Set new values of Eyewear class that we want to add
        eyewear.setName("name2");
        eyewear.setDescription("Description2");
        eyewear.setImage_url("Url2");
        eyewear.setBrand("Brand2");

        //Start transaction
        session.beginTransaction();

        //Update database to match class
        session.update(eyewear);
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
        System.out.println("Eyewear updated in database. ID: " + eyewear.getId());
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();
        // Start transaction
        session.beginTransaction();

        // Use parameters in the query to avoid SQL injection
        Query<Long> query = session.createQuery("select count(*) from EyewearAnnotation", Long.class);

        Long count = query.uniqueResult();
        // Close the session and release the database connection
        session.close();
        // Return the count as an int
        return count.intValue();
    }


    /**
     * Searches for Eyewear whose name is   @param name_input the name input
     *
     * @return the boolean
     */
    public boolean searchEyewear(String name_input) {
        // Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        // Start transaction
        session.beginTransaction();

        // Use parameters in the query to avoid SQL injection
        Query query = session.createQuery("from EyewearAnnotation where name = :name");
        query.setParameter("name", name_input);

        List<EyewearAnnotation> eyewearList = query.getResultList();

        // Close the session and release the database connection
        session.close();

        // Check if the eyewearList is not empty (i.e., eyewear with the given name exists)
        return !eyewearList.isEmpty();
    }



    /** Deletes an object without a foreign key from the database
     This will generate an error if there is a foreign key. */
//    public void deleteEyewear(){
//        //Create Eyewear class with the ID that we want to delete
//        EyewearAnnotation eyewear = new EyewearAnnotation();
//        eyewear.setId(5);
//
//        //Get a new Session instance from the session factory
//        Session session = sessionFactory.getCurrentSession();
//
//        //Start transaction
//        session.beginTransaction();
//
//        //Update database to match class
//        session.delete(eyewear);
//        session.getTransaction().commit();
//
//        //Close the session and release database connection
//        session.close();
//        System.out.println("Eyewear updated in database. ID: " + eyewear.getId());
//    }
//

    /**
     * Deletes an eyewear in a way that will work with tables with foreign keys
     */
    public void deleteEyewearsafe(){
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Start transaction
        session.beginTransaction();

        //Search for eyewear in database that has ID of 6
        Object persistentInstance = session.load(EyewearAnnotation.class, 1);

        //Delete object (and corresponding data) if we have found a match.
        if (persistentInstance != null)
            session.delete(persistentInstance);

        //Update database
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
        System.out.println("Eyewear updated in database. ID: 1" );
    }

//    public void deleteAllRows() {
//        //Get a new Session instance from the session factory
//        Session session = sessionFactory.getCurrentSession();
//
//        try {
//            // Start transaction
//            session.beginTransaction();
//
//            // Delete all rows from ComparisonAnnotation
//            session.createQuery("delete from ComparisonAnnotation").executeUpdate();
//
//            // Delete all rows from FrameAnnotation
//            session.createQuery("delete from FrameAnnotation ").executeUpdate();
//
//            // Delete all rows from EyewearAnnotation
//            session.createQuery("delete from EyewearAnnotation ").executeUpdate();
//
//            // Execute native SQL queries to reset auto-increment values
//            session.createNativeQuery("TRUNCATE TABLE eyewear").executeUpdate();
//            session.createNativeQuery("TRUNCATE TABLE frame").executeUpdate();
//            session.createNativeQuery("TRUNCATE TABLE comparison").executeUpdate();
//
//            // Commit transaction to save changes
//            session.getTransaction().commit();
//
//            System.out.println("All rows deleted from EyewearAnnotation, FrameAnnotation, and ComparisonAnnotation tables, and auto-increment values reset.");
//        } catch (Exception e) {
//            // Handle exceptions
//            if (session.getTransaction() != null) {
//                session.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            // Close the session and release database connection
//            session.close();
//        }
//    }

//    public void deleteAllRows() {
//        Session session = sessionFactory.getCurrentSession();
//
//        try {
//            session.beginTransaction();
//
//            // Delete all rows from ComparisonAnnotation (which doesn't have a foreign key)
//            session.createQuery("delete from ComparisonAnnotation").executeUpdate();
//
//            // Delete all rows from FrameAnnotation
//            session.createQuery("delete from FrameAnnotation").executeUpdate();
//
//            // Delete all rows from EyewearAnnotation (which may have foreign key constraints)
//            session.createQuery("delete from EyewearAnnotation").executeUpdate();
//
//            // Commit transaction
//            session.getTransaction().commit();
//
//            System.out.println("All rows deleted from EyewearAnnotation, FrameAnnotation, and ComparisonAnnotation tables.");
//        } catch (Exception e) {
//            if (session.getTransaction() != null) {
//                session.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }


    /**
     * Delete all rows.
     */
    public void deleteAllRows() {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // Delete all rows from ComparisonAnnotation (which doesn't have a foreign key)
            session.createQuery("delete from ComparisonAnnotation").executeUpdate();

            // Delete all rows from FrameAnnotation
            session.createQuery("delete from FrameAnnotation").executeUpdate();

            // Delete all rows from EyewearAnnotation (which may have foreign key constraints)
            session.createQuery("delete from EyewearAnnotation").executeUpdate();

            // Reset auto-increment values using native SQL queries
            session.createNativeQuery("ALTER TABLE eyewear AUTO_INCREMENT = 1").executeUpdate();
            session.createNativeQuery("ALTER TABLE frame AUTO_INCREMENT = 1").executeUpdate();
            session.createNativeQuery("ALTER TABLE comparison AUTO_INCREMENT = 1").executeUpdate();

            // Commit transaction
            session.getTransaction().commit();

            System.out.println("All rows deleted from EyewearAnnotation, FrameAnnotation, and ComparisonAnnotation tables, and auto-increment values reset.");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}