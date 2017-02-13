package yuana.kodemetro.com.cargallery.features.detailcar;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public interface DetailView {

    void showMessage(String msg);

    void hideProgress();

    void showProgress();

    void displaydata(String response);
}
