package www.wanshe.com.wstore.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonJsonCallback implements Callback {

    //与服务器的字段的一个对应关系
    protected final String RESULT_CODE = "ecode"; //有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    //自定义异常类型
    protected final int NETWORK_ERROR = -1; //网络错误
    protected final int JSON_ERROR = -2; //Json错误
    protected final int OTHER_ERROR = -3; //未知错误

    private Handler mDeliveryHandler; //进行消息的转发
    private DisposeDataListener mListener;//对响应数据的回调处理
    private Class<?> mClass;//对实体对象的转化

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    //请求失败处理
    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    //请求成功处理
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    //请求成功处理
    private void handleResponse(Object responseObj) {
        if (responseObj == null && responseObj.toString().trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
                //从JSON对象中取出我们的响应码，如果为0，则是正确的响应
                if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null) {
                        mListener.onSuccess(responseObj);
                    } else {
                        //转化为实体对象
                        Object obj = new Gson().fromJson((String) responseObj, mClass);
                        if (obj != null) {
                            //表明正确的转化为实体对象
                            mListener.onSuccess(obj);
                        } else {
                            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            } else {
                mListener.onFailure(new OkHttpException(OTHER_ERROR, result.get(RESULT_CODE)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
        }
    }
}
