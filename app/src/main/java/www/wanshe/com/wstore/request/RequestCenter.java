package www.wanshe.com.wstore.request;

import www.wanshe.com.wstore.bean.Novel;
import www.wanshe.com.wstore.constant.HttpConstant;
import www.wanshe.com.wstore.net.CommonOkHttpClient;
import www.wanshe.com.wstore.net.CommonRequest;
import www.wanshe.com.wstore.net.DisposeDataHandle;
import www.wanshe.com.wstore.net.DisposeDataListener;
import www.wanshe.com.wstore.net.RequestParams;

public class RequestCenter {
    private static void getRequest(String url, DisposeDataListener listener,
                                        RequestParams params,Class<?> clazz)
    {
        CommonOkHttpClient.get(CommonRequest.getRequest(url,params),new DisposeDataHandle(listener, clazz));
    }
    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.getRequest(HttpConstant.URL,listener,null, Novel.class);
    }
}
