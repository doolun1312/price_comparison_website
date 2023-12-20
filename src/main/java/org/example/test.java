//package org.example;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//public class test {
//
//    public static void main(String[] args) {
//        try {
//            // Create a builder for the standard service registry
//            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
//
//            // Load configuration from hibernate configuration file
//            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");
//
//            // Create the registry that will be used to build the session factory
//            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
//
//            // Create the session factory - this is the goal of the init method.
//            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//
//            // Open a session to the database and immediately close it
//            sessionFactory.openSession().close();
//
//            // Output result
//            System.out.println("Successfully connected to the database.");
//
//            // Shutdown Hibernate
//            sessionFactory.close();
//        } catch (Exception e) {
//            System.err.println("Failed to connect to the database.");
//            e.printStackTrace();
//        }
//    }
//}
