package android.si3.unice.polytech.com.example.pierrerainero.firm.view.adapter;

import android.si3.unice.polytech.com.example.pierrerainero.firm.view.fragment.StoreFragment;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class StoreFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Store> stores;
    private Firm firm;

    public StoreFragmentAdapter(FragmentManager fm, List<Store> stores, Firm firm) {
        super(fm);

        this.stores = stores;
        this.firm = firm;
    }

    @Override
    public Fragment getItem(int position) {
        return new StoreFragment().newInstance(position, stores, firm);
    }

    @Override
    public int getCount() {
        return stores.size();
    }
}
