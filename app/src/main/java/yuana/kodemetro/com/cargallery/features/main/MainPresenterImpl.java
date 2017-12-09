package yuana.kodemetro.com.cargallery.features.main;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuana.kodemetro.com.cargallery.models.Car;
import yuana.kodemetro.com.cargallery.networks.RestClient;
import yuana.kodemetro.com.cargallery.utils.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */

public class MainPresenterImpl implements MainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    private MainView mView;
    private Call<ResponseBody> mApi;

    public MainPresenterImpl(MainView view) {
        mView = view;
    }

    @Override
    public void destroy() {

        mView = null;

        if (mApi.isExecuted())
            mApi.cancel();
    }

    @Override
    public void getCars() {

        mView.showProgress();

        mApi = RestClient.getApi().getCars();

        mApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                mView.hideProgress();

                try {

                    if (response.isSuccessful()) {

                        String res = response.body().string();

                        JsonObject jRes = new Gson().fromJson(res, JsonObject.class);

                        Type listType = new TypeToken<List<Car>>() {}.getType();
                        List<Car> carList = new Gson()
                                .fromJson(jRes.get("data").getAsJsonArray(), listType);


                        mView.showData(carList);

                    } else {

                        String res = response.errorBody().string();

                        JsonObject jRes = Helper.getGsonInstance().fromJson(res, JsonObject.class);

                        mView.showMessage(jRes.get("msg").getAsString());
                    }
                } catch (Exception e) {

                    Log.d(TAG, e.getLocalizedMessage());

                    mView.hideProgress();

                    mView.showMessage(e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mView.hideProgress();

                mView.showMessage(t.getLocalizedMessage());

                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }
}
