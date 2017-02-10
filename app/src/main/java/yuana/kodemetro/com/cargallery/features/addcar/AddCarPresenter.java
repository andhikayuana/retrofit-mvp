package yuana.kodemetro.com.cargallery.features.addcar;

import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */
public interface AddCarPresenter {

    void destroy();

    void saveData();

    boolean validate();

    Car getInput();
}
