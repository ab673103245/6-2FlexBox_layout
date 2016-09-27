package qianfeng.a6_2flexbox_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<String> list;
    private Map<String,Boolean> map;
    private FlexboxLayout f1;
    private FlexboxLayout f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f1 = (FlexboxLayout) findViewById(R.id.f1);
        f2 = (FlexboxLayout) findViewById(R.id.f2);

        init();

        for(String s : list) // 为什么点击一下TextView，它会从上面的流式布局移到下面的流式布局？onCreate方法只加载一次，为什么会实现？因为接口回调啊！卧槽
        {
            initTv(s);

        }

    }

    private void initTv(String s) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setPadding(8,4,8,4);
        tv.setBackgroundResource(R.drawable.tv_bg);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8,4,8,4);
        tv.setLayoutParams(lp); // 设置布局参数
        // 给每个按钮设置点击事件，这个点击事件每次一触发，就会回调，回调其实就是程序由上往下运行，现在往上执行一段代码或者一个方法而已。
            tv.setOnClickListener(new View.OnClickListener() { // 给每个按钮各自设置一个点击事件，如果点击事件触发了，就接口回调这些方法
                @Override                                   // 每个接口回调的点击事件传进去的实例都不一样，是因为我new出来的每个接口的匿名内部类实例都不一样，是因为new出来，所以每个都不一样，地址都不一样，所以都是不同的实例。
                public void onClick(View v) {
                    TextView v1 = (TextView) v;
                    String key = v1.getText().toString();
                    if(map.get(key)) // 如果这个键原本是true你又点击了
                    {
                        f1.removeView(v1); // 先从已经存储它的父容器移除，因为现在v1已经有一个父容器,是f1,不先移除是会报错的！！！
                        map.put(key,false);
                        f2.addView(v1);
//                        Toast.makeText(MainActivity.this,map.get(key).toString(),Toast.LENGTH_SHORT).show();
                    }else
                    {
                        f2.removeView(v1);
                        map.put(key,false);
                        f1.addView(v1);
//                        Toast.makeText(MainActivity.this,map.get(key).toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            });

        if(map.get(s)) // 因为这个方法在后面，上面的点击方法是接口回调，意思就是只有点击的监听时，才会触发，
                    // 而下面这些方法，是无论如何都会执行的，这是检阅从数据库中的结果，即上一次保存的结果，进行重新显示控件的方法
        {
            f1.addView(tv); // 第一次添加进容器了！
        }else
        {
            f2.addView(tv);
        }
    }

    private void init() {

        list = new ArrayList<>();
        list.add("头条");
        list.add("FUN来了");
        list.add("国学");
        list.add("彩票");
        list.add("科技");
        list.add("文化");
        list.add("公益");
        list.add("自媒体");
        list.add("军事");
        list.add("旅游");

        map = new HashMap<>();

        for(String s : list)
        {
            map.put(s,false);
        }

        map.put("旅游",true);

    }
}
