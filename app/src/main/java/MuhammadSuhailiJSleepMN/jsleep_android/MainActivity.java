package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText pageNum;
    Button prevPage, nextPage, goPage;
    Context mContext;
    int currentPg = 1;
    public static Account loginToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        setContentView(R.layout.activity_main);
        pageNum = findViewById(R.id.pageText);
        prevPage = findViewById(R.id.prevButton);
        nextPage = findViewById(R.id.nextButton);
        goPage = findViewById(R.id.goButton);
        getAllRoom(currentPg);
        prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPg - 1 < 1) {
                    getAllRoom(currentPg);
                } else {
                    getAllRoom(currentPg - 1);
                }

            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getAllRoom(currentPg+1);
            }
        });
        goPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inpPage = Integer.parseInt(pageNum.getText().toString());
                if(inpPage < 1)
                    getAllRoom(currentPg);
                getAllRoom(inpPage);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resource, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
        }
        return true;
    }
    protected Room getAllRoom(int page){
        mApiService.getAllRoom(page-1, 4).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    ArrayList<Room> room;
                    room = (ArrayList<Room>)response.body();
                    System.out.println(room.toString());
                    ArrayList<String> names = new ArrayList<>();

                    for (Room r : room) {
                        names.add(r.name);
                    }
                    if(names.isEmpty()) {
                        Toast.makeText(mContext, "All available rooms are already displayed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, names);
                    ListView listView = (ListView) findViewById(R.id.MainListView);
                    listView.setAdapter(adapter);
                    pageNum.setText(String.valueOf(page));
                    currentPg = page;
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext,"no Account id=0", Toast.LENGTH_SHORT).show();
                System.out.println("Testing");
            }
        });
        return null;
    }
}