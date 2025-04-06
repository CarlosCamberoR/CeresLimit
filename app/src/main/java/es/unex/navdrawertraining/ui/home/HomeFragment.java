package es.unex.navdrawertraining.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentHomeBinding;
import es.unex.navdrawertraining.repoexterno.LocalesNetworkLoaderRunnable;
import es.unex.navdrawertraining.ui.categorias.CategoriasDiskLoaderRunnable;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView rvRecomendaciones = binding.rvHomeRecomendacionesList;
        rvRecomendaciones.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        HomeAdapter homeAdapter = new HomeAdapter();
        AppExecutors.getInstance().networkIO().execute(new LocalesNetworkLoaderRunnable(locales -> homeAdapter.swap(locales)));
        rvRecomendaciones.setAdapter(homeAdapter);

        //Categorías en la pantalla home
        RecyclerView rvCategorias = binding.rvHomeCategoriasList;
        rvCategorias.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        HomeCategoriasAdapter categoriasAdapter = new HomeCategoriasAdapter(getContext());
        AppExecutors.getInstance().diskIO().execute(new CategoriasDiskLoaderRunnable(categorias -> categoriasAdapter.swap(categorias), HomeFragment.this.getContext()));
        rvCategorias.setAdapter(categoriasAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}