package es.unex.navdrawertraining.ui.reservas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentReservasBinding;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.Reserva;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;


public class ReservasFragment extends Fragment {

    private FragmentReservasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvReservas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ReservasAdapter adapter = new ReservasAdapter();
        AppExecutors.getInstance().networkIO().execute(new LocalesReservasLoaderRunnable((locales, reservas) -> adapter.swap(reservas, locales), getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}