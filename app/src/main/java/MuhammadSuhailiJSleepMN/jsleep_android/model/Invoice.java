package MuhammadSuhailiJSleepMN.jsleep_android.model;

public class Invoice extends Serializable {
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS;
    }

    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD;
    }
}
