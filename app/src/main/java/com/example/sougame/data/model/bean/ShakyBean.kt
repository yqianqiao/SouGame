package com.example.sougame.data.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 10:44
 * Description: com.example.sougame.data.model.bean
 */
data class ShakyBean(
    val addtime: String? = null,
    val author: String? = null,
    val begin: String? = null,
    val cover: String? = null,
    val description: String? = null,
    val end: String? = null,
    val gamesubject: String? = null,
    val informationid: Int? = null,
    val informationtypeid: Int? = null,
    val informationtypename: String? = null,
    var type: Int = 1,
    var title: String = "",
    val subject: String? = null
) : MultiItemEntity {
    companion object {
        const val TITLE = 0
        const val CONTENT = 1
    }

    override val itemType: Int
        get() = if (type == 0) TITLE else CONTENT


}