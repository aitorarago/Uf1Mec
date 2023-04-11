package com.example.uf1mec;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Alarma {
    static class Respuesta {
        List<Timer> timers;
        Respuesta(){
            timers= new ArrayList<>();
        }
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
        @GET("/{file}.json")
        Call<Respuesta> getAlarms(@Path("file") String file);
    }
}
