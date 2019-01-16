package pgmacdesign.jetpacksamples.roomsamples;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {NotePOJO.class},
        version = 1,
        exportSchema = true
)
public abstract class NoteRoomDatabase extends RoomDatabase {

    /**
     * DAO == "Data Access Object"
     */
    public abstract NoteDao nodeDao();

    //Singleton Instance of DB
    private static volatile NoteRoomDatabase noteRoomInstance;

    static NoteRoomDatabase getInstance(final Context context){
        if(NoteRoomDatabase.noteRoomInstance == null){
            if(context == null){
                return null;
            }
            synchronized (NoteRoomDatabase.class){
                if(noteRoomInstance == null){
                    noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "notes_database")
                            .build();
                }
            }
        }
        return NoteRoomDatabase.noteRoomInstance;
    }
}
