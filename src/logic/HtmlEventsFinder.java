package logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class HtmlEventsFinder {

    private String url;
    private List<Event> events;

    public HtmlEventsFinder() {
        this.events = new ArrayList<Event>();
    }

    public List<Event> getEventsList() {
        return events;
    }

    public void setURL(String url){
        this.url = url;
    }

    public void extractEvents() {

        Document htmlDoc = getHtmlDocument();

        //finding current year in html
        int year = findYear(htmlDoc);

        //extracting single month data from html
        Elements monthsElements = htmlDoc.getElementsByClass("singleMonth");

        for (Element monthElement : monthsElements){
            int monthInt = findMonthInt(monthElement);

            //extracting single day data from html
            Elements days = monthElement.getElementsByClass("singleDay");
            for (Element day : days){
                String event = findEvent(day);

                if (!event.equals("")){
                    int dayInt = findDayInt(day);
                    events.add(new Event(dayInt, monthInt, year, event));
                }
            }
        }
    }

    private int findYear(Document htmlDocument){
        return Integer.parseInt(url.substring(url.length()-4));
    }

    private int findMonthInt(Element monthElement){

        String monthName = monthElement.getElementsByClass("monthName").text();

        //NOTE in java.util.Calendar months are zero-based! So, January → 0
        if (monthName.contains("Januar")){
            return 0;
        }
        if (monthName.contains("Februar")){
            return 1;
        }
        if (monthName.contains("März")){
            return 2;
        }
        if (monthName.contains("April")){
            return 3;
        }
        if (monthName.contains("Mai")){
            return 4;
        }
        if (monthName.contains("Juni")){
            return 5;
        }
        if (monthName.contains("Juli")){
            return 6;
        }
        if (monthName.contains("August")){
            return 7;
        }
        if (monthName.contains("September")){
            return 8;
        }
        if (monthName.contains("Oktober")){
            return 9;
        }
        if (monthName.contains("November")){
            return 10;
        }
        if (monthName.contains("Dezember")){
            return 11;
        }
        else{
            return -999;
        }
    }

    private String findEvent(Element dayElement){

        String eventString = "";

        Elements eventElements = dayElement.getElementsByClass("events");

        for (Element event : eventElements){
            if (!event.text().equals("")){
                switch (event.text()){
                    case "B": eventString = "blaue Tonne"; break;
                    case "R": eventString = "graue Tonne"; break;
                    case "RP": eventString = "graue Tonne"; break;
                    case "BIO": eventString = "Bio-Tonne"; break;
                    case "G": eventString = "gelbe Tonne"; break;
                }
            }
        }
        return eventString;
    }

    private int findDayInt(Element dayElement){
        return Integer.parseInt(dayElement.getElementsByClass("number").text());
    }

    private Document getHtmlDocument() {
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
            final String html = stringBuilder.toString();

            return Jsoup.parse(html, "utf-8");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getClass() + " " + e.getMessage());
            return null;
        }
    }

    //TODO check valid input
}
