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
import java.time.LocalDateTime;
import model.ReservationInfo;
import model.TimeEditCalendar;
import model.TimeEditEvent;

/**
 *
 * @author Caroline
 */
public class ClientControler {

    //Grundsökvägen till webservicarna vi vill anropa
    private static final String TIME_EDIT_URI = "https://cloud.timeedit.net/ltu/web/schedule1/ri.json?h=t&sid=3&p=20190902.x,20200906.x&objects=119838.28&ox=0&types=0&fe=0";
    private static final String CANVAS_URI = "https://ltu.instructure.com/api/v1/calendar_events.json";

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
//            System.out.println(line);
                //Ladda in datan i min tabell
                loadJTableFromJson(line);
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

    private void loadJTableFromJson(String jsonInput) {
        //Konvertera JSON-Array till en Array med Java-objekt (Av en klass som jag skapat som matchar)
        //Finns flera olika 3e-parts-bibliotek som kan användas för detta på https://www.json.org/json-en.html
        //I detta fallet används google-gson

        TimeEditCalendar timeEditCalendar = new TimeEditCalendar();
        JsonObject jsonObject = new Gson().fromJson(jsonInput, JsonObject.class);

        String[] columnheaders = new Gson().fromJson(jsonObject.get("columnheaders"), String[].class);
        ReservationInfo info = new Gson().fromJson(jsonObject.get("info"), ReservationInfo.class);
        TimeEditEvent[] timeEditEvent = new Gson().fromJson(jsonObject.get("reservations"), TimeEditEvent[].class);

        timeEditCalendar.setReservations(timeEditEvent);
        timeEditCalendar.setInfo(info);
        timeEditCalendar.setColumnheaders(columnheaders);

//        for (int i = 0; i < timeEditEvent.length; i++) {
//
//            System.out.printf("%s %s %s %s %s\n",
//                    timeEditEvent[i].getId(),
//                    timeEditEvent[i].getStartdate(),
//                    timeEditEvent[i].getStarttime(),
//                    timeEditEvent[i].getEnddate(),
//                    timeEditEvent[i].getEndtime());
//            for (int j = 0; j < timeEditEvent[i].getColumns().length; j++) {
//                System.out.printf("%s\n", timeEditEvent[i].getColumns()[j]);
//            }
//        }
    }

    //Lägger till kallenderevent till Canvaskalendern mha data i webformulär format
    public void setCanvasCalendar(CanvasEvent[] canvasEventsArray) {
       //Ta canvasEvent-arrayen och posta respektive objekt i den som ett kalenderobjekt
       //(Börja med att försöka posa ett objekt, sen hel array)
       CanvasEvent canvasEvent = new CanvasEvent();
       canvasEvent.setContextCode("user_65238");
       canvasEvent.setTitle("Test från NB 1");
       canvasEvent.setDescription("Detta är et test!");
       canvasEvent.setStartAt(LocalDateTime.of(2020,01,07,14,30));
       canvasEvent.setEndAt(LocalDateTime.of(2020,01,07,15,30));
       canvasEvent.setLocationName("Biblioteket");
       canvasEvent.setLocationAddress("Storgatan 5");
       
       //JAX-RS Client - Ett rekomenderat sätt att koppla upp sig (framför URL)
      //https://howtodoinjava.com/jersey/jersey-restful-client-examples/
      //jersey-client dependencyn behöver även jersey-hk2 dependencyn av samma version för att funka.
      Client client = ClientBuilder.newClient();
      //Går att göra om Client och WebTarget så de går at återanvända
      //istället för att skapa dem i varje metod - Görs så nu för exemplets skull.
      WebTarget target = client.target(ClientControler.CANVAS_URI);
      
      //Skapa ett Form-objekt som kan hålla formparametrarna från  ett application/x-www-form-urlencoded formulär
      Form form = new Form();
      form.param("calendar_event[context_code]", canvasEvent.getContextCode());
      form.param("calendar_event[title]", canvasEvent.getTitle());
      form.param("calendar_event[description]", canvasEvent.getDescription());
      form.param("calendar_event[start_at]", canvasEvent.getStartAt());
      form.param("calendar_event[end_at]", canvasEvent.getEndAt());
      form.param("calendar_event[location_name]", canvasEvent.getLocationName());
      form.param("calendar_event[location_address]", canvasEvent.getLocationAddress());
      
      //Skapa en anropsbyggare genom att använda target som håller URI...
      //... börja bygga en request och ange samtidigt vilken mediatyp som accepteras som respons.
      Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
      //Kör anropet med angivn metod, i detta fallet POST, 
      //och skickar med objektet i bodyn i form av en entitet av den angivna Mediatypen. 
      //Kan även vara .put(), .get() eller .delete()
      Response r = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
      if (r.getStatus() != 201) {
         JOptionPane.showMessageDialog(this, "FEL: " + r.getStatus() + " Ange för- och efternamn!");
      } else {
         getActorsByURL();//Updatera tabellen för att även visa senaste
         //Visa sökvägen till den nyligt skpade posten
         JOptionPane.showMessageDialog(this, "Ny post lades till: " + r.getLocation());
      }
    }

    public static void main(String[] args) {
        ClientControler run = new ClientControler();
        run.getTimeEditCalendar();
    }

}
