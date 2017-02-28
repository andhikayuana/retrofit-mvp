package yuana.kodemetro.com.cargallery.features.qrscanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import yuana.kodemetro.com.cargallery.R;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/22/17
 */
public class Scanner2Activity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button btnKanan, btnKiri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner2);

        initView();

        if (savedInstanceState != null) {
            init(barcodeScannerView, getIntent(), savedInstanceState);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            init(barcodeScannerView, getIntent(), null);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    private void init(DecoratedBarcodeView barcodeScannerView, Intent intent, Bundle savedInstanceState) {
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(intent, savedInstanceState);
        capture.decode();
    }

    private void initView() {
        btnKanan = (Button) findViewById(R.id.btnKanan);
        btnKiri = (Button) findViewById(R.id.btnKiri);
        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);

        btnKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Scanner2Activity.this, "kanan", Toast.LENGTH_SHORT).show();
            }
        });

        btnKiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Scanner2Activity.this, "kiri", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    init(barcodeScannerView, getIntent(), null);
                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        }
    }
}
