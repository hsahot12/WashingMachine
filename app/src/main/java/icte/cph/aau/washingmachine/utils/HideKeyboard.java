package icte.cph.aau.washingmachine.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class HideKeyboard {
    private Context context;

    public HideKeyboard(Context context) {
        this.context = context;
    }

    public void hide(View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
