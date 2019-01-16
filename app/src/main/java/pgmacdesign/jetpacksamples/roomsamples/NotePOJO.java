package pgmacdesign.jetpacksamples.roomsamples;

import com.google.gson.annotations.SerializedName;
import com.pgmacdesign.pgmactips.utilities.DateUtilities;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Note Pojo
 */
@Entity(tableName = "Notes")
public class NotePOJO {

    //region Constructors

    public NotePOJO(String note){
        this.init(null, note);
    }

    @Ignore
    public NotePOJO(String id, String note){
        this.init(id, note);
    }

    //endregion

    //region Init
    private void init(String id, String note){
        if(StringUtilities.isNullOrEmpty(note)){
            return;
        }
        this.note = note;
        this.lengthOfNote = (long)note.length();
        this.dateAdded = DateUtilities.getSimpleDate();
        if(StringUtilities.isNullOrEmpty(id)){
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
    }
    //endregion

    //region Vars

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @NonNull
    @ColumnInfo(name = "note", index = false)
    @SerializedName("note")
    private String note;
    @ColumnInfo(name = "dateAdded", index = true)
    @SerializedName("dateAdded")
    private String dateAdded;
    @SerializedName("lengthOfNote")
    private Long lengthOfNote;

    //endregion

    //region Getters and Setters
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Long getLengthOfNote() {
        return lengthOfNote;
    }

    public void setLengthOfNote(Long lengthOfNote) {
        this.lengthOfNote = lengthOfNote;
    }

    //endregion
}
