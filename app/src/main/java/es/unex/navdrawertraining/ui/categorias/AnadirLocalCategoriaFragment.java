package es.unex.navdrawertraining.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentAnadirLocalCategoriaBinding;


public class AnadirLocalCategoriaFragment extends Fragment {


    private FragmentAnadirLocalCategoriaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAnadirLocalCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvAnadirlocalcategoria;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        AnadirLocalCategoriaAdapter adapter = new AnadirLocalCategoriaAdapter(getArguments().getLong("id"), getActivity());

        AppExecutors.getInstance().diskIO().execute(new CategoriasDiskLoaderRunnable(categorias -> adapter.swap(categorias),getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}