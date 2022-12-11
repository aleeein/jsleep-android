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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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
import MuhammadSuhailiJSleepMN.jsleep_android.model.Payment;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText pageNum;
    Button prevPage, nextPage, goPage;
    ListView listView;
    MenuItem addBut;
    private int currentPg = 1;
    private final int pSize = 3;
    public static int roomPosition;
    public static Payment paymentAccount;
    public static Account loginToMain;
    public static Room listRoom;
    public static List<Room> getRoom;
    public static ArrayAdapter<String> adapt;
    public static ArrayAdapter<String> adapter;
    static ArrayAdapter<Room> AdaptlistView;

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
        listView = findViewById(R.id.MainListView);

        getAllRoom(currentPg);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                roomPosition = i;
                System.out.println(currentPg);
                System.out.println(roomPosition);
                //System.out.println(getRoom.get(roomPosition));
                if(getRoom != null){
                    Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
                    startActivity(move);
                }
            }
        });

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

    /**
     *The onCreateOptionsMenu() method is part of the Android framework and is called when the options menu is first created.
     * In this specific implementation, it inflates the options menu from a resource file and sets the visibility of one of the
     * menu items based on the value of a loginToMain.renter variable. It then returns true to indicate that the menu was successfully created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_resource, menu);
        addBut = menu.findItem(R.id.add_box_button);
        addBut.setVisible(MainActivity.loginToMain.renter != null);
        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.add_box_button:
                Intent intent1 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    /**
     * The getAllRoom() method is an HTTP request that fetches a page of rooms from an API service.
     * It takes a page number as input and makes an asynchronous call to the API using enqueue(),
     * which takes a Callback object as an argument. In the onResponse() method of the Callback,
     * the method checks if the response was successful and, if so, processes the response body to
     * extract a list of rooms and display them in a ListView. In the onFailure() method, the method
     * simply prints the page number and the throwable object to the console. The method returns null in both cases.
     *
     */
    protected Room getAllRoom(int page){
        mApiService.getAllRoom(page-1, pSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    getRoom = (ArrayList<Room>)response.body();
                    System.out.println(getRoom.toString());
                    ArrayList<String> names = new ArrayList<>();

                    for (Room r : getRoom) {
                        names.add(r.name);
                    }
                    if(names.isEmpty()) {
                        Toast.makeText(mContext, "All available rooms are already displayed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, names);
                    ListView listView = (ListView) findViewById(R.id.MainListView);
                    listView.setAdapter(adapter);
                    pageNum.setText(String.valueOf(page));
                    currentPg = page;
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                System.out.println(page);
                System.out.println(t);
            }
        });
        return null;
    }

}