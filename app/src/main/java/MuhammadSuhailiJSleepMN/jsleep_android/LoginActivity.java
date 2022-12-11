package MuhammadSuhailiJSleepMN.jsleep_android;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username,password;
    Context mContext;
    public static Account backToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button loginButton = findViewById(R.id.loginJSleep);
        TextView register = findViewById(R.id.RegisterNow);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestAccount();
                Account loginAccount = requestLogin();
            }
        });
    }

    /**
     * This Java code appears to define a method called requestAccount that makes an API call to mApiService.getAccount with a parameter of 0.
     * If the API call is successful, the method prints the Account object to the console. If the call is unsuccessful,
     * the method prints the string "Testing" to the console. It looks like this method is intended to retrieve an Account object from the API,
     * but it does not return the Account object to the caller. Instead, it always returns null.
     */
    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("Testing");
            }
        });
        return null;
    }

    /**
     * This Java code appears to define a method called requestLogin that makes an API call to mApiService.getLogin,
     * using a username and password entered by the user. If the API call is successful, the method saves the
     * returned Account object to a MainActivity variable, creates a new Intent to move to a MainActivity, and displays a
     * toast message indicating that the login was successful. If the API call is unsuccessful, the method displays a toast message
     * indicating that the login failed. Like the previous method, requestLogin always returns null to the caller.
     */
    protected Account requestLogin(){
        mApiService.getLogin(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.loginToMain = response.body();
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext,"Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;

    }
}