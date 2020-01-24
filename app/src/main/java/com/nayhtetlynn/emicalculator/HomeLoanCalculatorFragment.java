package com.nayhtetlynn.emicalculator;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLoanCalculatorFragment extends Fragment {
    @BindView(R.id.loan_amount_edit_text)
    EditText LoanAmountEditText;
    @BindView(R.id.interest_rate_edit_text)
    EditText InterestRateEditText;
    @BindView(R.id.year_edit_text)
    EditText YearEditText;


    @BindView(R.id.ripple_calculate_home_loan)
    RippleView calculateRippleView;
    @BindView(R.id.monthly_payment_text_view)
    TextView MonthlyPaymentTextView;
    @BindView(R.id.total_interest_text_view)
    TextView TotalInterestTextView;
    @BindView(R.id.loan_amount_text_view)
    TextView LoanAmountTextView;
    @BindView(R.id.interest_rate_text_view)
    TextView InterestRateTextView;
    @BindView(R.id.year_text_view)
    TextView YearTextView;
    @BindView(R.id.total_interest_label)
    TextView totalInterestLabel;

    @BindView(R.id.chart)
    PieChart chart;

    @BindView(R.id.lblLoanPaymentOver)
    TextView lblLoanPaymentOver;
    @BindView(R.id.resTable)
    TableLayout emiTableLayout;

    public HomeLoanCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_loan_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        calculateRippleView.setOnRippleCompleteListener(rippleView -> {
            if (validate()) {
                calculateEMI();
            } else {
                Toast.makeText(getActivity(), "Please fill all field...", Toast.LENGTH_SHORT).show();
            }
        });

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setHoleRadius(0f);
        chart.setCenterTextRadiusPercent(100f);
        chart.setTransparentCircleRadius(0f);

        chart.setRotationEnabled(true);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);

        calculateEMI();
    }

    private void setData(float interest, float principal) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(interest, "Total Interest"));
        entries.add(new PieEntry(principal, "Total Principal"));
        PieDataSet dataSet = new PieDataSet(entries, "");

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        chart.animateXY(1000,1000);
        chart.invalidate();
    }

    @SuppressLint("SetTextI18n")
    private void calculateEMI() {
        float principle = Float.valueOf(String.valueOf(LoanAmountEditText.getText()));
        float interestRate = Float.valueOf(String.valueOf(InterestRateEditText.getText()));
        float year = Float.valueOf(String.valueOf(YearEditText.getText()));

        float monthlyInterest = (interestRate / (12 * 100));
        float totalMonth = year * 12;
        float emiAmount = Math.round((principle * monthlyInterest * Math.pow(1 + monthlyInterest, totalMonth)) / (Math.pow(1 + monthlyInterest, totalMonth) - 1));

        float totalInterest = (float) 0.0;

        emiTableLayout.removeAllViews();

        TableRow tableRow1=new TableRow(getActivity());
        TextView textView1=new TextView(getActivity());
        TextView textView2=new TextView(getActivity());
        TextView textView3=new TextView(getActivity());
        TextView textView4=new TextView(getActivity());

        textView1.setBackground(getResources().getDrawable(R.drawable.like_button_background));
        textView2.setBackground(getResources().getDrawable(R.drawable.like_button_background));
        textView3.setBackground(getResources().getDrawable(R.drawable.like_button_background));
        textView4.setBackground(getResources().getDrawable(R.drawable.like_button_background));
        textView1.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
        textView2.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
        textView3.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
        textView4.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView3.setGravity(Gravity.CENTER);
        textView4.setGravity(Gravity.CENTER);
        textView1.setTextColor(getResources().getColor(R.color.blue));
        textView2.setTextColor(getResources().getColor(R.color.blue));
        textView3.setTextColor(getResources().getColor(R.color.blue));
        textView4.setTextColor(getResources().getColor(R.color.blue));
        tableRow1.addView(textView1,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        tableRow1.addView(textView2,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        tableRow1.addView(textView3,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        tableRow1.addView(textView4,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        textView1.setText("Month No.");
        textView2.setText("Principal");
        textView3.setText("Interest");
        textView4.setText("Payment");
        emiTableLayout.addView(tableRow1);

        for (int i = 0; i < totalMonth; i++) {
            float diff = Math.round(emiAmount - (principle * (interestRate / 100 / 12)));
            float interest = Math.round(principle * interestRate / 100 / 12);
            totalInterest += interest;

            //Write Something...
            TableRow emiTableRow=new TableRow(getActivity());
            textView1=new TextView(getActivity());
            textView2=new TextView(getActivity());
            textView3=new TextView(getActivity());
            textView4=new TextView(getActivity());

            textView1.setBackground(getResources().getDrawable(R.drawable.like_button_background));
            textView2.setBackground(getResources().getDrawable(R.drawable.like_button_background));
            textView3.setBackground(getResources().getDrawable(R.drawable.like_button_background));
            textView4.setBackground(getResources().getDrawable(R.drawable.like_button_background));
            textView1.setText(String.valueOf(i + 1));
            textView2.setText(String.valueOf(principle));
            textView3.setText(String.valueOf(interest));
            textView4.setText(String.valueOf(emiAmount));
            textView1.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
            textView2.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
            textView3.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
            textView4.setPadding(getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor),getResources().getDimensionPixelOffset(R.dimen.spacing_minor));
            textView1.setGravity(Gravity.CENTER);
            textView2.setGravity(Gravity.CENTER);
            textView3.setGravity(Gravity.CENTER);
            textView4.setGravity(Gravity.CENTER);
            textView1.setTextColor(getResources().getColor(android.R.color.black));
            textView2.setTextColor(getResources().getColor(android.R.color.black));
            textView3.setTextColor(getResources().getColor(android.R.color.black));
            textView4.setTextColor(getResources().getColor(android.R.color.black));
            emiTableRow.addView(textView1,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
            emiTableRow.addView(textView2,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
            emiTableRow.addView(textView3,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
            emiTableRow.addView(textView4,new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
            emiTableLayout.addView(emiTableRow);

            principle = Math.round(principle - diff);
        }

        float totalPrinciple = Float.valueOf(String.valueOf(LoanAmountEditText.getText())) + totalInterest;

        float interestPercentage=Math.round(((totalInterest/totalPrinciple)*100)*10)/10.0f;
        float principlePercentage=100-interestPercentage;

        setData(interestPercentage, principlePercentage);

        MonthlyPaymentTextView.setText(String.valueOf(emiAmount));
        TotalInterestTextView.setText(String.valueOf(totalInterest));
        LoanAmountTextView.setText(String.valueOf(LoanAmountEditText.getText()));
        InterestRateTextView.setText(InterestRateEditText.getText() + " %");
        if (Double.valueOf(String.valueOf(YearEditText.getText())) == 1) {
            YearTextView.setText(YearEditText.getText() + " year");

            lblLoanPaymentOver.setText("Loan Payment Over "+YearEditText.getText()+" year");
            totalInterestLabel.setText(getString(R.string.total_interest)+" "+YearEditText.getText()+" year");
        } else {
            YearTextView.setText(YearEditText.getText() + " years");

            lblLoanPaymentOver.setText("Loan Payment Over "+YearEditText.getText()+" years");
            totalInterestLabel.setText(getString(R.string.total_interest)+" "+YearEditText.getText()+" years");
        }
    }

    private boolean validate() {
        boolean check = true;

        if (TextUtils.isEmpty(String.valueOf(LoanAmountEditText.getText()))) {
            check = false;
        }

        if (TextUtils.isEmpty(String.valueOf(InterestRateEditText.getText()))) {
            check = false;
        }

        if (TextUtils.isEmpty(String.valueOf(YearEditText.getText()))) {
            check = false;
        }
        return check;
    }
}
