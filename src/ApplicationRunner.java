import logic.HtmlEventsFinder;

public class ApplicationRunner {
    public static void main(String[] args) {
        HtmlEventsFinder c = new HtmlEventsFinder("https://www.awl-neuss.de/nc/abfallkalender.html?streetSelector=POSTSTRA%C3%9FE&yearSelector=2020");
        c.extractEvents();
        System.out.println(c.getEventsList());
    }
}