package yuana.kodemetro.com.cargallery.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/10/17
 */

public class Helper {

    /**
     * return gsoon builder with non double number
     *
     * @return
     */
    public static Gson getGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                });
        Gson gson = gsonBuilder.create();
        return gson;
    }
}
