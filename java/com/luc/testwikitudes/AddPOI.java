package com.luc.testwikitudes;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class AddPOI extends Activity implements LocationListener {

    private TextView latitude, longitude, altitude;
    private EditText name, description;
    private LocationManager lmg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpoi);

        latitude = (TextView) findViewById(R.id.latval);
        longitude = (TextView) findViewById(R.id.lonval);
        altitude = (TextView) findViewById(R.id.altval);
        name = (EditText) findViewById(R.id.nomval);
        description = (EditText) findViewById(R.id.descrval);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charger();
            }
        });
    }


    //Envoi des données en tache asyncrone
    public void charger() {
        String lat, lon, alt, nam, descr;
        lat = (String) this.latitude.getText();
        lon = (String) this.longitude.getText();
        alt = (String) this.altitude.getText();
        nam = this.name.getText().toString();
        descr = this.description.getText().toString();


        BddCalcul calcul = new BddCalcul(lat, lon, alt, nam, descr);
        calcul.execute();
    }


    //Update des valeurs locales
    @Override
    public void onLocationChanged(Location location) {
        this.longitude.setText(Double.toString(location.getLongitude()));
        this.latitude.setText(Double.toString(location.getLatitude()));
        this.altitude.setText(Double.toString(location.getAltitude() + 0.1));
    }

    @Override
    public void onResume() {
        super.onResume();
        lmg = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lmg.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lmg.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lmg.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        lmg.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
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


    private class BddCalcul extends AsyncTask<Void, Integer, Void> {
        private static final String URL = "http://www.luc-debene.fr/andro/upload.php";
        private String param;

        public BddCalcul(String latitude, String longitude, String altitude, String name, String description) {
            param = "?latitude=" + latitude + "&longitude=" + longitude + "&altitude=" + altitude + "&name=" + name + "&description=" + description;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Debut du traitement asynchrone", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL + param);
                HttpResponse response = httpclient.execute(httppost);

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("log_tag", "Error " + statusCode + " for URL " + URL);
                }

            } catch (Exception e) {
                Log.w("log_tag", "log_tag: Error in http connection " + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Le traitement asynchrone est termine", Toast.LENGTH_LONG).show();
        }
    }
}
