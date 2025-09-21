package dto;

public class Elements {
    public static String BASE_URL = "https://www.booking.com/searchresults.en-gb.html";
    public static String SEARCH_INPUT = "//input[@name='ss']";
    public static String SUBMIT_BUTTON = "//button[@type='submit']";
    public static String HOTELS = "//div[@data-testid='title']";
    public static String RATING = "[data-testid='review-score'] > div:first-child + div";
    public static String HOTEL = "//ul[@role='group']//*[contains(text(), '%s')]";
}
