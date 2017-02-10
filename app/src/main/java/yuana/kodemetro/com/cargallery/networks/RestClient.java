package yuana.kodemetro.com.cargallery.networks;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */
public class RestClient {

    private static final String BASE_URL = "http://yuana.dev.php.or.id/slim/public/";

    public static Endpoints getApi() {
        OkHttpClient client = buildClient();
        Endpoints mApi = getRetrofit(BASE_URL, client).create(Endpoints.class);

        return mApi;
    }

    private static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        })
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(
                                chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
//                                        add another header here
                                .build()
                        );
                    }
                });

        return builder.build();
    }

    private static Retrofit getRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }
}
