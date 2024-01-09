//package org.example;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//
//public class EyeconicScraper extends Thread{
//
//    private int crawlDelay = 1;
//
//    //Allows us to shut down our application cleanly
//    volatile private boolean runThread = false;
//
//// ...
//
//    @Override
//    public void run(){
//        runThread = true;
//        int page = 0; // Start with the first page
//
//        // While loop will keep running until runThread is set to false;
//        while(runThread){
//            System.out.println("Scraper 5 thread is scraping data from page " + page);
//
//            // WEB SCRAPING CODE GOES HERE
//            try {
//
//                Hibernate_Session hibernate = new Hibernate_Session();
//
//                //Set up the SessionFactory
//                hibernate.init();
//
//                // Download HTML document from website
//                Document doc = Jsoup.connect("https://www.eyeconic.com/eyewear/eyeglasses?start=" + page).get();
//
//                String website = "Eyeconic";
//
//                // Get all of the products on the page
//                Elements prods = doc.select(".product-tile");
//
//                // Work through the products
//                for(int i=0; i<prods.size(); ++i){
//                    // Get the product description
//                    Elements name = prods.get(i).select(".name-link");
////
////                    // Get the product price
//                    Elements price = prods.get(i).select(".product-standard-price");
//
//                    Elements brand = prods.get(i).select(".productTileStyle");
//
//                    Elements model = prods.get(i).select(".product-code");
////
////
////                    // Get the product link
//                    Elements link = prods.get(i).select(".name-link");
//                    String productLink = link.attr("href");
////
//                    // Get the product image
//                    Elements image = prods.get(i).select(".name-link img");
//                    String productImage = image.attr("data-yo-src");
//                    // Replace spaces with "%20" in the productImage link
//                    productImage = productImage.replace(" ", "%20");
////
////
//                    Document doc1 = Jsoup.connect(productLink).get();
//
//                    // Get all of the info on the page
//                    Element description = doc1.select(".product-description").first();
//                    if (description == null){
//                        description = doc1.select(".frame-details .col-12").last();
//                    }
////                     Get the first occurrence of the element with the specified class
//                    Element size = doc1.select(".frame-single-size-text .js-frame-size-option .remove-telephone-style").first();
//
//                    if (size == null) {
//                        // If not found, try the other class
//                        size = doc1.select(".js-frame-size-option").first();
//                    }
//
////
////
////
//                    if (name.isEmpty() && brand.isEmpty() && productImage.isEmpty()) {
//
//                    } else {
//
//                        // Output the data that we have downloaded
//                        System.out.println("WEBSITE: " + website +
//                                " NAME: " + name.text() +
//                                " MODEL: " + model.text() +
//                                " DESCRIPTION: " + description.text() +
//                                " BRAND: " + brand.text() +
//                                " PRICE: " + price.text() +
//                                " LINK: " + productLink +
//                                " IMAGE: " + productImage +
//                                " SIZE: " + size.text());
//                    }
//                // Check if the product already exists in the database
//                if (!hibernate.searchEyewear(name.text())) {
//                    //add data to database
//                    hibernate.addEyewear(name.text(), model.text(), description.text(), productImage, brand.text(), size.text(), productLink, price.text());//Add data
//                } else {
//                    // Product already exists, you may want to log or handle this case
//                    System.out.println("Product already exists in the database: " + name.text());
//                }}
//
//            //Shut down Hibernate
//            hibernate.shutDown();
//
////                }
//                if (page > 300 ) {
//                    // No next page, stop scraping
//                    stopThread();
//                }else {
//                    // Move to the next page
//                    page+=30;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            // Sleep for the crawl delay, which is in seconds
//            try{
//                sleep(1000 * crawlDelay); // Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
//            } catch(InterruptedException ex) {
//                System.err.println(ex.getMessage());
//            }
//        }
//    }
//
//    //Other classes can use this method to terminate the thread.
//    public void stopThread(){
//        runThread = false;
//    }
//}
