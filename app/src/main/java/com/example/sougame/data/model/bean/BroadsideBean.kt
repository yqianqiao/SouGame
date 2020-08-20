package com.example.sougame.data.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *   Created by YX on 2020-07-17 15:36.
 */
data class BroadsideGameBean(
    var icon: String? = null,
    var subject: String? = null,
    var gametypename: String? = null,
    var title: String = "",
    var playnum: Int? = null,
    var gameid: Int? = null
) : MultiItemEntity {
    companion object {
        const val TITLE = 0
        const val CONTENT = 1
    }

    override val itemType: Int
        get() = if (title.isNotEmpty()) TITLE else CONTENT


}