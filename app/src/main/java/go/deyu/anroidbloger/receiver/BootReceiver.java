package go.deyu.anroidbloger.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context , "BootComplete 我開機啦" , Toast.LENGTH_LONG).show();
    }
}
