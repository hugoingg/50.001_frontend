package com.example.andyapp;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.andyapp.models.FetchIncome;
import com.example.andyapp.queries.FetchIncomes;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartObserver implements DataObserver<FetchIncomes>{
    private LineChart lineChart;
    private Context context;

    public LineChartObserver(LineChart lineChart, Context context) {
        this.lineChart = lineChart;
        this.context = context;
    }



    public void configureChart(){
        Typeface tf = ResourcesCompat.getFont(context, R.font.varela_round);
        int background = ContextCompat.getColor(context, R.color.background);
        lineChart.getXAxis().setTypeface(tf);
        lineChart.getAxisLeft().setTypeface(tf);
        //Line Chart Style
        lineChart.setBackgroundColor(background);
        // disable description text
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);
        //Add padding in the line Chart;
        lineChart.setExtraOffsets(30f, 30f, 30f, 30f);
        //Disable Grid Lines
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setDrawAxisLine(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);;
        // force pinch zoom along both axis
        lineChart.setPinchZoom(true);
        lineChart.setDrawGridBackground(false);
    }

    public LineData configureDataSet(ArrayList<Entry> entries){
        int lineColor = ContextCompat.getColor(context, R.color.pie3);
        int textColor = ContextCompat.getColor(context, R.color.black);
        LineDataSet lineDataSet = new LineDataSet(entries, "Income");
        lineDataSet.setColor(lineColor);
        lineDataSet.setValueTextColor(textColor);
        lineDataSet.setCircleColor(lineColor);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    @Override
    public void updateData(FetchIncomes data) {
        ArrayList<FetchIncome> models = data.getFetchIncomeArrayList();
        ArrayList<Entry> entries = new ArrayList<>();
        for (FetchIncome model: models){
            //Insert Logic for rendering data

        }
        LineData lineData = configureDataSet(entries);
        configureChart();
        lineChart.setData(lineData);
    }
}
