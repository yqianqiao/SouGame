package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 17:46
 * Description: com.example.sougame.data.model.bean
 */
data class BannerBean(
    val mobileswipe1: Mobileswipe1,
    val mobileswipe2: Mobileswipe2,
    val mobileswipe3: Mobileswipe3,
    val mobileswipe4: Mobileswipe4,
    val mobileswipe5: Mobileswipe5

)

data class Mobileswipe1(
    override val gameid: Int,
    override val images: String,
    override val wxBannerSite: String
) : Mobileswipe

data class Mobileswipe2(
    override val gameid: Int,
    override val images: String,
    override val wxBannerSite: String
) : Mobileswipe

data class Mobileswipe3(
    override val gameid: Int,
    override val images: String,
    override val wxBannerSite: String
) : Mobileswipe

data class Mobileswipe4(
    override val gameid: Int,
    override val images: String,
    override val wxBannerSite: String
) : Mobileswipe

data class Mobileswipe5(
    override val gameid: Int,
    override val images: String,
    override val wxBannerSite: String
) : Mobileswipe

interface Mobileswipe {
    val gameid: Int
    val images: String
    val wxBannerSite: String
}