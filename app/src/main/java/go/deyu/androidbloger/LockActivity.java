package go.deyu.androidbloger;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class LockActivity extends AppCompatActivity {


    @BindViews({R.id.tbtn_back,R.id.tbtn_task})ToggleButton[] mLocks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        如果tbtn_task被選的話，鎖住Task Menu
//        原理是一Pause又重新把自己moveTaskToFront
        if(mLocks[1].isChecked()){
            ActivityManager activityManager = (ActivityManager) getApplicationContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.moveTaskToFront(getTaskId(), 0);
        }
    }

    @Override
    public void onBackPressed() {
//        鎖住Back鍵
//        如tbtn被選的話，不執行super 就可以把Back預設行為無效化。
        if(mLocks[0].isChecked())
            return;
        super.onBackPressed();
    }
}
