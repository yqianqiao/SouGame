package com.example.sougame.data.repository.request

import com.example.sougame.app.network.NetWorkApi
import com.example.sougame.data.model.bean.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 11:35
 * Description: 从网络中获取数据
 */
class HttpRequestManger {
    companion object {
        val instance: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpRequestManger()
        }
    }

    /**
     * 首页
     * ***************************************************************************
     */

    /**
     * Banner
     */
    suspend fun getBannerData(): ApiResponse<BannerBean> {
        return NetWorkApi.service.getBannerData()
    }

    /**
     * 首页数据
     */
    suspend fun getHomeData(wap: Int): ApiResponse<HomeBean> {
        return NetWorkApi.service.getHomeData(wap)
    }

    /**
     * 游戏类型
     */
    suspend fun getGameTypeData(): ApiResponse<ArrayList<GameType>> {
        return NetWorkApi.service.getGameTypeData()
    }

    /**
     * r : 推荐游戏
     * v :搜索游戏
     */
    suspend fun getGameListData(
        r: String? = null,
        n: Int? = null,
        v: String? = null,
        offset: Int? = null,
        gametypeid: Int? = null
    ): ApiResponse<ArrayList<GameListBean>> {
        return NetWorkApi.service.getGameListData(r, n, v, offset, gametypeid)
    }

    suspend fun getBroadsideGameListData(
        r: String? = null,
        n: Int? = null,
        v: String? = null,
        offset: Int? = null,
        gametypeid: Int? = null
    ): ApiResponse<ArrayList<BroadsideGameBean>> {
        return NetWorkApi.service.getBroadsideGameListData(r, n, v, offset, gametypeid)
    }

    /**
     * 活动
     */
    suspend fun getShakyData(
        informationtypeid: Int,
        gameid: Int? = null,
        n: Int? = null
    ): ApiResponse<ArrayList<ShakyBean>> {
        return NetWorkApi.service.getShakyData(informationtypeid, gameid, n)
    }

    /**
     * 开服
     */
    suspend fun getOpenServiceData(type: Int): ApiResponse<ArrayList<OpenServiceBean>> {
        return NetWorkApi.service.getOpenServiceData(type)
    }

    /**
     * 游戏详情
     */
    suspend fun getGameDetailsData(gameId: Int): ApiResponse<GameDetailsBean> {
        return NetWorkApi.service.getGameDetailsData(gameId)
    }

    /**
     * 游戏礼包
     */
    suspend fun gameGiftBagData(gameId: Int, vip: Int): ApiResponse<ArrayList<GameGiftBag>> {
        return NetWorkApi.service.gameGiftBagData(gameId, vip)
    }

    /**
     * 登录
     */
    suspend fun login(
        username: String? = null,
        phone: String? = null,
        code: String? = null,
        password: String? = null,
        type: String? = null,
        weixincode: String? = null,
        qqcode: String? = null,
        qqopenid: String? = null,
        qqaccesstoken: String? = null,
        terminal: String? = "mobile"
    ): ApiResponse<UserInfo> {
        return NetWorkApi.service.login(
            username,
            phone,
            code,
            password,
            type,
            weixincode,
            qqcode,
            qqopenid,
            qqaccesstoken,
            terminal
        )
    }

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(): ApiResponse<UserInfo> {
        return NetWorkApi.service.getUserInfo()
    }

    /**
     * 获取邀请信息
     */
    suspend fun getProgress(): ApiResponse<ProgressBean> {
        return NetWorkApi.service.getProgress()
    }

    /**
     * 获取最近玩过
     */
    suspend fun getRecentData(): ApiResponse<ArrayList<GameListBean>> {
        return NetWorkApi.service.getRecentData()
    }

    /**
     * 实名认证
     */
    suspend fun certification(realname: String, card: String): ApiResponse<ArrayList<String>> {
        return NetWorkApi.service.certification(realname, card)
    }

    /**
     * 重置密码
     */
    suspend fun getResetPassword(
        oldpassword: String,
        password: String
    ): ApiResponse<ArrayList<String>> {
        return NetWorkApi.service.getResetPassword(oldpassword, password)
    }

    /**
     * 我的卡券
     */
    suspend fun getCouponList(): ApiResponse<CouponBean> {
        return NetWorkApi.service.getCouponList()
    }

    /**
     * 充值记录
     */
    suspend fun getRechargeRecord(): ApiResponse<ArrayList<RechargeRecordBean>> {
        return NetWorkApi.service.getRechargeRecord()
    }

    /**
     * 积分记录
     */
    suspend fun getIntegralRecord(type: String? = null): ApiResponse<ArrayList<IntegralRecordBean>> {
        return NetWorkApi.service.getIntegralRecord(type)
    }


    /**
     * 邀请好友
     */
    suspend fun getInviteNum(): ApiResponse<ArrayList<InviteFriendsBean>> {
        return NetWorkApi.service.getInviteNum()
    }

    /**
     * 游戏礼包
     */
    suspend fun getGamePackList(
        vip: Int?,
        type: Int? = null
    ): ApiResponse<ArrayList<GamePackBean>> {
        return NetWorkApi.service.getGamePackList(vip, type)
    }


    /**
     * 优惠卷礼包
     */
    suspend fun getVirtualGift(): ApiResponse<ArrayList<GamePackBean>> {
        return NetWorkApi.service.getVirtualGift()
    }

    /**
     * 实物商品
     */
    suspend fun getEntityGoods(): ApiResponse<ArrayList<EntityGoodsBean>> {
        return NetWorkApi.service.getEntityGoods()
    }

    /**
     * 我的礼包
     */
    suspend fun getGiftBagHistory(): ApiResponse<ArrayList<GamePackBean>> {
        return NetWorkApi.service.getGiftBagHistory()
    }

    /**
     * 注册
     */
    suspend fun register(
        username: String? = null,
        phone: Int? = null,
        code: Int? = null,
        password: String? = null,
        type: String? = null,
        terminal: String? = "mobile"
    ): ApiResponse<RegisterBean> {
        return NetWorkApi.service.register(username, phone, code, password, type, terminal)
    }

    /**
     * 手机登录
     */
    suspend fun phoneLogin(phone: String): ApiResponse<Any> {
        return NetWorkApi.service.phoneLogin(phone)
    }

    /**
     * 签到
     */
    suspend fun sign(): ApiResponse<SignBean> {
        return NetWorkApi.service.sign()
    }

    /**
     * 游戏礼包领取
     */
    suspend fun giftbag(giftbagtypeid: Int): ApiResponse<GiftBagBean> {
        return NetWorkApi.service.giftbag(giftbagtypeid)
    }

    /**
     * 积分兑换礼包
     */
    suspend fun virtualGiftbag(id: Int): ApiResponse<Any> {
        return NetWorkApi.service.virtualGiftbag(id)
    }

    /**
     * 绑定手机验证码
     */
    suspend fun bindPhoneCode(phone: String): ApiResponse<Any> {
        return NetWorkApi.service.bindPhoneCode(phone)
    }

    /**
     * 绑定手机
     */
    suspend fun bindPhone(phone: String,code: String): ApiResponse<Any> {
        return NetWorkApi.service.bindPhone(phone,code)
    }


}