package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import MuhammadSuhailiJSleepMN.jsleep_android.model.BedType;
import MuhammadSuhailiJSleepMN.jsleep_android.model.City;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    Spinner bedType;
    Spinner city;
    ArrayAdapter adapterCity, adapterBedType;
    EditText name, price, address, size;
    CheckBox checkAC, checkRefrigerator, checkWifi, checkBathtub, checkBalcony, checkRestaurant, checkSwimmingPool, checkFitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.nameCreateRoom);
        price = findViewById(R.id.priceCreateRoom);
        address = findViewById(R.id.addressCreateRoom);
        size = findViewById(R.id.sizeCreateRoom);

        checkAC = findViewById(R.id.detailAC);
        checkRefrigerator = findViewById(R.id.detailRefrigerator);
        checkWifi = findViewById(R.id.detailWifi);
        checkBathtub = findViewById(R.id.detailBathtub);
        checkBalcony = findViewById(R.id.detailBalcony);
        checkRestaurant = findViewById(R.id.detailRestaurant);
        checkSwimmingPool = findViewById(R.id.detailSwimmingPool);
        checkFitness = findViewById(R.id.detailFitnessCenter);

        Button createRoom = findViewById(R.id.createRoomButton);
        Button cancelRoom = findViewById(R.id.cancelRoomButton);
        bedType = findViewById(R.id.spinnerBedType);
        city = findViewById(R.id.spinnerCity);

        adapterBedType = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, BedType.values());
        adapterBedType.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        bedType.setAdapter(adapterBedType);
        adapterCity = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, City.values());
        adapterCity.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        city.setAdapter(adapterCity);

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRoom();
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

    }

    /**
     * It defines a method called requestRoom, which sends a request to create a new room using the API service mApiService.
     * The requestRoom method first gets a list of facilities that have been checked by the user using the getFacilities method.
     * It then calls the getRoom method on the mApiService object, passing in several parameters that are entered by the user, such as the room name, size, price, bed type, and city.
     * The getRoom method sends an API request to create a new room and returns a Call object that can be used to track the response to the request. The requestRoom method then registers a Callback object with the Call object, which will be called when the response to the API request is received. If the response is successful, a Toast message is displayed to the user indicating that the room was created successfully. If the response is not successful, a different Toast message is displayed indicating that the room creation failed.
     */
    protected Room requestRoom(){
        ArrayList<Facility> checkedFacilites = getFacilities();
        System.out.println(checkedFacilites);
        mApiService.getRoom(MainActivity.loginToMain.id,
                name.getText().toString(),
                Integer.parseInt(size.getText().toString()),
                Integer.parseInt(price.getText().toString()),
                checkedFacilites,
                BedType.valueOf(bedType.getSelectedItem().toString()),
                City.valueOf(city.getSelectedItem().toString()),
                address.getText().toString()
        ).enqueue(new Callback<Room>(){
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    MainActivity.listRoom = response.body();
                    Toast.makeText(mContext, "Room Created Successfully", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Room Creation Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * Adding checked facilities to the array list.
     */
    public ArrayList<Facility> getFacilities () {
        ArrayList<Facility> facility = new ArrayList<>();
        if (checkAC.isChecked())
            facility.add(Facility.AC);
        if (checkRefrigerator.isChecked())
            facility.add(Facility.Refrigerator);
        if (checkWifi.isChecked())
            facility.add(Facility.WiFi);
        if (checkBathtub.isChecked())
            facility.add(Facility.Bathtub);
        if (checkBalcony.isChecked())
            facility.add(Facility.Balcony);
        if (checkRestaurant.isChecked())
            facility.add(Facility.Restaurant);
        if (checkSwimmingPool.isChecked())
            facility.add(Facility.SwimmingPool);
        if (checkFitness.isChecked())
            facility.add(Facility.FitnessCenter);
        return facility;
    }
}