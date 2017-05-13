package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Context;
import android.content.Intent;
import android.si3.unice.polytech.com.example.pierrerainero.firm.activity.OneStoreActivity;
import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.RecyclerAdapterForStore;
import android.si3.unice.polytech.com.example.pierrerainero.firm.database.FirmBD;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Firm firm;
    private GoogleApiClient mApiClient;
    private List<Integer> selectedStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGoogleApiClient();
        initContent();
    }

    private void initContent(){
        FirmBD db = new FirmBD(this);
        selectedStore = new ArrayList<>();
        try {
            db.createDataBase();
            db.openDataBase();
            firm = db.getFirm();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) this.findViewById(R.id.shops);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterForStore(firm.getStores(), mApiClient, selectedStore);
        mRecyclerView.setAdapter(mAdapter);

        ImageButton analyzeBtn = (ImageButton) this.findViewById(R.id.openShopButton);
        final Context context = this;

        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedStore.size()==1) {
                    Intent myIntent = new Intent(MainActivity.this, OneStoreActivity.class);
                    myIntent.putExtra("store", firm.getStores().get(selectedStore.get(0)));
                    myIntent.putExtra("firm", firm);
                    MainActivity.this.startActivity(myIntent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Attention");
                    builder.setMessage("Vous ne devez sélectionner qu'un magasin pour l'analyser !");
                    builder.show();
                }
            }
        });
    }


    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        mApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }
}
