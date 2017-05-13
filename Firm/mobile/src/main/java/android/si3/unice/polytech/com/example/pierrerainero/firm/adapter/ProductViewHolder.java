package android.si3.unice.polytech.com.example.pierrerainero.firm.adapter;

import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView viewName;
    public TextView viewReference;
    public ImageView viewImage;
    public TextView viewDescription;
    public TextView viewBenef;

    public ProductViewHolder(View view) {
        super(view);

        viewName = (TextView) view.findViewById(R.id.nameProduct);
        viewReference = (TextView) view.findViewById(R.id.referenceProduct);
        viewImage = (ImageView) view.findViewById(R.id.imageProduct);
        viewDescription = (TextView) view.findViewById(R.id.descriptionProduct);
        viewBenef = (TextView) view.findViewById(R.id.benefProduct);
    }
}
