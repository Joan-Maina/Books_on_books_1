package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.w3c.dom.Text;

import java.math.BigDecimal;

public class Station_info extends AppCompatActivity {

    TextView marker,space, bikes, about;
    Button book, cancel;
    //TextView marker;
    private int PAYPAL_REQ_CODE = 12;
    private  static PayPalConfiguration paypalConfig =new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalID.PAYPAL_CLIENT_ID);
    //DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);

        marker = findViewById(R.id.tryiing);
        space = findViewById(R.id.no_space);
        about = findViewById(R.id.about_station);
        bikes = findViewById(R.id.no_bikes);
        book = findViewById(R.id.book);
        cancel = findViewById(R.id.cancel);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DatabaseReference reference = database.getReference("message");
        //DatabaseReference reference1 = database.getReference("bikes");

        String name = "";
        String number = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            number = extras.getString("number");
        }
        marker.setText(name);
        about.setText(number);
        //get data from maps activity
        //String title = getIntent().getStringExtra("title");
        //marker.setText(title);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Station_info.this, homepage.class);
                startActivity(i);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaypalPaymentMethod();
            }
        });
    }

        private void PaypalPaymentMethod() {
            PayPalPayment payment = new PayPalPayment(new BigDecimal(1),
                    "USD",
                    "Test Payment", PayPalPayment.PAYMENT_INTENT_SALE
            );
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            startActivityForResult(intent, PAYPAL_REQ_CODE);

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PAYPAL_REQ_CODE){
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "Payment Made Succesfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(this, "Payment Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onDestroy() {
            stopService(new Intent(this, PayPalService.class));
            super.onDestroy();
        }
    }
       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot .getValue(String.class);
                bikes.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Station_info.this,"database error",Toast.LENGTH_SHORT).show();
            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n = snapshot .getValue(String.class);
                space.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/


