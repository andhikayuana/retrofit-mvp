package yuana.kodemetro.com.cargallery.features.qrscanner;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/11/17
 */

public class ScannerFragment extends Fragment implements
        ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;

    public static ScannerFragment newInstance() {
        Bundle args = new Bundle();
        ScannerFragment fragment = new ScannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        zXingScannerView = new ZXingScannerView(getActivity());
        return zXingScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                zXingScannerView.resumeCameraPreview(ScannerFragment.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }
}
