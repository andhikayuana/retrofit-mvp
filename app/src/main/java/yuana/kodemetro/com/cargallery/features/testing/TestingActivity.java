package yuana.kodemetro.com.cargallery.features.testing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.features.testing.edit_text_otp.EditTextOtp;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/10/17
 */

public class TestingActivity extends AppCompatActivity {

    private static final String CLIENT_NAME = "EPGCLIENT-ANDROID-STB";
    private WebView webView;
    private EditTextOtp etOtp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        initVIew();

        initData();
    }

    private void initVIew() {
        webView = (WebView) findViewById(R.id.webView);
        etOtp = (EditTextOtp) findViewById(R.id.etOtp);

        etOtp.setOtp("9933");

        findViewById(R.id.btnHaloOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestingActivity.this, etOtp.getOtp(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
//        String html = "<p>Tekan &quot<font color=\"blue\">OK</font>&quot untuk memesan " +
//                "atau <img src='ic_back.png' /> untuk membatalkan</p>";

//        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);

        webView.loadUrl("file:///android_asset/buy-vicall-prompt.html");

        getData();
    }

    private void getData() {
        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                byte[] result = null;
                String str = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(params[0]); // in this case,
                // params[0]
                // is URL
                try {
                    // set up post data

                    HashMap<String, String> request = new HashMap<String, String>();
//                    request.put("guid", generateRandomAlphaNumeric(16));
//                    request.put("data", getDataForToken());

                    request.put("guid", "1414044f86694fceae48d8681e213c74");
                    request.put("data", getJsonDataString());
//                    request.put("hash", "b2d601b6bddd3ed83d992e5edfb7d22a3c314f61697b2c3b76f409eb953eda2a");
                    request.put("hash", generateHash(getJsonDataString()));

                    Log.d("TESTING" , " Request : "+ request.toString());

                    ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                    Iterator<String> it = request.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        nameValuePair.add(new BasicNameValuePair(key, request.get(key)));
                    }

                    post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                    HttpResponse response = client.execute(post);
                    org.apache.http.StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
                        result = EntityUtils.toByteArray(response.getEntity());
                        str = new String(result, "UTF-8");
                    }
                } catch (Exception e) {
                    Log.d("TESTING", "error exception");

                }
                return str;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject response = new JSONObject(s);

                    if (response.getInt("code") == 0) {

                        handleSuccess(response);

                    } else {

                        Log.d("TESTING", "error code not 0");
                        handleError(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TESTING", e.getMessage());
                }
            }
        }.execute("http://10.37.37.33/epgproxy/public/subscription/get-package");
    }

    private String getJsonDataString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("value", 75);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String randomGuidClient() {
        return "generaterandomguid2017";
    }

    public static int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        return randomNumber;
    }

    private void handleError(JSONObject response) {

    }

    public static String generateRandomAlphaNumeric(int count) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        while (count-- != 0) {
            int ch = (int)(Math.random()*alphaNum.length());
            sb.append(alphaNum.charAt(ch));
        }
        return sb.toString().toLowerCase();
    }

    public static String toMd5(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String generateHash(String stringData) {
        String hashResult = null;
        try {
            Mac hasher = Mac.getInstance("HmacSHA256");

            String md5String = toMd5(getKey());

            Log.d("TESTING", "md5 : " + md5String);

            hasher.init(new SecretKeySpec(md5String.getBytes("UTF-8"),"HmacSHA256"));
            byte[] bytes = hasher.doFinal(stringData.getBytes());

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }

            hashResult = hash.toString();

        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return hashResult;
    }

    public static String getDataForToken() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss");
        String dateTimeString = dateFormat.format(calendar.getTime());
        String dataForToken = String
                .format("Hello I\'am %s and today is %s.", CLIENT_NAME, dateTimeString);
        return dataForToken;
    }

    public static String getKey() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(calendar.getTime());
//        return "EPG." + generateRandomAlphaNumeric(16) + ".TELKOM." + dateString;
        String key = "EPG." + randomGuidClient() + ".TELKOM." + dateString;
        Log.d("TESTING", "key : " +key);
        return key;
    }

    private void handleSuccess(JSONObject response) {

        try {
            Log.d("TESTING", response.getString("guid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!response.isNull("data")) {

            try {
                JSONObject data = response.getJSONObject("data");

                int status = data.getInt("status");

                if (status == 1) {

                    Log.d("TESTING", "started");

                } else if (status == 2) {

                    Log.d("TESTING", "subscribed");

                } else if (status == 3
                        || status == 4
                        || status == 5) {

                    Log.d("TESTING", "must listpackage");

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TESTING", "error exception : " + e.getMessage());
            }

        } else {
            Log.d("TESTING", "no key data or null");
        }
    }
}
