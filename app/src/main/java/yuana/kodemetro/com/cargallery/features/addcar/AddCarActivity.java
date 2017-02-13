package yuana.kodemetro.com.cargallery.features.addcar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.models.Car;
import yuana.kodemetro.com.cargallery.utils.AlertBuilder;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */

public class AddCarActivity extends AppCompatActivity implements AddCarView {

    EditText etModel, etMake, etYear;
    Button btnSave;

    private AddCarPresenter mPresenter;
    private ProgressDialog mProgress;

    private void initView() {

        etModel = (EditText) findViewById(R.id.etModel);
        etMake = (EditText) findViewById(R.id.etMake);
        etYear = (EditText) findViewById(R.id.etYear);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveData();
            }
        });

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
    }

    private void initPresenter() {
        mPresenter = new AddCarPresenterImpl(this);
    }

    @Override
    public Car getIinputCar() {
        Car car = new Car();

        car.setModel(etModel.getText().toString());
        car.setMake(etMake.getText().toString());
        car.setYear(etYear.getText().toString());

        return car;
    }

    @Override
    public void setModelError() {
        setError(etModel);
    }

    @Override
    public void setErrorNull() {
        etModel.setError(null);
        etMake.setError(null);
        etYear.setError(null);
    }

    @Override
    public void setMakeError() {
        setError(etMake);
    }

    @Override
    public void setYearError() {
        setError(etYear);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }

    @Override
    public void saveDataSuccess() {
        AlertBuilder.create(this);
    }

    private void setError(EditText editText) {
        editText.setError("Error");
        editText.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_car);

        initView();

        initPresenter();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}
