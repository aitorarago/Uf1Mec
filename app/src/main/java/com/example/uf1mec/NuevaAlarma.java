package com.example.uf1mec;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.uf1mec.databinding.FragmentNuevaAlarmaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevaAlarma extends Fragment {
    private TimePicker picker;
    Calendar calendar;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNuevaAlarmaBinding binding = FragmentNuevaAlarmaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ImageButton close = view.findViewById(R.id.closse);
        Button save = view.findViewById(R.id.guardar);
        picker=view.findViewById(R.id.timePicker);
        calendar=Calendar.getInstance();
        alarm_manager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        AlarmViewModel model = new ViewModelProvider(requireActivity()).get(AlarmViewModel.class);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                Intent intent = new Intent(requireContext(), AlarmViewModel.class);
                pending_intent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
                Log.d("msg119", ""+calendar.getTimeInMillis()+" "+pending_intent);

                Alarma.Timer timer = new Alarma.Timer(picker.getHour(),picker.getMinute(),picker.getHour()+":"+ picker.getMinute());
                model.addAlarma(timer);
                navController.navigate(R.id.alarmaReciver);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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