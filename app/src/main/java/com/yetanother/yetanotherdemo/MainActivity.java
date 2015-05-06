package com.yetanother.yetanotherdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {

    public String TUM_TUM_DATA_URL = "http://tumtum-iitb.org/jsonweb";
    private OkHttpClient mOkHttpClient;
    private GoogleMap mGoogleMap;
    private GetTumTumsLocation mGetTumTumsLocation;
    private BitmapDescriptor mGreenMarker;
    private BitmapDescriptor mYellowMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        mOkHttpClient = new OkHttpClient();
        mGetTumTumsLocation = new GetTumTumsLocation();
        mGetTumTumsLocation.execute();
        mGreenMarker = BitmapDescriptorFactory.fromResource(R.drawable.green);
        mYellowMarker = BitmapDescriptorFactory.fromResource(R.drawable.yellow);
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
    }

    public class GetTumTumsLocation extends AsyncTask<Void,MarkerModel,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url(TUM_TUM_DATA_URL)
                    .build();
            while(true) {
                Log.d("MainActivity.class", "in while loop");
                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    if(response!=null) {
                        Gson gson = new Gson();
                        ResponseModel responseModel = gson.fromJson(response.body().string(),
                                ResponseModel.class);
                        publishProgress(responseModel.getMarkers());
                        Thread.currentThread().sleep(5000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(MarkerModel... markers) {
            updateMarkers(markers);
        }

        @Override
        protected void onPostExecute(Void params) {

        }
    }

    private void updateMarkers(MarkerModel[] markers) {
        Log.d("MianActivity", "update markers called");
        if(mGoogleMap!=null) {
            mGoogleMap.clear();
            for(int i=0; i<markers.length; i++){
                MarkerModel marker = markers[i];
                LatLng position = new LatLng(Double.parseDouble(marker.getLat()),
                        Double.parseDouble(marker.getLng()));
                MarkerOptions markerObject = new MarkerOptions()
                        .title(marker.getRoute())
                        .snippet(marker.getDescription())
                        .position(position);
                if(marker.getIdle()==1) {
//                    markerObject.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellow));
                    mGoogleMap.addMarker(new MarkerOptions()
                            .title(marker.getRoute())
                            .snippet(marker.getDescription())
                            .position(position)
                            .icon(mYellowMarker));
                } else {
//                    markerObject.icon(BitmapDescriptorFactory.fromResource(R.drawable.green));
                    mGoogleMap.addMarker(new MarkerOptions()
                            .title(marker.getRoute())
                            .snippet(marker.getDescription())
                            .position(position)
                            .icon(mGreenMarker));
                }
//                mGoogleMap.addMarker(markerObject);
            }
        }
    }
}
