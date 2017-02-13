package yuana.kodemetro.com.cargallery.networks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */

public interface Endpoints {

    // TODO: 2/9/17

    @GET("cars")
    Call<ResponseBody> getCars();

    @GET("cars/{id}")
    Call<ResponseBody> getCars(@Path("id") int id);

    @POST("cars")
    Call<ResponseBody> postCars(@Body Car car);
}
