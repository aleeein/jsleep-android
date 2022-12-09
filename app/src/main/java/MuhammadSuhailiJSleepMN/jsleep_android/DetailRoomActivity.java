package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {
    TextView nameDetail, bedDetail, sizeDetail, priceDetail, addressDetail;
    CheckBox ac, refrigerator, wifi, bathtub, balcony, restaurant, pool, fitness;

    public static Room displayRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        displayRoom = MainActivity.getRoom.get(MainActivity.roomPosition);
        nameDetail = findViewById(R.id.answerNameDetailRoom);
        bedDetail = findViewById(R.id.answerBedTypeDetailRoom);
        sizeDetail = findViewById(R.id.answerSizeDetailRoom);
        priceDetail = findViewById(R.id.answerPriceDetailRoom);
        addressDetail = findViewById(R.id.answerAddressDetailRoom);

        ac = findViewById(R.id.detailAC);
        refrigerator = findViewById(R.id.detailRefrigerator);
        wifi = findViewById(R.id.detailWifi);
        bathtub = findViewById(R.id.detailBathtub);
        balcony = findViewById(R.id.detailBalcony);
        restaurant = findViewById(R.id.detailRestaurant);
        pool = findViewById(R.id.detailSwimmingPool);
        fitness = findViewById(R.id.detailFitnessCenter);

        nameDetail.setText(displayRoom.name);
        priceDetail.setText(String.valueOf(displayRoom.price.price));
        sizeDetail.setText(String.valueOf(displayRoom.size));
        addressDetail.setText(displayRoom.address);
        bedDetail.setText(displayRoom.bedType.toString());

        for (int i = 0; i < displayRoom.facility.size(); i++) {
            if(displayRoom.facility.get(i).equals(Facility.AC)) {
                ac.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refrigerator.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Bathtub)) {
                bathtub.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if(displayRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitness.setChecked(true);
            }
        }
    }
}