package com.example.speed_meas_concurention;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.speed_meas_concurention.databinding.ActivityCheckBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Check extends AppCompatActivity {

    private ActivityCheckBinding binding;
    private Calculations calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = new Intent(this,PrzebiegKonkurencji.class);
        Intent intent = getIntent();
        calc = (Calculations) intent.getSerializableExtra("Calculations");
        binding = ActivityCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DataPoint[] dpax = new DataPoint[calc.number_of_samples];
        DataPoint[] dpay = new DataPoint[calc.number_of_samples];
        DataPoint[] dpaz = new DataPoint[calc.number_of_samples];
        DataPoint[] dpanx = new DataPoint[calc.number_of_samples];
        DataPoint[] dpany = new DataPoint[calc.number_of_samples];
        DataPoint[] dpanz = new DataPoint[calc.number_of_samples];
        DataPoint[] dpanvx = new DataPoint[calc.number_of_samples];
        DataPoint[] dpanvy = new DataPoint[calc.number_of_samples];
        DataPoint[] dpanvz = new DataPoint[calc.number_of_samples];
        DataPoint[] dptjx = new DataPoint[calc.number_of_samples];
        DataPoint[] dptjy = new DataPoint[calc.number_of_samples];
        DataPoint[] dptjz = new DataPoint[calc.number_of_samples];
        DataPoint[] dpvelx = new DataPoint[calc.number_of_samples];
        DataPoint[] dpvely = new DataPoint[calc.number_of_samples];
        DataPoint[] dpvelz = new DataPoint[calc.number_of_samples];


        for(int i = 0; i<calc.number_of_samples;i++){

            dpax[i] = new DataPoint(i,calc.getAcc().getDatax().get(i));
            dpay[i] = new DataPoint(i,calc.getAcc().getDatay().get(i));
            dpaz[i] = new DataPoint(i,calc.getAcc().getDataz().get(i));
            dpanx[i] = new DataPoint(i,(float)Math.toDegrees((double)calc.getAngles().getDatax().get(i)));
            dpany [i] = new DataPoint(i,(float)Math.toDegrees((double)calc.getAngles().getDatay().get(i)));
            dpanz[i] = new DataPoint(i,(float)Math.toDegrees((double)calc.getAngles().getDataz().get(i)));
            dpanvx[i] = new DataPoint(i,calc.getGyro().getDatax().get(i));
            dpanvy[i] = new DataPoint(i,calc.getGyro().getDatay().get(i));
            dpanvz[i] = new DataPoint(i,calc.getGyro().getDataz().get(i));
            dptjx[i] = new DataPoint(i,calc.getTrajectory().getDatax().get(i));
            dptjy[i] = new DataPoint(i,calc.getTrajectory().getDatay().get(i));
            dptjz[i] = new DataPoint(i,calc.getTrajectory().getDataz().get(i));
            dpvelx[i] = new DataPoint(i,calc.getVelocity().getDatax().get(i));
            dpvely[i] = new DataPoint(i,calc.getVelocity().getDatay().get(i));
            dpvelz[i] = new DataPoint(i,calc.getVelocity().getDataz().get(i));


        }

        GraphView graphacc = (GraphView) findViewById(R.id.graphacc);
        LineGraphSeries<DataPoint> accx = new LineGraphSeries<DataPoint>(dpax);
        LineGraphSeries<DataPoint> accy = new LineGraphSeries<DataPoint>(dpay);
        LineGraphSeries<DataPoint> accz = new LineGraphSeries<DataPoint>(dpaz);
        accx.setTitle("x");
        accy.setTitle("y");
        accy.setColor(Color.argb(255, 254 ,0, 0));
        accz.setTitle("z");
        accz.setColor(Color.argb(255, 0, 254, 0));
        graphacc.getLegendRenderer().setVisible(true);
        graphacc.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        GraphView graphang = (GraphView) findViewById(R.id.graphang);
        LineGraphSeries<DataPoint> angx = new LineGraphSeries<DataPoint>(dpanx);
        LineGraphSeries<DataPoint> angy = new LineGraphSeries<DataPoint>(dpany);
        LineGraphSeries<DataPoint> angz = new LineGraphSeries<DataPoint>(dpanz);
        angx.setTitle("x");
        angy.setTitle("y");
        angy.setColor(Color.argb(255, 254 ,0, 0));
        angz.setTitle("z");
        angz.setColor(Color.argb(255, 0, 254, 0));
        graphang.getLegendRenderer().setVisible(true);
        graphang.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        GraphView graphangvel = (GraphView) findViewById(R.id.graphangvel);
        LineGraphSeries<DataPoint> anvx = new LineGraphSeries<DataPoint>(dpanvx);
        LineGraphSeries<DataPoint> anvy = new LineGraphSeries<DataPoint>(dpanvy);
        LineGraphSeries<DataPoint> anvz = new LineGraphSeries<DataPoint>(dpanvz);
        anvx.setTitle("x");
        anvy.setTitle("y");
        anvy.setColor(Color.argb(255, 254 ,0, 0));
        anvz.setTitle("z");
        anvz.setColor(Color.argb(255, 0, 254, 0));
        graphangvel.getLegendRenderer().setVisible(true);
        graphangvel.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        GraphView graphtraj = (GraphView) findViewById(R.id.graphtraj);
        LineGraphSeries<DataPoint> atjx = new LineGraphSeries<DataPoint>(dptjx);
        LineGraphSeries<DataPoint> atjy = new LineGraphSeries<DataPoint>(dptjy);
        LineGraphSeries<DataPoint> atjz = new LineGraphSeries<DataPoint>(dptjz);
        atjx.setTitle("x");
        atjy.setTitle("y");
        atjy.setColor(Color.argb(255, 254 ,0, 0));
        atjz.setTitle("z");
        atjz.setColor(Color.argb(255, 0, 254, 0));
        graphtraj.getLegendRenderer().setVisible(true);
        graphtraj.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        GraphView graphvelo = (GraphView) findViewById(R.id.graphvelo);
        LineGraphSeries<DataPoint> velx = new LineGraphSeries<DataPoint>(dpvelx);
        LineGraphSeries<DataPoint> vely = new LineGraphSeries<DataPoint>(dpvely);
        LineGraphSeries<DataPoint> velz = new LineGraphSeries<DataPoint>(dpvelz);
        velx.setTitle("x");
        vely.setTitle("y");
        vely.setColor(Color.argb(255, 254 ,0, 0));
        velz.setTitle("z");
        velz.setColor(Color.argb(255, 0, 254, 0));
        graphvelo.getLegendRenderer().setVisible(true);
        graphvelo.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graphacc.setTitle("Acceleration");
        graphang.setTitle("Angles");
        graphangvel.setTitle("Angular Velocity");
        graphtraj.setTitle("Trajectory");
        graphvelo.setTitle("Velocity");


        graphacc.addSeries(accx);graphacc.addSeries(accy);graphacc.addSeries(accz);
        graphang.addSeries(angx);graphang.addSeries(angy);graphang.addSeries(angz);
        graphangvel.addSeries(anvx);graphangvel.addSeries(anvy);graphangvel.addSeries(anvz);
        graphtraj.addSeries(atjx);graphtraj.addSeries(atjy);graphtraj.addSeries(atjz);
        graphvelo.addSeries(velx);graphvelo.addSeries(vely);graphvelo.addSeries(velz);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

    }
}