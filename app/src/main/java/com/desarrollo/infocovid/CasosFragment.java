package com.desarrollo.infocovid;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CasosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasosFragment extends Fragment {
    TextView vrec,vca,vfalle,vdrec,vdca,vdfalle,vrange;
    int colorrojo = rgb(204, 0, 51);
    int coloramarillo = rgb(204, 153, 0);
    int colorverde = rgb(102, 153, 0);


    private DatabaseReference mDatabase;
    ArrayList<Integer> arrayList1;
    ArrayList<Integer> arrayList2;
    ArrayList<Integer> arrayList3;
    View vista;
    LineChart lineChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CasosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CasosFragment newInstance(String param1, String param2) {
        CasosFragment fragment = new CasosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_casos, container, false);
        lineChart = vista.findViewById(R.id.Linechart1);
        arrayList1 = new ArrayList<Integer>();
        arrayList2 = new ArrayList<Integer>();
        arrayList3 = new ArrayList<Integer>();

        vrec = vista.findViewById(R.id.lrec);
        vca = vista.findViewById(R.id.lca);
        vfalle = vista.findViewById(R.id.lfalle);
        vdrec = vista.findViewById(R.id.ldrec);
        vdca = vista.findViewById(R.id.ldca);
        vdfalle = vista.findViewById(R.id.ldfalle);
        vrange = vista.findViewById(R.id.lrange);

        lineChart.setMaxVisibleValueCount(40);
        obtenerdatos();
        dat();
        setData(7,7);

        return  vista;

    }



    private void obtenerdatos() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Casos");
        mDatabase.child("8-16-20").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    int xtrec= Integer.parseInt(snapshot.child("trec").getValue().toString());
                    int xca = Integer.parseInt(snapshot.child("tca").getValue().toString());
                    int xfalle = Integer.parseInt(snapshot.child("tfalle").getValue().toString());
                    int drec = Integer.parseInt(snapshot.child("drec").getValue().toString());
                    int dca = Integer.parseInt(snapshot.child("dca").getValue().toString());
                    int dfalle = Integer.parseInt(snapshot.child("dfalle").getValue().toString());
                    //int drange = Integer.parseInt(snapshot.child("range").getValue().toString());

                    vrec.setText(String.valueOf(xtrec));
                    vca.setText(String.valueOf(xca));
                    vfalle.setText(String.valueOf(xfalle));
                    vdrec.setText(String.valueOf(drec));
                    vdca.setText(String.valueOf(dca));
                    vdfalle.setText(String.valueOf(dfalle));
                    //range.setText(String.valueOf(drange));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val = arrayList1.get(i);
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val2 = arrayList2.get(i);
            values2.add(new Entry(i, val2));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val3 = arrayList3.get(i);
            values3.add(new Entry(i, val3));
        }

        LineDataSet set1, set2, set3;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) lineChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "Recuperados");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(colorverde);
            set1.setCircleColor(colorverde);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "C. Activos");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(coloramarillo);
            set2.setCircleColor(coloramarillo);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "Fallecidos");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(colorrojo);
            set3.setCircleColor(colorrojo);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            lineChart.setData(data);
        }
    }
    private void dat() {
        arrayList1.add(20);
        arrayList1.add(50);
        arrayList1.add(60);
        arrayList1.add(30);
        arrayList1.add(10);
        arrayList1.add(120);
        arrayList1.add(140);

        arrayList2.add(40);
        arrayList2.add(80);
        arrayList2.add(90);
        arrayList2.add(150);
        arrayList2.add(170);
        arrayList2.add(90);
        arrayList2.add(100);

        arrayList3.add(10);
        arrayList3.add(50);
        arrayList3.add(60);
        arrayList3.add(170);
        arrayList3.add(20);
        arrayList3.add(40);
        arrayList3.add(50);

    }
}