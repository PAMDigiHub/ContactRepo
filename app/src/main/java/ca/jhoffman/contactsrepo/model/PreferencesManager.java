package ca.jhoffman.contactsrepo.model;

/**
 * Created by jhoffman on 2016-09-27.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private final String PREFERENCES_FILE_NAME = "ca.jhoffman.contactsrepo.prefs_file";
    private final String KEY_FIRST_LAUNCH = "ca.jhoffman.contactsrepo.prefs_file.key.is_first_launch";

    private SharedPreferences preferences;

    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    //
    //Is first launch
    //

    public boolean isFirstLaunch() {
        return preferences.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    public void setIsFirstLaunch(boolean firstLaunch) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_FIRST_LAUNCH, firstLaunch);
        editor.commit();
    }
}

