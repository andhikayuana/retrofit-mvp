package yuana.kodemetro.com.cargallery.features.main;

import java.util.List;

import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */

public interface MainView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void showData(List<Car> carList);
}
