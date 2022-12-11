package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Payment;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    public static Room displayRoom;
    TextView namePayment, bedPayment, sizePayment, pricePayment, addressPayment;
    TextView ansNamePayment, ansBedPayment, ansSizePayment, ansPricePayment, ansAddressPayment;
    CheckBox acPay, refPay, wifiPay, bathPay, balconyPay, restaurantPay, poolPay, fitnessPay;
    private Button accBtn, decBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        displayRoom = MainActivity.getRoom.get(MainActivity.roomPosition);

        namePayment = findViewById(R.id.namePaymentRoom);
        bedPayment = findViewById(R.id.bedTypePaymentRoom);
        sizePayment = findViewById(R.id.sizePaymentRoom);
        pricePayment = findViewById(R.id.pricePaymentRoom);
        addressPayment = findViewById(R.id.addressPaymentRoom);

        ansNamePayment = findViewById(R.id.answerNamePaymentRoom);
        ansBedPayment = findViewById(R.id.answerBedTypePaymentRoom);
        ansSizePayment = findViewById(R.id.answerSizePaymentRoom);
        ansPricePayment = findViewById(R.id.answerPricePaymentRoom);
        ansAddressPayment = findViewById(R.id.answerAddressPaymentRoom);

        acPay = findViewById(R.id.paymentAC);
        refPay = findViewById(R.id.paymentRefrigerator);
        wifiPay = findViewById(R.id.paymentWifi);
        bathPay = findViewById(R.id.paymentBathtub);
        balconyPay = findViewById(R.id.paymentBalcony);
        restaurantPay = findViewById(R.id.paymentRestaurant);
        poolPay = findViewById(R.id.paymentSwimmingPool);
        fitnessPay = findViewById(R.id.paymentFitnessCenter);

        ansNamePayment.setText(displayRoom.name);
        ansPricePayment.setText(String.valueOf(displayRoom.price.price));
        ansSizePayment.setText(String.valueOf(displayRoom.size));
        ansAddressPayment.setText(displayRoom.address);
        ansBedPayment.setText(displayRoom.bedType.toString());

        accBtn = findViewById(R.id.acceptPaymentButton);
        decBtn = findViewById(R.id.declinePaymentButton);

        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptPay();
                Toast.makeText(mContext, "Payment Successful", Toast.LENGTH_SHORT).show();
                System.out.println(MainActivity.loginToMain.balance);
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declinePay();
                Toast.makeText(mContext, "Payment Cancelled", Toast.LENGTH_SHORT).show();
                System.out.println(MainActivity.loginToMain.balance);
            }
        });

        for (int i = 0; i < displayRoom.facility.size(); i++) {
            if(displayRoom.facility.get(i).equals(Facility.AC)) {
                acPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.WiFi)) {
                wifiPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Bathtub)) {
                bathPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Balcony)) {
                balconyPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurantPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                poolPay.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitnessPay.setChecked(true);
            }
        }
    }

    protected Payment acceptPay(){
        mApiService.acceptPayment(MainActivity.paymentAccount.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
        return null;

    }

    protected Payment declinePay(){
        mApiService.cancelPayment(MainActivity.paymentAccount.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Intent move1 = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(move1);
                }

            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);

            }
        });
        return null;
    }
}