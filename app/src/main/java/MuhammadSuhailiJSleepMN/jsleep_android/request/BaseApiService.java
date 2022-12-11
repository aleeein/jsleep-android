package MuhammadSuhailiJSleepMN.jsleep_android.request;

import java.util.ArrayList;
import java.util.List;

import MuhammadSuhailiJSleepMN.jsleep_android.LoginActivity;
import MuhammadSuhailiJSleepMN.jsleep_android.R;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;
import MuhammadSuhailiJSleepMN.jsleep_android.model.BedType;
import MuhammadSuhailiJSleepMN.jsleep_android.model.City;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Facility;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Payment;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Renter;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("/account/login")
    Call<Account> getLogin (@Query("email") String username, @Query("password") String password);

    @POST("/account/register")
    Call<Account> getRegister (@Query("name") String username, @Query("email") String email, @Query("password") String password);

    @POST("/account/{id}/registerRenter")
    Call<Renter> getRegisterRenter (@Path("id") int id,
                                    @Query("username") String username,
                                    @Query("address") String address,
                                    @Query("phoneNumber") String phoneNumber);

    @GET("/room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page,
                                 @Query("pageSize") int pageSize);

    @POST("/account/{id}/topUp")
    Call<Boolean> getTopUp(@Path("id") int id,
                        @Query("balance") double balance);

    @POST("/room/create")
    Call<Room> getRoom (@Query("accountId") int accountId,
                       @Query("name") String name,
                       @Query("size") int size,
                       @Query("price") int price,
                       @Query("facility") ArrayList<Facility> facility,
                       @Query("bedType") BedType bedType,
                       @Query("city") City city,
                       @Query("address") String address);

    @POST("/payment/create")
    Call<Payment> getPayment (@Query("buyerId") int buyerId,
                              @Query("renterId") int renterId,
                              @Query("roomId") int roomId,
                              @Query("from") String from,
                              @Query("to") String to);

    @POST("/payment/{id}/accept")
    Call<Boolean> acceptPayment(@Path("id") int id);

    @POST("/payment/{id}/cancel")
    Call<Boolean> cancelPayment(@Path("id") int id);
}

