package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {
    TextView nameDetail, bedDetail, sizeDetail, priceDetail, addressDetail;
    CheckBox ac, refrigerator, wifi, bathtub, balcony, restaurant, pool, fitness;
    public static Room displayRoom;
    private DatePickerDialog datePickerDialog;
    private Button fromDate, toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        initDatePicker();
        displayRoom = MainActivity.getRoom.get(MainActivity.roomPosition);

        fromDate = findViewById(R.id.fromDateButton);
        toDate = findViewById(R.id.toDateButton);
        fromDate.setText(getTodaysDate());
        toDate.setText(getTodaysDate());

        //Text view on the left
        nameDetail = findViewById(R.id.answerNameDetailRoom);
        bedDetail = findViewById(R.id.answerBedTypeDetailRoom);
        sizeDetail = findViewById(R.id.answerSizeDetailRoom);
        priceDetail = findViewById(R.id.answerPriceDetailRoom);
        addressDetail = findViewById(R.id.answerAddressDetailRoom);

        //Facilities checkbox
        ac = findViewById(R.id.detailAC);
        refrigerator = findViewById(R.id.detailRefrigerator);
        wifi = findViewById(R.id.detailWifi);
        bathtub = findViewById(R.id.detailBathtub);
        balcony = findViewById(R.id.detailBalcony);
        restaurant = findViewById(R.id.detailRestaurant);
        pool = findViewById(R.id.detailSwimmingPool);
        fitness = findViewById(R.id.detailFitnessCenter);

        //Text view on the right
        nameDetail.setText(displayRoom.name);
        priceDetail.setText(String.valueOf(displayRoom.price.price));
        sizeDetail.setText(String.valueOf(displayRoom.size));
        addressDetail.setText(displayRoom.address);
        bedDetail.setText(displayRoom.bedType.toString());

        //Checks the facilities if they are included in room creation
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fromDate.setText(date);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog= new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUNE";
        if(month == 7)
            return "JULY";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEPT";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";

    }

    public void openDatePicker(View view){
        datePickerDialog.show();

    }

}