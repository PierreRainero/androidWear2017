package android.si3.unice.polytech.com.example.pierrerainero.firm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.adapter.StoreFragmentAdapter;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class CompareStoresActivity extends AppCompatActivity {
    private ViewPager fragment;
    private ViewPager fragment2;
    private StoreFragmentAdapter adapter;
    private ArrayList<Store> stores;
    private Firm firm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_stores_activity);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayout);

        Intent intent = getIntent();
        stores = (ArrayList<Store>) intent.getSerializableExtra("storesIndex");
        firm = (Firm) intent.getSerializableExtra("firm");

        if(linearLayout.getTag() != null) {
            String screen_density = (String) linearLayout.getTag();
            if(screen_density.equalsIgnoreCase("sw600dp") && getResources().getConfiguration().orientation==2)
                if(initLandView())
                    return;
        }

        fragment = (ViewPager)findViewById(R.id.storeFrag);
        initContent(fragment, stores);
    }

    private boolean initLandView(){
        if(stores.size()<2)
            return false;

        setContentView(R.layout.compare_stores_activity_land);
        fragment = (ViewPager)findViewById(R.id.storeFrag);
        fragment2 = (ViewPager)findViewById(R.id.storeFrag2);

        List<Store> stores1 = copyStores();
        List<Store> stores2 = copyStores();
        stores1.remove(1);
        stores2.remove(0);

        initContent(fragment, stores1);
        initContent(fragment2, stores2);
        return true;
    }

    private void initContent(ViewPager pager, List<Store> storesToDisplay){
        adapter = new StoreFragmentAdapter(getSupportFragmentManager(), storesToDisplay, firm);
        pager.setAdapter(adapter);
    }

    private List<Store> copyStores(){
        List<Store> returnV = new ArrayList<>();
        for(int i=0;i<stores.size();i++){
            returnV.add(stores.get(i));
        }
        return returnV;
    }
}
