package edu.upc.tracksapp.track;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    // Use 10.0.2.2 para que el emulador Android acceda al localhost del host
    final static String URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit;
    private static TrackInterface trackService;

    public static Retrofit getRetrofit() {
        if(retrofit==null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static TrackInterface getTracks() {
        if(trackService==null) {
            trackService = getRetrofit().create(TrackInterface.class);
        }
        return trackService;
    }
}
