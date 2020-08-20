package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.databinding.FragmentEntityGoodsBinding
import com.example.sougame.ui.adapter.EntityGoodsAdapter
import com.example.sougame.viewmodel.EntityGoodsViewModer
import kotlinx.android.synthetic.main.fragment_entity_goods.*


class EntityGoodsFragment : BaseFragment<EntityGoodsViewModer, FragmentEntityGoodsBinding>() {

    private val entityGoodsAdapter = EntityGoodsAdapter()


    override fun layoutId() = R.layout.fragment_entity_goods

    override fun initView(savedInstanceState: Bundle?) {
        rv_entity_goods.init(entityGoodsAdapter, 3, layoutMangers = GridLayoutManager(context, 2))
    }

    override fun lazyLoadData() {
        mViewModel.getEntityGoods()
    }

    override fun createObserver() {
        mViewModel.entityGoodsData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                entityGoodsAdapter.setList(list)
            })
        })
    }

}