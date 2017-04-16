package android.si3.unice.polytech.com.example.pierrerainero.firm.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by PierreRainero on 16/04/2017.
 */

public class AsyncTaskImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageViewToUpdate;

    public AsyncTaskImage(ImageView iv){
        imageViewToUpdate = iv;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmapImage = null;
        try {
            InputStream in = new URL(urls[0]).openStream();
            bitmapImage = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmapImage) {
        imageViewToUpdate.setImageBitmap(bitmapImage);
    }
}

