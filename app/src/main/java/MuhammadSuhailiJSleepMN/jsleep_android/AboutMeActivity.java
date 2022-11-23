package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;

public class AboutMeActivity extends AppCompatActivity {
    TextView name, email, balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        name = findViewById(R.id.answerName);
        email = findViewById(R.id.answerEmail);
        balance = findViewById(R.id.answerBalance);
        name.setText(MainActivity.loginToMain.name);
        email.setText(MainActivity.loginToMain.email);
        String balanceAboutMe = String.valueOf(MainActivity.loginToMain.balance);
        balance.setText(balanceAboutMe);
    }

}