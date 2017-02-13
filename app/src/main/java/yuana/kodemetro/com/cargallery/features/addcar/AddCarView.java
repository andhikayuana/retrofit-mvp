package yuana.kodemetro.com.cargallery.features.addcar;

import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */
public interface AddCarView {

    Car getIinputCar();

    void setModelError();

    void setErrorNull();

    void setMakeError();

    void setYearError();

    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void saveDataSuccess();
}
