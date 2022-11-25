package MuhammadSuhailiJSleepMN.jsleep_android.request;

import MuhammadSuhailiJSleepMN.jsleep_android.LoginActivity;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Account;
import MuhammadSuhailiJSleepMN.jsleep_android.model.Renter;
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
                                    @Query("name") String username,
                                    @Query("phone number") String phoneNumber,
                                    @Query("address") String address);
}
