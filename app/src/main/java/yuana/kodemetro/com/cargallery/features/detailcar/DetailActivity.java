package yuana.kodemetro.com.cargallery.features.detailcar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public class DetailActivity extends AppCompatActivity implements DetailView {

    TextView tvDetailCar;
    private DetailPresenter mPresenter;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_car);

        initView();

        initPresenter();

        initData();
    }

    private void initData() {

        Car car = (Car) getIntent().getSerializableExtra("DATA");

        mPresenter.getCarDetail(car.getId());
    }

    private void initPresenter() {
        mPresenter = new DetailPresenter(this);
    }

    private void initView() {
        tvDetailCar = (TextView) findViewById(R.id.tvDetailCar);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void displaydata(String response) {
        tvDetailCar.setText(response);
    }
}
