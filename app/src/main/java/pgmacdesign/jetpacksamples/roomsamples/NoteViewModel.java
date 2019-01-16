package pgmacdesign.jetpacksamples.roomsamples;

import android.app.Application;
import android.os.AsyncTask;

import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Using AndroidViewModel instead of AndroidModel so as to gain the application context instead
 */
public class NoteViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    //Live Data will run on the background thread
    private LiveData<List<NotePOJO>> mListObjects;

    public NoteViewModel(Application application){
        super(application);
        this.noteDB = NoteRoomDatabase.getInstance(application);
        if(this.noteDB == null){
            return;
        }
        this.noteDao = noteDB.nodeDao();
        this.mListObjects = noteDao.readAll();
    }

    //region CRUD Operation items here

    public void insert(NotePOJO note){
        new DBCreateAsync(noteDao).execute(note);
    }

    public LiveData<List<NotePOJO>> getAllNotes(){
//        return this.noteDao.readAll();
        return this.mListObjects;
    }

    public LiveData<NotePOJO> getNote(NotePOJO note){
        return (note == null) ? null : getNote(note.getId());
    }

    public LiveData<NotePOJO> getNote(String noteId){
        return noteDao.getNote(noteId);
    }

    public void deleteNote(NotePOJO notePOJO){
        new DBDeleteAsync(noteDao).execute(notePOJO);
    }

    public void deleteNote(String noteId){
        try {
            deleteNote(getNote(noteId).getValue());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateNote(NotePOJO noteToUpdate){
        if(noteToUpdate != null){
            new DBUpdateAsync(noteDao).execute(noteToUpdate);
        }
    }


    //endregion

    //region Async Tasks
    private static class DBCreateAsync extends AsyncTask<NotePOJO, Void, Void> {
        NoteDao noteDao;

        public DBCreateAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NotePOJO... notePOJOS) {
            if(!MiscUtilities.isArrayNullOrEmpty(notePOJOS)) {
                for (NotePOJO n : notePOJOS) {
                    if (n != null) {
                        this.noteDao.insert(n);
                    }
                }
            }
            return null;
        }
    }

    private static class DBUpdateAsync extends AsyncTask<NotePOJO, Void, Void> {
        NoteDao noteDao;

        public DBUpdateAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NotePOJO... notePOJOS) {
            if(!MiscUtilities.isArrayNullOrEmpty(notePOJOS)) {
                for (NotePOJO n : notePOJOS) {
                    if (n != null) {
                        this.noteDao.update(n);
                    }
                }
            }
            return null;
        }
    }


    private static class DBDeleteAsync extends AsyncTask<NotePOJO, Void, Void> {
        NoteDao noteDao;

        public DBDeleteAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NotePOJO... notePOJOS) {
            if(!MiscUtilities.isArrayNullOrEmpty(notePOJOS)) {
                for (NotePOJO n : notePOJOS) {
                    if (n != null) {
                        this.noteDao.delete(n);
                    }
                }
            }
            return null;
        }
    }

    //endregion

    @Override
    protected void onCleared() {
        super.onCleared();
    }
    
    
}
