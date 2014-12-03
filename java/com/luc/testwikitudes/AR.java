package com.luc.testwikitudes;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by luc on 28/11/14.
 */
public class AR extends Activity implements LocationListener {
    private ArchitectView architectView;
    private Intent i;
    private LocationManager lmg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar);

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectView.ArchitectConfig config = new ArchitectView.ArchitectConfig( getResources().getString(R.string.sdk_key) /* license key */ );
        this.architectView.onCreate( config );
        Button b = (Button) findViewById(R.id.addPOI);



        i = new Intent(this, AddPOI.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        this.architectView.onPostCreate();
        try {
            this.architectView.load("index.html");
        }catch(IOException ioe) {
            Log.v("HTML loading", "" + ioe.getMessage());
        }
    }

    public void onPostCreate() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPause() {
        super.onPause();
        architectView.onPause();
        lmg.removeUpdates(this);
    }

    public void onResume() {
        super.onResume();
        architectView.onResume();
        super.onResume();
        lmg = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lmg.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lmg.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lmg.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
    }

    public void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        architectView.setLocation(location.getLatitude(), location.getLongitude(), 0.0f);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_enabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
