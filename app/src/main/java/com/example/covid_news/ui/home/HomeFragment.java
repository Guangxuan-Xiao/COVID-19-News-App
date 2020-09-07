package com.example.covid_news.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import java.util.Comparator;
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
        mChart.setDrawValueAboveBar(true);
        mChart.setPinchZoom(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setScaleEnabled(false);
        mChart.setDragEnabled(true);
        mChart.setHighlightPerDragEnabled(true);
        mChart.zoom(10f, 1f, 0, 0);

        List<BarEntry> confirmedEntry = new ArrayList<BarEntry>();
        //List<BarEntry> suspectedEntry = new ArrayList<BarEntry>();
        List<BarEntry> curedEntry = new ArrayList<BarEntry>();
        List<BarEntry> deadEntry = new ArrayList<BarEntry>();

        DataBase db = new DataBase(getContext());
        List<Region> regionList = db.getRegionListFromLocal();
        regionList.sort(new Comparator<Region>() {
            @Override
            public int compare(Region t1, Region t2) {
                int sz1 = t1.data.size(), sz2 = t2.data.size();
                return -t1.data.get(sz1-1).get(0) + t2.data.get(sz2-1).get(0);
            }
        });
        int length = regionList.size();
        String[] name = new String[length];
        int cnt = 0;
        for (int i = 0; i < length; ++i){
            String str = regionList.get(i).name;
            if (str.contains("|") || str.contains("World")){
                continue;
            }
            name[cnt] = str;
            int sz = regionList.get(i).data.size();
            cnt += 1; // start from 1
            confirmedEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(0)));
            //suspectedEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(1)));
            curedEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(2)));
            deadEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(3)));
            if (cnt == 50){
                break;
            }
        }
        Log.i("Count", String.valueOf(cnt));
        BarDataSet barDataSet = new BarDataSet(confirmedEntry, "确诊");
        barDataSet.setColor(Color.RED);
        //BarDataSet barDataSet2 = new BarDataSet(suspectedEntry, "疑似");
        //barDataSet2.setColor(Color.YELLOW);
        BarDataSet barDataSet3 = new BarDataSet(curedEntry, "治愈");
        barDataSet3.setColor(Color.GREEN);
        BarDataSet barDataSet4 = new BarDataSet(deadEntry, "死亡");
        barDataSet4.setColor(Color.DKGRAY);
        BarData barData = new BarData(barDataSet);
        //barData.addDataSet(barDataSet2);
        barData.addDataSet(barDataSet3);
        barData.addDataSet(barDataSet4);
        mChart.setData(barData);

        barData.setBarWidth(0.2f);
        barData.groupBars(1f, 0.6f, 0);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(cnt);
        xAxis.setSpaceMax(0f);
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                int index = (int)Math.ceil(value);
                System.out.println(index);
                if (index >= 50){
                    index = 49;
                }
                if (index < 0){
                    index = 0;
                }
                return name[index-1];
            }
        });
        barDataSet.setHighlightEnabled(true);
        mChart.notifyDataSetChanged();
        mChart.invalidate();

        return root;
    }
}