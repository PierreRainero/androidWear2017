package android.si3.unice.polytech.com.example.pierrerainero.firm;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Allows you to listen messages sent from a paired device (mobile module)
 * Created by PierreRainero on 16/04/2017.
 */
public class WearMessageListenerService extends WearableListenerService {
    private static final String START_ACTIVITY = "/start_activity";

    @Override
    /**
     * {@inheritDoc}
     */
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
