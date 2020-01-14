/*
2020-01-13
D0031N Enterprise Architecture och SOA
Examinationsuppgift 4, Schemahantering LTU - EA-modellering och Systemintegration
Caroline Blomgren, carbol-8@student.ltu.se
Robin Hellström, robhel-4@student.ltu.se
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

    private final ClientGUI clientGui;
    private CanvasEvent[] canvasEvent;

    //Grundsökvägen till webservicarna vi vill anropa
    private static final String TIME_EDIT_URI = "https://cloud.timeedit.net/ltu/web/schedule1/ri.json?h=t&sid=3&p=20190902.x,20200906.x&objects=119838.28&ox=0&types=0&fe=0";
    private static final String CANVAS_URI = "https://ltu.instructure.com/api/v1/calendar_events.json";
    
    //Token för OAuth2 autentisering vid Canvas API-anrop
    private static final String TOKEN = "3755~TwMIw2unF5GG6JJ3Sxlxf59jb5QZAoCxLAyvyA8SPOrIkHsUv8Ab1vF2a1efxiVt";

    
    public ClientControler(ClientGUI clientGui) {
        this.clientGui = clientGui;
    }

    
    //Kör metod som ser till att canvasEvent blir tilldelad 
    public CanvasEvent[] getTimeEditEvent() {
        this.getTimeEditCalendar();
        return this.canvasEvent;
    }

    
    public void setCanvasEvent(CanvasEvent[] canvasEvent) {
        this.canvasEvent = canvasEvent;
    }

    
    public CanvasEvent[] getCanvasEvent() {
        return this.canvasEvent;
    }

    
    //Sätter en ny description till ett objekt i arrayen-canvasEvent
    public void setCanvasEventDescription(String description, int i) {
        this.canvasEvent[i].setDescription(description);
    }

    
    //Använder klassen URL för att peka ut den TimeEdit-resurs vi vill få data från
    //Anropar loadJTableFromTimeEdit()
    private void getTimeEditCalendar() {
        try {
//            URL url = new URL(ClientControler.TIME_EDIT_URI);
            //Test
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
                loadJTableFromTimeEdit(line);
                line = br.readLine();
            }

            //Stäng http-connection
            httpCon.disconnect();

        } catch (MalformedURLException urlEx) {
            Logger.getLogger(ClientControler.class.getName()).log(Level.SEVERE, null, urlEx);
        } catch (IOException ioEx) {
            Logger.getLogger(ClientControler.class.getName()).log(Level.SEVERE, null, ioEx);
        }
    }

    
    //Konverterar ett JSON-objekt till ett Java-objekt mha google-gson
    //Kallar sen på ConvertTimeEditEventToCanvasEvent()
    private void loadJTableFromTimeEdit(String jsonInput) {
        
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

    
    //Plockar ut de individuella TimeEdit-reservationerna från ett TimeEditCalendar-objekt 
    //och lägger i instansvariabeln canvasEvent som är en CanvasEvent-array
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

    
    //Sätter contextCode hos objekten i canvasEvent-arrayen till det kalender-ID som fås som inparameter
    private void setContextCode(String contextCode) {

        for (int i = 0; i < this.canvasEvent.length; i++) {
            //I skarp version ska det vara "setContextCode("course_" + contextCode);"
            //då det är kursscheman som ska sättas
            //+ behöver fixas kontroll på vad användaren matar in
            this.canvasEvent[i].setContextCode("user_" + contextCode);
        }
    }

    
    //Formaterar om start- och sluttid så som Canvas API vill få dem
    private void formatCanvasTime() {
        //Hårdkodad variant, kan lätt ändras till en mer dynamisk som söker efter mellanrum, men bör ha tillräckligt
        //stor kontroll på tiden för att det inte ska behövas
        for (int i = 0; i < this.canvasEvent.length; i++) {
            canvasEvent[i].setStartAt(canvasEvent[i].getStartAt().substring(0, 10) + "T" + canvasEvent[i].getStartAt().substring(11));
            canvasEvent[i].setEndAt(canvasEvent[i].getEndAt().substring(0, 10) + "T" + canvasEvent[i].getEndAt().substring(11));
        }
    }

    
    //Lägger till kallenderevent till Canvaskalendern mha JAX-RS Jersey Client och data i webformulär format
    public void setCanvasCalendar(String contextCode) { 

        this.setContextCode(contextCode);
        this.formatCanvasTime();

        //OAuth2-autentisering med token
        Feature feature = OAuth2ClientSupport.feature(TOKEN);

        Client client = ClientBuilder.newBuilder().register(feature).build();
        WebTarget target = client.target(ClientControler.CANVAS_URI);

        //Skapar en anropsbyggare genom att använda target som håller URI,
        //börjar bygga en request och ange samtidigt vilken mediatyp som accepteras som respons.
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        //Räknare för hur många kalenderevent som inte gick att posta
        int numberOfErrors = 0;

        for (int i = 0; i < this.canvasEvent.length; i++) {

            //Skapar ett Form-objekt som kan hålla formparametrarna från ett application/x-www-form-urlencoded formulär
            Form form = new Form();
            form.param("calendar_event[context_code]", this.canvasEvent[i].getContextCode());
            form.param("calendar_event[title]", this.canvasEvent[i].getTitle());
            form.param("calendar_event[description]", this.canvasEvent[i].getDescription());
            form.param("calendar_event[start_at]", this.canvasEvent[i].getStartAt());
            form.param("calendar_event[end_at]", this.canvasEvent[i].getEndAt());
            form.param("calendar_event[location_name]", this.canvasEvent[i].getLocationName());
            form.param("calendar_event[location_address]", this.canvasEvent[i].getLocationAddress());

            //Kör anropet med angivn metod, i detta fallet POST och skickar med 
            //objektet i bodyn i form av en entitet av den angivna Mediatypen. 
            Response r = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            if (r.getStatus() != 201) {
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
}
