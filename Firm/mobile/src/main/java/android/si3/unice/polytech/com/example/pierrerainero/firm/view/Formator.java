package android.si3.unice.polytech.com.example.pierrerainero.firm.view;

import android.graphics.Color;
import android.widget.TextView;

/**
 * Allows to shaping a TextView
 * Created by PierreRainero on 13/05/2017.
 */

public class Formator {

    private Formator(){
    }

    /**
     * Formate a money textview
     * @param benef numeric value
     * @param txtview textView to shape
     */
    public static void formateMoneyText(double benef, TextView txtview){
        txtview.setText(String.format("%.2f", benef)+" €");
        if(benef>0)
            txtview.setTextColor(Color.parseColor("#4da34d"));
        else
            txtview.setTextColor(Color.parseColor("#f01000"));
    }
}
