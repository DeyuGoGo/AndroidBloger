package go.deyu.androidbloger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    /*    記得在你的manifests上增加：
        <activity android:name=".MapActivityActivity"/>
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        定位需要幾秒，等一下囉。
     */
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                requestUserLocation();
                break;
        }
    }
    public void requestUserLocation() {
        final LocationManager mLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //判斷當前是否已經獲得了定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MapActivity.this,"權限沒開",Toast.LENGTH_SHORT).show();
//            如果是6.0以上的去需求權限
            requestCameraPermission();
            return;
        }

        mLocation.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                final StringBuffer sb = new StringBuffer();
                sb.append("Now location is : \n")
                        .append("lat : " + location.getLatitude()).append("\n")
                        .append("lng : " + location.getLongitude());
                textView.setText("lat : " + location.getLatitude());
                textView2.setText("lng : " + location.getLongitude());
                Toast.makeText(MapActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(final String s, final int i, final Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(final String s) {
            }

            public void onProviderDisabled(final String s) {
            }
        },  MapActivity.this.getMainLooper());
    }
    private void requestCameraPermission(){
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M)
            return;

        final List<String> permissionsList = new ArrayList<>();
        if(this.checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            permissionsList.add(Manifest.permission.CAMERA);
        if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionsList.size()<1)
            return;
        if(this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
            this.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]) , 0x00);
        else
            goToAppSetting();
    }

    private void goToAppSetting(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", this.getPackageName(), null));
        startActivityForResult(intent , 0x00);
    }
}