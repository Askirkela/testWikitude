package com.luc.testwikitudes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wikitude.architect.ArchitectView;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by luc on 28/11/14.
 */
public class AR extends Activity {
    private ArchitectView architectView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar);

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectView.ArchitectConfig config = new ArchitectView.ArchitectConfig( "hJrKxY9SHgBjUbON37+BaFoSkCO0VgPoFedpitZHBge3iJO++GmcDSAt7x1RwAKUHbZGs7TsTumfTsfa67OafpWZNG66beA3AAKoraD4c1sphs1hgLCb/GUjRv5MSo7BZleK6qk9bhBltpxBy6y+0dzzzVXl13Np8BKafOn7ewRTYWx0ZWRfXygQFR7iIQiKELFbBn975SvjoMqz/H/D75oJCwHh7KnkuRNuof4+V666OzPQ289FxV217JEIKEwFwgO9KyKo38ggkv08pRwsvrQ4lkCVmtOxr74mqzn3Z/AKZn+RSZQgQwN8js57USdfpYVVIWQrh2Bu+QRM4Pmbu3JPbbRIBM0MUmzAUkmF1ey/Ygv+6t/fxywjltNAMEjmnit+nVpEaCLbEoeDTO9A3IMLlKSHTwBskoT2IqShnz/Z69QUTIOvr6PZl0kQs/VoaC+TEflysyNvf4KLdeMtyUYb7ImmC5Zo81qBZd9jP53pjGRxEV8prWxuwE4Y1mHESGkbAgiH+i+mT4cOrPMLCZ4/zwRj+VgXZzvYliW0ycjr1PqahnmPoSpIBwlrp8+XokiDWUH9IcxYbGzv8bMCh7j1eMejExVmhVCVAc/JGEjLussH9LAzf850+ptKGTXKvC0rpd83KXmtpQ6CCeTWOVzJYxNbFqmxSvkzxpgG7/U=" /* license key */ );
        this.architectView.onCreate( config );

        this.architectView.onPostCreate();
        try {
            this.architectView.load("geoloc.html");
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
    }
    public void onResume() {
        super.onResume();
    }
    public void onDestroy() {
        super.onDestroy();
    }
}
