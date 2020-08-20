package com.example.sougame.app.network

import com.example.sougame.data.model.bean.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "http://api.sooyooj.com/"
    }


    @POST("member/game/recent/list")
    suspend fun getRecentData(): ApiResponse<ArrayList<GameListBean>>


    @POST("index/ads/zone")
    suspend fun getBannerData(): ApiResponse<BannerBean>


    @FormUrlEncoded
    @POST("index/index/list")
    suspend fun getHomeData(@Field("wap") wap: Int): ApiResponse<HomeBean>

    @POST("index/game/type")
    suspend fun getGameTypeData(): ApiResponse<ArrayList<GameType>>


    @FormUrlEncoded
    @POST("index/game/list")
    suspend fun getGameListData(
        @Field("r") r: String?,
        @Field("n") n: Int?,
        @Field("v") v: String?,
        @Field("offset") offset: Int?,
        @Field("gametypeid") gametypeid: Int?
    ): ApiResponse<ArrayList<GameListBean>>

    @FormUrlEncoded
    @POST("index/game/list")
    suspend fun getBroadsideGameListData(
        @Field("r") r: String?,
        @Field("n") n: Int?,
        @Field("v") v: String?,
        @Field("offset") offset: Int?,
        @Field("gametypeid") gametypeid: Int?
    ): ApiResponse<ArrayList<BroadsideGameBean>>

    @FormUrlEncoded
    @POST("index/information/list")
    suspend fun getShakyData(
        @Field("informationtypeid") informationtypeid: Int,
        @Field("gameid") gameid: Int?,
        @Field("n") n: Int?
    ): ApiResponse<ArrayList<ShakyBean>>

    @FormUrlEncoded
    @POST("index/server/list")
    suspend fun getOpenServiceData(@Field("type") type: Int): ApiResponse<ArrayList<OpenServiceBean>>

    @FormUrlEncoded
    @POST("index/game/info")
    suspend fun getGameDetailsData(@Field("gameid") gameid: Int): ApiResponse<GameDetailsBean>

    @FormUrlEncoded
    @POST("index/giftbag/type")
    suspend fun gameGiftBagData(
        @Field("gameid") gameid: Int,
        @Field("vip") vip: Int
    ): ApiResponse<ArrayList<GameGiftBag>>

    @FormUrlEncoded
    @POST("index/login/login")
    suspend fun login(
        @Field("username") username: String?,
        @Field("phone") phone: String?,
        @Field("code") code: String?,
        @Field("password") password: String?,
        @Field("type") type: String?,
        @Field("weixincode") weixincode: String?,
        @Field("qqcode") qqcode: String?,
        @Field("qqopenid") qqopenid: String?,
        @Field("qqaccesstoken") qqaccesstoken: String?,
        @Field("terminal") terminal: String?
    ): ApiResponse<UserInfo>


    @POST("member/info")
    suspend fun getUserInfo(): ApiResponse<UserInfo>


    @POST("index/invite/progress")
    suspend fun getProgress(): ApiResponse<ProgressBean>

    @FormUrlEncoded
    @POST("member/realname")
    suspend fun certification(
        @Field("realname") realname: String,
        @Field("card") card: String
    ): ApiResponse<ArrayList<String>>

    @FormUrlEncoded
    @POST("member/repassword")
    suspend fun getResetPassword(
        @Field("oldpassword") oldpassword: String,
        @Field("password") password: String
    ): ApiResponse<ArrayList<String>>


    @POST("index/coupon/list")
    suspend fun getCouponList(): ApiResponse<CouponBean>

    @POST("member/pay/log")
    suspend fun getRechargeRecord(): ApiResponse<ArrayList<RechargeRecordBean>>

    @FormUrlEncoded
    @POST("member/accgrade/history")
    suspend fun getIntegralRecord(@Field("type") type: String?): ApiResponse<ArrayList<IntegralRecordBean>>

    @POST("index/invite/invitenum")
    suspend fun getInviteNum(): ApiResponse<ArrayList<InviteFriendsBean>>

    @FormUrlEncoded
    @POST("index/giftbag/type")
    suspend fun getGamePackList(
        @Field("vip") vip: Int?,
        @Field("type") type: Int?
    ): ApiResponse<ArrayList<GamePackBean>>

    @POST("index/platform/virtual/gift")
    suspend fun getVirtualGift(): ApiResponse<ArrayList<GamePackBean>>

    @POST("index/accgrade/commodity/list")
    suspend fun getEntityGoods(): ApiResponse<ArrayList<EntityGoodsBean>>

    @POST("member/giftbag/history")
    suspend fun getGiftBagHistory(): ApiResponse<ArrayList<GamePackBean>>

    @FormUrlEncoded
    @POST("index/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("phone") phone: Int?,
        @Field("code") code: Int?,
        @Field("password") password: String?,
        @Field("type") type: String?,
        @Field("terminal") terminal: String?
    ): ApiResponse<RegisterBean>


    @FormUrlEncoded
    @POST("index/login/phone/code")
    suspend fun phoneLogin(@Field("phone") phone: String): ApiResponse<Any>

    @FormUrlEncoded
    @POST("member/bind/phone/code")
    suspend fun bindPhoneCode(@Field("phone") phone: String): ApiResponse<Any>

    @FormUrlEncoded
    @POST("member/bind/phone")
    suspend fun bindPhone(@Field("phone") phone: String, @Field("code") code: String): ApiResponse<Any>


    @POST("member/sign")
    suspend fun sign(): ApiResponse<SignBean>

    @FormUrlEncoded
    @POST("member/giftbag")
    suspend fun giftbag(@Field("giftbagtypeid") giftbagtypeid: Int): ApiResponse<GiftBagBean>

    @FormUrlEncoded
    @POST("index/conversion/platform/virtual/gift")
    suspend fun virtualGiftbag(@Field("id") id: Int): ApiResponse<Any>


}