package pgmacdesign.jetpacksamples.roomsamples;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Note DAO Interface. Reference docs here:
 * https://developer.android.com/reference/android/arch/persistence/room/Query
 */
@Dao
public interface NoteDao {
    //CRUD Methods:

    //region Create
    @Insert
    void insert(NotePOJO note);

    //endregion

    //region Read
    @Query("SELECT * FROM notes WHERE id IS :id")
    LiveData<NotePOJO> getNote(String id);
    @Query("SELECT * FROM notes")
    LiveData<List<NotePOJO>> readAll();
    @Query("SELECT * FROM notes WHERE dateAdded IS :dateToCheck")
    LiveData<List<NotePOJO>> getAllOnDate(String dateToCheck);

    //endregion

    //region Update
    @Update
    void update(NotePOJO notePOJO);

    //endregion

    //region Delete
    @Delete
    Integer delete(NotePOJO notePOJO);

    //endregion
}
