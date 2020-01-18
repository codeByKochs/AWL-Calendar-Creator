import logic.CalendarFileCreator;
import logic.HtmlEventsFinder;

import java.util.ArrayList;

public class ApplicationRunner {
    public static void main(String[] args) {
        HtmlEventsFinder finder = new HtmlEventsFinder("https://www.awl-neuss.de/nc/abfallkalender.html?streetSelector=POSTSTRA%C3%9FE&yearSelector=2020");
        CalendarFileCreator c = new CalendarFileCreator(new ArrayList<String>());
        c.createCalendar();
    }
}