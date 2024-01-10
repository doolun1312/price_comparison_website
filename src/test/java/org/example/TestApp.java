package org.example;

import org.example.*;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Test app.
 */
public class TestApp {

    /**
     * Test thread start.
     */
    @Test
    @DisplayName("Threads Test")
    void testThreadStart() {
        // Assuming you have a class named 'MyRunnable' implementing Runnable
        EyeonsScraper scraper1 = new EyeonsScraper();
        EbayScraper scraper2 = new EbayScraper();
        EyebuydirectSraper scraper3 = new EyebuydirectSraper();
        DiscountedSunglassesScraper scraper4 = new DiscountedSunglassesScraper();
        AlensaScraper scraper5 = new AlensaScraper();


        // Create threads with the runnables
        Thread thread1 = new Thread(scraper1);
        Thread thread2 = new Thread(scraper2);
        Thread thread3 = new Thread(scraper3);
        Thread thread4 = new Thread(scraper4);
        Thread thread5 = new Thread(scraper5);

        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        // Add more threads if needed

        // Check if threads are alive
        assertThreadStarted(thread1);
        assertThreadStarted(thread2);
        assertThreadStarted(thread3);
        assertThreadStarted(thread4);
        assertThreadStarted(thread5);

    }

    private void assertThreadStarted(Thread thread) {
        // Assert that the thread is alive (started)
        if (!thread.isAlive()) {
            throw new AssertionError("Thread " + thread.getName() + " is not started.");
        }
    }

    private static SessionFactory sessionFactory;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        // Set up Hibernate Session Factory before running the tests
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    /**
     * Test session factory not null.
     */
    @Test
    @DisplayName("Session Factory Test")
    public void testSessionFactoryNotNull() {
        // Assert that the Session Factory is not null
        assertNotNull(sessionFactory);
    }

    // Add more tests as needed

    /**
     * Tear down.
     */
    @AfterAll
    public static void tearDown() {
        // Close the Session Factory after running all tests
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


    /**
     * Test scraper.
     *
     * @throws IOException the io exception
     */
    @Test
    @DisplayName("Web scraping test")
    void testScraper() throws IOException {

        // Replace these URLs with the actual URLs of the 5 websites you are scraping
        String[] websiteUrls = {"https://www.alensa.co.uk/designer-glasses-and-frames/ray-ban--oakley--calvin-klein--guess--nike--versace--tommy-hilfiger--vogue--hugo-by-hugo-boss--boss-by-hugo-boss?page=1",
                "https://www.discountedsunglasses.co.uk/brands/glasses?p=1", "https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313&_nkw=Eyeglass Frames&_sacat=0&_pgn=1",
                "https://www.eyebuydirect.com/eyeglasses/s?name=glasses&page=1", "https://www.eyeons.com/eyeglasses/?page=1"};

        for (String website : websiteUrls){
            //Download HTML document from website
            Document doc = Jsoup.connect(website).get();
            assertNotNull(doc);
        }
    }

    /**
     * Test add search.
     */
    @Test
    @DisplayName("Add and search in Database test")
    void testAdd_Search() {
        String website = "Eyeons";
        String name="Marc Jacobs™ - MARC 331/F";
        String model= "MARC 331/F";
        String description = "MARC 331/F Eyeglasses by Marc Jacobs™. Shape: Cat-Eye / Butterfly, Material: Plastic, Frame Type: Full Rim.";
        String image_url = "https://www.eyeons.com/images/cache/catalog/products/marc-jacobs/mjb-331-f-0086-00-874x437.jpg";
        String brand = "Marc Jacobs";
        String size = "S";
        String url = "https://www.eyeons.com/marc-jacobs-331-f";
        String price = "$73.00 - $129.27";


        Hibernate_Session Hibernate = new Hibernate_Session();

        Hibernate.init();

        Hibernate.addEyewear(website, name, model, description, image_url, brand, size, url, price);
        assertTrue(Hibernate.searchEyewear(name));
    }

    /**
     * Test delete all.
     */
    @Test
    @DisplayName("Delete all data test")
    void testDeleteAll() {
        // Add some dummy data to the database for testing
        String website = "Alensa";
        String name="Guess GU2658 052";
        String model= "GU2658 052";
        String description = "About Guess\n" +
                "Make a fashion statement wherever you go with Guess glasses. Our selection from the famous American brand will satisfy all of you as it includes most iconic and timeless shapes, colours, and styles.\n" +
                "\n" +
                "\n" +
                "About these glasses\n" +
                "The shape is square, which works perfectly for those with diamond, oval, and round-shaped faces. The frame colour features a havana pattern, which is definitely modern and fits with most outfits, hairstyles, and eye colours. The frame colour is brown, which works great with dark hair and is generally a classic colour for everyday wear. These glasses are equipped with flexible temple hinges, which is a great component for those who often take off their glasses! The material is mainly made of lightweight and durable plastic. Would you like to see if these glasses are the perfect fit for you? Get a closer look with our virtual try-on tool!";
        String image_url = "https://cdn.alensa.co.uk/imagesCdn/32838/50402-cropped-400x300.webp";
        String brand = "Guess";
        String size = "S, M";
        String url = "https://www.alensa.co.uk/guess-gu2658-052";
        String price = "£102.50";

        Hibernate_Session Hibernate = new Hibernate_Session();

        Hibernate.init();

        Hibernate.addEyewear(website, name, model, description, image_url, brand, size, url, price);

        // Check if the dummy data is in the database before deletion
        assertTrue(Hibernate.searchEyewear(name));

        // Delete all rows from the database
        Hibernate.deleteAllRows();

        // Check if the dummy data is no longer in the database after deletion
        assertFalse(Hibernate.searchEyewear(name));
    }

}
