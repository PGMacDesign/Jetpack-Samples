package pgmacdesign.jetpacksamples.viewmodelsamples;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pgmacdesign.pgmactips.datamodels.SamplePojo;
import com.pgmacdesign.pgmactips.utilities.GsonUtilities;
import com.pgmacdesign.pgmactips.utilities.L;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import pgmacdesign.jetpacksamples.R;

public class SampleViewModelUsage extends AppCompatActivity {

    /*
    Info:
        View Model Structure:
            1) Activity is created and the view model remains active until it is destroyed.
            2) Once destroyed, onClear() is called within the ViewModel class to clear up things
                a) This is the best spot to clear up memory saved within the ViewModel
            3) Note that ViewModel is NOT the same as onSaveInstanceState()

     */


    //UI
    private TextView view_model_tv1, view_model_tv2, view_model_tv3;
    private Button view_model_button;

    //Vars
    private Observer<Integer> intObserver;
    private LiveData<Integer> liveDataInt;
    private Observer<String> strObserver;
    private LiveData<String> liveDataString;
    private Observer<Map<Integer, Object>> objObserver;
    private LiveData<Map<Integer, Object>> liveDataMap;
    private SampleViewModelClass viewModel;
    private boolean timersActivated;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sample_view_model_usage);
        this.timersActivated = false;
        this.view_model_tv1 = (TextView) this.findViewById(R.id.view_model_tv1);
        this.view_model_tv2 = (TextView) this.findViewById(R.id.view_model_tv2);
        this.view_model_tv3 = (TextView) this.findViewById(R.id.view_model_tv3);
        this.view_model_button = (Button) this.findViewById(R.id.view_model_button);

        //Instance of the ViewModel class
        this.viewModel = ViewModelProviders.of(this).get(SampleViewModelClass.class);
        this.intObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                SampleViewModelUsage.this.view_model_tv1.setText(integer + "");
            }
        };

        this.strObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.m("STR Observer triggered, S == " + s);
                SampleViewModelUsage.this.view_model_tv2.setText(s);
            }
        };

        this.objObserver = new Observer<Map<Integer, Object>>() {
            @Override
            public void onChanged(Map<Integer, Object> integerObjectMap) {
                if(integerObjectMap == null){
                    return;
                }
                SamplePojo samplePojo = (SamplePojo) integerObjectMap.get(SampleViewModelClass.TAG_SAMPLE_POJO);
                if(samplePojo != null) {
                    String s1 = "Object to JSON:\n" + GsonUtilities.convertObjectToString(samplePojo, SamplePojo.class);
                    SampleViewModelUsage.this.view_model_tv3.setText(s1);
                }
            }
        };

        this.liveDataInt = viewModel.getNumber();
        this.liveDataString = viewModel.getRandomString();
        this.liveDataMap = viewModel.getMapData();

        this.liveDataInt.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                SampleViewModelUsage.this.view_model_tv1.setText(integer + "");
            }
        });
        this.liveDataString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.m("Setting String (s) in onChanged to " + s);
                SampleViewModelUsage.this.view_model_tv2.setText(s + "");
            }
        });
        this.liveDataMap.observe(this, objObserver);

        this.view_model_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.m("Button click event");
//                SampleViewModelUsage.this.viewModel.createString();
//                SampleViewModelUsage.this.viewModel.createNumber();
//                SampleViewModelUsage.this.viewModel.createListOfObjects();
                if(SampleViewModelUsage.this.timersActivated){
                    SampleViewModelUsage.this.viewModel.stopResetTimers();
                    SampleViewModelUsage.this.timersActivated = false;
                    L.Toast(SampleViewModelUsage.this, "Stopping Timers");
                } else {
                    SampleViewModelUsage.this.viewModel.startResetTimersOnLoop();
                    SampleViewModelUsage.this.timersActivated = true;
                    L.Toast(SampleViewModelUsage.this, "Starting Timers");
                }

            }
        });
//        startResetTimersOnLoop();
    }



}
