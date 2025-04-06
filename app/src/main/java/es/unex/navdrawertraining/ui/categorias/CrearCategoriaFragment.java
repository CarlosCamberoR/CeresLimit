package es.unex.navdrawertraining.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.databinding.FragmentCrearCategoriaBinding;
import es.unex.navdrawertraining.modelodatos.Categoria;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;


public class CrearCategoriaFragment extends Fragment {
        private Categoria categoria;

    private FragmentCrearCategoriaBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Categoria cat = new Categoria();
        binding = FragmentCrearCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView iconos = binding.rvCrearcategoriaIconos;
        iconos.setLayoutManager( new GridLayoutManager(getActivity(), 3, LinearLayoutManager.HORIZONTAL, false));

        CrearCategoriaAdapter adapter = new CrearCategoriaAdapter();

        iconos.setAdapter(adapter);

        ChipGroup chipGroup = binding.cgCrearcategoriaColor;

        Button confirmar = binding.bCrearcategoriaConfirmar;

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCat = binding.etCrearcategoriaNombrecat.getText().toString();
                int icono = adapter.getLastCheckedIcon();
                int chipId = chipGroup.getCheckedChipId();
                if((chipId != View.NO_ID) && (!nombreCat.isEmpty()) && (icono != -1)) {
                    cat.setColor_categoria(generarColorCategoria(chipId));
                    //int icono_value = getContext().getResources().getIdentifier("icono_cat_helado", "drawable", getContext().getPackageName());
                    //int color_value = getContext().getResources().getIdentifier("cat_indigo_dye", "color", getContext().getPackageName());
                    cat.setNombre_categoria(nombreCat);
                    cat.setIcono_categoria(getResources().getResourceEntryName(adapter.getIconos().get(icono)));
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            CeresLimitDatabase.getInstance(getContext()).getDaoCategoria().insert(cat);
                        }
                    });
                    CrearCategoriaFragment.this.getActivity().onBackPressed();

                } else {
                    Toast.makeText(view.getContext(),
                            "Por favor, selecciona todos los campos requeridos.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public String generarColorCategoria (int chipId) {
        String colorCorrespondiente = "";
        switch (chipId) {
            case R.id.crearcategoria_chip1:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_indigo_dye);
                break;

            case R.id.crearcategoria_chip2:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_blue_shappire);
                break;

            case R.id.crearcategoria_chip3:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_zomp);
                break;

            case R.id.crearcategoria_chip4:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_pistachio);
                break;

            case R.id.crearcategoria_chip5:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_red_salsa);
                break;

            case R.id.crearcategoria_chip6:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_orange_red);
                break;

            case R.id.crearcategoria_chip7:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_maize_crayola);
                break;

            case R.id.crearcategoria_chip8:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_ultra_red);
                break;

            case R.id.crearcategoria_chip9:
                colorCorrespondiente = getResources().getResourceEntryName(R.color.cat_dark_orchid);
                break;
        }
        return colorCorrespondiente;
    }
}