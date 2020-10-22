package com.desarrollo.infocovid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PruebasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PruebasFragment extends Fragment {

    TextView tpruebas,dpruebas,tpcr,dpcr,trapidas,drapidas,pruebasejemplo;
    private DatabaseReference mDatabase;
    public int xtpruebas,xdpruebas,xtpcr,xdpcr,xtrap,xdrap;
    int pru1=0;
    int pru2=0;
    int coloramarillo = rgb(204, 153, 0);
    int colorverde = rgb(102, 153, 0);


    ArrayList<Integer> arrayList2;
    ArrayList<Integer> arrayList3;
    ArrayList<BarEntry> yValues;

    View vista;
    BarChart barChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PruebasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PruebasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PruebasFragment newInstance(String param1, String param2) {
        PruebasFragment fragment = new PruebasFragment();
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

        arrayList2 = new ArrayList<Integer>();
        arrayList3 = new ArrayList<Integer>();
        yValues = new ArrayList<>();
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_pruebas, container, false);






        tpruebas = vista.findViewById(R.id.tpruebas);
        dpruebas = vista.findViewById(R.id.dpruebas);
        tpcr = vista.findViewById(R.id.tpcr);
        dpcr = vista.findViewById(R.id.dpcr);
        trapidas = vista.findViewById(R.id.traps);
        drapidas = vista.findViewById(R.id.drapi);
        pruebasejemplo = vista.findViewById(R.id.pruebasejemplo);
        barChart = vista.findViewById(R.id.idbarchart);

        barChart.setMaxVisibleValueCount(40);

        obtenerdatosparte1();
        obtenerdatosparte2();
        /*readData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Integer> list){


            }
        });*/

        creardatos();

        for (int i = 0; i < 7; i++) {

                        int val2 = arrayList2.get(i);
                        int val3 = arrayList3.get(i);
                        yValues.add(new BarEntry(i, new float[]{val2, val3}));
        }

        BarDataSet set1;
        set1 = new BarDataSet(yValues, "Estadisticas de Pruebas");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Pcr", "Rapidas"});
        set1.setColors(coloramarillo,colorverde);
        //set1.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
        barChart.animateY(2000);

        //Toast.makeText(getContext(),tempString, Toast.LENGTH_LONG).show();
        return vista;
    }


    /*private void readData(final FirebaseCallback firebaseCallback){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Casos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        pru1 = Integer.parseInt(ds.child("tpcr").getValue().toString());
                        arrayList2.add(pru1);
                        pru2 = Integer.parseInt(ds.child("trap").getValue().toString());
                        //arrayList3.add(pru2);
                    }
                    firebaseCallback.onCallback(arrayList2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }
    private interface FirebaseCallback {
        void onCallback(ArrayList<Integer> list);
    }*/

    public void obtenerdatosparte2() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Casos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){

                        pru1 = Integer.parseInt(ds.child("tpcr").getValue().toString());
                        //arrayList2.add(pru1);
                        pru2 = Integer.parseInt(ds.child("trap").getValue().toString());
                        //arrayList3.add(pru2);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }

        });
    }




    private void obtenerdatosparte1() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Casos");
        mDatabase.child("8-16-20").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    //2do cuadro
                    xtpruebas = Integer.parseInt(snapshot.child("tpruebas").getValue().toString());
                    xdpruebas = Integer.parseInt(snapshot.child("dpruebas").getValue().toString());
                    xtpcr = Integer.parseInt(snapshot.child("tpcr").getValue().toString());
                    xdpcr = Integer.parseInt(snapshot.child("dpcr").getValue().toString());
                    xtrap = Integer.parseInt(snapshot.child("trap").getValue().toString());
                    xdrap = Integer.parseInt(snapshot.child("drap").getValue().toString());

                    tpruebas.setText(String.valueOf(xtpruebas));
                    dpruebas.setText(String.valueOf(xdpruebas));
                    tpcr.setText(String.valueOf(xtpcr));
                    dpcr.setText(String.valueOf(xdpcr));
                    trapidas.setText(String.valueOf(xtrap));
                    drapidas.setText(String.valueOf(xdrap));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

    private void creardatos() {

        arrayList2.add(40);
        arrayList2.add(50);
        arrayList2.add(60);
        arrayList2.add(70);
        arrayList2.add(80);
        arrayList2.add(90);
        arrayList2.add(100);

        arrayList3.add(40);
        arrayList3.add(50);
        arrayList3.add(60);
        arrayList3.add(70);
        arrayList3.add(80);
        arrayList3.add(90);
        arrayList3.add(100);
    }

}