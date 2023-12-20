//package org.example;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.*;
//
//import eu.davidgamez.csd3205.scraperexample.HatDao;
//import eu.davidgamez.csd3205.scraperexample.Hat;
//import java.util.List;
//
//@DisplayName("Test Clock")
//public class TestHatDao {
//    static SessionFactory sessionFactory;
//
//    @BeforeAll
//    static void initAll() {
//        try {
//            //Create a builder for the standard service registry
//            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
//
//            //Load configuration from hibernate configuration file.
//            //Here we are using a configuration file that specifies Java annotations.
//            standardServiceRegistryBuilder.configure("resources/hibernate.cfg.xml");
//
//            //Create the registry that will be used to build the session factory
//            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
//            try {
//                //Create the session factory - this is the goal of the init method.
//                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//            }
//            catch (Exception e) {
//                    /* The registry would be destroyed by the SessionFactory,
//                        but we had trouble building the SessionFactory, so destroy it manually */
//                    System.err.println("Session Factory build failed.");
//                    e.printStackTrace();
//                    StandardServiceRegistryBuilder.destroy( registry );
//            }
//            //Ouput result
//            System.out.println("Session factory built.");
//        }
//        catch (Throwable ex) {
//            // Make sure you log the exception, as it might be swallowed
//            System.err.println("SessionFactory creation failed." + ex);
//            ex.printStackTrace();
//        }
//    }
//
//    @BeforeEach
//    void init() {
//    }
//
//    @Test
//    @DisplayName("Test Save Hat")
//    void testSaveHat() {
//        //Create instance of class we are going to test
//        HatDao hatDao = new HatDao();
//        hatDao.setSessionFactory(sessionFactory);
//
//        //Create hat with random name
//        String randomName = String.valueOf(Math.random());
//        Hat hat = new Hat();
//        hat.setName(randomName);
//
//        //Use HatDao to save hat
//        hatDao.saveHat(hat);
//
//        //Check that hat exists in database
//        //Get a new Session instance from the session factory
//        Session session = sessionFactory.getCurrentSession();
//
//        //Start transaction
//        session.beginTransaction();
//
//        //Get hats with random name
//        List<Hat> hatList = session.createQuery("from Hat where name='"+randomName+"'").getResultList();
//        if(hatList.size() != 1)//One result should be stored
//            fail("Hat not successfully stored. Hat list size: " + hatList.size());
//
//        //Delete hat from database
//        session.delete(hatList.get(0));
//
//        //Commit transaction to save it to database
//        session.getTransaction().commit();
//
//        //Close the session and release database connection
//        session.close();
//    }
//
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//    }
//}