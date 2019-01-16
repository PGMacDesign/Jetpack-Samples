package pgmacdesign.jetpacksamples.roomsamples;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pgmacdesign.pgmactips.adaptersandlisteners.OnTaskCompleteListener;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;

import java.util.List;

import androidx.annotation.Nullable;
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
                NotePOJO n;
                if(customTag == RoomModelAdapter.TAG_ROOM_MODEL_ADAPTER_CLICK){
                    n = (NotePOJO) result;
                    if(n != null){
                        L.m("Note (short click) == " + new Gson().toJson(n, NotePOJO.class));
                    }
                }
                if(customTag == RoomModelAdapter.TAG_ROOM_MODEL_ADAPTER_LONG_CLICK){
                    n = (NotePOJO) result;
                    if(n != null){
                        L.m("Note (long click) == " + new Gson().toJson(n, NotePOJO.class));
                    }
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

}
