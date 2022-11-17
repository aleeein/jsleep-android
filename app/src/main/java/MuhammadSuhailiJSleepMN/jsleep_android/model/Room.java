package MuhammadSuhailiJSleepMN.jsleep_android.model;

import java.util.Date;
import java.util.ArrayList;

public class Room extends Serializable{
    public int accountId;
    public String name;
    public int size;
    public Price price;
    public Facility facility;
    public String address;
    public BedType bedType;
    public City city;
    public ArrayList<Date> booked = new ArrayList<>();
}
