package com.example.covid_news.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.covid_news.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.example.covid_news.data.DataBase;
import com.example.covid_news.data.Region;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private BarChart mChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
//        mLineChart = root.findViewById(R.id.chart1);
//        List<Entry> entries = new ArrayList<Entry>();
//        entries.add(new Entry(1, 2));
//        entries.add(new Entry(2, 5));
//        entries.add(new Entry(3, 8));
//        LineDataSet dataSet = new LineDataSet(entries, "label");
//        dataSet.setColor(Color.BLUE);
//        LineData lineData = new LineData(dataSet);
//        mLineChart.setData(lineData);
//        mLineChart.invalidate();
        mChart = root.findViewById(R.id.chart1);
        List<BarEntry> entries = new ArrayList<BarEntry>();

        DataBase db = new DataBase(getContext());
        List<Region> regionList = db.getRegionList();
        int length = regionList.size();
        String[] name = new String[length];
        for (int i = 0; i < length; ++i){
            name[i] = regionList.get(i).name;
            int confirmed = regionList.get(i).data.get(-1).get(0);
            entries.add(new BarEntry(i+1, confirmed));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "累计确诊");
        BarData barData = new BarData(barDataSet);
        mChart.setData(barData);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                int index = Math.round(value);
                return name[index-1];
            }
        });
        barDataSet.setHighlightEnabled(true);
        mChart.notifyDataSetChanged();
        mChart.invalidate();

        return root;
    }
}