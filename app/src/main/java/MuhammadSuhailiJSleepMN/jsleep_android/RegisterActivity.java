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
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username,email,password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        Button regButton = findViewById(R.id.registerButton);

        username = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);

        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Account account = requestRegister();
            }
        });
    }

    /**
     * This method is defining an requestRegister method in Java. It appears to be making an API call to mApiService.getRegister with some provided username,
     * email, and password arguments. If the API call is successful, it starts a new LoginActivity and displays a toast message. If the call is not successful,
     * it displays a different toast message.
     * The requestRegister method is marked with the protected access modifier, which means it can be accessed by classes in the same package and by subclasses
     * of the class where it is defined.
     * The method returns null at the end, but it appears that it doesn't need to return anything because it is only starting a new activity and displaying messages.
     * It would be better to either remove the return null statement or to change the return type of the method to void to indicate that it does not return anything.
     * It's also worth noting that this method is using an asynchronous call to the API using a Callback object. This means that the method will return immediately,
     * and the code in the onResponse and onFailure methods of the Callback will be executed at some point in the future when the API call completes.
     * This is important to keep in mind if this code is being used in a context where the result of the API call is needed right away.
     */
    protected Account requestRegister(){
        mApiService.getRegister(username.getText().toString(),email.getText().toString(),password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    LoginActivity.backToLogin = response.body();
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                    Toast.makeText(getApplicationContext(), "Account Registered Successfully", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext,"Account is already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}