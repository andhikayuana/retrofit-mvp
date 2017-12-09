package yuana.kodemetro.com.cargallery.features.qrscanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import yuana.kodemetro.com.cargallery.R;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/11/17
 */

public class ScannerFragmentActivity extends AppCompatActivity {

    private Button btnKanan;
    private Button btnKiri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner_fragment);

        initView();

        initFragment();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnKanan = (Button) findViewById(R.id.btnKanan);
        btnKiri = (Button) findViewById(R.id.btnKiri);

        btnKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScannerFragmentActivity.this, "Halo btn Kanan", Toast.LENGTH_SHORT).show();
            }
        });

        btnKiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScannerFragment fragmentById = (ScannerFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frContainer);

                fragmentById.clickFromFragment();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frContainer, ScannerFragment.newInstance())
                .commit();
    }
}
