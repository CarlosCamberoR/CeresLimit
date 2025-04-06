package es.unex.navdrawertraining.repoexterno;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Local;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubService {

    @GET("sgarciatz/Locales/main/listaLocales.json")
    Call<List<Local>> listLocales ();
}
