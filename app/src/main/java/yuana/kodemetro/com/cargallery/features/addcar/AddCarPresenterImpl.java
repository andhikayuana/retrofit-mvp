package yuana.kodemetro.com.cargallery.features.addcar;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuana.kodemetro.com.cargallery.models.Car;
import yuana.kodemetro.com.cargallery.networks.RestClient;
import yuana.kodemetro.com.cargallery.utils.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */
public class AddCarPresenterImpl implements AddCarPresenter {

    private static final String TAG = AddCarPresenterImpl.class.getSimpleName();

    private AddCarView mView;
    private Call<ResponseBody> mApi;

    public AddCarPresenterImpl(AddCarView view) {
        mView = view;
    }

    @Override
    public void destroy() {

        mView = null;

        if (mApi.isExecuted())
            mApi.cancel();
    }

    @Override
    public void saveData() {

        if (validate()) {

            mView.showProgress();

            mApi = RestClient.getApi().postCars(getInput());

            mApi.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    mView.hideProgress();

                    try {

                        if (response.isSuccessful()) {

                            mView.saveDataSuccess();

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
                }
            });
        }

    }

    @Override
    public boolean validate() {

        mView.setErrorNull();

        boolean valid = true;

        if (TextUtils.isEmpty(getInput().getYear())) {
            mView.setYearError();
            valid = false;
        }

        if (TextUtils.isEmpty(getInput().getMake())) {
            mView.setMakeError();
            valid = false;
        }

        if (TextUtils.isEmpty(getInput().getModel())) {
            mView.setModelError();
            valid = false;
        }


        return valid;
    }

    @Override
    public Car getInput() {

        Car car = new Car();

        car.setModel(mView.getModel());
        car.setMake(mView.getMake());
        car.setYear(mView.getYear());

        return car;
    }
}
