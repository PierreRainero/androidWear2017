package android.si3.unice.polytech.com.example.pierrerainero.firm.fragment;

import android.si3.unice.polytech.com.example.pierrerainero.firm.adapter.RecyclerAdapterForProduct;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.AsyncTaskImage;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.Formator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class StoreFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Store currentStore;
    private static Firm firm;

    public StoreFragment() {}

    public static StoreFragment newInstance(int pos,  List<Store> stores, Firm dfirm) {
        StoreFragment myFrag = new StoreFragment();
        currentStore = stores.get(pos);
        firm = dfirm;

        return myFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.store_fragment, container, false);

        TextView storeName = (TextView) rootView.findViewById(R.id.storeName);
        TextView description = (TextView) rootView.findViewById(R.id.descriptionShop);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageShop);
        TextView address = (TextView) rootView.findViewById(R.id.addressStore);
        TextView rank = (TextView) rootView.findViewById(R.id.rankStore);
        TextView employees = (TextView) rootView.findViewById(R.id.employeesStore);
        TextView benef = (TextView) rootView.findViewById(R.id.benefStore);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.products);
        mLayoutManager = new LinearLayoutManager(getContext(), 0, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterForProduct(firm.getProducts(), currentStore);
        mRecyclerView.setAdapter(mAdapter);

        storeName.setText(currentStore.getName());
        description.setText(currentStore.getDescription());
        address.setText(currentStore.getAddress());
        rank.setText(currentStore.getRank()+"/"+currentStore.getLastRank());
        employees.setText(String.valueOf(currentStore.getEmployeeNb()));

        Formator.formateMoneyText(currentStore.getProfit(), benef);

        AsyncTaskImage async = new AsyncTaskImage(image);
        async.execute(currentStore.getImage());

        return rootView;
    }
}
