package com.example.common.base.view.status

import com.example.common.R
import com.kingja.loadsir.callback.Callback

class EmptyStatus : Callback() {
    override fun onCreateView(): Int {
      return  R.layout.common_layout_empty
    }


}
