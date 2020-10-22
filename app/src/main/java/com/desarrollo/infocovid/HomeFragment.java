package com.desarrollo.infocovid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    TextView tcas,dcas,pac,pdi,tho,dho,tuc,duc,tre,dre,tpr,dpr,tpc,dpc,tra,dra,tfa,dfa,tca,dca;
    View vista;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_home, container, false);
        tcas=vista.findViewById(R.id.tcasos);
        dcas=vista.findViewById(R.id.dcasos);
        pac=vista.findViewById(R.id.pacumulada);
        pdi=vista.findViewById(R.id.pdiaria);
        tho=vista.findViewById(R.id.thosp);
        dho=vista.findViewById(R.id.dhosp);
        tuc=vista.findViewById(R.id.tuci);
        duc=vista.findViewById(R.id.duci);
        tre=vista.findViewById(R.id.trec);
        dre=vista.findViewById(R.id.drec);
        tpr=vista.findViewById(R.id.tpruebas);
        dpr=vista.findViewById(R.id.dpruebas);
        tpc=vista.findViewById(R.id.tpcr);
        dpc=vista.findViewById(R.id.dpcr);
        tra=vista.findViewById(R.id.trap);
        dra=vista.findViewById(R.id.drap);
        tfa=vista.findViewById(R.id.tfalle);
        dfa=vista.findViewById(R.id.dfalle);
        tca=vista.findViewById(R.id.tca);
        dca=vista.findViewById(R.id.dca);

        mDatabase = FirebaseDatabase.getInstance().getReference("Casos");

        obtenerDatos();




        return vista;
    }

    private void obtenerDatos() {

        mDatabase.child("8-14-20").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //primer cuadro
                    String xtcasos = snapshot.child("tcasos").getValue().toString();
                    String xdcasos = snapshot.child("dcasos").getValue().toString();
                    String xpacum = snapshot.child("pacumulada").getValue().toString();
                    String xdacum = snapshot.child("pdiaria").getValue().toString();
                    //2do cuadro
                    String xtpruebas = snapshot.child("tpruebas").getValue().toString();
                    String xdpruebas = snapshot.child("dpruebas").getValue().toString();
                    String xtpcr = snapshot.child("tpcr").getValue().toString();
                    String xdpcr = snapshot.child("dpcr").getValue().toString();
                    String xtrap = snapshot.child("trap").getValue().toString();
                    String xdrap = snapshot.child("drap").getValue().toString();
                    //3ro cuadro
                    String xthosp = snapshot.child("thosp").getValue().toString();
                    String xdhosp = snapshot.child("dhosp").getValue().toString();
                    String xtuci = snapshot.child("tuci").getValue().toString();
                    String xduci = snapshot.child("duci").getValue().toString();
                    String xtrec = snapshot.child("trec").getValue().toString();
                    String xdrec = snapshot.child("drec").getValue().toString();
                    //4to cuadro
                    String xtfalle = snapshot.child("tfalle").getValue().toString();
                    String xdfalle = snapshot.child("dfalle").getValue().toString();
                    String xcac = snapshot.child("tca").getValue().toString();
                    String xdac = snapshot.child("dca").getValue().toString();

                    //primer cuadro
                    tcas.setText(xtcasos);
                    dcas.setText(xdcasos);
                    pac.setText(xpacum);
                    pdi.setText(xdacum);
                    //2do cuadro
                    tpr.setText(xtpruebas);
                    dpr.setText(xdpruebas);
                    tpc.setText(xtpcr);
                    dpc.setText(xdpcr);
                    tra.setText(xtrap);
                    dra.setText(xdrap);
                    //3ro cuadro
                    tho.setText(xthosp);
                    dho.setText(xdhosp);
                    tuc.setText(xtuci);
                    duc.setText(xduci);
                    tre.setText(xtrec);
                    dre.setText(xdrec);
                    //4to cuadro
                    tfa.setText(xtfalle);
                    dfa.setText(xdfalle);
                    tca.setText(xcac);
                    dca.setText(xdac);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
}
