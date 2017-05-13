package android.si3.unice.polytech.com.example.pierrerainero.firm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.RecyclerAdapterForProduct;
import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.RecyclerAdapterForStore;
import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.StoreFragmentAdapter;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.AsyncTaskImage;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.Formator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class OneStoreActivity extends AppCompatActivity {
    private ViewPager pager;
    private StoreFragmentAdapter adapter;
    private Store selectedStore;
    private Firm firm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_one_store);

        Intent intent = getIntent();
        selectedStore = (Store) intent.getSerializableExtra("store");
        firm = (Firm) intent.getSerializableExtra("firm");
        initContent();
    }

    private void initContent(){
        pager = (ViewPager)findViewById(R.id.storeFrag);
        List<Store> stores = new ArrayList<>();
        stores.add(selectedStore);
        adapter = new StoreFragmentAdapter(getSupportFragmentManager(), stores, firm);
        pager.setAdapter(adapter);
    }
}
