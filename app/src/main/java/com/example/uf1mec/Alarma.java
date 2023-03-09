package com.example.uf1mec;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Alarma {
    static class Respuesta {
        List<Timer> timers;
    }

    static class Timer {
        int hour;
        int minute;
        String comment;

        public Timer(int hour, int minute, String s) {
            this.hour=hour;
            this.minute=minute;
            comment=s;
        }
    }
    public static Api api = new Retrofit.Builder()
            .baseUrl("http://mec.elpuig.xeill.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);
    public interface Api {
        @GET("/")
        Call<Respuesta> buscar(@Query("extension") String extension);

    }
}
