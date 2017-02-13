package yuana.kodemetro.com.cargallery.features.detailcar;

import android.util.Log;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuana.kodemetro.com.cargallery.networks.RestClient;
import yuana.kodemetro.com.cargallery.utils.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public class DetailPresenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();

    private DetailView mView;
    private Call<ResponseBody> mApi;

    public DetailPresenter(DetailView mView) {
        this.mView = mView;
    }


    public void getCarDetail(Integer id) {

        mView.showProgress();

        mApi = RestClient.getApi().getCars(id);

        mApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    mView.hideProgress();

                    if (response.isSuccessful()) {

                        mView.displaydata(response.body().string());

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

    public void destroy() {
        mView = null;

        if (mApi != null && mApi.isExecuted())
            mApi.cancel();
    }
}
