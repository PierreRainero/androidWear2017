package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.Serializer;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty);
        firstMessageReceived = false;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
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
        bestProduct.setText(selectedStore.getBestProduct().toString());
        worstProduct.setText(selectedStore.getWorstProduct().toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
    }

    @Override
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
    protected void onDestroy() {
        if( mApiClient != null )
            mApiClient.unregisterConnectionCallbacks( this );
        super.onDestroy();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}