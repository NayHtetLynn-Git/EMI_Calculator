package com.nayhtetlynn.emicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeLoanCalculatorFragment homeLoanCalculatorFragment=new HomeLoanCalculatorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeLoanCalculatorFragment).commit();

    }
}
