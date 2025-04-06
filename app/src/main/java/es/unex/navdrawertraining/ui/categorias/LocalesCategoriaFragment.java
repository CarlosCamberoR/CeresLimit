package es.unex.navdrawertraining.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.databinding.FragmentLocalesCategoriaBinding;


public class LocalesCategoriaFragment extends Fragment {
    private FragmentLocalesCategoriaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLocalesCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvLocalescategorias;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LocalesCategoriaAdapter adapter = new LocalesCategoriaAdapter();
        AppExecutors.getInstance().networkIO().execute(new LocalesCategoriaLoaderRunnable((localesCategoria, locales) -> adapter.swap(localesCategoria, locales), getContext(), getArguments().getLong("id")));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}