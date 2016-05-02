package icte.cph.aau.washingmachine.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferencesHolder {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    
    private static final String EMPTY_STRING = "";

    public SharedPreferencesHolder(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void saveLong(final String key, final long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public void saveString(final String key, final String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void saveBoolean(final String key, final boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }
    
    public Object load(final String key) {
        return sharedPreferences.getString(key, EMPTY_STRING);
    }

    public String loadString(final String key) {
        return sharedPreferences.getString(key, EMPTY_STRING);
    }

    public long loadLong(final String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public boolean loadBoolean(final String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}