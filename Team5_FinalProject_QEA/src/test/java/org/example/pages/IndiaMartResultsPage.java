
package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class IndiaMartResultsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(IndiaMartResultsPage.class);

    // ── Locators ──────────────────────────────────────────────────────────
    private static final By     CHENNAI_FILTER   = By.xpath("//input[@id='Chennai-based Suppliersid']");
    private static final By     FOAM_WASH_FILTER = By.xpath("//span[normalize-space()='Foam Wash']");
    private static final By     VACUUMING_FILTER = By.xpath("//span[normalize-space()='Vacuuming']");
    private static final By     ONSITE_FILTER    = By.xpath("//span[normalize-space()='On-site']");
    private static final By     GYM_ONLY_FILTER  = By.xpath("//span[normalize-space()='Gym Only']");
    private static final By     LISTING_CARDS    = By.cssSelector(".card.brs5");
    private static final String CSS_SERVICE_NAME = ".producttitle a.cardlinks";
    private static final String CSS_COMPANY_NAME = ".companyname a.cardlinks";
    private static final String CSS_LOCATION     = ".highlight";
    private static final String ATTR_RATING      = "data-rating";

    private static final String[] LISTING_HEADERS = {"#", "Service Name", "Company Name", "Rating", "Location"};
    private static final int      MAX_RECORDS     = 5;

    public IndiaMartResultsPage(WebDriver driver) {
        super(driver);
    }

    // ── SM's methods (TF-26) : Filters ───────────────────────────────────

    public void handlePostSearchPopup() {
        log.info("Checking for post-search popup.");
        waitAndHandlePopup();
        log.info("Post-search popup check complete.");
    }

    public void applyChennaiFilter() {
        log.info("Applying Chennai-based Suppliers filter.");
        handlePopupIfVisible();
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(CHENNAI_FILTER));
        safeClick(checkbox);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Chennai-based Suppliers filter applied.");
    }

    public void applyFoamWashFilter() {
        log.info("Applying Foam Wash filter.");
        handlePopupIfVisible();
        WebElement foamWash = wait.until(
                ExpectedConditions.elementToBeClickable(FOAM_WASH_FILTER));
        safeClick(foamWash);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Foam Wash filter applied.");
    }

    public void applyVacuumingFilter() {
        log.info("Applying Vacuuming filter.");
        handlePopupIfVisible();
        WebElement vacuuming = wait.until(
                ExpectedConditions.elementToBeClickable(VACUUMING_FILTER));
        safeClick(vacuuming);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Vacuuming filter applied.");
    }

    public void applyOnsiteFilter() {
        log.info("Applying On-site filter.");
        handlePopupIfVisible();
        WebElement onsite = wait.until(
                ExpectedConditions.elementToBeClickable(ONSITE_FILTER));
        safeClick(onsite);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("On-site filter applied.");
    }

    public void applyGymOnlyFilter() {
        log.info("Applying Gym Only filter.");
        handlePopupIfVisible();
        WebElement gymOnly = wait.until(
                ExpectedConditions.elementToBeClickable(GYM_ONLY_FILTER));
        safeClick(gymOnly);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Gym Only filter applied.");
    }

    // ── YG's methods (TF-27) : Display ───────────────────────────────────

    public void displayTop5Listings() {
        List<WebElement> cards = driver.findElements(LISTING_CARDS);
        List<String[]>   excelRows = new ArrayList<>();

        log.info("===== CAR WASHING SERVICES IN CHENNAI (TOP {}) =====", MAX_RECORDS);

        if (cards.isEmpty()) {
            log.warn("No results found. URL: {}", driver.getCurrentUrl());
        } else {
            int count = Math.min(MAX_RECORDS, cards.size());
            log.info("Displaying {} of {} result(s) found.", count, cards.size());

            for (int i = 0; i < count; i++) {
                try {
                    String serviceName = extractText(cards.get(i), CSS_SERVICE_NAME);
                    String companyName = extractText(cards.get(i), CSS_COMPANY_NAME);
                    String rating      = extractAttr(cards.get(i), ATTR_RATING);
                    String location    = extractText(cards.get(i), CSS_LOCATION);

                    log.info("{}. Service: {} | Company: {} | Rating: {} | Location: {}",
                            i + 1, serviceName, companyName, rating, location);

                    excelRows.add(new String[]{
                            String.valueOf(i + 1), serviceName, companyName, rating, location
                    });

                } catch (Exception e) {
                    log.error("Error reading card {}: {}", i + 1, e.getMessage());
                }
            }
        }

        log.info("====================================================");
        ExcelUtil.writeListings(
                "test-output/CarWashServices.xlsx",
                "Car Wash Services",
                LISTING_HEADERS,
                excelRows
        );
    }

    // ── YK's method (TF-39) : Display ALL Listings ───────────────────────

    public void displayAllListings() {
        List<WebElement> cards = driver.findElements(LISTING_CARDS);
        List<String[]>   excelRows = new ArrayList<>();

        log.info("===== FITNESS CENTERS IN CHENNAI - GYM ONLY (TOP {}) =====", MAX_RECORDS);

        if (cards.isEmpty()) {
            log.warn("No results found. URL: {}", driver.getCurrentUrl());
        } else {
            int count = Math.min(MAX_RECORDS, cards.size());
            log.info("Displaying {} of {} result(s) found.", count, cards.size());

            for (int i = 0; i < count; i++) {
                try {
                    String serviceName = extractText(cards.get(i), CSS_SERVICE_NAME);
                    String companyName = extractText(cards.get(i), CSS_COMPANY_NAME);
                    String rating      = extractAttr(cards.get(i), ATTR_RATING);
                    String location    = extractText(cards.get(i), CSS_LOCATION);

                    log.info("{}. Service: {} | Company: {} | Rating: {} | Location: {}",
                            i + 1, serviceName, companyName, rating, location);

                    excelRows.add(new String[]{
                            String.valueOf(i + 1), serviceName, companyName, rating, location
                    });

                } catch (Exception e) {
                    log.error("Error reading card {}: {}", i + 1, e.getMessage());
                }
            }
        }

        log.info("==========================================================");
        ExcelUtil.writeListings(
                "test-output/FitnessCenter.xlsx",
                "Fitness Centers",
                LISTING_HEADERS,
                excelRows
        );
    }

    // ── Private helpers ───────────────────────────────────────────────────

    private String extractText(WebElement card, String cssSelector) {
        try {
            String text = card.findElement(By.cssSelector(cssSelector)).getText().trim();
            return text.isEmpty() ? "N/A" : text;
        } catch (Exception e) {
            return "N/A";
        }
    }

    private String extractAttr(WebElement card, String attribute) {
        try {
            String val = card.getAttribute(attribute);
            return (val == null || val.isEmpty()) ? "N/A" : val;
        } catch (Exception e) {
            return "N/A";
        }
    }
}
