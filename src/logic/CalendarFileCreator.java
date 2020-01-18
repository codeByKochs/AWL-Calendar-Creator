package logic;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VTimeZone;

import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFileCreator {

    private List<String> eventsList;
    private Calendar calendar;

    public CalendarFileCreator(List<String> eventsList){
        this.eventsList = eventsList;
    }

    public void createCalendar(){
        this.calendar = new Calendar();
        // Create a TimeZone
//        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
//        TimeZone timezone = registry.getTimeZone("America/Mexico_City");
//        VTimeZone tz = timezone.getVTimeZone();


        // Start Date is on: April 1, 2008, 9:00 am
        java.util.Calendar startDate = new GregorianCalendar();
//        startDate.setTimeZone();
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        startDate.set(java.util.Calendar.YEAR, 2008);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // End Date is on: April 1, 2008, 13:00
        java.util.Calendar endDate = new GregorianCalendar();
//        endDate.setTimeZone(timezone);
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        endDate.set(java.util.Calendar.YEAR, 2008);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);
    }

}
