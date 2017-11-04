package win.zwping.nineimg;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.ielse.view.imagewatcher.ImageWatcher;
import win.zwping.nineimg_lib.NineImg;

public class MainActivity extends AppCompatActivity {

    private NineImg nineImg;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decoderView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decoderView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//            //or ?
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nineImg = findViewById(R.id.nine_img);
        recyclerView = findViewById(R.id.recycler);

//        ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < 9; i++) {
//            list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2662232333,1061247545&fm=27&gp=0.jpg");
//        }
//        nineImg.setList(1080, list);

//        lists.add(list);
        for (int i = 0; i < 10; i++) {
            ArrayList<String> list1 = new ArrayList<>();
            for (int i1 = 0; i1 < (new Random().nextBoolean() ? 1 : new Random().nextInt(8) + 1); i1++) {
                if (new Random().nextBoolean()) {
                    list1.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2662232333,1061247545&fm=27&gp=0.jpg");
                } else if (new Random().nextBoolean()) {
                    list1.add("http://zwping.oss-cn-shenzhen.aliyuncs.com/lu_big_gif.gif");
                } else {
                    list1.add("http://zwping.oss-cn-shenzhen.aliyuncs.com/lu_big.png");
                }
            }
            lists.add(list1);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
    }

    public void OnClick(View view) {
        //不允许这种方式
//        lists = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ArrayList<String> list1 = new ArrayList<>();
//            for (int i1 = 0; i1 < new Random().nextInt(8) + 1; i1++) {
//                list1.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2662232333,1061247545&fm=27&gp=0.jpg");
//            }
//            lists.add(list1);
//        }
        adapter.notifyDataSetChanged();

    }

    private List<ArrayList<String>> lists = new ArrayList<>();

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_nine_img, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.nineImg.setList(1080, lists.get(position));

            holder.nineImg.setDisplay(true, MainActivity.this);
            holder.nineImg.setAutoSize(true);
            holder.textView.setText(position + "------");
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private NineImg nineImg;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            nineImg = itemView.findViewById(R.id.nine_img);
            textView = itemView.findViewById(R.id.text);
        }

    }


}
