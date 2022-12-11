package MuhammadSuhailiJSleepMN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Payment;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import MuhammadSuhailiJSleepMN.jsleep_android.request.BaseApiService;
import MuhammadSuhailiJSleepMN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView nameDetail, bedDetail, sizeDetail, priceDetail, addressDetail;
    CheckBox ac, refrigerator, wifi, bathtub, balcony, restaurant, pool, fitness;
    public static Room displayRoom;
    private DatePickerDialog datePickerDialog, datePickerDialog1;
    private Button fromDate, toDate, makeBook, cancelBook, confirmBook;
    public static String toDateStr = "0000-00-00";
    public static String fromDateStr = "0000-00-00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        initDatePicker();
        initDatePicker1();

        displayRoom = MainActivity.getRoom.get(MainActivity.roomPosition);

        CardView cardView = (CardView) findViewById(R.id.cardViewBooking);

        makeBook = findViewById(R.id.makeBookingButton);
        cancelBook = findViewById(R.id.cancelBookButton);
        confirmBook = findViewById(R.id.confirmBookButton);
        fromDate = findViewById(R.id.fromDateButton);
        toDate = findViewById(R.id.toDateButton);
        fromDate.setText(getTodaysDate());
        toDate.setText(getTodaysDate());

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

        makeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingLayout(1);
            }
        });

        cancelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { bookingLayout(2);}
        });

        confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

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

    /**
     * It defines a method called initDatePicker, which initializes a DatePickerDialog object and sets it up to be displayed to the user by fromDateButton.
     * The DatePickerDialog allows the user to select a date using a graphical calendar interface.
     * The initDatePicker method first defines a listener object that will be called when the user selects a date using the DatePickerDialog.
     * The listener is an instance of the OnDateSetListener interface, which has a single onDateSet method that is called when the user selects a date.
     * In this case, the onDateSet method sets the text of a fromDate object to a string representation of the selected date.
     * The initDatePicker method then gets the current date from the system using the Calendar class, and uses this date to initialize the DatePickerDialog object.
     * The DatePickerDialog is then set to use a dark theme and is configured to call the previously defined listener when a date is selected.
     */
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fromDate.setText(date);
                fromDateStr = year + "-" + month + "-" + day;

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    /**
     * The same as initDatePicker, but for toDateButton.
     */
    private void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                toDate.setText(date);
                toDateStr = year + "-" + month + "-" +day;

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog1 = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    /**
     * This code appears to be part of a Java program. It defines a method called getTodaysDate, which returns a string representation of the current date.
     * The getTodaysDate method first gets the current date using the Calendar class. It then extracts the year, month, and day of the month from the Calendar
     * object and uses these values to create a string representation of the date in the format "dd/mm/yyyy".
     */
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    /**
     * Defines a method called getMonthFormat, which takes an integer representing a month and returns a string representation of the month in uppercase abbreviated form
     * (e.g. "JAN" for January, "FEB" for February, etc.).
     * The getMonthFormat method uses a series of if statements to check the value of the input month parameter,
     * and returns the corresponding abbreviated month string. If the input month is not in the range 1-12, it returns "JAN" as a default value.
     */
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

        return "JAN"; //default value

    }

    /**
     * Showing date for forDateButton
     */
    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    /**
     * Showing date for toDateButton
     */
    public void openDatePicker1(View view){
        datePickerDialog1.show();
    }

    /**
     * Layout when booking button and cancel booking button is pressed
     */
    private void bookingLayout(int state){
        if(state == 1){
            CardView cv = (CardView) findViewById(R.id.cardViewBooking);
            cv.setVisibility(View.VISIBLE);
        }
        if(state == 2){
            CardView cv1 = (CardView) findViewById(R.id.cardViewBooking);
            cv1.setVisibility(View.INVISIBLE);
        }
    }

    protected Payment create(){
        System.out.println(MainActivity.loginToMain.renter.id);
        System.out.println(displayRoom.id);
        System.out.println(fromDateStr);
        System.out.println(toDateStr);

        mApiService.getPayment(MainActivity.loginToMain.id, MainActivity.loginToMain.renter.id, displayRoom.id, fromDateStr.toString(),
                toDateStr.toString()).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    MainActivity.paymentAccount = response.body();
                    System.out.println("Success");
                    Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                    startActivity(move);
                    Toast toast = Toast.makeText(getApplicationContext(), "Room Booking Successful", Toast.LENGTH_SHORT);
                    toast.show();
                    MainActivity.loginToMain.balance = MainActivity.loginToMain.balance - displayRoom.price.price;
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Room Booking Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}