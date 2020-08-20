package com.example.common.base.view.status

import android.content.Context
import android.view.View
import com.example.common.R
import com.kingja.loadsir.callback.Callback

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/5/12 23:21
 * Description: com.example.lib_common.mvvm.view.status
 */
class BlankStatus : Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_layout_init
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        //不响应reload事件
        return true
    }
//    //填充布局
//    @Override
//    protected int onCreateView() {
//        return R.layout.layout_custom;
//    }
//    //当前Callback的点击事件，如果返回true则覆盖注册时的onReloa()，如果返回false则两者都执行，先执行onReloadEvent()。
//    @Override
//    protected boolean onReloadEvent(final Context context, View view) {
//        Toast.makeText(context.getApplicationContext(), "Hello buddy! :p", Toast.LENGTH_SHORT).show();
//        (view.findViewById(R.id.iv_gift)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context.getApplicationContext(), "It's your gift! :p", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return true;
//    }
//
//    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
//    @Override
//    public boolean getSuccessVisible() {
//        return super.getSuccessVisible();
//    }
//
//    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
//    @Override
//    public void onAttach(Context context, View view) {
//        super.onAttach(context, view);
//    }
//
//    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
//    @Override
//    public void onDetach() {
//        super.onDetach(context, view);
//    }


}