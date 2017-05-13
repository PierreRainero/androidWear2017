package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DismissOverlayView;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity to open Google maps for a specific coordinate (longitude & latitude)
 * Created by PierreRainero on 09/05/2017.
 */

public class MapActivity extends WearableActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private LatLng coord;
    private GoogleMap mMap;

    private MapFragment mMapFragment;
    private DismissOverlayView mDismissOverlay;

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Store selectedStore = (Store) getIntent().getSerializableExtra("store");
        coord = new LatLng(selectedStore.getLatitude(), selectedStore.getLongitude());
        setContentView(R.layout.activity_map);

        ImageButton returnButton = (ImageButton) this.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });

        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText("Chargement...");
        mDismissOverlay.showIntroIfNecessary();

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onMapLongClick(LatLng latLng) {
        mDismissOverlay.show();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.addMarker(new MarkerOptions().position(coord).title("Store localisation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 10));
        mMap.setOnMapLongClickListener(this);

    }
}