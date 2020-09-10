package com.example.covid_news.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.example.covid_news.data.DataBase;
import com.example.covid_news.data.Region;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.*;

public class HomeFragment extends Fragment {

    private BarChart mChart;
    private BarChart mChartNational;
    private LineChart mChartQuery;
    private List<Region> regionList;
    @BindView(R.id.spin_country)
    Spinner countrySpinner;
    @BindView(R.id.spin_region)
    Spinner regionSpinner;
    @BindView(R.id.spin_city)
    Spinner citySpinner;
    @BindView(R.id.confirm_button)
    Button mButton;
    ArrayAdapter<String> countryAdapter = null;
    ArrayAdapter<String> regionAdapter = null;
    ArrayAdapter<String> cityAdapter = null;
    private List<String> country;
    private Map<String, List<String>> region;
    private Map<String, List<String>> city;
    private Map<String, Integer> selection_idx;
    static int countryPosition = 0;
    static int regionPosition = 0;
    static int cityPosition = 0;

    public void constructHierarchy(){
        country = new ArrayList<String>();
        region = new LinkedHashMap<String, List<String>>();
        city = new LinkedHashMap<String, List<String>>();
        selection_idx = new LinkedHashMap<String, Integer>();
        int length = regionList.size();
        for (int i = 0; i < length; ++i){
            String str = regionList.get(i).name;
            String[] strList = str.split("\\|");
            selection_idx.put(str, i);
            if (strList.length == 1){
                country.add(str);
            }
            else if (strList.length == 2){
                if (region.containsKey(strList[0])){
                    region.get(strList[0]).add(strList[1]);
                }
                else{
                    List<String> tmp_list = new ArrayList<String>();
                    tmp_list.add("");
                    tmp_list.add(strList[1]);
                    region.put(strList[0], tmp_list);
                }
            }
            else if (strList.length == 3){
                System.out.println(str);
                if (city.containsKey(strList[1])){
                    city.get(strList[1]).add(strList[2]);
                    //System.out.println("has "+strList[1]+" add "+strList[2]);
                }
                else{
                    List<String> tmp_list = new ArrayList<String>();
                    tmp_list.add("");
                    tmp_list.add(strList[2]);
                    city.put(strList[1], tmp_list);
                    //System.out.println("new "+strList[1]+ " "+strList[2]);
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBase db = new DataBase(getContext());
        regionList = db.getRegionListFromLocal();
        regionList.sort(new Comparator<Region>() {
            @Override
            public int compare(Region t1, Region t2) {
                int sz1 = t1.data.size(), sz2 = t2.data.size();
                return -t1.data.get(sz1-1).get(0) + t2.data.get(sz2-1).get(0);
            }
        });
        constructHierarchy();
    }

    private void setSpinner(){
        countryAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, country);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setSelection(0, true);
        List<String> region_tmp = region.containsKey(country.get(0)) ?
                region.get(country.get(0)) : new ArrayList<String>();
        regionAdapter = new ArrayAdapter<String>(this.getContext(),
                R.layout.list_item, region_tmp);
        regionSpinner.setAdapter(regionAdapter);
        regionSpinner.setSelection(0, true);
        List<String> city_tmp = region_tmp.size() == 0 ? new ArrayList<String>() :
                city.containsKey(region_tmp.get(0)) ?
                        city.get(region_tmp.get(0)) : new ArrayList<String>();
        cityAdapter = new ArrayAdapter<String>(this.getContext(),
                R.layout.list_item, city_tmp);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0, true);

        Context context = getContext();
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<String> region_tmp = region.containsKey(country.get(i)) ?
                        region.get(country.get(i)) : new ArrayList<String>();
                regionAdapter = new ArrayAdapter<String>(context,
                        R.layout.list_item, region_tmp);
                regionSpinner.setAdapter(regionAdapter);
                countryPosition = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<String> city_tmp =
                        city.containsKey(region.get(country.get(countryPosition)).get(i)) ?
                                city.get(region.get(country.get(countryPosition)).get(i)) :
                                new ArrayList<String>();
//                Log.i("Country name:", country.get(countryPosition));
//                Log.i("Region name:", region.get(country.get(countryPosition)).get(i));
                Log.i("City number:", String.valueOf(city_tmp.size()));
                cityAdapter = new ArrayAdapter<String>(context,
                        R.layout.list_item, city_tmp);
                citySpinner.setAdapter(cityAdapter);
                regionPosition = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityPosition = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        setSpinner();

        mChart = root.findViewById(R.id.chart1);
        mChartNational = root.findViewById(R.id.chart2);
        mChartQuery = root.findViewById(R.id.chart3);
        mChart.setDrawValueAboveBar(true);
        mChartNational.setDrawValueAboveBar(true);
        mChart.setPinchZoom(false);
        mChartNational.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChartNational.setScaleEnabled(false);
        mChart.setDragEnabled(true);
        mChartNational.setDragEnabled(true);
        mChart.setHighlightPerDragEnabled(true);
        mChartNational.setHighlightPerDragEnabled(true);
        mChart.zoom(10f, 1f, 0, 0);
        mChartNational.zoom(10f, 1f, 0, 0);

        Description description = new Description();
        mChart.setDescription(description);
        mChartNational.setDescription(description);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<BarEntry> confirmedEntry = new ArrayList<BarEntry>();
                //List<BarEntry> suspectedEntry = new ArrayList<BarEntry>();
                List<BarEntry> curedEntry = new ArrayList<BarEntry>();
                List<BarEntry> deadEntry = new ArrayList<BarEntry>();

                int length = regionList.size();
                String[] name = new String[length];
                int cnt = 0;
                for (int i = 0; i < length; ++i){
                    String str = regionList.get(i).name;
                    if (str.contains("|") || str.contains("World")){
                        continue;
                    }
                    if (str.contains("United States")){
                        str = "USA";
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
                barData.groupBars(1f, 0.4f, 0);

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
                            index = 50;
                            return "";
                        }
                        if (index < 1){
                            index = 1;
                            return "";
                        }
                        return name[index-1];
                    }
                });
                barDataSet.setHighlightEnabled(true);
                mChart.notifyDataSetChanged();
                mChart.invalidate();

                String[] name2 = new String[length];
                cnt = 0;
                confirmedEntry = new ArrayList<BarEntry>();
                curedEntry = new ArrayList<BarEntry>();
                deadEntry = new ArrayList<BarEntry>();
                for (int i = 0; i < length; ++i){
                    String str = regionList.get(i).name;
                    if (!str.contains("China|")){
                        continue;
                    }
                    name2[cnt] = str.substring(6);
                    if (name2[cnt].contains("|")){
                        continue;
                    }
                    int sz = regionList.get(i).data.size();
                    cnt += 1; // start from 1
                    confirmedEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(0)));
                    curedEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(2)));
                    deadEntry.add(new BarEntry(cnt, regionList.get(i).data.get(sz-1).get(3)));
                    if (cnt == 50){
                        break;
                    }
                }
                Log.i("Count", String.valueOf(cnt));
                barDataSet = new BarDataSet(confirmedEntry, "确诊");
                barDataSet.setColor(Color.RED);
                barDataSet3 = new BarDataSet(curedEntry, "治愈");
                barDataSet3.setColor(Color.GREEN);
                barDataSet4 = new BarDataSet(deadEntry, "死亡");
                barDataSet4.setColor(Color.GRAY);
                barData = new BarData(barDataSet);
                barData.addDataSet(barDataSet3);
                barData.addDataSet(barDataSet4);
                mChartNational.setData(barData);

                barData.setBarWidth(0.2f);
                barData.groupBars(1f, 0.4f, 0);

                xAxis = mChartNational.getXAxis();
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
                        if (index >= 50){
                            index = 50;
                            return "";
                        }
                        if (index < 1){
                            index = 1;
                            return "";
                        }
                        return name2[index-1];
                    }
                });
                barDataSet.setHighlightEnabled(true);
                mChartNational.notifyDataSetChanged();
                mChartNational.invalidate();
            }
        }).start();



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = (String) countrySpinner.getSelectedItem();
                System.out.println(str1);
                String str2 = (String)regionSpinner.getSelectedItem();
                System.out.println(str2);
                String str3 = (String)citySpinner.getSelectedItem();
                System.out.println(str3);
                String str = str2 == null || str2 == "" ? str1 : str3 == null || str3 == "" ?
                        str1 + "|" + str2 : str1 + "|" + str2 + "|" + str3;
                System.out.println("Result: " + str);
                if (selection_idx.containsKey(str)){
                    int idx = selection_idx.get(str);
                    Region target = regionList.get(idx);
                    List<Entry> confirmedEntry = new ArrayList<Entry>();
                    List<Entry> curedEntry = new ArrayList<Entry>();
                    List<Entry> deadEntry = new ArrayList<Entry>();
                    int length = target.data.size();
                    for (int i = 0; i < length; ++i){
                        confirmedEntry.add(new Entry(i, target.data.get(i).get(0)));
                        curedEntry.add(new Entry(i, target.data.get(i).get(2)));
                        deadEntry.add(new Entry(i, target.data.get(i).get(3)));
                    }
                    LineDataSet dataSet = new LineDataSet(confirmedEntry, "确诊");
                    dataSet.setColor(Color.RED);
                    dataSet.setCircleColor(Color.RED);
                    dataSet.setFillColor(Color.RED);
                    dataSet.setDrawFilled(true);
                    LineDataSet dataSet2 = new LineDataSet(curedEntry, "治愈");
                    dataSet2.setColor(Color.GREEN);
                    dataSet2.setCircleColor(Color.GREEN);
                    dataSet2.setFillColor(Color.GREEN);
                    dataSet2.setDrawFilled(true);
                    LineDataSet dataSet3 = new LineDataSet(deadEntry, "死亡");
                    dataSet3.setColor(Color.GRAY);
                    dataSet3.setCircleColor(Color.GRAY);
                    dataSet3.setFillColor(Color.GRAY);
                    dataSet3.setDrawFilled(true);
                    LineData lineData = new LineData(dataSet);
                    lineData.addDataSet(dataSet2);
                    lineData.addDataSet(dataSet3);
                    XAxis xAxis1 = mChartQuery.getXAxis();
                    xAxis1.setLabelCount(0);
                    YAxis yAxis = mChartQuery.getAxisLeft();
                    yAxis.setYOffset(0.0f);
                    mChartQuery.setData(lineData);
                    mChartQuery.invalidate();
                }
            }
        });

        return root;
    }
}