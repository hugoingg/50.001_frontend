package com.example.andyapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andyapp.DataSubject;
import com.example.andyapp.LineChartObserver;
import com.example.andyapp.LoginActivity;
import com.example.andyapp.R;
import com.example.andyapp.adapters.IncomeRecyclerViewAdapter;
import com.example.andyapp.models.FetchIncome;
import com.example.andyapp.queries.FetchIncomes;
import com.example.andyapp.queries.IncomeService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {
    private LineChart lineChart;
    private LineChartObserver lineChartObserver;
    private RecyclerView recyclerView;
    private FetchIncomes fetchIncomes;
    private IncomeRecyclerViewAdapter adapter;
    private DataSubject<FetchIncomes> subject;
    private IncomeService incomeService;
    private SharedPreferences mPref;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Permissions
        mPref = getContext().getSharedPreferences(LoginActivity.PREFTAG, Context.MODE_PRIVATE);
        userId = mPref.getString(LoginActivity.USERKEY, LoginActivity.DEFAULT_USERID);
        //Initialise views and DataSubject and data
        lineChart = view.findViewById(R.id.incomeLineChart);
        lineChartObserver = new LineChartObserver(lineChart, requireContext());
        recyclerView = view.findViewById(R.id.incomeRecycler);
        subject = new DataSubject<>();
        fetchIncomes = new FetchIncomes(new ArrayList<>());
        incomeService = new IncomeService(requireContext());

        //Configure RecyclerView
        adapter = new IncomeRecyclerViewAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        //Register observers
        subject.registerObserver(lineChartObserver);
        subject.registerObserver(adapter);
        setFetchIncomes();
    }

    public void setFetchIncomes(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                LocalDate currentDate = LocalDate.now();
                int month = currentDate.getMonthValue();
                int year = currentDate.getYear();
                incomeService.fetchIncomesByMonth(userId, String.valueOf(month), String.valueOf(year), subject, handler);
            }
        });

    }
}