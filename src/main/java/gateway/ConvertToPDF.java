package gateway;
import event.EventManager;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * The ConvertToPDF Class converts the event schedule to a PDF file.
 *
 *
 * @author Temilade Adeleye
 * @version 1.0
 */

public class ConvertToPDF {
    private HashMap<String, HashMap<String, String>> rooms;
    EventManager e = new SerializeData().deserializeEvent("phase2/src/main/java/gateway/eventManager.ser");

    /**
     * Constructor of the ConvertToPDF Class.
     */
    public ConvertToPDF() {}

    /**
     * Helper method: gets roomID, Time and eventID info from eventManager.ser and
     * puts info in rooms HashMap where the key is the roomID, the value is another HashMap
     * where the key is Time and the value is eventID
     */
    private  void getRoom(){
        rooms = new HashMap<>();
        if (e!=null) {
            for (String key : e.getEventList().keySet()) {
                String roomID = e.getRoomID(key);
                String startTime = e.getStartTime(key);
                String endTime = e.getEndTime(key);
                HashMap<String, String> time = new HashMap<>();
                time.put(startTime, key);
                //add the same eventID for consecutively different times
                //in the same room into rooms hashmap
                int sTime = Integer.parseInt(startTime);
                int eTime = Integer.parseInt(endTime);
                while (sTime < eTime) {
                    if (rooms.containsKey(roomID)) {
                        rooms.get(roomID).put(sTime + "", key);
                    } else {
                        rooms.put(roomID, time);
                    }
                    sTime++;
                }
            }
        }
    }

    /**
     * creates PDF using information in rooms HashMap
     * @param destination the destination of the PDF file
     * @throws IOException Throws IOException
     */
    public void createPdf(String destination) throws IOException{
        PdfWriter writer = new PdfWriter(new FileOutputStream(destination));
        Document document = new Document(new PdfDocument(writer));

        this.getRoom();
        if(!rooms.isEmpty()){
            document.add(new Paragraph("Event Schedule").setTextAlignment(TextAlignment.CENTER));
            //creates a table with the number of columns based on the number of rooms
            float[] pointColumnWidths;
            int numColumn = rooms.size()+1;
            float columnWidth = (float)500/numColumn;
            pointColumnWidths = new float[numColumn];
            for (int i = 0; i < numColumn; i++) {
                pointColumnWidths[i] = columnWidth;
            }
            Table table = new Table(pointColumnWidths).setHorizontalAlignment(HorizontalAlignment.CENTER);
            // adds table headers (Time, Room 1, Room 2, ..., Room n)
            table.addCell(new Cell().add("Time"));
            for (String key : rooms.keySet()){
                table.addCell(new Cell().add("Room "+key));
            }
            // adds time and event info under the appropriate headers
            int t1 = 9;
            int t2 = 10;
            while(t1<17){
                table.addCell(new Cell().add(t1+ ":00-"+ t2 +":00"));
                for (String key : rooms.keySet()) {
                    if (rooms.get(key).containsKey(t1+"")) {
                        table.addCell(new Cell().add(e.getEventName(rooms.get(key).get(t1+""))));
                    } else {
                        table.addCell(new Cell().add(""));
                    }
                }
                t1++;
                t2++;
            }
            document.add(table);
        }
        else{
            document.add(new Paragraph("No Schedule Yet").setTextAlignment(TextAlignment.CENTER));
        }
        document.close();
    }



}

