package yuana.kodemetro.com.cargallery.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public class AlertBuilder {

    public static void create(Context context, String msg, final AlertBuilderListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onClickOk();
                    }
                })
                .create()
                .show();
    }

    public static void create(final Context context) {
        create(context, "Sukses", new AlertBuilderListener() {
            @Override
            public void onClickOk() {
                ((AppCompatActivity) context).finish();
            }
        });
    }

    public interface AlertBuilderListener {
        void onClickOk();
    }
}
