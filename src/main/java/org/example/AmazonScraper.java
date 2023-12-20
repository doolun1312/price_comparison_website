//package org.example;
//
//        import org.jsoup.Jsoup;
//        import org.jsoup.nodes.Document;
//        import org.jsoup.select.Elements;
//
//
//        import java.util.List;
//
//public class AmazonScraper extends Thread {
//
//    private int crawlDelay = 1;
//
//    //Allows us to shut down our application cleanly
//    volatile private boolean runThread = false;
//
//    @Override
//    public void run() {
//        runThread = true;
//        int page = 1; // Start with the first page
//
//        // Create a new instance of the Chrome Driver
//        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
//        WebDriver driver = new ChromeDriver(options);
//
//        // While loop will keep running until runThread is set to false;
//        while (runThread) {
//            try {
//                // Navigate to the website
//                driver.get("https://www.amazon.com/s?k=glasses&page=" + page);
//                String source = driver.getPageSource();
//                Document doc = Jsoup.parse(source);
//
//                // Get all of the products on the page
//                Elements prods = doc.select(".a-section");
//
//                // Work through the products
//                for (int i = 0; i < prods.size(); ++i) {
//
//                    // Get the product description
//                    Elements name = prods.get(i).select(".a-size-base-plus");
//
//                    // Get the product price
//                    Elements price1 = prods.get(i).select(".a-offscreen");
//
//                    // Get the product link
//                    Elements link = prods.get(i).select(".a-link-normal");
//                    String productLink = "https://www.amazon.com" + link.attr("href");
//
//                    // Get the product image
//                    Elements image = prods.get(i).select(".a-section img");
//                    String productImage = image.attr("src");
//
//
//                    // Navigate to the product page
//                    driver.get(productLink);
//
////                    WebDriverWait wait = new WebDriverWait(driver, 20);
////                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(".sizes__value___J_CBO")));
////                    WebElement element1 = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('.description__description___iSwvd');");
//
////                    System.out.println(element1);
//
//                    // Get the page source after the specified element is present
//                    String productPageSource = driver.getPageSource();
//                    Document productDoc = Jsoup.parse(productPageSource);
//
//
//                    System.out.println(productDoc);
//
//                    // Get all of the info on the product page
//                    Elements brandContain = productDoc.select(".product-tag a img");
//                    String brand = brandContain.attr("alt");
//                    Elements descriptionElement = productDoc.select(".a-list-item");
//                    String description = descriptionElement.text();
//                    Elements size = productDoc.select(".a-text-left");
//
//                    if (name.isEmpty() && brand.isEmpty() && size.isEmpty() && description.isEmpty()
//                            && productImage.isEmpty()) {
//                        // Skip if essential information is missing
//                    } else {
//                        // Output the data that we have downloaded
//                        System.out.println("NAME: " + name.text() +
//                                " DESCRIPTION: " + description +
//                                " BRAND: " + brand +
//                                " PRICE: " + price1.text() +
//                                " LINK: " + productLink +
//                                " IMAGE: " + productImage +
//                                " SIZE: " + size.text()
//                        );
//                    }
//                }
//
//                // Check if there is a next page
//                Elements nextPageLink = doc.select("a:contains(page-" + (page + 1) + ")");
//                System.out.println(nextPageLink);
//
//                if (nextPageLink.isEmpty()) {
//                    // No next page, stop scraping
//                    stopThread();
//                } else {
//                    // Move to the next page
//                    page++;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            // Sleep for the crawl delay, which is in seconds
//            try {
//                sleep(1000 * crawlDelay); // Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
//            } catch (InterruptedException ex) {
//                // Handle interruption
//            }
//        }
//
//        // Exit driver and close Chrome
//        driver.quit();
//    }
//
//    // Other classes can use this method to terminate the thread.
//    public void stopThread() {
//        runThread = false;
//    }
//}
