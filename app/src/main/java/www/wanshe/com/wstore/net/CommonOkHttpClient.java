package www.wanshe.com.wstore.net;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import www.wanshe.com.wstore.utils.HttpsUtils;

/**
 * 1.请求的发送。
 * 2.请求参数的配置
 * 3.支持https
 */

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    //为client配置参数。使用静态代码块
    static {
        OkHttpClient.Builder mOKHttpBuilder = new OkHttpClient.Builder();
        mOKHttpBuilder
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                    //添加允许重定向
                    .followRedirects(true)
                    //支持https
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager());
        mOkHttpClient = mOKHttpBuilder.build();
    }
    //发送具体的http以及https请求
    public static Call sendRequest(Request request, CommonJsonCallback commonJsonCallback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonJsonCallback);
        return call;
    }
    //发送get请求
    public static Call get(Request request,DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
    //发送post请求
    public static Call post(Request request,DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

}
