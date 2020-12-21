package com.example.TRS_VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.common.primitives.Doubles;
import com.squareup.picasso.Downloader;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Text;

import java.util.Random;

public class InfoActivity extends AppCompatActivity {
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    int valeur;
    Button stop;
    private SharedPreferences sharedPrefChrono;
    private SharedPreferences.Editor editor;

    private SharedPreferences produitValuesPref;
    private SharedPreferences.Editor editorProd;

    public GraphicalView HChart;
    private GraphicalView VChart;

    public XYSeries HvisitsSeries ;
    public XYSeries HvisitsSeries2 ;

    private XYSeries VvisitsSeries ;

    private XYMultipleSeriesDataset Hdataset;
    private XYMultipleSeriesDataset Vdataset;

    public XYSeriesRenderer HvisitsRenderer;
    public XYMultipleSeriesRenderer HmultiRenderer;

    private XYSeriesRenderer VvisitsRenderer;
    private XYMultipleSeriesRenderer VmultiRenderer;
    public static String part2;
    public static String cadenceStat;
    public static String nombreDePiecess;

    public static String QUALITYF;
    public static String DISPONIBILITYF;
    public static String PERFORMANCEF;
    public static String TRSF;
    static Boolean close=false;
    private XYMultipleSeriesDataset Hdataset2;
    private XYSeriesRenderer HvisitsRenderer2;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //
        init();
        produitValuesPref=getSharedPreferences("prefProd",MODE_PRIVATE);
        String  cadence=produitValuesPref.getString("qtefab","Empty");
         String nombreDePieces=produitValuesPref.getString("cadence","Empty");
         nombreDePiecess=nombreDePieces;
        cadenceStat=cadence;
        Log.i("cadenceee",cadenceStat);
        stop=findViewById(R.id.button7);
         setupHChart();
        setupVChart();
        runMultipleAsyncTask();
        new  ChartTask().execute();
        new HChartTask().execute();
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close =true;
                Intent i = new Intent(InfoActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void runMultipleAsyncTask() {
        ChartTask asyncTask = new ChartTask(); // First

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // Above Api Level 13
        {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else // Below Api Level 13
        {
            asyncTask.execute();
        }
        HChartTask asyncTask2 = new HChartTask(); // Second
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)// Above Api Level 13
        {
            asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else // Below Api Level 13
        {
            asyncTask2.execute();
        }



    }


    private void setupVChart() {
        // Creating an  XYSeries for Visits
        VvisitsRenderer = new XYSeriesRenderer();
        switch (Main2Activity.var){
            case 1:
                VvisitsRenderer.setColor(Color.YELLOW);
                VvisitsSeries = new XYSeries("ARRET");
                break;
            case 2:
                VvisitsRenderer.setColor(Color.RED);
                VvisitsSeries = new XYSeries("PANNE");
                break;
            case 3:
                VvisitsRenderer.setColor(Color.BLUE);
                VvisitsSeries = new XYSeries("REGLAGE");
                break;
            case 4:
                VvisitsRenderer.setColor(Color.MAGENTA);
                VvisitsSeries = new XYSeries("MAINTENANCE");
                break;
            default:
                VvisitsRenderer.setColor(Color.GREEN);
                VvisitsSeries = new XYSeries("PRODUCTION");
                break;

        }


        // Creating a Hdataset to hold each series
        Vdataset = new XYMultipleSeriesDataset();
        // Adding Visits Series to the Hdataset
        Vdataset.addSeries(VvisitsSeries);

        // Creating XYSeriesRenderer to customize HvisitsSeries

        VvisitsRenderer.setPointStyle(PointStyle.CIRCLE);
        VvisitsRenderer.setFillPoints(true);

        VvisitsRenderer.setLineWidth(2);
        VvisitsRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        VmultiRenderer = new XYMultipleSeriesRenderer();

        VmultiRenderer.setChartTitle("Arret");
        VmultiRenderer.setXTitle("Seconds");
        VmultiRenderer.setYTitle("Count");
        VmultiRenderer.setApplyBackgroundColor(true);
        VmultiRenderer.setMarginsColor(Color.WHITE);
        //VmultiRenderer.setZoomButtonsVisible(true);

        VmultiRenderer.setXAxisMin(0);
        VmultiRenderer.setXAxisMax(3);

        VmultiRenderer.setYAxisMin(0);
        VmultiRenderer.setYAxisMax(800);

        VmultiRenderer.setBarSpacing(2);
        VmultiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.VERTICAL);
        // Adding visitsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        VmultiRenderer.addSeriesRenderer(VvisitsRenderer);

        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer2 = (LinearLayout) findViewById(R.id.chart_container2);

        VChart = (GraphicalView) ChartFactory.getBarChartView(getBaseContext(), Vdataset, VmultiRenderer, Type.DEFAULT);

        // Adding the Line Chart to the LinearLayout
        chartContainer2.addView(VChart);
    }

    private void setupHChart() {
        // Creating an  XYSeries for Visits

        HvisitsSeries = new XYSeries("Cadence");

        int[] y ={50};
        // Creating a Hdataset to hold each series
        Hdataset = new XYMultipleSeriesDataset();

        // Adding Visits Series to the Hdataset
        Hdataset.addSeries(HvisitsSeries);
        // Creating XYSeriesRenderer to customize HvisitsSeries
        HvisitsRenderer = new XYSeriesRenderer();
        HvisitsRenderer.setColor(Color.RED);
        HvisitsRenderer.setPointStyle(PointStyle.CIRCLE);
        HvisitsRenderer.setFillPoints(true);
        HvisitsRenderer.setLineWidth(3);
        HvisitsRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize HvisitsSeries


        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        HmultiRenderer = new XYMultipleSeriesRenderer();

        HmultiRenderer.setChartTitle("CADENCE");
        HmultiRenderer.setApplyBackgroundColor(true);
        HmultiRenderer.setMarginsColor(Color.WHITE);


        HmultiRenderer.setZoomButtonsVisible(true);

        HmultiRenderer.setXAxisMin(0);
        HmultiRenderer.setXAxisMax(4);

        HmultiRenderer.setYAxisMin(0);
        HmultiRenderer.setYAxisMax(3000);

        HmultiRenderer.setBarSpacing(2);

        // Adding visitsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        HmultiRenderer.addSeriesRenderer(HvisitsRenderer);
        //HmultiRenderer.addSeriesRenderer(HvisitsRenderer2);

        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

        HChart = (GraphicalView) ChartFactory.getBarChartView(getBaseContext(), Hdataset, HmultiRenderer, Type.DEFAULT);

        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(HChart);

    }



    private class ChartTask extends AsyncTask<Void, String, Void>{

        // Generates dummy data in a non-ui thread

        @Override
        protected Void doInBackground(Void... params) {

            try{
                while(true){

                    String graphVal = ArretActivity.valChrono3; //arret
                    String[] parts = graphVal.split(":");
                    int pauseValue=0;
                    if(parts.length== 2){
                        int part2 = Integer.parseInt(parts[0]);//sec
                        pauseValue=part2;

                    }else {
                        int part2 = Integer.parseInt(parts[1]);//sec
                        int part3= Integer.parseInt(parts[0]); //min
                        pauseValue=part2+ 60*part3;

                    }






                    String [] values = new String[2];
                    String [] values2 = new String[2];





                    values[0] = Integer.toString(1);
                    values[1] =  pauseValue+"";

                    values2[0] = Integer.toString(2);
                    values2[1] = "5";



                    publishProgress(values);
                    publishProgress(values2);



                    Thread.sleep(100);

                    Log.i("in Asych1","iiiiiiiiiiiiiiiiiiiiinnnn111");
                    if (InfoActivity.close) break;

                }
            }catch(Exception e){ }
            return null;
        }
        // Plotting generated data in the graph
        @Override
        protected void onProgressUpdate(String... values) {

            VvisitsSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            VChart.repaint();


        }
    }



    private class HChartTask extends AsyncTask<Void, String, Void>{


        // Generates dummy data in a non-ui thread
        @Override
        protected Void doInBackground(Void... params) {

            try{
                while(true){


                    String [] values10 = new String[2];
                    String [] values22 = new String[2];




                    values10[0] = Integer.toString(1);
                    values10[1] =  InfoActivity.cadenceStat;

                    values22[0] = Integer.toString(2);
                    values22[1] = "2200";


                    publishProgress(values10);
                    publishProgress(values22);


                    Thread.sleep(100);

                    Log.i("in Asych2","iiiiiiiiiiiiiiiiiiiiinnnn2");
                    if (InfoActivity.close) break;


                }
            }catch(Exception e){ }
            return null;
        }
        // Plotting generated data in the graph
        @Override
        protected void onProgressUpdate(String... values) {
            HvisitsSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            HChart.repaint();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void init() {



        //horizontalBarChart=(HorizontalBarChart)findViewById(R.id.Horizontalchart);
        //pieChart = (PieChart) findViewById(R.id.piechart);
        //sharedPrefChrono = getApplicationContext().getSharedPreferences("MyPrefChrono", 0);
        //editor = sharedPrefChrono.edit();
        String dispo;

        currentValue();

    }
    private void currentValue() {

        TextView disponibility=findViewById(R.id.dtxt);
        final TextView performance=findViewById(R.id.ptxt);
        final TextView quality=findViewById(R.id.qtxt);
        final TextView TRS=findViewById(R.id.ttxt);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView disponibility=findViewById(R.id.dtxt);
                                txt1=findViewById(R.id.txt1);
                                txt2=findViewById(R.id.txt2);
                                txt3=findViewById(R.id.txt3);
                                txt4=findViewById(R.id.txt4);
                                txt1.setText(Main2Activity.valChrono1);
                                txt2.setText(Main2Activity.valChrono2);
                                txt3.setText(ArretActivity.valChrono3);
                                txt4.setText(Main2Activity.valChrono3);
                                try {
                                    String graphV = ArretActivity.valChrono3;
                                    String[] parts = graphV.split(":");
                                    int pauseValue=0;
                                    if(parts.length== 2){
                                        int part2 = Integer.parseInt(parts[0]);//sec
                                        pauseValue=part2;

                                    }else {
                                        int part2 = Integer.parseInt(parts[1]);//sec
                                        int part3= Integer.parseInt(parts[0]); //min
                                        pauseValue=part2+ 60*part3;

                                    }

                                    Log.i("seconds", String.valueOf(pauseValue));
                                } catch (NullPointerException ex) {
                                    ex.printStackTrace();
                                }



                                String graphV2 = Main2Activity.valChrono2;
                                String[] parts2 = graphV2.split(":");
                                int openValue=0;
                                if(parts2.length== 2){
                                    int part2 = Integer.parseInt(parts2[0]);//sec
                                    openValue=part2;

                                }else {
                                    int part2 = Integer.parseInt(parts2[1]);//sec
                                    int part3= Integer.parseInt(parts2[0]); //min
                                    openValue=part2+ 60*part3;

                                }

                                Log.i("openValue", String.valueOf(openValue));


                                String graphV3 = Main2Activity.valChrono1;
                                String[] parts3 = graphV3.split(":");
                                int netValue=0;
                                if(parts3.length== 2){
                                    int part2 = Integer.parseInt(parts3[0]);//sec
                                    netValue=part2;

                                }else {
                                    int part2 = Integer.parseInt(parts3[1]);//sec
                                    int part3= Integer.parseInt(parts3[0]); //min
                                    netValue=part2+ 60*part3;

                                }

                                Log.i("netValue", String.valueOf(netValue));
                                Double xx=(Double.valueOf(netValue)/Double.valueOf(openValue))*100;
                                String y = Double.toString(round(xx,2));

                                DISPONIBILITYF= y +" %";

                                disponibility.setText(DISPONIBILITYF);


                                //netValue=nombre de pieces sorties
                                //InfoActivity.nombreDePiecess=nombre total
                                Double ff=(Double.valueOf(netValue)*100/Double.valueOf(InfoActivity.nombreDePiecess));
                                String f=Double.toString(round(ff,2));

                                PERFORMANCEF= f +" %";
                                performance.setText(PERFORMANCEF);


                                //QualityActivity.pieceMauvaise= les pieces mauvaises
                                Double kk=((Double.valueOf(netValue)-Double.valueOf(QualiteActivity.pieceMauvaise))*100/Double.valueOf(netValue));
                                String k=Double.toString(round(kk,2));

                                QUALITYF= k +" %";
                                quality.setText(QUALITYF);


                                Double trs= (xx*ff*kk)/10000;
                                String t=Double.toString(round(trs,2));

                                TRSF= t +" %";
                                TRS.setText(TRSF);





                            }
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        };
        t.start();

    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
