package com.yetanother.yetanotherdemo;

import android.app.DownloadManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {

    public String TUM_TUM_DATA_URL = "http://tumtum-iitb.org/jsonweb";
    private OkHttpClient mOkHttpClient;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        mOkHttpClient = new OkHttpClient();
        new GetTumTumsLocation().execute();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mGoogleMap==null) {
            mGoogleMap = googleMap;
        }
        LatLng iitBombay = new LatLng(19.131612, 72.913573);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iitBombay, 15));

        googleMap.addMarker(new MarkerOptions()
                .title("IIT Bombay")
                .snippet("Dystopia")
                .position(iitBombay));
    }

    public class GetTumTumsLocation extends AsyncTask<Void,MarkerModel,MarkerModel[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MarkerModel[] doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url(TUM_TUM_DATA_URL)
                    .build();
            Response response = null;
            try {
                response = mOkHttpClient.newCall(request).execute();
                if(response!=null) {
                    Gson gson = new Gson();
                    ResponseModel responseModel = gson.fromJson(response.body().string(),
                            ResponseModel.class);
                    // publishProgress(responseModel.markers);
                    return responseModel.getMarkers();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(MarkerModel... markers) {

        }

        @Override
        protected void onPostExecute(MarkerModel... markers) {
            updateMarkers(markers);
        }
    }

    private void updateMarkers(MarkerModel[] markers) {
        if(mGoogleMap!=null) {
            for(int i=0; i<markers.length; i++){
                MarkerModel marker = markers[i];
                if(marker.getIdle()==1) {
                    continue;
                }
                LatLng position = new LatLng(Double.parseDouble(marker.getLat()),
                        Double.parseDouble(marker.getLng()));
                mGoogleMap.addMarker(new MarkerOptions()
                        .title(marker.getRoute())
                        .snippet(marker.getDescription())
                        .position(position));
            }
        }
    }
}
