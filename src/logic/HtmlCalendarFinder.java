package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlCalendarFinder {

    static final String HTML_MONTH_WRAPPER_START = "<div class=\"month\">";
    static final String HTML_MONTH_WRAPPER_END = "</div>";

    static final String HTML_DAY_WRAPPER_START = "<tr class=\"singleDay wednesday\">";
    static final String HTML_DAY_WRAPPER_END = "</tr>";

    static final String HTML_DATE_WRAPPER_START = ">";
    static final String HTML_DATE_WRAPPER_END = "<";

    static final String HTML_EVENT_WRAPPER_START = ">";
    static final String HTML_EVENT_WRAPPER_END = "<";


    private String url;
    private Event[] calendar;

    public HtmlCalendarFinder() {
        //TODO change to list
//        calendar = new Date[365];
        this.url = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Event[] getCalendar() {
        return calendar;
    }

    //TODO set to private, return list of schedule dates
    public void extractScheduleDates() {
        String sourceData = getHtmlString();
        String monthData[] = findMonthData(sourceData);

        for (String singleMonthData : monthData) {
            //find month from HTML data
            int month = findMonth(singleMonthData);

            //find list of single days and their info
            List<String> dayData = findDayData(singleMonthData);

            for (String singleDayData : dayData){

                //TODO if event is found create a Date with the event and add it to the list of event days
                int date = findDate(singleDayData);
                String event = findEvent(singleDayData);
                int year = findYear(singleDayData);

                if (event != null){
                    System.out.println(date+"."+month+"."+year+" "+" event: "+event);
                }
            }
        }
    }

    public String[] findMonthData(String data) {

        String monthMatches[] = new String[12];

        Pattern monthPattern = Pattern.compile(HTML_MONTH_WRAPPER_START + ".+?" + HTML_MONTH_WRAPPER_END);
        Matcher monthMatcher = monthPattern.matcher(data);

        try {
            int counter = 0;
            while (monthMatcher.find()) {
                monthMatches[counter] = monthMatcher.group();
                counter++;
            }

        }
        catch (Exception e){
            System.out.println("Exception Error while searching for month data: "+e.getMessage());
        }
        return monthMatches;
    }

    public int findYear(String data){
        //TODO rework
        return 2020;
    }

    public int findMonth(String data){
        try{
            Pattern monthNamePattern = Pattern.compile("(Januar|Februar|M&auml;rz|April|Mai|Juni|Juli|August|September|Oktober|November|Dezember)");
            Matcher monthNameMatcher = monthNamePattern.matcher(data);
            monthNameMatcher.find();
            String monthName = monthNameMatcher.group();
            if (monthName.equals("M&auml;rz")) {
                monthName = "März";
            }
            switch (monthName){
                case "Januar":
                    return 1;
                case "Februar":
                    return 2;
                case "März":
                    return 3;
                case "April":
                    return 4;
                case "Mai":
                    return 5;
                case "Juni":
                    return 6;
                case "Juli":
                    return 7;
                case "August":
                    return 8;
                case "September":
                    return 9;
                case "Oktober":
                    return 10;
                case "November":
                    return 11;
                case "Dezember":
                    return 12;
                default:
                    return 0;
            }
        }
        catch (Exception e){
            System.out.println("Exception Error while searching for month: "+e.getMessage());
            return 0;
        }
    }

    public List<String> findDayData(String data){
        List<String> dayMatches = new ArrayList<String>();
        try {
            Pattern dayPattern = Pattern.compile(HTML_DAY_WRAPPER_START + ".+?" + HTML_DAY_WRAPPER_END);
            Matcher dayMatcher = dayPattern.matcher(data);

            while (dayMatcher.find()) {
                dayMatches.add(dayMatcher.group());
            }
            return dayMatches;
        }
        catch (Exception e){
            System.out.println("Exception Error while searching for day data: "+e.getMessage());
            return null;
        }
    }

    public int findDate(String data){
        try{
            Pattern datePattern = Pattern.compile(HTML_DATE_WRAPPER_START + "/d{1,2}" + HTML_DATE_WRAPPER_END);
            Matcher dateMatcher = datePattern.matcher(data);
            dateMatcher.find();

            return Integer.parseInt(dateMatcher.group().substring(HTML_DATE_WRAPPER_START.length(), dateMatcher.group().length()-HTML_DATE_WRAPPER_END.length()-1));
        }
        catch (Exception e){
            System.out.println("Exception Error while searching for date: "+e.getMessage());
            return -999;
        }
    }

    public String findEvent(String data){
        try{
            Pattern eventPattern = Pattern.compile(HTML_EVENT_WRAPPER_START + "(B|R|P|G|BIO)" + HTML_EVENT_WRAPPER_END);
            Matcher eventMatcher = eventPattern.matcher(data);
            eventMatcher.find();

            return eventMatcher.group().substring(HTML_EVENT_WRAPPER_START.length(), eventMatcher.group().length()-HTML_EVENT_WRAPPER_END.length());
        }
        catch (Exception e){
            System.out.println("Exception Error while searching for event: "+e.getMessage());
            return null;
        }
    }

    public String getHtmlString() {
        try {
            String LINE_SEPARATOR = System.getProperty("line.separator");

            final URLConnection urlConnection = new URL(url).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            final StringBuilder stringBuilder = new StringBuilder();

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
                stringBuilder.append(LINE_SEPARATOR);
            }
            final String siteText = stringBuilder.toString();
            return siteText;
        } catch (Exception e) {
            return "An error occurred: " + e.getClass() + " " + e.getMessage();
        }
    }
}
