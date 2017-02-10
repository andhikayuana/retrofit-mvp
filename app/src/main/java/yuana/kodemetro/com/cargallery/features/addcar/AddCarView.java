package yuana.kodemetro.com.cargallery.features.addcar;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */
public interface AddCarView {

    String getModel();

    String getMake();

    String getYear();

    void setModelError();

    void setErrorNull();

    void setMakeError();

    void setYearError();

    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void saveDataSuccess();
}
