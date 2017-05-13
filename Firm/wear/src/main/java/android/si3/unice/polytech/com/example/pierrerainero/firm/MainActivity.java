package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.Serializer;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;

/**
 * Main activity for the wear module
 * Created by PierreRainero
 */
public class MainActivity extends WearableActivity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {
    private static final String WEAR_MESSAGE_PATH = "/message";
    private GoogleApiClient mApiClient;

    private boolean firstMessageReceived;
    private Store selectedStore;

    private TextView storeName;
    private TextView profit;
    private TextView rank;
    private TextView nbEmployee;
    private TextView bestProduct;
    private TextView worstProduct;

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty);
        firstMessageReceived = false;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();
    }

    /**
     * Initializes the GoogleApi (for receive and send message to the mobile)
     */
    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onResume() {
        super.onResume();
        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onStart() {
        super.onStart();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onMessageReceived( final MessageEvent messageEvent ) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                if( messageEvent.getPath().equalsIgnoreCase( WEAR_MESSAGE_PATH ) ) {
                    try {
                        selectedStore = (Store) Serializer.deserialize(messageEvent.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(!firstMessageReceived) {
                        initNewScene();
                        firstMessageReceived = true;
                    }

                    if(selectedStore!=null)
                        updateSelectedStore();
                }
            }
        });
    }

    /**
     * Change the xml layout to display a store
     */
    private void initNewScene(){
        setContentView(R.layout.activity_main);
        storeName = (TextView) findViewById(R.id.storeName);
        profit = (TextView) findViewById(R.id.benefValue);
        rank = (TextView) findViewById(R.id.rankValue);
        nbEmployee = (TextView) findViewById(R.id.employeeValue);
        bestProduct = (TextView) findViewById(R.id.bestValue);
        worstProduct = (TextView) findViewById(R.id.worstValue);
        ImageButton openMaps = (ImageButton) this.findViewById(R.id.mapButton);
        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent myIntent = new Intent(MainActivity.this, MapActivity.class);
                myIntent.putExtra("store", selectedStore);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    /**
     * Update the view with the new datas for the selected store
     */
    private void updateSelectedStore(){
        storeName.setText(selectedStore.getName());

        double benef = selectedStore.getProfit();
        profit.setText(String.format("%.2f", benef)+" €");
        if(benef>0)
            profit.setTextColor(Color.parseColor("#4da34d"));
        else
            profit.setTextColor(Color.parseColor("#f01000"));

        rank.setText(selectedStore.getRank()+"/"+selectedStore.getLastRank());

        nbEmployee.setText(String.valueOf(selectedStore.getEmployeeNb()));
        bestProduct.setText(selectedStore.getBestProduct());
        worstProduct.setText(selectedStore.getWorstProduct());
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onStop() {
        if ( mApiClient != null ) {
            Wearable.MessageApi.removeListener( mApiClient, this );
            if ( mApiClient.isConnected() ) {
                mApiClient.disconnect();
            }
        }
        super.onStop();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onDestroy() {
        if( mApiClient != null )
            mApiClient.unregisterConnectionCallbacks( this );
        super.onDestroy();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onConnectionSuspended(int i) {

    }
}