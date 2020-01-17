import logic.HtmlCalendarFinder;

public class ApplicationRunner {
    public static void main(String[] args) {
        HtmlCalendarFinder c = new HtmlCalendarFinder();
        c.setUrl("https://www.awl-neuss.de/nc/abfallkalender.html?streetSelector=POSTSTRA%C3%9FE&yearSelector=2020");
        c.extractScheduleDates();
    }
}