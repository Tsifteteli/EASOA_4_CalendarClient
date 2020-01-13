/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.CanvasEvent;
import javax.ws.rs.core.Feature;
import model.ReservationInfo;
import model.TimeEditCalendar;
import model.TimeEditEvent;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import view.ClientGUI;

/**
 *
 * @author Caroline
 */
public class ClientControler {

    private ClientGUI clientGui;

    //Grundsökvägen till webservicarna vi vill anropa
    private static final String TIME_EDIT_URI = "https://cloud.timeedit.net/ltu/web/schedule1/ri.json?h=t&sid=3&p=20190902.x,20200906.x&objects=119838.28&ox=0&types=0&fe=0";
    private static final String CANVAS_URI = "https://ltu.instructure.com/api/v1/calendar_events.json";
    private static final String TOKEN = "3755~TwMIw2unF5GG6JJ3Sxlxf59jb5QZAoCxLAyvyA8SPOrIkHsUv8Ab1vF2a1efxiVt";
    private CanvasEvent[] canvasEvent;

    public ClientControler(ClientGUI clientGui) {
        this.clientGui = clientGui;
    }

    //P.g.a. hårdkodning av sökning på kurskod kan egentligen en modifikation till getCanvasCalendar
    //input värde ge ne sträng som designerar vilken kurs som ska hämtas
    //Där finns också ett timeEditEvent objekt som innehåller alla headers också
    //som man kan återanvända till GUI:n för att visa headers där.
    public CanvasEvent[] getTimeEditEvent() {
        getTimeEditCalendar();
        return this.canvasEvent;
    }

    public void setCanvasEvent(CanvasEvent[] canvasEvent) {
        this.canvasEvent = canvasEvent;
    }

    public CanvasEvent[] getCanvasEvent() {
        return this.canvasEvent;
    }

    public void setCanvasEventDescription(String description, int i) {
        this.canvasEvent[i].setDescription(description);
    }

    private void getTimeEditCalendar() {
        try {
            //Använd klassen URL för att peka ut en resurs på WWW
            //https://docs.oracle.com/javase/8/docs/api/java/net/URL.html
            URL url = new URL(ClientControler.TIME_EDIT_URI);
            //Öppnar connectionen mot REST servicen
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("Accept", "application/json");

            //Generera undantag om connection
            if (httpCon.getResponseCode() != 200) {
                throw new RuntimeException("FEL : HTTP-kod : "
                        + httpCon.getResponseCode());
            }

            //getInputstream() anropar angivet http-REST-API och tar emot responsen som en teckenström
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    httpCon.getInputStream()));

            //Läs av rad för rad från BufferedReadern till min tabell
            String line = br.readLine();
            while (line != null) {
                //Testa först med en System.out.println så jag ser att jag får ut data och vilken sorts data det är, 
                //så jag kan skapa en egen klass som matchar och kan hålla datat.
                //Ladda in datan i min tabell
                loadJTableFromTimeEdit(line);
                line = br.readLine();
            }

            //Ett annat sätt at läsa av alla rader från BufferedReadern till min tabell
            //loadJTableFromJson(br.lines().collect(Collectors.joining(System.lineSeparator())));
            //Signalera stängning av http-connection
            httpCon.disconnect();

        } catch (MalformedURLException urlEx) {
            Logger.getLogger(ClientControler.class.getName()).log(Level.SEVERE, null, urlEx);
        } catch (IOException ioEx) {
            Logger.getLogger(ClientControler.class.getName()).log(Level.SEVERE, null, ioEx);
        }
    }

    private void loadJTableFromTimeEdit(String jsonInput) {
        //Konvertera JSON-Array till en Array med Java-objekt (Av en klass som jag skapat som matchar)
        //Finns flera olika 3e-parts-bibliotek som kan användas för detta på https://www.json.org/json-en.html
        //I detta fallet används google-gson
        TimeEditCalendar timeEditCalendar = new TimeEditCalendar();
        JsonObject jsonObject = new Gson().fromJson(jsonInput, JsonObject.class);

        String[] columnheaders = new Gson().fromJson(jsonObject.get("columnheaders"), String[].class);
        ReservationInfo info = new Gson().fromJson(jsonObject.get("info"), ReservationInfo.class);
        TimeEditEvent[] timeEditEvent = new Gson().fromJson(jsonObject.get("reservations"), TimeEditEvent[].class);

        timeEditCalendar.setInfo(info);
        timeEditCalendar.setReservations(timeEditEvent);
        timeEditCalendar.setColumnheaders(columnheaders);

        ConvertTimeEditEventToCanvasEvent(timeEditCalendar);

    }

    private void ConvertTimeEditEventToCanvasEvent(TimeEditCalendar timeEditCalendar) {

        this.canvasEvent = new CanvasEvent[timeEditCalendar.getInfo().getReservationcount()];

        //Statisk variabel för varje del, kan ändras till något mer dynamiskt som känner av vilken typ
        //som finns i coulmnheader för att sedan föra över rätt typ till canvas
        for (int i = 0; i < this.canvasEvent.length; i++) {
            this.canvasEvent[i] = new CanvasEvent();
            this.canvasEvent[i].setLocationName(timeEditCalendar.getReservations()[i].getColumns()[1]);
            this.canvasEvent[i].setTitle(timeEditCalendar.getReservations()[i].getColumns()[3] + " " + timeEditCalendar.getReservations()[i].getColumns()[2]);
            this.canvasEvent[i].setLocationAddress(timeEditCalendar.getReservations()[i].getColumns()[6]);
            this.canvasEvent[i].setStartAt(timeEditCalendar.getReservations()[i].getStartdate() + " " + timeEditCalendar.getReservations()[i].getStarttime());
            this.canvasEvent[i].setEndAt(timeEditCalendar.getReservations()[i].getEnddate() + " " + timeEditCalendar.getReservations()[i].getEndtime());
            this.canvasEvent[i].setDescription(timeEditCalendar.getReservations()[i].getColumns()[7]);
        }
    }

    private void setContextCode(String contextCode) {

        for (int i = 0; i < this.canvasEvent.length; i++) {
            //I skarp version ska det vara setContextCode("course_" + contextCode);
            //+ fixa kontroll på vad användaren matar in
            this.canvasEvent[i].setContextCode("user_" + contextCode);
        }

    }
    
    private void fixTimeOneHour() { //Onödig om det funkar med att skippa Z
       
      for (int i = 0; i < this.canvasEvent.length; i++) {
         //2020-01-10T17:00:00Z
         //Få ut timmarna
         String startTime = this.canvasEvent[i].getStartAt();
         String startHour = startTime.substring(12,14);

         //Konverera till int
         int intStartHour = Integer.parseInt(startHour);

         //ta minus 1h (if klockslaget är 00, sätt till 23)
         if (intStartHour == 0) {
            intStartHour = 23;
         } else {
            intStartHour--;
         }
         //Konvertera tillbaka till Sring
         startHour = Integer.toString(intStartHour);

         //Kopiera in den nya timmen på rätt plats
         //Om Stringen bara är 1 car lång - lägg en nolla på index 12
         //Om Stringen är 2 car lång - lägg in som nedan
         char[] startTimeCharArray = startTime.toCharArray();
         startTimeCharArray[12] = 'x'; //Byt ut x mot get car från startHour
         startTimeCharArray[13] = 'x';
         startTime = String.valueOf(startTimeCharArray);
      }
    }

    private void formatCanvasTime() {
        //Hårdkodad variant, kan lätt ändras till en mer dynamisk som söker efter mellanrum, men bör ha tillräckligt
        //stor kontroll på tiden för att det inte ska behövas
        for (int i = 0; i < this.canvasEvent.length; i++) {
            canvasEvent[i].setStartAt(canvasEvent[i].getStartAt().substring(0, 10) + "T" + canvasEvent[i].getStartAt().substring(11));
            canvasEvent[i].setEndAt(canvasEvent[i].getEndAt().substring(0, 10) + "T" + canvasEvent[i].getEndAt().substring(11));
        }
    }

    //Lägger till kallenderevent till Canvaskalendern mha data i webformulär format
    public void setCanvasCalendar(String contextCode) { //Lägg in CanvasEvent[] canvasEventsArray vid test

        //JAX-RS Client - Ett rekomenderat sätt att koppla upp sig (framför URL)
        //https://howtodoinjava.com/jersey/jersey-restful-client-examples/
        //jersey-client dependencyn behöver även jersey-hk2 dependencyn av samma version för att funka.
        //Går att göra om Client och WebTarget så de går at återanvända
        //istället för att skapa dem i varje metod - Görs så nu för exemplets skull.
//      //Hur man gör en GET-request med OAuth2-autentisering
//      public Response bearerAuthenticationWithOAuth2AtClientLevel(String token) {
//          Feature feature = OAuth2ClientSupport.feature(token);
//          Client client = ClientBuilder.newBuilder().register(feature).build();
//
//          return client.target("https://sse.examples.org/")
//            .request()
//            .get();
//      }
        this.setContextCode(contextCode);
        this.formatCanvasTime();

        Feature feature = OAuth2ClientSupport.feature(TOKEN);

        Client client = ClientBuilder.newBuilder().register(feature).build();

        WebTarget target = client.target(ClientControler.CANVAS_URI);

        //Skapa en anropsbyggare genom att använda target som håller URI...
        //... börja bygga en request och ange samtidigt vilken mediatyp som accepteras som respons.
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

//        //Testsnurra med testArray
//        for (int i = 0; i < canvasEventsArray.length; i++) {
//            
//           //Skapa ett Form-objekt som kan hålla formparametrarna från  ett application/x-www-form-urlencoded formulär
//           Form form = new Form();
//           form.param("calendar_event[context_code]", canvasEventsArray[i].getContextCode());
//           form.param("calendar_event[title]", canvasEventsArray[i].getTitle());
//           form.param("calendar_event[description]", canvasEventsArray[i].getDescription());
//           form.param("calendar_event[start_at]", canvasEventsArray[i].getStartAt());
//           form.param("calendar_event[end_at]", canvasEventsArray[i].getEndAt());
//           form.param("calendar_event[location_name]", canvasEventsArray[i].getLocationName());
//           form.param("calendar_event[location_address]", canvasEventsArray[i].getLocationAddress());
        //Räknare för hur många kalenderevent som inte gick att posta
        int numberOfErrors = 0;

        for (int i = 0; i < this.canvasEvent.length; i++) {

            //Skapa ett Form-objekt som kan hålla formparametrarna från  ett application/x-www-form-urlencoded formulär
            Form form = new Form();
            form.param("calendar_event[context_code]", this.canvasEvent[i].getContextCode());
            form.param("calendar_event[title]", this.canvasEvent[i].getTitle());
            form.param("calendar_event[description]", this.canvasEvent[i].getDescription());
            form.param("calendar_event[start_at]", this.canvasEvent[i].getStartAt());
            form.param("calendar_event[end_at]", this.canvasEvent[i].getEndAt());
            form.param("calendar_event[location_name]", this.canvasEvent[i].getLocationName());
            form.param("calendar_event[location_address]", this.canvasEvent[i].getLocationAddress());

            //Kör anropet med angivn metod, i detta fallet POST, 
            //och skickar med objektet i bodyn i form av en entitet av den angivna Mediatypen. 
            //Kan även vara .put(), .get() eller .delete()
            Response r = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            if (r.getStatus() != 201) {
//                System.out.println("Någor sket sig... " + r.getStatus());
                numberOfErrors++;
                JOptionPane.showMessageDialog(this.clientGui, "Someting went wrong: "
                        + r.getStatus(), "Unable to post calendar event", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (numberOfErrors == 0) {
            JOptionPane.showMessageDialog(this.clientGui, "All events were "
                    + "successfully added to the canvas calendar with ID "
                    + this.canvasEvent[0].getContextCode(), "Status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editCanvasEvent() {

    }

    private static CanvasEvent[] testArray() {
        CanvasEvent[] testArray = new CanvasEvent[3];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = new CanvasEvent();
            testArray[i].setContextCode("user_65238");
            testArray[i].setLocationName("Biblioteket");
            testArray[i].setTitle("Test från NB 4");
            testArray[i].setLocationAddress("Storgatan 5");
            testArray[i].setStartAt("2020-01-10T16:00:00Z");
            testArray[i].setEndAt("2020-01-10T19:00:00Z");
            testArray[i].setDescription("Detta är et test!");
        }
        return testArray;
    }

//    //Testobjekt
//        CanvasEvent canvasEvent = new CanvasEvent();
//        canvasEvent.setContextCode("user_65238");
//        canvasEvent.setTitle("Test från NB 3");
//        canvasEvent.setDescription("Detta är et test!");
//        canvasEvent.setStartAt("2020-01-10T17:00:00Z");
//        canvasEvent.setEndAt("2020-01-10T19:00:00Z");
//        canvasEvent.setLocationName("Biblioteket");
//        canvasEvent.setLocationAddress("Storgatan 5");
}
