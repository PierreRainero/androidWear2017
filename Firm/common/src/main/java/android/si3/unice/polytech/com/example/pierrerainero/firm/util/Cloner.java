package android.si3.unice.polytech.com.example.pierrerainero.firm.util;

import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PierreRainero on 29/04/2017.
 */

public class Cloner {

    private Cloner(){
    }

    public static List<Store> cloneStoreList(List<Store> list) {
        List<Store> clone = new ArrayList<Store>(list.size());
        for (Store item : list)
            clone.add(item.clone());
        return clone;
    }
}
