package test.com.easytranslation.api.endpoint;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.com.easytranslation.home.model.TranslationResponse;

/**
 * Created by Hridoy on 20-Jul-17.
 */

public interface ITranslateEnpoint {

    @GET("translate")
    Call<TranslationResponse> getTranslation(@Query("key") String key, @Query("lang") String lang, @Query("text") String text, @Query("format") String format);
}
