package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.RecyclerAdapterForStore;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Store> stores;
    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGoogleApiClient();
        initContent();
    }

    private void initContent(){
        stores = new ArrayList<>();
        Store st = new Store("Magasin 1", "28 rue des champs", "le bled", 83210, "Var", "PACA", "Super centre", "magasin cool", "http://archives.varmatin.com/media_varmatin/imagecache/article-taille-normale-nm/image/ouch/2015/02/21/50b1f4c8ed2cffb22ec8ce367f56baf1.jpg");
        stores.add(st);
        stores.add(st);
        stores.add(st);
        stores.add(st);
        stores.add(st);
        stores.add(st);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.shops);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterForStore(stores, mApiClient);
        mRecyclerView.setAdapter(mAdapter);
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
