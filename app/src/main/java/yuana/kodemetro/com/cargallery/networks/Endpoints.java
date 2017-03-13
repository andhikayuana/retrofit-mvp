package yuana.kodemetro.com.cargallery.networks;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import yuana.kodemetro.com.cargallery.models.Car;
import yuana.kodemetro.com.cargallery.models.DirectionResults;

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

    @GET("maps/api/directions/{output}")
    Call<DirectionResults> getDirections(
            @Path("output") String output,
            @QueryMap Map<String, String> queryParams
    );
}
