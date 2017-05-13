package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Intent;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DismissOverlayView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by PierreRainero on 09/05/2017.
 */

public class MapActivity extends WearableActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private LatLng coord;
    private GoogleMap mMap;

    private MapFragment mMapFragment;
    private DismissOverlayView mDismissOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Store selectedStore = (Store) intent.getSerializableExtra("store");
        coord = new LatLng(selectedStore.getLatitude(), selectedStore.getLongitude());
        setContentView(R.layout.activity_map);

        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText("Chargement...");
        mDismissOverlay.showIntroIfNecessary();

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mDismissOverlay.show();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.addMarker(new MarkerOptions().position(coord).title("Store localisation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 10));
        mMap.setOnMapLongClickListener(this);

    }
}