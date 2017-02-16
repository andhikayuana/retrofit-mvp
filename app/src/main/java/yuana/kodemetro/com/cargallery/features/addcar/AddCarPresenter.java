package yuana.kodemetro.com.cargallery.features.addcar;

import android.content.Context;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */
public interface AddCarPresenter {

    void destroy();

    void saveData();

    boolean validate();

    void attachDb(Context context);
}
