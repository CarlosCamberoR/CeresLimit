package es.unex.navdrawertraining.ui.preferencias;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import es.unex.navdrawertraining.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}