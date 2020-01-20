import logic.CalendarFileCreator;
import logic.Event;
import logic.HtmlEventsFinder;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRunner {
    public static void main(String[] args) {
        HtmlEventsFinder finder = new HtmlEventsFinder("https://www.awl-neuss.de/nc/abfallkalender.html?streetSelector=POSTSTRA%C3%9FE&yearSelector=2020");
        finder.extractEvents();

//        List<Event> eventsList = finder.getEventsList();
//
//        for (Event event :eventsList){
//            System.out.println(event.getDay()+"."+event.getMonth()+"."+event.getYear()+" event: "+event.getEvent());
//        }


        CalendarFileCreator c = new CalendarFileCreator(finder.getEventsList());
        c.createCalendar();
        c.saveCalendar();
        System.out.println(c.getIcsCalendar());
    }
}