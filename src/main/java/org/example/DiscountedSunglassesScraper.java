package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class DiscountedSunglassesScraper extends Thread{

    private int crawlDelay = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;
        int page = 1; // Start with the first page

        // While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("Scraper 4 thread is scraping data from page " + page);

            // WEB SCRAPING CODE GOES HERE
            try {

                Hibernate_Session hibernate = new Hibernate_Session();
//
//                //Set up the SessionFactory
                hibernate.init();

                // Download HTML document from website
                Document doc = Jsoup.connect("https://www.discountedsunglasses.co.uk/brands/glasses?p=" + page).get();

                String website = "DiscountedSunglasses";

                // Get all of the products on the page
                Elements prods = doc.select(".product-item");

                // Work through the products
                for(int i=0; i<prods.size(); ++i) {
                    // Get the product description
                    Elements name = prods.get(i).select(".product-item-name");

                    // Get the product image
                    Elements image = prods.get(i).select(".product-image-wrapper  img");
                    String productImage = image.attr("src");

                    // Get the product link
                    Elements link = prods.get(i).select(".product-item-name a");
                    String productLink = link.attr("href").trim();



                    if (name.isEmpty() || productLink.isEmpty()) {

                    } else {
                        Document doc1 = Jsoup.connect(productLink).get();
                        // Get all of the info on the page
                        Elements priceContain = doc1.select(".price-container:contains(Our Price)");
                        String priceText = priceContain.text();
                        String price = priceText.substring(priceText.indexOf("Our Price") + "Our Price".length()).trim();
                        Elements brand = doc1.select("#product-attribute-specs-table > tbody > tr:nth-child(1) > td");
                        Elements description = doc1.select(".description");
                        // Get the product price

                        if (description.isEmpty() && brand.isEmpty() && image.isEmpty()) {

                        } else {

                            // Output the data that we have downloaded
                            System.out.println("WEBSITE: " + website +
                                    " NAME: " + name.text() +
                                " DESCRIPTION: " + description.text() +
                                    " BRAND: " + brand.text() +
                                    " PRICE: " + price +
                                    " LINK: " + productLink +
                                    " IMAGE: " + productImage );

                            // Check if the product already exists in the database
                            if (!hibernate.searchEyewear(name.text())) {
//                             Product doesn't exist, add it to the database
                                hibernate.addEyewear(name.text(), description.text(), productImage, brand.text(), "", productLink, price);
                            } else {
//                             Product already exists, you may want to log or handle this case
                                System.out.println("Product already exists in the database: " + name.text());
                            }
                        }
                    }
                }



//                    }

                if (page > 10) {
                    // No next page, stop scraping
                    stopThread();
                } else {
                    // Move to the next page
                    page++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Sleep for the crawl delay, which is in seconds
            try{
                sleep(1000 * crawlDelay); // Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            } catch(InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    //Other classes can use this method to terminate the thread.
    public void stopThread(){
        runThread = false;
    }
}
