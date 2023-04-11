package com.example.uf1mec;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.google.android.material.snackbar.Snackbar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmViewModel extends AndroidViewModel {

    MutableLiveData<Alarma.Respuesta> alarmas = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    View view;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscar(String pos,View view){
        this.view=view;
        Alarma.api.getAlarms(pos).enqueue(new Callback<Alarma.Respuesta>() {
            @Override
            public void onResponse(@NonNull Call<Alarma.Respuesta> call, @NonNull Response<Alarma.Respuesta> response) {
                alarmas.postValue(response.body());
                System.out.println(alarmas.hasActiveObservers());
            }
            @Override
            public void onFailure(@NonNull Call<Alarma.Respuesta> call, @NonNull Throwable t) {
                    Snackbar.make(view.getRootView(), "ERROR AL INTENTAR DESCARGAR LAS ALARMAS EN EL DISPOSITIVO",Snackbar.LENGTH_SHORT)
                            .show();
            }
        });
    }


    public void addAlarma(Alarma.Timer res){
           Alarma.Respuesta rr = alarmas.getValue();
            if (rr == null) {
                rr = new Alarma.Respuesta();
            }
            rr.timers.add(res);
            System.out.println(res.hour+" : "+res.minute);
            alarmas.setValue(rr);
            alarmas.postValue(rr);
    }
}