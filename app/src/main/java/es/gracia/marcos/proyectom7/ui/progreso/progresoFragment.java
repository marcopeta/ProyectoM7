package es.gracia.marcos.proyectom7.ui.progreso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import es.gracia.marcos.proyectom7.R;

public class progresoFragment extends Fragment {

    BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_progreso, container, false);
        barChart = root.findViewById(R.id.prograsoChart);
        ArrayList<BarEntry> barEntries = new ArrayList();
        barEntries.add(new BarEntry(0, 23.4f));
        barEntries.add(new BarEntry(1, 23.4f));
        barEntries.add(new BarEntry(2, 23.4f));
        barEntries.add(new BarEntry(3, 23.4f));
        barEntries.add(new BarEntry(4, 23.4f));
        barEntries.add(new BarEntry(5, 23.4f));
        barEntries.add(new BarEntry(6, 23.4f));
        barEntries.add(new BarEntry(7, 23.4f));
        barEntries.add(new BarEntry(8, 23.4f));
        barEntries.add(new BarEntry(9, 23.4f));
        barEntries.add(new BarEntry(10, 23.4f));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> theDates = new ArrayList();
        theDates.add("Enero");
        theDates.add("Febrero");
        theDates.add("Marzo");
        theDates.add("Abril");
        theDates.add("Enero");
        theDates.add("Febrero");
        theDates.add("Marzo");
        theDates.add("Abril");
        theDates.add("Enero");
        theDates.add("Febrero");
        theDates.add("Marzo");
        theDates.add("Abril");

        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.setScaleEnabled(false);
        barChart.animateY(2000);
        barChart.zoom(2f,1f,1f,1f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(theDates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(35f);
        yAxis.setAxisMinimum(12f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        Description description = barChart.getDescription();
        description.setEnabled(false);

        return root;
    }
}