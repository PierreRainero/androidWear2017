package android.si3.unice.polytech.com.example.pierrerainero.firm.view.fragment;

import android.graphics.Color;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Product;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.adapter.RecyclerAdapterForProduct;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Fragment (view) for a store
 * Created by PierreRainero on 13/05/2017.
 */

public class StoreFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<Store> stores;
    private static Firm firm;

    private static final int PROMOTED_COLOR = -6697984;
    private static final int STANDARD_COLOR = -13388315;
    private static final int FLAGSHIP_COLOR = -17613;

    public static final String POSITION = "position";

    public StoreFragment() {}

    public static StoreFragment newInstance(int pos,  List<Store> dstores, Firm dfirm) {
        StoreFragment myFrag = new StoreFragment();
        stores = dstores;
        firm = dfirm;

        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, pos);
        myFrag.setArguments(bundle);

        return myFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.store_fragment, container, false);
        int index = getArguments().getInt(POSITION);
        Store currentStore = stores.get(index);

        TextView storeName = (TextView) rootView.findViewById(R.id.storeName);
        TextView description = (TextView) rootView.findViewById(R.id.descriptionShop);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageShop);
        TextView address = (TextView) rootView.findViewById(R.id.addressStore);
        TextView rank = (TextView) rootView.findViewById(R.id.rankStore);
        TextView employees = (TextView) rootView.findViewById(R.id.employeesStore);
        TextView benef = (TextView) rootView.findViewById(R.id.benefStore);
        TextView in = (TextView) rootView.findViewById(R.id.inStore);
        TextView out = (TextView) rootView.findViewById(R.id.outStore);


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
        in.setText(String.valueOf(currentStore.getTurnover()));
        out.setText(String.valueOf(currentStore.getCost()));

        AsyncTaskImage async = new AsyncTaskImage(image);
        async.execute(currentStore.getImage());

        PieChartView chart = (PieChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        List<SliceValue> values = new ArrayList<>();
        for(int i = 0; i < firm.getProducts().size(); i++){
            String ref = firm.getProducts().get(i).getReference();
            SliceValue sliceValue = new SliceValue((float) (currentStore.getProductProfit(ref)/currentStore.getProfit()), getColor(firm.getProducts().get(i)));
            sliceValue.setLabel(ref);
            values.add(sliceValue);
        }

        PieChartData data = new PieChartData(values);
        if(values.size()>6)
            data.setHasLabels(false);
        else
            data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(false);
        data.setHasCenterCircle(false);

        chart.setPieChartData(data);

        return rootView;
    }

    private int getColor(Product product){
        if(product.isFlagship())
            return FLAGSHIP_COLOR;
        if(product.isPromoted())
            return PROMOTED_COLOR;
        return STANDARD_COLOR;
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
