package www.wanshe.com.wstore.net;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 接收请求参数，生成request对象
 */

public class CommonRequest {

    //创建get请求的request
    public static Request getRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }

    //创建post请求
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                mFormBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        //通过请求构建类的build方法获取到真正的请求体对象
        FormBody mFormBody = mFormBodyBuilder.build();
        return new Request.Builder().url(url).post(mFormBody).build();
    }
}
