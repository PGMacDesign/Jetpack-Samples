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
        new InsertAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<NotePOJO>> getAllNotes(){
//        return this.noteDao.readAll();
        return this.mListObjects;
    }

    //endregion

    //region Async Tasks
    private class InsertAsyncTask extends AsyncTask<NotePOJO, Void, Void> {
        NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao){
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

    //endregion

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
