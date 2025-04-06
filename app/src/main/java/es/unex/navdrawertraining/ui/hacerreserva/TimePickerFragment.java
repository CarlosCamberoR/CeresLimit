package es.unex.navdrawertraining.ui.hacerreserva;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import es.unex.navdrawertraining.R;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);
        return timePickerDialog;
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
        EditText editText = (EditText) getActivity().findViewById(R.id.et_hora);
        String sMinuto = null;
        if (minuto < 10) {
            sMinuto = "0" + String.valueOf(minuto);
        } else {
            sMinuto = String.valueOf(minuto);
        }
        editText.setText(String.valueOf(hora) +":"+ sMinuto);
    }
}