package win.zwping.nineimg_lib;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import win.zwping.nineimg_lib.i.ImageLoaderInterface;
import win.zwping.nineimg_lib.i.OnItemClickListener;
import win.zwping.nineimg_lib.view.GridSpacingItemDecoration;
import win.zwping.plib.frame.PImageView;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2017/10/31 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <p>
 * 兼容性bug{@link #setAutoSize(boolean)}，使用流式布局展示兼容性更好，ps：时间不够
 */
public class NineImg extends RelativeLayout {

    //<editor-fold desc="构造函数">

    public NineImg(Context context) {
        super(context);
        initView(null);
    }

    public NineImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public NineImg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NineImg(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    //</editor-fold>
    //<editor-fold desc="参数">

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<String> data;
    private float width;
    private int loadingImg, errorImg;
//    public static ImageLoaderInterface imageLoader;

    /**
     * 自动设置图片大小（根据图片数量设置图片的宽高，默认为1/3宽度）
     */
    private boolean autoSize;
    /**
     * 分割线的大小
     */
    private int dividerSize = 2;
    //</editor-fold>
    //<editor-fold desc="功能变现">

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.nine_img_layout, this);
        recyclerView = findViewById(R.id.nine_img_recycler);

        if (null == adapter) {
            data = new ArrayList<>();
            adapter = new Adapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), getColumn()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(getColumn(), dividerSize, false));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private int getColumn() {
        int size = data.size();
        return autoSize ? size == 1 ? 1 : 3 : 3;
    }

    //</editor-fold>

    //<editor-fold desc="API">

    public void setImageLoader(ImageLoaderInterface imageLoader) {
//        NineImg.imageLoader = imageLoader;
    }

    /**
     * 设置资源
     *
     * @param layoutWidth
     * @param list        与 {@link #setAutoSize(boolean)}冲突，如果开启了autoSize，就不可以更改list数量了
     */
    public void setList(float layoutWidth, ArrayList<String> list) {
        width = layoutWidth;
        data = list;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getColumn()));
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据图片数量，自动调整图片大小
     *
     * @param autoSize 与 {@link #setList(float, ArrayList)} 冲突
     */
    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
        if (null != adapter) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getColumn()));
            adapter.notifyDataSetChanged();
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    //</editor-fold>

    //<editor-fold desc="adapter">

    private class Adapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_nine_img_layout, parent, false);
            view.setOnClickListener(this);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (0 != width) {
                holder.imageView.getLayoutParams().height = (int) ((width - (((getColumn() - 1) * dividerSize))) / (getColumn() == 1 ? 2.1f : 3));
                holder.imageView.getLayoutParams().width = (int) ((width - (((getColumn() - 1) * dividerSize))) / (getColumn() == 1 ? 2.5f : 3));
                holder.imageView.setLayoutParams(holder.imageView.getLayoutParams());
            }
            holder.imageView.displayImage(data.get(position));
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(v, (Integer) v.getTag());
            }
            if (display && null != fromActivity) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("list", data);
                bundle.putInt("currentPosition", (Integer) v.getTag());

                Intent intent = new Intent(fromActivity, DisplayNineImgActivity.class);
                intent.putExtras(bundle);
//                if (Build.VERSION.SDK_INT >= 21) {
//                    fromActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(fromActivity, v, "itemNineImg").toBundle());
//                } else {
                fromActivity.startActivity(intent);
//                }
            }
        }
    }

    private boolean display;
    private Activity fromActivity;

    public void setDisplay(boolean display, Activity activity) {
        this.display = display;
        this.fromActivity = activity;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private PImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.nine_img_img);
            if (0 != loadingImg) {
                imageView.setLoadingImg(loadingImg);
            }
            if (0 != errorImg) {
                imageView.setErrorImg(errorImg);
            }
        }
    }
    //</editor-fold>
}
