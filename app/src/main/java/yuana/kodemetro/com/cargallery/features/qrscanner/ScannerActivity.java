package yuana.kodemetro.com.cargallery.features.qrscanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/22/17
 */

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = ScannerActivity.class.getSimpleName();

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.d(TAG, "-----------------------------------");
        Log.d(TAG, result.getText());
        Log.d(TAG, result.getBarcodeFormat().toString());
        Log.d(TAG, "-----------------------------------");
//        finish();
        mScannerView.resumeCameraPreview(this);
    }
}
