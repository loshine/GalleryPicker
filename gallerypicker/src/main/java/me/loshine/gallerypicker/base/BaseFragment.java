package me.loshine.gallerypicker.base;

import android.support.v4.app.Fragment;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class BaseFragment extends Fragment {

    /**
     * 返回 true 消费事件，返回 false 不消费事件，默认不消费
     *
     * @return boolean
     */
    public boolean onBackPressed() {
        return false;
    }
}
