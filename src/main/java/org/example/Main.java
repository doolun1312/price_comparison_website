package org.example;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the Hibernate_Session class
        Hibernate_Session hibernate = new Hibernate_Session();

        // Set up the SessionFactory
        hibernate.init();

        // Delete all rows in the database
        hibernate.deleteAllRows();

        // Get the row count from the database
        int rowCount = hibernate.getCount();

        System.out.println(rowCount);

        // Start the threads running only if the row count is less than or equal to 100.
//        EyeonsScraper scraper1 = new EyeonsScraper();
        EbayScraper scraper2 = new EbayScraper();
//        EyebuydirectSraper scraper3 = new EyebuydirectSraper();
        DiscountedSunglassesScraper scraper4 = new DiscountedSunglassesScraper();
//        EyeconicScraper scraper5 = new EyeconicScraper();

//        scraper1.start();
        scraper2.start();
//        scraper3.start();
        scraper4.start();
//        scraper5.start();

        if (hibernate.getCount() > 50) {
//            scraper1.stopThread();
            scraper2.stopThread();
//            scraper3.stopThread();
            scraper4.stopThread();
//            scraper5.stopThread();

            // Wait for threads to finish
            try {
//                scraper1.join();
                scraper2.join();
//                scraper3.join();
                scraper4.join();
//                scraper5.join();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted exception thrown: " + ex.getMessage());
            }

            System.out.println("Web scraping complete");


            // Shut down Hibernate
            hibernate.shutDown();
        }
    }}


//package org.example;
//
//// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
//// then press Enter. You can now see whitespace characters in your code.
//public class Main {
//    public static void main(String[] args) {
//
////        // Initialize Spring context
////        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
////
////        // Retrieve the Hibernate_Session bean
////        Hibernate_Session hibernate = context.getBean(Hibernate_Session.class);
//
//
////        //Create a new instance of the HibernateExample class
//        Hibernate_Session hibernate = new Hibernate_Session();
////
////
////        //Set up the SessionFactory
//        hibernate.init();
////
//        hibernate.deleteAllRows();
//
//        //Example operations
////        hibernateXmlExample.addEyewear();//Add data
////         hibernateXmlExample.updateEyewear();//Updates data
////        hibernateXmlExample.searchEyewear();//Search for data
////        hibernateXmlExample.deleteEyewearsafe();//Delete data
////
////        //Shut down Hibernate
////        hibernate.shutDown();
////    }
////        System.out.println("Sraping- website 1: eyeons");
////        new EyeonsScraper();
//        EyeonsScraper scraper1 = new EyeonsScraper();
//        EbayScraper scraper2 = new EbayScraper();
//        EyebuydirectSraper scraper3= new EyebuydirectSraper();
//        DiscountedSunglassesScraper scraper4 = new DiscountedSunglassesScraper();
//        EyeconicScraper scraper5 = new EyeconicScraper();
//
//        //Start the threads running.
////        scraper1.start();
//        scraper2.start();
////        scraper3.start();
////        scraper4.start();
////        scraper5.start();
//
//
//
//        //Read input from user until they type 'stop'
////        Scanner scanner = new Scanner(System.in);
////        String userInput = scanner.nextLine();
////        while(!userInput.equals("stop")){
////            userInput = scanner.nextLine();
////        }
////
//        System.out.println(hibernate.getCount());
//
//
////Stop threads
//        scraper1.stopThread();
//        scraper2.stopThread();
//        scraper3.stopThread();
//        scraper4.stopThread();
//        scraper5.stopThread();
//
//
//        //Wait for threads to finish - join can throw an InterruptedException
//        try{
//            scraper1.join();
//            scraper2.join();
//            scraper3.join();
//            scraper4.join();
//            scraper5.join();
//        }
//        catch(InterruptedException ex){
//            System.out.println("Interrupted exception thrown: " + ex.getMessage());
//        }
//
//        System.out.println("Web scraping complete");
//
////        System.out.println("Sraping- website 2: ebay");
////        new EbayScraper();
////        System.out.println("Sraping- website 3: eyebuydirect");
////        new EyebuydirectSraper();
////        System.out.println("Sraping- website 4: GlassesUsa");
////        new GlassesUsaScraper();
////        System.out.println("Sraping- website 5: Amazon");
////        new AmazonScraper();
//
//
////    }
//        // Close the Spring context when done
////        ((ClassPathXmlApplicationContext) context).close();
//    }
//}