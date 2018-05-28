package www.wanshe.com.wstore.net;

/**
 * 自定义事件监听的回调，用于处理成功和失败的请求，
 * 参数中使用的是Object类型，这样能够更灵活的处理数据
 */

public interface DisposeDataListener {
    //请求成功回调
    public void onSuccess(Object responseObj);
    //请求失败回调
    public void onFailure(Object responseObj);
}
