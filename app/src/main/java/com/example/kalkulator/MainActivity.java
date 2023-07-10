package com.example.kalkulator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView pokazTv, wynikTv;
    MaterialButton nawiasOtwierający, nawiasZamykajacy;
    MaterialButton pDzielenia, pMnozenia, pDodawania, pOdejmowania, pRownania;
    MaterialButton p0, p1, p2, p3, p4, p5, p6, p7, p8, p9;
    MaterialButton pWyczysc, pKropka, pPotegowania;
    private String finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokazTv = findViewById(R.id.result_tv);
        wynikTv = findViewById(R.id.solution_tv);

        assignId(nawiasOtwierający, R.id.nawiasOtwierający);
        assignId(nawiasZamykajacy, R.id.nawiasZamykajacy);
        assignId(pDzielenia, R.id.pDzielenia);
        assignId(pMnozenia, R.id.pMnozenia);
        assignId(pDodawania, R.id.pDodawania);
        assignId(pOdejmowania, R.id.pOdejmowania);
        assignId(pRownania, R.id.pRownania);
        assignId(p0, R.id.p0);
        assignId(p1, R.id.p1);
        assignId(p2, R.id.p2);
        assignId(p3, R.id.p3);
        assignId(p4, R.id.p4);
        assignId(p5, R.id.p5);
        assignId(p6, R.id.p6);
        assignId(p7, R.id.p7);
        assignId(p8, R.id.p8);
        assignId(p9, R.id.p9);
        assignId(pWyczysc, R.id.pWyczysc);
        assignId(pKropka, R.id.pKropka);
        assignId(pPotegowania, R.id.pPotegowania);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = wynikTv.getText().toString();

        if (buttonText.equals("AC")) {
            wynikTv.setText("");
            pokazTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            wynikTv.setText(pokazTv.getText());
            return;
        }
        if (buttonText.equals("^")) {
            dataToCalculate = dataToCalculate + "^";
            wynikTv.setText(dataToCalculate);
            return;
        }

        dataToCalculate = dataToCalculate + buttonText;
        wynikTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);


        if (!finalResult.equals("Err")) {
            pokazTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            if (data.contains("^")) {
                String[] parts = data.split("\\^");
                double base = Double.parseDouble(parts[0]);
                double exponent = Double.parseDouble(parts[1]);
                double result = Math.pow(base, exponent);
                finalResult = String.valueOf(result);
            } else {
                Context context = Context.enter();
                context.setOptimizationLevel(-1);
                Scriptable scriptable = context.initStandardObjects();
                finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            }

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}



