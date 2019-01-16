package pgmacdesign.jetpacksamples.lifecyclesamples;

import com.pgmacdesign.pgmactips.adaptersandlisteners.OnTaskCompleteListener;
import com.pgmacdesign.pgmactips.misc.PGMacTipsConstants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class LifeCycleListener  <E extends Enum<E>> implements LifecycleObserver {

    // TODO: 1/10/2019 this only accounts for Activities as of now, need to implement Frags

    //4481
    public static final int TAG_LIFE_CYCLE_LISTENER = PGMacTipsConstants.TAG_LIFE_CYCLE_LISTENER;

    //region Instance Vars
    private OnTaskCompleteListener listener;
    private String activityName, fragmentName;
    private E customEnumValue;
    //endregion

    //region Constructors

    /**
     * Overloaded constructor for omission calls. Full constructor is here:
     * {@link LifeCycleListener#LifeCycleListener(OnTaskCompleteListener, String, String, Enum)}
     */
    public LifeCycleListener(@NonNull OnTaskCompleteListener listener){
        this.listener = listener;
        this.activityName = null;
        this.fragmentName = null;
        this.customEnumValue = null;
    }

    /**
     * Overloaded constructor for omission calls. Full constructor is here:
     * {@link LifeCycleListener#LifeCycleListener(OnTaskCompleteListener, String, String, Enum)}
     */
    public LifeCycleListener(@NonNull OnTaskCompleteListener listener,
                             @Nullable E customEnumValue){
        this.listener = listener;
        this.activityName = null;
        this.fragmentName = null;
        this.customEnumValue = customEnumValue;
    }

    /**
     * Constructor to pass in whichever variables should be returned on an event change
     * @param listener {@link OnTaskCompleteListener} listener to send back data upon
     * @param activityName Name of the activity this is being used in conjunction with
     * @param fragmentName Name of the fragment this is being used in conjunction with
     * @param customEnumValue Name of the custom enum value (IE, fragment or UI element) this is
     *                        being used in conjunction with
     */
    public LifeCycleListener(@NonNull OnTaskCompleteListener listener,
                             @Nullable String activityName,
                             @Nullable String fragmentName,
                             @Nullable E customEnumValue){
        this.listener = listener;
        this.activityName = activityName;
        this.fragmentName = fragmentName;
        this.customEnumValue = customEnumValue;
    }

    //endregion

    //region Events
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_CREATE), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_DESTROY), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPauseEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_PAUSE), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_RESUME), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_START), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStopEventHit(){
        if(listener != null){
            listener.onTaskComplete(buildPojo(Lifecycle.Event.ON_STOP), TAG_LIFE_CYCLE_LISTENER);
        }
    }

    //endregion

    //region Private Utility Functions
    private LifeCycleListenerPojo buildPojo(@NonNull Lifecycle.Event event){
        return new LifeCycleListenerPojo(this.activityName, this.fragmentName, this.customEnumValue, event);
    }
    //endregion
}
