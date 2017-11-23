package test.com.easytranslation.api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.com.easytranslation.Constant;


public class ApiClient {


    private static Retrofit retrofit = null;



    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return retrofit;
    }


    public static Retrofit getAuthClient(String token) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptor(token));
       // httpClient.addNetworkInterceptor(new StethoInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit;

    }
    public static class AddHeaderInterceptor implements Interceptor {
        private String token;

        public AddHeaderInterceptor(String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
           // builder.addHeader("Authorization", Constant.API_AUTHORIZATION_BEARER+token);
            builder.addHeader("Content-Type","application/json");
            return chain.proceed(builder.build());

        }
    }

}


