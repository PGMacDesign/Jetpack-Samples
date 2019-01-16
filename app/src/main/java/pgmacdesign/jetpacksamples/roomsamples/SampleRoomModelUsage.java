package pgmacdesign.jetpacksamples.roomsamples;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pgmacdesign.pgmactips.adaptersandlisteners.OnTaskCompleteListener;
import com.pgmacdesign.pgmactips.utilities.DialogUtilities;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import pgmacdesign.jetpacksamples.R;

public class SampleRoomModelUsage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //UI
    private EditText sample_room_model_et;
    private Button sample_room_model_button;
    private SwipeRefreshLayout sample_room_model_swiperefreshview;
    private RecyclerView sample_room_model_recyclerview;

    //Vars
    private RoomModelAdapter roomModelAdapter;
    private OnTaskCompleteListener listener;
    private EditNoteDialog editNoteDialog;

    //View Model
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sample_room_model_usage);
        this.initVariables();
        this.initUI();
        this.initViewModels();
        this.initListeners();
        this.initLiveData();
    }

    //region Init Methods

    private void initVariables(){
        this.listener = new OnTaskCompleteListener() {
            @Override
            public void onTaskComplete(Object result, int customTag) {
                final NotePOJO n;
                switch (customTag){
                    case RoomModelAdapter.TAG_ROOM_MODEL_ADAPTER_CLICK:
                        n = (NotePOJO) result;
                        if(n != null){
                            L.m("Note (short click) == " + new Gson().toJson(n, NotePOJO.class));
                        }
                        break;

                    case RoomModelAdapter.TAG_ROOM_MODEL_ADAPTER_LONG_CLICK:
                        n = (NotePOJO) result;
                        if(n != null){
                            L.m("Note (long click) == " + new Gson().toJson(n, NotePOJO.class));
                            AlertDialog d = DialogUtilities.buildSimpleAlertDialog(
                                    SampleRoomModelUsage.this, new DialogUtilities.DialogFinishedListener() {
                                @Override
                                public void dialogFinished(Object object, int tag) {
                                    switch (tag){
                                        case DialogUtilities.YES_RESPONSE:
                                            SampleRoomModelUsage.this.viewModel.deleteNote(n);
                                            break;

                                        case DialogUtilities.NO_RESPONSE:
                                            //Do nothing, they canceled
                                            break;
                                    }
                                }
                            }, "Delete", "Are you sure you want to delete this note?",
                                    "Yes, Delete", "No, Cancel");
                            d.show();
                        }
                        break;

                    case RoomModelAdapter.TAG_ROOM_MODEL_ADAPTER_X_IV:
                        L.m("TAG_ROOM_MODEL_ADAPTER_X_IV");
                        n = (NotePOJO) result;
                        if(n != null){
                            showDialog(n);
                        }
                        break;

                }

            }
        };
        this.roomModelAdapter = new RoomModelAdapter(this, listener);
    }

    private void initUI(){
        this.sample_room_model_et = (EditText) this.findViewById(
                R.id.sample_room_model_et);
        this.sample_room_model_button = (Button) this.findViewById(
                R.id.sample_room_model_button);
        this.sample_room_model_recyclerview = (RecyclerView) this.findViewById(
                R.id.sample_room_model_recyclerview);
        this.sample_room_model_swiperefreshview = (SwipeRefreshLayout) this.findViewById(
                R.id.sample_room_model_swiperefreshview);
    }

    private void initViewModels(){
        this.viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    private void initListeners(){
        this.sample_room_model_swiperefreshview.setOnRefreshListener(this);
        this.sample_room_model_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = sample_room_model_et.getText().toString();
                if(!StringUtilities.isNullOrEmpty(s)){
                    NotePOJO n = new NotePOJO(s);
                    viewModel.insert(n);
                    sample_room_model_et.setText("");
                } else {
                    L.Toast(SampleRoomModelUsage.this, "No data to insert");
                }
            }
        });
        this.sample_room_model_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.sample_room_model_recyclerview.setAdapter(roomModelAdapter);
    }

    /**
     * Set the live data
     */
    private void initLiveData(){
        this.viewModel.getAllNotes().observe(this, new Observer<List<NotePOJO>>() {
            @Override
            public void onChanged(List<NotePOJO> notePOJOS) {
                roomModelAdapter.setListObjects(notePOJOS);
            }
        });
    }

    //endregion

    //region override methods


    @Override
    public void onRefresh() {
        this.sample_room_model_swiperefreshview.setRefreshing(false);
        //do stufff
    }

    //endregion

    //region Dialog Methods

    private void showDialog(final NotePOJO notePOJO){
        L.m("showDialog - 169");
        if(notePOJO == null){
            return;
        }
        L.m("showDialog - 169");
        if(this.editNoteDialog != null){
            this.editNoteDialog.dismiss();
        }
        L.m("showDialog - 169");
        this.editNoteDialog = null;
        L.m("showDialog - 169");
        this.editNoteDialog = new EditNoteDialog(this, new DialogUtilities.DialogFinishedListener() {
            @Override
            public void dialogFinished(Object object, int tag) {
                switch (tag){
                    case DialogUtilities.SUCCESS_RESPONSE:
                        String str = (String) object;
                        //Update note here with new data
                        notePOJO.setNote(str);
                        SampleRoomModelUsage.this.viewModel.updateNote(notePOJO);
                        L.toast(SampleRoomModelUsage.this, "Note Updated");
                        break;

                    case DialogUtilities.FAIL_RESPONSE:
                        //Means they canceled, ignore
                        L.toast(SampleRoomModelUsage.this, "Canceled Edit");
                        break;
                }
            }
        });
        this.editNoteDialog.show();
        L.m("showDialog - 169");
    }
    //endregion
}
