package es.unex.navdrawertraining.ui.busquedamapa;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.repoexterno.LocalesNetworkLoaderRunnable;
import es.unex.navdrawertraining.repoexterno.OnLocalesLoaderListener;

public class BusquedaMapaFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    Local l = (Local) marker.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",l.getId());
                    bundle.putString("nombre",l.getNombre());
                    bundle.putString("descripcion", l.getDescripcion());
                    double[] coordenadas = {l.getCoordenadas().get(0),
                            l.getCoordenadas().get(1)};
                    bundle.putDoubleArray("coordenadas", coordenadas);
                    bundle.putFloat("valoracion", l.getValoracionGlobal());
                    bundle.putString("fotoPerfil", l.getFotoPerfil());
                    bundle.putString("fotoBackground", l.getFotoBackground());
                    bundle.putInt("aforoTotal", l.getAforoTotal());
                    bundle.putInt("ocupacionActual", l.getOcupacionActual());
                    bundle.putStringArrayList("tags", new ArrayList<String>(l.getEtiquetas()));
                    Navigation.findNavController(getView()).navigate(R.id.action_nav_mapa_to_nav_detalles, bundle);
                }
            });

            LatLng caceres = new LatLng(39.475107, -6.371448);
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            int zoom = Integer.parseInt(preferences.getString("zoom", "15"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(caceres, zoom));
            AppExecutors.getInstance().networkIO().execute(new LocalesNetworkLoaderRunnable(new OnLocalesLoaderListener() {
                @Override
                public void onLocalesLoaded(List<Local> locales) {
                    for (Local l : locales) {
                        AppExecutors.getInstance().networkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(l.getCoordenadas().get(0), l.getCoordenadas().get(1))).title(l.getNombre());
                                    Bitmap icono = Bitmap.createScaledBitmap(Picasso.get().load(l.getFotoPerfil()).get(), 150, 150, false);
                                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icono));
                                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            Marker marker = googleMap.addMarker(markerOptions);
                                            marker.setTag(l);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_busqueda_mapa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }



}