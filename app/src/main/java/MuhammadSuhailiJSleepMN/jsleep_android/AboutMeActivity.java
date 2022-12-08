package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Renter;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView name, email, balance;
    EditText registerNameRenter, registerAddressRenter, registerPhoneNumberRenter;
    TextView nameRenter, addressRenter, phoneNumberRenter, ansRenterName, ansRenterAddress, ansRenterPhoneNumber;
    Button registRent, cancelRent, bigRegistRent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.answerName);
        email = findViewById(R.id.answerEmail);
        balance = findViewById(R.id.answerBalance);
        bigRegistRent = findViewById(R.id.registerRenterButton);
        registerNameRenter = findViewById(R.id.registRenterName);
        registerAddressRenter = findViewById(R.id.registRenterAddress);
        registerPhoneNumberRenter = findViewById(R.id.registRenterPhoneNumber);
        registRent = findViewById(R.id.registerAboutMeButton);
        cancelRent = findViewById(R.id.cancelAboutMeButton);
        nameRenter = findViewById(R.id.renterName);
        addressRenter = findViewById(R.id.renterAddress);
        phoneNumberRenter = findViewById(R.id.renterPhoneNumber);
        ansRenterName = findViewById(R.id.answerRenterName);
        ansRenterAddress = findViewById(R.id.answerRenterAddress);
        ansRenterPhoneNumber = findViewById(R.id.answerPhoneNumberRenter);
        name.setText(MainActivity.loginToMain.name);
        email.setText(MainActivity.loginToMain.email);
        String balanceAboutMe = String.valueOf(MainActivity.loginToMain.balance);
        balance.setText(balanceAboutMe);
        if (MainActivity.loginToMain.renter == null) {
            setLayout(1);
        } else {
            ansRenterName.setText(MainActivity.loginToMain.renter.username);
            ansRenterAddress.setText(MainActivity.loginToMain.renter.address);
            ansRenterPhoneNumber.setText(MainActivity.loginToMain.renter.phoneNumber);
            setLayout(3);
        }
        bigRegistRent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setLayout(2);
            }
        });
        registRent.setOnClickListener(view -> {
            requestRenter();
            setLayout(3);
        });
        cancelRent.setOnClickListener(view -> {
            setLayout(1);
        });
    }
    protected Renter requestRenter(){
        mApiService.getRegisterRenter(MainActivity.loginToMain.id, registerNameRenter.getText().toString(),
                registerAddressRenter.getText().toString(),
                registerPhoneNumberRenter.getText().toString()
        ).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    MainActivity.loginToMain.renter = response.body();
                    Toast.makeText(mContext, "Rent Successful", Toast.LENGTH_SHORT).show();
                    System.out.println(response.body());
                    //kanan
                    ansRenterName.setText(MainActivity.loginToMain.renter.username);
                    ansRenterAddress.setText(MainActivity.loginToMain.renter.address);
                    ansRenterPhoneNumber.setText(MainActivity.loginToMain.renter.phoneNumber);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("id="+MainActivity.loginToMain.id);
                Toast.makeText(mContext, "Rent Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    private void setLayout(int state){
        if(state == 1){
            bigRegistRent.setVisibility(View.VISIBLE);
            registerNameRenter.setVisibility(View.INVISIBLE);
            registerAddressRenter.setVisibility(View.INVISIBLE);
            registerPhoneNumberRenter.setVisibility(View.INVISIBLE);
            nameRenter.setVisibility(View.INVISIBLE);
            addressRenter.setVisibility(View.INVISIBLE);
            phoneNumberRenter.setVisibility(View.INVISIBLE);
            ansRenterName.setVisibility(View.INVISIBLE);
            ansRenterAddress.setVisibility(View.INVISIBLE);
            ansRenterPhoneNumber.setVisibility(View.INVISIBLE);
            registRent.setVisibility(View.INVISIBLE);
            cancelRent.setVisibility(View.INVISIBLE);
        }
        if(state == 2){
            bigRegistRent.setVisibility(View.INVISIBLE);
            registerNameRenter.setVisibility(View.VISIBLE);
            registerAddressRenter.setVisibility(View.VISIBLE);
            registerPhoneNumberRenter.setVisibility(View.VISIBLE);
            nameRenter.setVisibility(View.INVISIBLE);
            addressRenter.setVisibility(View.INVISIBLE);
            phoneNumberRenter.setVisibility(View.INVISIBLE);
            ansRenterName.setVisibility(View.INVISIBLE);
            ansRenterAddress.setVisibility(View.INVISIBLE);
            ansRenterPhoneNumber.setVisibility(View.INVISIBLE);
            registRent.setVisibility(View.VISIBLE);
            cancelRent.setVisibility(View.VISIBLE);
        }
        if(state == 3){
            bigRegistRent.setVisibility(View.INVISIBLE);
            registerNameRenter.setVisibility(View.INVISIBLE);
            registerAddressRenter.setVisibility(View.INVISIBLE);
            registerPhoneNumberRenter.setVisibility(View.INVISIBLE);
            nameRenter.setVisibility(View.VISIBLE);
            addressRenter.setVisibility(View.VISIBLE);
            phoneNumberRenter.setVisibility(View.VISIBLE);
            ansRenterName.setVisibility(View.VISIBLE);
            ansRenterAddress.setVisibility(View.VISIBLE);
            ansRenterPhoneNumber.setVisibility(View.VISIBLE);
            registRent.setVisibility(View.INVISIBLE);
            cancelRent.setVisibility(View.INVISIBLE);
        }
    }
}