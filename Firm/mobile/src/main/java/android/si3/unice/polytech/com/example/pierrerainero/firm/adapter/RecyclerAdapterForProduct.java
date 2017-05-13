package android.si3.unice.polytech.com.example.pierrerainero.firm.adapter;

import android.graphics.Color;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Product;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.AsyncTaskImage;
import android.si3.unice.polytech.com.example.pierrerainero.firm.view.Formator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PierreRainero on 13/05/2017.
 */

public class RecyclerAdapterForProduct extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> products;
    private Store selectedStore;

    public RecyclerAdapterForProduct(List<Product> products, Store selectedStore){
        this.products = products;
        this.selectedStore = selectedStore;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);

        ProductViewHolder vh = new ProductViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int pos) {
        Product currentProduct = products.get(pos);

        holder.viewName.setText(currentProduct.getName());
        holder.viewReference.setText(currentProduct.getReference());
        holder.viewDescription.setText(currentProduct.getDescription());

        Formator.formateMoneyText(selectedStore.getProductProfit(currentProduct.getReference()), holder.viewBenef);

        AsyncTaskImage async = new AsyncTaskImage(holder.viewImage);
        async.execute(currentProduct.getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
