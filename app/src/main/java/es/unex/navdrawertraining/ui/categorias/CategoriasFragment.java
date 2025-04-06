package es.unex.navdrawertraining.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.databinding.FragmentCategoriasBinding;
import es.unex.navdrawertraining.modelodatos.Categoria;


public class CategoriasFragment extends Fragment {


    private FragmentCategoriasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvCategorias;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        CategoriasAdapter adapter = new CategoriasAdapter(getContext());
        AppExecutors.getInstance().diskIO().execute(new CategoriasDiskLoaderRunnable(new OnCategoriasLoadedListener() {
            @Override
            public void onCategoriasLoaded(List<Categoria> categorias) {
                adapter.swap(categorias);
            }
        }, CategoriasFragment.this.getContext()));

        recyclerView.setAdapter(adapter);

        binding.fbCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.crear_categoria_action);
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