package win.zwping.nineimg_lib.i;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2017/10/24 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface ImageLoaderInterface<T extends ImageView> extends Serializable {
    void displayImage(Context context, Object path, T imageView);
}
