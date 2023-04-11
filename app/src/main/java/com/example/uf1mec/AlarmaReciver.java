package com.example.uf1mec;

import static com.example.uf1mec.R.id.alarmaReciver;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.ArrayList;
import java.util.List;

public class AlarmaReciver extends Fragment {
    private FragmentAlarmBinding binding;
    View view;
    AlarmViewModel alarmViewModel;
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
        NavController navController = Navigation.findNavController(view);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.bottom1Fragment);
        MenuItem item2 = menu.findItem(R.id.bottom2Fragment);
        MenuItem item3 = menu.findItem(R.id.bottom3Fragment);
        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewAlarmas.setAdapter(contenidosAdapter);
        AlarmViewModel itunesViewModel = new ViewModelProvider(requireActivity()).get(AlarmViewModel.class);
        Alarma.Respuesta r = itunesViewModel.alarmas.getValue();
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
        itunesViewModel.alarmas.observe(getViewLifecycleOwner(), new Observer<Alarma.Respuesta>() {
            @Override
            public void onChanged(Alarma.Respuesta respuesta) {
                respuesta.timers.forEach(timer -> contenidosAdapter.addContenido(timer));
            }
        });


    }


    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderContenidoBinding binding;


        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder> {
        List<Alarma.Timer> contenidoList = new ArrayList<>();

        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull AlarmaReciver.ContenidoViewHolder holder, int position) {
            Alarma.Timer contenido = contenidoList.get(position);
            holder.binding.hora.setText(contenido.hour+" :");
            if(contenido.minute<10){
                holder.binding.minutos.setText(" 0"+contenido.minute+"\n");
            }
            else holder.binding.minutos.setText(contenido.minute+"\n");
        }

        @Override
        public int getItemCount() {

            return contenidoList == null ? 0 : contenidoList.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void addContenido(Alarma.Timer respuesta) {
            this.contenidoList.add(respuesta);
            notifyDataSetChanged();
        }
    }
}

