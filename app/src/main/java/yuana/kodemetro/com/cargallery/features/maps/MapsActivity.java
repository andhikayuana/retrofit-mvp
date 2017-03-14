package yuana.kodemetro.com.cargallery.features.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.models.DirectionResults;
import yuana.kodemetro.com.cargallery.models.Location;
import yuana.kodemetro.com.cargallery.models.Route;
import yuana.kodemetro.com.cargallery.models.Step;
import yuana.kodemetro.com.cargallery.networks.RestClient;
import yuana.kodemetro.com.cargallery.utils.Const;
import yuana.kodemetro.com.cargallery.utils.Helper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Call<DirectionResults> mApi;
    private String TAG = MapsActivity.class.getSimpleName();
    private LatLng originLatLng;
    private LatLng destLatLng;
    private EditText etOrigin;
    private EditText etDest;
    private Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        initView();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void initView() {
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDest = (EditText) findViewById(R.id.etDest);
        btnFind = (Button) findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDirections();
            }
        });
    }

    private void getDirections() {

        mApi = RestClient
                .getApi(Const.BASE_MAP_API_URL)
                .getDirections("json", getQueryParams());

        mApi.enqueue(new Callback<DirectionResults>() {
            @Override
            public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {
                try {

                    if (response.isSuccessful()) {

                        mMap.clear();

                        DirectionResults directionResults = response.body();

                        Log.i(TAG, "inside on success" + directionResults.getRoutes().size());

                        ArrayList<LatLng> routelist = new ArrayList<LatLng>();

                        if (directionResults.getRoutes().size() > 0) {
                            ArrayList<LatLng> decodelist;
                            Route routeA = directionResults.getRoutes().get(0);

                            Log.i(TAG, "Legs length : " + routeA.getLegs().size());

                            if (routeA.getLegs().size() > 0) {
                                List<Step> steps = routeA.getLegs().get(0).getSteps();
                                Log.i(TAG, "Steps size :" + steps.size());
                                Step step;
                                Location location;
                                String polyline;

                                originLatLng = routeA.getLegs().get(0).getStartLocation().toLatLng();
                                destLatLng = routeA.getLegs().get(0).getEndLocation().toLatLng();

                                mMap.addMarker(new MarkerOptions()
                                        .position(originLatLng)
                                        .title(routeA.getLegs().get(0).getStartAddress())
                                );
                                mMap.addMarker(new MarkerOptions()
                                        .position(destLatLng)
                                        .title(routeA.getLegs().get(0).getEndAddress())
                                );

                                for (int i = 0; i < steps.size(); i++) {
                                    step = steps.get(i);
                                    location = step.getStartLocation();

                                    routelist.add(location.toLatLng());

                                    Log.i(TAG, "Start Location :" + location.getLat() + ", " + location.getLng());

                                    polyline = step.getPolyline().getPoints();

                                    decodelist = Helper.decodePoly(polyline);

                                    routelist.addAll(decodelist);
                                    location = step.getEndLocation();

                                    routelist.add(location.toLatLng());

                                    Log.i(TAG, "End Location :" + location.getLat() + ", " + location.getLng());
                                }
                            }

                        }
                        Log.i(TAG, "routelist size : " + routelist.size());
                        if (routelist.size() > 0) {
                            PolylineOptions rectLine = new PolylineOptions().width(10).color(
                                    Color.RED);

                            for (int i = 0; i < routelist.size(); i++) {
                                rectLine.add(routelist.get(i));
                            }
                            // Adding route on the map
                            mMap.addPolyline(rectLine);

                            CameraUpdate center =
                                    CameraUpdateFactory.newLatLng(originLatLng);
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                            mMap.moveCamera(center);
                            mMap.animateCamera(zoom);
                        }

                    } else {
                        Log.d(TAG, response.errorBody().string());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DirectionResults> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }

    private Map<String, String> getQueryParams() {
        HashMap<String, String> q = new HashMap<>();
        q.put("origin", etOrigin.getText().toString());
        q.put("destination", etDest.getText().toString());
        return q;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mApi.isExecuted()) mApi.cancel();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Bugs Training Center and move the camera
        LatLng btc = new LatLng(-7.758302, 110.382246);

        mMap.addMarker(
                new MarkerOptions().position(btc).title("Marker in BTC")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        );


        LatLng pizzaJakal = new LatLng(-7.756378, 110.382267);
        mMap.addPolyline(
                new PolylineOptions()
                        .add(
                                pizzaJakal,
                                new LatLng(-7.758717, 110.381505),
                                new LatLng(-7.756644, 110.381355),
                                new LatLng(-7.756113, 110.380261),
                                new LatLng(-7.756059, 110.381302),
                                btc
                        )
                        .width(10)
                        .color(Color.RED)
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(btc, 18));
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}
