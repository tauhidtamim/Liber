package com.inxs5859.liber.User;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.R;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class StatsFragment extends Fragment {

    DatabaseReference databaseReference;
    TextView headingText, titleText, percentText, emptyText, monthText;
    ProgressBar progressBar;
    TextInputEditText goal;
    TextInputLayout goalLayout;
    Button button, editButton;
    LineChart lineChart;

    String booksGoal, userName;
    int booksRead = 0, booksReadThisMonth = 0;

    String[] xAxisLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int[] xAxis = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    int[] yAxis = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        //hooks
        headingText = view.findViewById(R.id.textView);
        titleText = view.findViewById(R.id.title);
        percentText = view.findViewById(R.id.percent);
        emptyText = view.findViewById(R.id.empty_title);
        monthText = view.findViewById(R.id.month_text);
        progressBar = view.findViewById(R.id.progress_bar);
        goal = view.findViewById(R.id.input_goal_text);
        goalLayout = view.findViewById(R.id.input_goal);
        button = view.findViewById(R.id.btn);
        editButton = view.findViewById(R.id.edit_btn);
        lineChart = view.findViewById(R.id.chart);


        //retrieve username
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();
        userName = userDetails.get(SessionManager.KEY_USERNAME);

        //check whether goal already has been set
        checkIfGoalSet();

        //set current year in heading
        String heading = String.valueOf(Year.now().getValue());
        heading = heading + " READING CHALLENGE";
        headingText.setText(heading);

        //set # books read in current month
        setCurrentMonthRead();

        //get book counts & configure line-chart
        configureChart();


        //set goal
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = goal.getText().toString();

                if (input.equals("0") || input.isEmpty()) {
                    Toast.makeText(getContext(), "ERROR! SET GOAL CORRECTLY!", Toast.LENGTH_LONG).show();
                    return;
                }


                databaseReference.child(userName).setValue(input);
                booksGoal = input;
                goal.setText("");
                //Log.d("YEAR", String.valueOf(Year.now().getValue()));
                Toast.makeText(getContext(), "GOAL SET!", Toast.LENGTH_LONG).show();
                checkIfGoalSet();

            }
        });


        //edit goal
        editButton.setEnabled(false);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //retrieve number of books read in current year
        Query query = FirebaseDatabase.getInstance().getReference("Read")
                .child(userName);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String date = ds.child("date").getValue(String.class);
                    //Log.d("BOOKS READ DATA", date);
                    if (date.contains(String.valueOf(Year.now().getValue()))) {
                        booksRead++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);


        return view;
    }


    private void configureChart() {

        //get books read in every month
        Query query = FirebaseDatabase.getInstance().getReference("Read")
                .child(userName);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (int i = 0; i < 12; i++) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String date = ds.child("date").getValue(String.class);
                        if (date.contains(String.valueOf(Year.now().getValue()))) {
                            String[] splitString = date.split("/");
                            if (splitString[1].equals(String.valueOf(i + 1))) {
                                yAxis[i]++;
                            }
                        }
                    }
                }

                config();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    private void config() {

        lineChart.setTouchEnabled(false);
        lineChart.setPinchZoom(false);

        //formatter for x axis labels
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisLabels[(int) value];
            }

        };

        //configure x axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawGridLinesBehindData(false);
        xAxis.setAxisMaximum(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(12,true);

        //configure y axis
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setLabelCount(7, true);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(6f);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawGridLinesBehindData(false);

        //configure right y axis
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        //configure dataset
        LineDataSet lineDataSet = new LineDataSet(lineChartData(),null);
        lineDataSet.setValueFormatter(new DefaultAxisValueFormatter(0));
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();


        //configure chart as a whole
        lineChart.setNoDataText("Data Not Available!");
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);


        Description description = lineChart.getDescription();
        description.setEnabled(true);
        String text = String.valueOf(Year.now().getValue());
        text = text + " In Books";
        description.setText(text);
        description.setTextSize(16f);
        description.setPosition(1000f, 50f);


        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(6);
        lineDataSet.setValueTextColor(Color.BLACK);

    }

    private ArrayList<Entry> lineChartData() {

        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            dataSet.add(new Entry(xAxis[i], yAxis[i]));
        }

        return dataSet;
    }

    private void setCurrentMonthRead() {

        //retrieve number of books read in current month
        Query query = FirebaseDatabase.getInstance().getReference("Read")
                .child(userName);

        //get current month
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        final int finalMonth = month;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String date = ds.child("date").getValue(String.class);
                    if (date.contains(String.valueOf(Year.now().getValue()))) {
                        String[] splitString = date.split("/");
                        if (splitString[1].equals(String.valueOf(finalMonth))) {
                            booksReadThisMonth++;
                        }
                    }
                }
                String text = String.valueOf(booksReadThisMonth);
                text = "Books Read This Month: " + text;
                monthText.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void checkIfGoalSet() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Goal")
                .child(String.valueOf(Year.now().getValue()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userName)) {

                    //goal set
                    //so make empty_title, input box and button gone
                    emptyText.setVisibility(View.GONE);
                    goal.setVisibility(View.GONE);
                    goalLayout.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);


                    //make other views visible
                    titleText.setVisibility(View.VISIBLE);
                    percentText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.VISIBLE);

                    //store goal
                    booksGoal = snapshot.child(userName).getValue(String.class);

                    //set title
                    String booksReadCast = String.valueOf(booksRead);
                    String title = "You've read " + booksReadCast + " of " + booksGoal + " Books!";
                    titleText.setText(title);

                    //set progress bar accordingly
                    setProgressBar();


                } else {
                    //goal not set
                    //make other views gone
                    titleText.setVisibility(View.GONE);
                    percentText.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setProgressBar() {

        //set percentage
        double read = (double) booksRead;
        double goal = Double.parseDouble(booksGoal);
        double percent = (read * 100.0) / goal;
        percent = Math.round(percent);
        int temp_percent = (int) percent;
        String text = String.valueOf(temp_percent);
        text = text + "%";
        percentText.setText(text);

        //set progress bar
        progressBar.setMax(Integer.parseInt(booksGoal));
        progressBar.setProgress(booksRead);

    }


}