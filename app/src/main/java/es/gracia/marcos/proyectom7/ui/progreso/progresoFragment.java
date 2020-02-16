package es.gracia.marcos.proyectom7.ui.progreso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import es.gracia.marcos.proyectom7.R;

public class progresoFragment extends Fragment {

    BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_progreso, container, false);
        barChart = root.findViewById(R.id.prograsoChart);
        ArrayList<BarEntry> barEntries = new ArrayList();
        barEntries.add(new BarEntry(0, 5));
        barEntries.add(new BarEntry(1, 3));
        barEntries.add(new BarEntry(2, 4));
        barEntries.add(new BarEntry(3, 3));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates = new ArrayList();
        theDates.add("Enero");
        theDates.add("Febrero");
        theDates.add("Marzo");
        theDates.add("Abril");

        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.setScaleEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(theDates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        barChart.animateY(2000);

        return root;
    }
}