package com.desarrollo.infocovid;

import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
 * Use the {@link EnfermosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnfermosFragment extends Fragment {
    TextView tcas,tca,poraisl,paisl,porrec,rec,porhosp,hosp,poruci,uci,forfalle,falle,capor;
    private DatabaseReference mDatabase;
    int xtcasos,xtca,xporaislados,xpaislados,xporrec,xrec,xporhosp,xhosp,xporuci,xuci,xporfalle,xfalle;


    View vista;
    PieChart pieChart;

    int coloramarillo = rgb(204,153,0);
    int colorrojo = rgb(204,0,51);
    int colorverde = rgb(102,153,0);
    int colorvioleta = rgb(102,0,102);
    int colorplomo = rgb(153,153,153);


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnfermosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnfermosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnfermosFragment newInstance(String param1, String param2) {
        EnfermosFragment fragment = new EnfermosFragment();
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
        vista = inflater.inflate(R.layout.fragment_enfermos, container, false);
        tcas = vista.findViewById(R.id.tcasos);
        tca = vista.findViewById(R.id.tca);
        poraisl = vista.findViewById(R.id.porpaislados);
        paisl = vista.findViewById(R.id.paislados);
        porrec = vista.findViewById(R.id.porrec);
        rec = vista.findViewById(R.id.rec);
        porhosp = vista.findViewById(R.id.porhosp);
        hosp = vista.findViewById(R.id.hosp);
        poruci = vista.findViewById(R.id.poruci);
        uci = vista.findViewById(R.id.uci);
        forfalle = vista.findViewById(R.id.porfalle);
        falle = vista.findViewById(R.id.falle);
        //faltaagregar
        capor = vista.findViewById(R.id.capor);


        pieChart = vista.findViewById(R.id.piechart);

        obtenerdatosparte1();

        return vista;

    }

    private void obtenerdatosparte1() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Casos");
        mDatabase.child("8-15-20").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    
                    xtcasos = Integer.parseInt(snapshot.child("tcasos").getValue().toString());
                    xtca = Integer.parseInt(snapshot.child("tca").getValue().toString());

                    xporaislados = Integer.parseInt(snapshot.child("tpcr").getValue().toString());
                    xpaislados = Integer.parseInt(snapshot.child("trec").getValue().toString());

                    xporrec = Integer.parseInt(snapshot.child("trap").getValue().toString());
                    xrec = Integer.parseInt(snapshot.child("trec").getValue().toString());

                    xporhosp = Integer.parseInt(snapshot.child("trap").getValue().toString());
                    xhosp = Integer.parseInt(snapshot.child("thosp").getValue().toString());

                    xporuci = Integer.parseInt(snapshot.child("trap").getValue().toString());
                    xuci = Integer.parseInt(snapshot.child("tuci").getValue().toString());

                    xporfalle = Integer.parseInt(snapshot.child("trap").getValue().toString());
                    xfalle = Integer.parseInt(snapshot.child("tfalle").getValue().toString());


                    tcas.setText(String.valueOf(xtcasos));
                    tca.setText(String.valueOf(xtca));
                    poraisl.setText(String.valueOf(xporaislados));
                    paisl.setText(String.valueOf(xpaislados));
                    porrec.setText(String.valueOf(xporrec));
                    rec.setText(String.valueOf(xrec));
                    porhosp.setText(String.valueOf(xporhosp));
                    hosp.setText(String.valueOf(xhosp));
                    poruci.setText(String.valueOf(xporuci));
                    uci.setText(String.valueOf(xuci));
                    forfalle.setText(String.valueOf(xporfalle));
                    falle.setText(String.valueOf(xfalle));



                    graficochart(xpaislados,xrec,xhosp,xuci,xfalle);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

    private void graficochart(int aislados, int recuperados, int hospitalizados, int ucis, int fallecidos) {
        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(aislados));
        visitors.add(new PieEntry(recuperados));
        visitors.add(new PieEntry(hospitalizados));
        visitors.add(new PieEntry(ucis));
        visitors.add(new PieEntry(fallecidos));

        PieDataSet pieDataSet = new PieDataSet(visitors, "Total Casos");
        pieDataSet.setColors(coloramarillo,colorverde,colorrojo,colorvioleta,colorplomo);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText("Total Casos");
        //pieChart.animateXY(2000,2000);

        pieChart.animateY(2000);
    }
}