package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Context;
import android.content.Intent;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.activity.CompareStoresActivity;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.adapter.RecyclerAdapterForStore;
import android.si3.unice.polytech.com.example.pierrerainero.firm.database.FirmBD;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;

import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity for the mobile module
 * Created by PierreRainero
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Firm firm;
    private GoogleApiClient mApiClient;
    private List<Integer> selectedStore;

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGoogleApiClient();
        initContent();
    }

    /**
     * Load all datas and init the main view
     */
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

        initSpin();
        fillListView();

        ImageButton compareBtn = (ImageButton) this.findViewById(R.id.compareButton);
        final Context context = this;
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedStore.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(getString(R.string.warning));
                    builder.setMessage(getString(R.string.oneStoreMini));
                    builder.show();
                }else{
                    Intent myIntent = new Intent(MainActivity.this, CompareStoresActivity.class);
                    myIntent.putExtra("storesIndex", storesToCompare());
                    myIntent.putExtra("firm", firm);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    /**
     * Initializes the spinner and link the values to the correct sorting methodes
     */
    private void initSpin(){
        Spinner storeSpin = (Spinner) this.findViewById(R.id.storeSelector);
        List<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add(getString(R.string.byProfit));
        spinnerArray.add(getString(R.string.byCity));
        spinnerArray.add(getString(R.string.byDepartment));
        spinnerArray.add(getString(R.string.byRegion));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpin.setAdapter(adapter);

        storeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position){
                    case 0:default :
                        firm.rankStoreByProfit();
                        break;

                    case 1:
                        firm.rankStoreByCity();
                        break;

                    case 2:
                        firm.rankStoreByDepartement();
                        break;

                    case 3:
                        firm.rankStoreByRegion();
                        break;
                }
                fillListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }

    /**
     * Fill the recyclerView with stores
     */
    private void fillListView(){
        mRecyclerView = (RecyclerView) this.findViewById(R.id.shops);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterForStore(firm.getStores(), mApiClient, selectedStore);
        mRecyclerView.setAdapter(mAdapter);
        selectedStore.clear();
    }

    /**
     * Generate a ArrayList (this class is serializable) of stores to display
     * @return stores to display in the compare view
     */
    private ArrayList<Store> storesToCompare(){
        ArrayList<Store> returnV = new ArrayList<>();
        for(int i=0;i<selectedStore.size();i++)
            returnV.add(firm.getStores().get(selectedStore.get(i)));
        return returnV;
    }

    /**
     * Initializes the GoogleApi (for receive and send message to the wear)
     */
    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        mApiClient.connect();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onConnectionSuspended(int i) {

    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }
}
