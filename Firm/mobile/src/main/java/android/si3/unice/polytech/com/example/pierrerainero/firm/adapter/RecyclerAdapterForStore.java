package android.si3.unice.polytech.com.example.pierrerainero.firm.adapter;

import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.AsyncTaskImage;
import android.si3.unice.polytech.com.example.pierrerainero.firm.util.Serializer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.util.List;

/**
 * Created by PierreRainero on 16/04/2017.
 */

public class RecyclerAdapterForStore extends RecyclerView.Adapter<StoreViewHolder> {
    private List<Store> stores;
    private GoogleApiClient mApiClient;
    private static final String WEAR_MESSAGE_PATH = "/message";

    public RecyclerAdapterForStore(List<Store> stores, GoogleApiClient mApiClient){
        this.stores = stores;
        this.mApiClient = mApiClient;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_shop, parent, false);

        StoreViewHolder vh = new StoreViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, final int pos) {
        Store currentStore = stores.get(pos);

        holder.viewName.setText(currentStore.getName());
        holder.viewMallName.setText(currentStore.getMallName());
        holder.viewAddress.setText(currentStore.getAddress());
        holder.viewWearButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                try {
                    sendMessage(WEAR_MESSAGE_PATH, Serializer.serialize(stores.get(pos)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        AsyncTaskImage async = new AsyncTaskImage(holder.viewImage);
        async.execute(currentStore.getImage());
    }

    private void sendMessage( final String path, final byte[] obj ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), path, obj).await();
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}