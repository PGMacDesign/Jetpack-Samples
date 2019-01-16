package pgmacdesign.jetpacksamples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import pgmacdesign.jetpacksamples.roomsamples.SampleRoomModelUsage;
import pgmacdesign.jetpacksamples.viewmodelsamples.SampleViewModelUsage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //region Vars

    //endregion

    //region UI
    private Button button_to_sample_room_model_class, button_to_sample_view_model_class;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.initVariables();
        this.initUI();
    }

    //region Init Functions

    private void initVariables(){
        //TBD
    }

    private void initUI(){
        this.button_to_sample_room_model_class = (Button) this.findViewById(
                R.id.button_to_sample_room_model_class);
        this.button_to_sample_view_model_class = (Button) this.findViewById(
                R.id.button_to_sample_view_model_class);

        this.button_to_sample_room_model_class.setTransformationMethod(null);
        this.button_to_sample_view_model_class.setTransformationMethod(null);

        this.button_to_sample_room_model_class.setOnClickListener(this);
        this.button_to_sample_view_model_class.setOnClickListener(this);
    }


    //endregion

    //region Override Functions


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.button_to_sample_room_model_class:
                intent = new Intent(MainActivity.this, SampleRoomModelUsage.class);
                break;

            case R.id.button_to_sample_view_model_class:
                intent = new Intent(MainActivity.this, SampleViewModelUsage.class);
                break;

            default:
                intent = null;
        }
        if(intent != null){
            this.startActivity(intent);
        }
    }

    //endregion

}
