package pgmacdesign.jetpacksamples.roomsamples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgmacdesign.pgmactips.adaptersandlisteners.OnTaskCompleteListener;
import com.pgmacdesign.pgmactips.misc.CustomAnnotationsBase;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import pgmacdesign.jetpacksamples.R;

public class RoomModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TAG_ROOM_MODEL_ADAPTER_CLICK = -121;
    public static final int TAG_ROOM_MODEL_ADAPTER_LONG_CLICK = -122;
    public static final int TAG_ROOM_MODEL_ADAPTER_X_IV = -123;

    //Dataset List
    private List<NotePOJO> mListObjects;

    //UI
    private LayoutInflater mInflater;

    //Misc
    private Context context;
    private boolean oneSelectedAnimate;
    private int layoutResourceId;
    private OnTaskCompleteListener listener;

    private int COLOR_BLACK;

    @CustomAnnotationsBase.RequiresDependency(requiresDependency = CustomAnnotationsBase.Dependencies.AndroidSupport_Design)
    public RoomModelAdapter(@NonNull Context context, @Nullable OnTaskCompleteListener listener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.COLOR_BLACK = ContextCompat.getColor(context, com.pgmacdesign.pgmactips.R.color.black);
        this.layoutResourceId = R.layout.adapter_room_model_recyclerview;
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutResourceId, parent, false);
        RoomHolder holder = new RoomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder0, final int position) {
        final NotePOJO note = this.mListObjects.get(position);
        RoomHolder holder = (RoomHolder) holder0;
        if(note == null){
            //Do stuff
            holder.adapter_room_model_tv1.setText("");
            holder.adapter_room_model_tv2.setText("No Note Found");
            holder.adapter_room_model_tv3.setText("");
            return;
        }

        String sNote = note.getNote();
        String dateAdded = "Added on: " + note.getDateAdded();
        String id = "ID: " + note.getId();

        holder.adapter_room_model_tv1.setText(id);
        holder.adapter_room_model_tv2.setText(sNote);
        holder.adapter_room_model_tv3.setText(dateAdded);

        if(this.listener != null){
            holder.adapter_room_model_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RoomModelAdapter.this.listener.onTaskComplete(note, TAG_ROOM_MODEL_ADAPTER_CLICK);
                }
            });
            holder.adapter_room_model_root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    RoomModelAdapter.this.listener.onTaskComplete(note, TAG_ROOM_MODEL_ADAPTER_LONG_CLICK);
                    return true;
                }
            });
            holder.adapter_room_model_cancel_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RoomModelAdapter.this.listener.onTaskComplete(note, TAG_ROOM_MODEL_ADAPTER_X_IV);
                }
            });
        }
        int size = (MiscUtilities.isListNullOrEmpty(this.mListObjects) ? 0 : this.mListObjects.size());
        if(position < size - 1){
            holder.separator.setVisibility(View.VISIBLE);
        } else {
            holder.separator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (MiscUtilities.isListNullOrEmpty(mListObjects) ? 0 : mListObjects.size());
    }


    public void setListObjects(List<NotePOJO> mListObjects) {
        this.mListObjects = mListObjects;
        this.notifyDataSetChanged();
    }

    private class RoomHolder extends RecyclerView.ViewHolder {

        private TextView adapter_room_model_tv1, adapter_room_model_tv2, adapter_room_model_tv3;
        private RelativeLayout adapter_room_model_root;
        private ImageView adapter_room_model_cancel_iv;
        private View separator;

        public RoomHolder(View itemView) {
            super(itemView);
            this.adapter_room_model_cancel_iv = itemView.findViewById(R.id.adapter_room_model_cancel_iv);
            this.adapter_room_model_root = itemView.findViewById(R.id.adapter_room_model_root);
            this.adapter_room_model_tv1 = itemView.findViewById(R.id.adapter_room_model_tv1);
            this.adapter_room_model_tv2 = itemView.findViewById(R.id.adapter_room_model_tv2);
            this.adapter_room_model_tv3 = itemView.findViewById(R.id.adapter_room_model_tv3);
            this.separator = itemView.findViewById(R.id.separator);
        }
    }

}
