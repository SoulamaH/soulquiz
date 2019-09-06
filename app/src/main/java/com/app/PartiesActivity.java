package com.app;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.app.quiz.activeandroid.dao.PartiesDao;
import com.app.quiz.activeandroid.dao.rest.PartiesRestDao;
import com.app.quiz.activeandroid.model.Parties;

import java.util.ArrayList;
import java.util.List;

import app.com.quizapplication.R;

public class PartiesActivity extends AppCompatActivity {

    //test bande
   private AnyChartView anyChartView;
   private Cartesian cartesian;
   private Column column;

    // pie
    private Pie pie;


   private List<Parties> partiesList;

    private List<DataEntry> data;

    public static final String TAG = "PartiesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);

        anyChartView = findViewById(R.id.any_chart_view);

        //

        partiesList = (List<Parties>) getIntent().getSerializableExtra("chargeGraphe");
        Log.i(TAG,"****** getBestScore partie onResponse *******" + partiesList );
        //bande();
      // pie();

        pie = AnyChart.pie();

        data = new ArrayList<>();

        for (Parties parties:    partiesList) {

            //
            if (parties.getJoueurs() != null && parties.getCategories() != null && parties.getDifficultes() != null){
                 data.add(new ValueDataEntry(parties.getJoueurs().getNumero(), parties.getScore()));
            }
        }


        pie.data(data);

        pie.title(" Cinq (05) meilleurs score");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text(String.format("Partie %s %s",partiesList.get(0).getCategories().getName(),partiesList.get(0).getDifficultes().getNiveauDifficulte()))
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pie.animation(true);
        anyChartView.setChart(pie);
    }

//    public void bande(){
//
//        cartesian = AnyChart.column();
//
//        data = new ArrayList<>();
//        data.add(new ValueDataEntry("Rouge", 80540));
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        for (Parties parties: partiesList) {
//
//        }
//
//
//        data.add(new ValueDataEntry(partiesList.get(0).getJoueurs().getNumero(), partiesList.get(0).getScore()));
//        data.add(new ValueDataEntry(partiesList.get(1).getJoueurs().getNumero(), partiesList.get(1).getScore()));
//        data.add(new ValueDataEntry(partiesList.get(2).getJoueurs().getNumero(), partiesList.get(2).getScore()));
//        data.add(new ValueDataEntry(partiesList.get(3).getJoueurs().getNumero(), partiesList.get(3).getScore()));
//        data.add(new ValueDataEntry(partiesList.get(4).getJoueurs().getNumero(), partiesList.get(4).getScore()));
//       // data.add(new ValueDataEntry(parties.getJoueurs().getNumero(), 52));
//
//
//
//        column = cartesian.column(data);
//
//        column.tooltip()
//                .titleFormat("{%X}")
//                .position(Position.CENTER_BOTTOM)
//                .anchor(Anchor.CENTER_BOTTOM)
//                .offsetX(0d)
//                .offsetY(5d)
//                .format("${%Value}{groupsSeparator: }");
//
//        cartesian.animation(true);
//        cartesian.title("Top 5 Meilleur score");
//
//        cartesian.yScale().minimum(0d);
//
//        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
//
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//        cartesian.interactivity().hoverMode(HoverMode.BY_X);
//
//        cartesian.xAxis(0).title("Noms des Joueurs");
//        cartesian.yAxis(0).title("Score");
//
//        anyChartView.setChart(cartesian);
//    }
}
