package yuana.kodemetro.com.cargallery.features.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.features.addcar.AddCarActivity;
import yuana.kodemetro.com.cargallery.features.maps.MapsActivity;
import yuana.kodemetro.com.cargallery.features.qrscanner.Scanner2Activity;
import yuana.kodemetro.com.cargallery.features.qrscanner.ScannerActivity;
import yuana.kodemetro.com.cargallery.features.qrscanner.ScannerFragmentActivity;
import yuana.kodemetro.com.cargallery.models.Car;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView rvCars;
    Toolbar toolbar;
    FloatingActionButton fab;

    private CarsAdapter mAdapter;
    private MainPresenter mPresenter;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        initPresenter();

        initData();

    }

    @Override
    protected void onResume() {
        mPresenter.getCars();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    private void initPresenter() {
        mPresenter = new MainPresenterImpl(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });

        rvCars = (RecyclerView) findViewById(R.id.rvCars);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
    }

    private void initData() {
        mPresenter.getCars();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_scanner) {

            startActivity(new Intent(this, ScannerActivity.class));
        } else if (id == R.id.menu_scanner2) {

            new IntentIntegrator(this)
                    .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                    .setOrientationLocked(false)
                    .setCaptureActivity(Scanner2Activity.class)
                    .setPrompt("")
                    .initiateScan();

        } else if (id == R.id.menu_maps) {

            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        } else if (id == R.id.menu_scanner_fragment) {

            startActivity(new Intent(MainActivity.this, ScannerFragmentActivity.class));
        }

        return true;
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
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(List<Car> carList) {

        CarsAdapter carsAdapter = new CarsAdapter(this, carList);

        rvCars.setLayoutManager(new LinearLayoutManager(this));
        rvCars.setAdapter(carsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
