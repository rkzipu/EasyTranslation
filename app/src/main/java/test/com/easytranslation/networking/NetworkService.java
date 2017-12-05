package test.com.easytranslation.networking;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import test.com.easytranslation.home.model.TranslationResponse;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {
    @GET("translate")
    Observable<Response> getTranslation(@Query("key") String key, @Query("lang") String lang, @Query("text") String text, @Query("format") String format);


}
