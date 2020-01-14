/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testCode;

import model.CanvasEvent;

/**
 *
 * @author Caroline
 */
public class TestCode {
   
   //Test-URI för eventen i december
    private static final String TEST_TIME_EDIT_URI = "https://cloud.timeedit.net/ltu/web/schedule1/ri.json?h=t&sid=3&p=20191202.x,20200906.x&objects=119838.28&ox=0&types=0&fe=0";
   
   //        //Testsnurra med testArray för setCanvasCalendar()
//   //Lägg in CanvasEvent[] canvasEventsArray som inparameter vid test
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
   
   //    //Testobjekt
//        CanvasEvent canvasEvent = new CanvasEvent();
//        canvasEvent.setContextCode("user_65238");
//        canvasEvent.setTitle("Test från NB 3");
//        canvasEvent.setDescription("Detta är et test!");
//        canvasEvent.setStartAt("2020-01-10T17:00:00Z");
//        canvasEvent.setEndAt("2020-01-10T19:00:00Z");
//        canvasEvent.setLocationName("Biblioteket");
//        canvasEvent.setLocationAddress("Storgatan 5");
   
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
   
   private CanvasEvent[] canvasEvent; 
   
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
   
}
