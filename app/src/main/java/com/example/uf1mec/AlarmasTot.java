package com.example.uf1mec;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uf1mec.databinding.FragmentAlarmasTotBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlarmasTot extends Fragment {
    NavController navController;
    View view;
    FragmentAlarmasTotBinding binding;

    private Button buttontots;
    private Button buttonmati;
    private Button buttontarda;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAlarmasTotBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view=view;
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        buttontots=view.findViewById(R.id.ciclesformtiustots);
        AlarmaReciver alarmReceiver = new AlarmaReciver();

        buttontots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.setBuscar(0);
                navController.navigate(R.id.alarmaReciver);

            }
        });
        buttonmati=view.findViewById(R.id.ciclesformtiusmati);
        buttonmati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.setBuscar(1);
                navController.navigate(R.id.alarmaReciver);

            }
        });
        buttontarda=view.findViewById(R.id.ciclesformtiustarda);
        buttontarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmReceiver.setBuscar(2);
                navController.navigate(R.id.alarmaReciver);
            }
        });
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.bottom1Fragment);
        MenuItem item2 = menu.findItem(R.id.bottom2Fragment);
        MenuItem item3 = menu.findItem(R.id.bottom3Fragment);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                navController.navigate(R.id.alarmaReciver);
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



    }

}
