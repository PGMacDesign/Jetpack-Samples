package pgmacdesign.jetpacksamples.roomsamples;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Note DAO Interface
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
    LiveData<NotePOJO> readSingle(String id);
    @Query("SELECT * FROM notes")
    LiveData<List<NotePOJO>> readAll();
    @Query("SELECT * FROM notes WHERE dateAdded IS :dateToCheck")
    LiveData<List<NotePOJO>> readAllOnDate(String dateToCheck);

    //endregion

    //region Update
//    @Update
//    LiveData<NotePOJO> update(String id, String noteText);
//    @Update
//    LiveData<NotePOJO> update(String id, NotePOJO notePOJO);
//    @Update
//    LiveData<NotePOJO> update(NotePOJO notePOJO);

    //endregion

    //region Delete
//    @Delete
//    Integer delete(String id);
//    @Delete
//    Integer delete(NotePOJO notePOJO);

    //endregion
}
