package com.example.sougame.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/15 16:47
 * Description: com.example.sougame.data.model.bean
 */
@Parcelize
data class GameListBean(
    val addtime: String?,
    val banner: String,
    val bannerSite: String,
    val bigrecommend_images: String,
    val collectid: Int,
    val cover: String,
    val description: String,
    val gameid: Int,
    val gametypeicon: String,
    val gametypeid: Int,
    val gametypename: String,
    val grade: Float,
    val height: Int,
    val icon: String,
    val images: String,
    val images_app: String,
    val info: String,
    val is_show: Int,
    val longimages: String,
    val pagegame: Int,
    val playnum: Int,
    val recommend: Int,
    val slideimages_app: String,
    val smallrecommend_images: String,
    val subject: String,
    val tag: String,
    val width: Int,
    val playtime: String,
    val online: Int,
    val handpick_img: String,
    val hot_img: String

) : Parcelable