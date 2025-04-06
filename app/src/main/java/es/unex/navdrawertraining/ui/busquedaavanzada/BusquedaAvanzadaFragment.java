package es.unex.navdrawertraining.ui.busquedaavanzada;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentBusquedaAvanzadaBinding;
import es.unex.navdrawertraining.repoexterno.LocalesNetworkLoaderRunnable;


public class BusquedaAvanzadaFragment extends Fragment {



    private FragmentBusquedaAvanzadaBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        binding = FragmentBusquedaAvanzadaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvBusquedaAvanzada;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BusquedaAvanzadaAdapter adapter = new BusquedaAvanzadaAdapter();
        AppExecutors.getInstance().networkIO().execute(new LocalesNetworkLoaderRunnable(locales -> adapter.swap(locales)));
        RadioButton todos = binding.rbBusquedaAvanzadaTodos;
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setModo(1);
                adapter.filter();
            }
        });
        RadioButton consitio = binding.rbBusquedaAvanzadaConsitio;
        consitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setModo(2);
                adapter.filter();
            }
        });
        recyclerView.setAdapter(adapter);
        SearchView searchView =  binding.svBusquedaAvanzada;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.setQuery(query);
                adapter.filter();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setQuery(newText);
                adapter.filter();
                return true;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}