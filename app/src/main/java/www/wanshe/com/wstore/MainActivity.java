package www.wanshe.com.wstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import www.wanshe.com.wstore.bean.Novel;
import www.wanshe.com.wstore.net.DisposeDataListener;
import www.wanshe.com.wstore.request.RequestCenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.tv);
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Novel novel = (Novel) responseObj;
                textView.setText(novel.getData().get(0).getFemalename());
            }

            @Override
            public void onFailure(Object responseObj) {
                Log.e("测试网络框架", "onFailure: " + responseObj.toString() );
            }
        });
    }
}
