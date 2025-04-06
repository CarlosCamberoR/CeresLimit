package es.unex.navdrawertraining.ui.hacerreserva;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentHacerReservaBinding;
import es.unex.navdrawertraining.modelodatos.Reserva;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import es.unex.navdrawertraining.roomdb.DateConverter;


public class HacerReservaFragment extends Fragment {

    private FragmentHacerReservaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHacerReservaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Foto background
        Picasso.get().load(getArguments().getString("fotoBackground")).into(binding.ivBackgroundLocal);
        //Foto perfil
        Picasso.get().load(getArguments().getString("fotoPerfil")).into(binding.ivLogoLocal);

        //Nombre del local
        TextView nombre = binding.tvNombreLocal;
        nombre.setText(getArguments().getString("nombre"));

        EditText fecha = binding.etFecha;
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        EditText hora = binding.etHora;
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        binding.bHacerReservaConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !nombre.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty() && !hora.getText().toString().isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    String[] fecha = binding.etFecha.getText().toString().split("/");
                    String[] hora = binding.etHora.getText().toString().split(":");


                    calendar.set(Integer.valueOf(fecha[2]), Integer.valueOf(fecha[1]), Integer.valueOf(fecha[0]), Integer.valueOf(hora[0]), Integer.valueOf(hora[1]), 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    if (calendar.getTimeInMillis() < (System.currentTimeMillis() + 1800000.0f)) {
                        Toast.makeText(view.getContext(),
                                "La reserva tiene que ser, como poco, con media hora de antelación.",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Reserva reserva = new Reserva(getArguments().getLong("id"), calendar.getTime());

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Reserva bdReserva = CeresLimitDatabase.getInstance(getContext()).getDaoReserva().get(reserva.getId_reserva(), DateConverter.toTimestamp(reserva.getFecha_hora_reserva()));
                                if (bdReserva == null) {
                                    CeresLimitDatabase.getInstance(getContext()).getDaoReserva().insert(reserva);
                                    getActivity().runOnUiThread(() -> getActivity().onBackPressed());
                                } else {
                                    getActivity().runOnUiThread(() -> Toast.makeText(view.getContext(),
                                            "Ya tienes una reserva en este local a esta hora en este día!.",
                                            Toast.LENGTH_LONG).show());
                                }
                            }
                        });
                    }

                }
            }
        });
        return root;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}