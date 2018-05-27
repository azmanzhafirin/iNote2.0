package demo.inote;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Firin on 10-Apr-18.
 */

public class Notes {
    private String noteId;
    private String noteCategory;
    private String note;
    private String rating;
    private HashMap<String, Object> timeStamp;

   // private Map<String, String> timeStamp;

    public Notes(){

    }

    public Notes(String noteId, String noteCategory, String note, String rating) {
        this.noteId = noteId;
        this.noteCategory = noteCategory;
        this.note = note;
        this.rating = rating;
        //this.timeStamp = timeStamp;
        HashMap<String, Object> timestampNow = new HashMap<>();
        timestampNow.put("timestamp", ServerValue.TIMESTAMP);
        this.timeStamp = timestampNow;
    }

    public HashMap<String, Object> getTimestampCreated(){
        return timeStamp;
    }

    @Exclude
    public long getTimestampCreatedLong(){
        return (long)timeStamp.get("timestamp");
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteCategory() {
        return noteCategory;
    }

    public String getNote() {
        return note;
    }

    public String getRating() {
        return rating;
    }

   // public Long getTimeStamp() { return timeStamp;}
}
