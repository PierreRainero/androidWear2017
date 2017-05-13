package android.si3.unice.polytech.com.example.pierrerainero.firm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.adapter.StoreFragmentAdapter;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class CompareStoresActivity extends AppCompatActivity {
    private ViewPager pager;
    private StoreFragmentAdapter adapter;
    private ArrayList<Store> stores;
    private Firm firm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_stores_activity);

        Intent intent = getIntent();
        stores = (ArrayList<Store>) intent.getSerializableExtra("storesIndex");
        firm = (Firm) intent.getSerializableExtra("firm");
        initContent();
    }

    private void initContent(){
        pager = (ViewPager)findViewById(R.id.storeFrag);
        adapter = new StoreFragmentAdapter(getSupportFragmentManager(), stores, firm);
        pager.setAdapter(adapter);
    }
}
