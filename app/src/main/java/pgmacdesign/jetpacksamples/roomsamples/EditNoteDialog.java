package pgmacdesign.jetpacksamples.roomsamples;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pgmacdesign.pgmactips.utilities.DialogUtilities;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;

import pgmacdesign.jetpacksamples.R;

public class EditNoteDialog extends Dialog implements View.OnClickListener {

    //Vars
    private Context context;
    private DialogUtilities.DialogFinishedListener dialogFinishedListener;

    //UI
    private Button room_model_edit_note_button_submit, room_model_edit_note_button_cancel;
    private EditText room_model_edit_note_et;

    public EditNoteDialog(Context context, DialogUtilities.DialogFinishedListener dialogFinishedListener) {
        super(context);
        this.dialogFinishedListener = dialogFinishedListener;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        this.setContentView(R.layout.room_model_edit_note);
        this.initUI();
    }

    private void initUI(){
        this.room_model_edit_note_button_submit = this.findViewById(R.id.room_model_edit_note_button_submit);
        this.room_model_edit_note_button_cancel = this.findViewById(R.id.room_model_edit_note_button_cancel);
        this.room_model_edit_note_et = this.findViewById(R.id.room_model_edit_note_et);

        this.room_model_edit_note_button_cancel.setTransformationMethod(null);
        this.room_model_edit_note_button_submit.setTransformationMethod(null);

        this.room_model_edit_note_button_cancel.setOnClickListener(this);
        this.room_model_edit_note_button_submit.setOnClickListener(this);
    }

    public void setNoteData(NotePOJO noteData){
        if(noteData != null){
            setNoteData(noteData.getNote());
        }
    }

    public void setNoteData(String noteData){
        if(this.room_model_edit_note_et != null) {
            if (!StringUtilities.isNullOrEmpty(noteData)) {
                this.room_model_edit_note_et.setText(noteData);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.room_model_edit_note_button_cancel:
                if(this.dialogFinishedListener != null){
                    this.dialogFinishedListener.dialogFinished(null, DialogUtilities.FAIL_RESPONSE);
                }
                this.dismiss();
                break;

            case R.id.room_model_edit_note_button_submit:
                String str = room_model_edit_note_et.getText().toString();
                if(StringUtilities.isNullOrEmpty(str)){
                    L.toast(context, "Must have text in the field to update");
                    return;
                }
                if(this.dialogFinishedListener != null){
                    this.dialogFinishedListener.dialogFinished(str, DialogUtilities.SUCCESS_RESPONSE);
                }
                this.dismiss();
                break;
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        if(this.dialogFinishedListener != null){
            this.dialogFinishedListener.dialogFinished(null, DialogUtilities.FAIL_RESPONSE);
        }
    }
}
