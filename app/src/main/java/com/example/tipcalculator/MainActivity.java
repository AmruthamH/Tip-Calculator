package com.example.tipcalculator;

import static java.lang.Math.ceil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.Key;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText billWithTax,numPeople;
    private TextView tipAmt,totalWithTip,perPerson,overage;
    RadioGroup tp;
    RadioButton r12,r15,r18,r20;
    double percentage=0;
    Double overallTotal=0.0;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billWithTax= findViewById(R.id.editTextNumberDecimal);
        tipAmt= findViewById(R.id.editTextNumberDecimal2);
        totalWithTip= findViewById(R.id.editTextNumberDecimal3);
        numPeople= findViewById(R.id.editTextNumberDecimal4);
        perPerson= findViewById(R.id.editTextNumberDecimal5);
        overage= findViewById(R.id.editTextNumberDecimal13);
        tp= findViewById(R.id.radioGroup);
        r12= findViewById(R.id.radioButton);
        r15= findViewById(R.id.radioButton2);
        r18= findViewById(R.id.radioButton3);
        r20= findViewById(R.id.radioButton4);

    }

    public void clearClicked(View v){

        billWithTax.setText("");
        tipAmt.setText("");
        totalWithTip.setText("");
        numPeople.setText("");
        perPerson.setText("");
        overage.setText("");
        tp.clearCheck();

    }

    public void radioClicked(View v){
        String billToString = billWithTax.getText().toString();
        Double billToDouble= Double.parseDouble(billToString);


        if((percentage==0) && (billWithTax.getText().toString().isEmpty())){
            tp.clearCheck();

        }
        else if(v.getId() == R.id.radioButton)
            percentage = 0.12;
        else if(v.getId() == R.id.radioButton2)
            percentage = 0.15;
        else if(v.getId() == R.id.radioButton3)
            percentage = 0.18;
        else if(v.getId() == R.id.radioButton4)
            percentage = 0.20;
        tipCalculation(percentage);

    }



    public void tipCalculation(Double percentage){
        String billToString = billWithTax.getText().toString();
        Double billToDouble= Double.parseDouble(billToString);



        if(billToDouble == 0){
            tp.clearCheck();
            billWithTax.setText("");
            tipAmt.setText("");
            totalWithTip.setText("");

        }

        else{
            //Calculating tip
            String totalBill;
            Double tip_amount = percentage * billToDouble;
            billToString= ("$" + decimalFormat.format(tip_amount));
            tipAmt.setText(billToString);
            //Adding tip to the total bill
            overallTotal = billToDouble + tip_amount;
            totalBill="$"+decimalFormat.format(overallTotal);
            totalWithTip.setText(totalBill);

        }
    }

    public void perPersonSplit(View v){
        String billToString = billWithTax.getText().toString();
        Double billToDouble= Double.parseDouble(billToString);
        String noOfPeople= numPeople.getText().toString();
        int numP=Integer.parseInt(noOfPeople);

        //Number of people cant be zero
        if(((numP==0)) || (((numP>0) && (billToDouble == 0))))
        {
            tp.clearCheck();
            billWithTax.setText("");
            tipAmt.setText("");
            totalWithTip.setText("");
            numPeople.setText("");
            overage.setText("");
            perPerson.setText("");

        }
        else{
            //per person share is calculated
            Double perPersonShare = overallTotal/numP;//60/3=20
            perPersonShare=perPersonShare*10;//200
            perPersonShare=ceil(perPersonShare);//200
            perPersonShare=perPersonShare/10;//20
            perPerson.setText("$"+ decimalFormat.format(perPersonShare));//$20
            //Calculating overage
            perPersonShare=perPersonShare*numP;//20*3=60
            Double overageDouble = perPersonShare*100 - overallTotal*100;
            overageDouble = overageDouble / 100;
            overageDouble=Math.abs(overageDouble);
            overage.setText("$"+decimalFormat.format(overageDouble));


        }
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){


        outState.putString("BILL_WITH_TAX",billWithTax.getText().toString());
        outState.putString("TOTAL_WITH_TIP",totalWithTip.getText().toString());
        outState.putString("TIP_AMOUNT",tipAmt.getText().toString());
        outState.putString("PER_PERSON",perPerson.getText().toString());
        outState.putString("OVERAGE",overage.getText().toString());
        outState.putString("NUMBER_OF_PEOPLE",numPeople.getText().toString());
        outState.putDouble("TOTAL", overallTotal);
        outState.putBoolean("radioOption1", r12.isChecked());
        outState.putBoolean("radioOption2", r15.isChecked());
        outState.putBoolean("radioOption3", r18.isChecked());
        outState.putBoolean("radioOption14", r20.isChecked());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){


        billWithTax.setText(savedInstanceState.getString("BILL_WITH_TAX"));
        totalWithTip.setText(savedInstanceState.getString( "TOTAL_WITH_TIP"));
        tipAmt.setText(savedInstanceState.getString( "TIP_AMOUNT"));
        perPerson.setText(savedInstanceState.getString( "PER_PERSON"));
        overage.setText(savedInstanceState.getString( "OVERAGE"));
        numPeople.setText(savedInstanceState.getString( "NUMBER_OF_PEOPLE"));
        overallTotal = savedInstanceState.getDouble("TOTAL");
        r12.setChecked(savedInstanceState.getBoolean("radioOption1"));
        r15.setChecked(savedInstanceState.getBoolean("radioOption2"));
        r18.setChecked(savedInstanceState.getBoolean("radioOption3"));
        r20.setChecked(savedInstanceState.getBoolean("radioOption4"));
        super.onRestoreInstanceState(savedInstanceState);

    }
}