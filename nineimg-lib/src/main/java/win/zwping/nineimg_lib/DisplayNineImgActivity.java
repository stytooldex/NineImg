package win.zwping.nineimg_lib;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import win.zwping.plib.frame.PImageView;

/**
 * 9图显示
 *
 * @author zwping
 */
public class DisplayNineImgActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TextView textView;

    private List<String> list;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_nine_img);
        position = getIntent().getExtras().getInt("currentPosition");
        list = getIntent().getExtras().getStringArrayList("list");

        viewPager = findViewById(R.id.viewpager);
        textView = findViewById(R.id.number);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);

    }

    //<editor-fold desc="viewPager Adapter">

    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PImageView imageView = new PImageView(DisplayNineImgActivity.this);
            imageView.setBigModel();
            imageView.setLoadProgress();
            imageView.display(list.get(position));
            container.addView(imageView);
            return imageView;
        }
    };
    //</editor-fold>

    //<editor-fold desc="viewPager切换监听">

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
        textView.setText(position + 1 + " / " + list.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //</editor-fold>
}
