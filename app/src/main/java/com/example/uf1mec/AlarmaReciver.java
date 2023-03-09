package com.example.uf1mec;

import static com.example.uf1mec.R.id.alarmaReciver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.uf1mec.databinding.FragmentAlarmBinding;
import com.example.uf1mec.databinding.ViewholderContenidoBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AlarmaReciver extends Fragment {
    private FragmentAlarmBinding binding;
    View view;
    AlarmViewModel alarmViewModel;
    ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
    private int busqueda;
    public void setAlarm(Alarma.Timer alarm){
        contenidosAdapter.addContenido(alarm);
        contenidosAdapter.notifyDataSetChanged();
    }
    public void setListTimers(MutableLiveData<Alarma.Respuesta> respuestas){
        Alarma.Respuesta r = respuestas.getValue();
        List<Alarma.Timer> timers = new ArrayList<>(r.timers);
        contenidosAdapter.setContenidoList(timers);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (this.binding = FragmentAlarmBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewAlarmas.setAdapter(contenidosAdapter);
        NavController navController = Navigation.findNavController(view);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.bottom1Fragment);
        MenuItem item2 = menu.findItem(R.id.bottom2Fragment);
        MenuItem item3 = menu.findItem(R.id.bottom3Fragment);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                navController.navigate(alarmaReciver);
                return true;
            }
        });
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                navController.navigate(R.id.nuevaAlarma);
                return true;
            }
        });
        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                navController.navigate(R.id.alarmasTot);
                return true;
            }
        });
        switch (busqueda){
            case 0:
                alarmViewModel.buscar("ccff-diurn-i-tarda.json",view);
                break;
            case 1:
                alarmViewModel.buscar("ccff-diurn.json",view);
                break;
            case 2:
                alarmViewModel.buscar("ccff-tarda.json",view);
                break;
        }


    }
    public void setBuscar(int i){
        busqueda=i;

    }

    public void sendError(View view) {
        Snackbar.make(view, "ERROR AL INTENTAR DESCARGAR LAS ALARMAS EN EL DISPOSITIVO",Snackbar.LENGTH_SHORT)
                .show();
    }

    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderContenidoBinding binding;


        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class ContenidosAdapter extends RecyclerView.Adapter<AlarmaReciver.ContenidoViewHolder> {
        List<Alarma.Timer> contenidoList;

        @NonNull
        @Override
        public AlarmaReciver.ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AlarmaReciver.ContenidoViewHolder holder, int position) {
            Alarma.Timer h = contenidoList.get(0);
            Alarma.Timer m = contenidoList.get(1);
            holder.binding.hora.setText((CharSequence) h+" : ");
            holder.binding.minutos.setText((CharSequence) m);
        }

        @Override
        public int getItemCount() {
            return contenidoList == null ? 0 : contenidoList.size();
        }

        public void setContenidoList(List<Alarma.Timer> contenidoList) {
            this.contenidoList = contenidoList;
            notifyDataSetChanged();
        }
        public void addContenido(Alarma.Timer respuesta) {
            this.contenidoList.add(respuesta);
            notifyDataSetChanged();
        }
    }
}

