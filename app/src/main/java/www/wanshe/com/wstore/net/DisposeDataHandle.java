package www.wanshe.com.wstore.net;

/**
 * 处理Json转换实体对象的字节码对象做一个封装
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;
    //传入单参
    public DisposeDataHandle(DisposeDataListener listener){
        this.mListener = listener;
    }
    //传入带字节码的双参
    public DisposeDataHandle(DisposeDataListener listener,Class<?> clazz){
        this.mListener = listener;
        this.mClass = clazz;
    }
}
