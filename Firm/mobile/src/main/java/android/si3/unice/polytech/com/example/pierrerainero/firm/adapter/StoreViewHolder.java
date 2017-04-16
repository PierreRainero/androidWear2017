package android.si3.unice.polytech.com.example.pierrerainero.firm.adapter;

import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PierreRainero on 16/04/2017.
 */

public class StoreViewHolder extends RecyclerView.ViewHolder {
    public TextView viewName;
    public TextView viewMallName;
    public TextView viewAddress;
    public ImageView viewImage;
    public ImageButton viewWearButon;

    public StoreViewHolder(View view) {
        super(view);

        viewName = (TextView) view.findViewById(R.id.storeName);
        viewMallName = (TextView) view.findViewById(R.id.mallName);
        viewAddress = (TextView) view.findViewById(R.id.storeAddress);
        viewImage = (ImageView) view.findViewById(R.id.storeImage);
        viewWearButon = (ImageButton) view.findViewById(R.id.seeOnWatch);
    }
}
