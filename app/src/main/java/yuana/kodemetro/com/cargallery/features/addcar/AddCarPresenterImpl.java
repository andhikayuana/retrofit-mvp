package yuana.kodemetro.com.cargallery.features.addcar;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuana.kodemetro.com.cargallery.db.DbHelper;
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
    private DbHelper db;

    public AddCarPresenterImpl(AddCarView view) {
        mView = view;
    }

    @Override
    public void destroy() {

        mView = null;

        if (mApi != null && mApi.isExecuted())
            mApi.cancel();
    }

    @Override
    public void saveData() {

        if (validate()) {

            mView.showProgress();

            mApi = RestClient.getApi().postCars(mView.getIinputCar());

            mApi.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    mView.hideProgress();

                    try {

                        if (response.isSuccessful()) {

                            db.save(mView.getIinputCar());

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

        Car car = mView.getIinputCar();

        if (TextUtils.isEmpty(car.getYear())) {
            mView.setYearError();
            valid = false;
        }

        if (TextUtils.isEmpty(car.getMake())) {
            mView.setMakeError();
            valid = false;
        }

        if (TextUtils.isEmpty(car.getModel())) {
            mView.setModelError();
            valid = false;
        }


        return valid;
    }

    @Override
    public void attachDb(Context context) {
        db = new DbHelper(context);
    }
}
