package com.kingja.yaluji.model.service;


import com.kingja.yaluji.model.entiy.AnswerResult;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.ArticleDetail;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.City;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.model.entiy.HttpResult;
import com.kingja.yaluji.model.entiy.Login;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.model.entiy.Message;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.OrderDetail;
import com.kingja.yaluji.model.entiy.OrderResult;
import com.kingja.yaluji.model.entiy.PraiseItem;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.model.entiy.QuestionDetail;
import com.kingja.yaluji.model.entiy.SceneryIntroduce;
import com.kingja.yaluji.model.entiy.ScenicType;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.model.entiy.TicketDetail;
import com.kingja.yaluji.model.entiy.VersionInfo;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.model.entiy.WeixinPayResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 项目名称：和Api相关联
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/12 16:32
 * 修改备注：
 */
public interface ApiService {
    /*获取轮播图*/
    @POST("/app/banner/indexlist")
    Observable<HttpResult<List<LunBoTu>>> getLunBoTuList();

    /*获取美文雷暴*/
    @POST("/app/article/indexarticle")
    Observable<HttpResult<List<ArticleSimpleItem>>> getArticleList();


    /*注册OK*/
    @FormUrlEncoded
    @POST("/app/user/register")
    Observable<HttpResult<Object>> register(@Field("mobile") String mobile, @Field("passwd") String password,
                                            @Field("code") String code);

    /*登录OK*/
    @FormUrlEncoded
    @POST("/app/user/login")
    Observable<HttpResult<Login>> login(@Field("mobile") String mobile, @Field("password") String password, @Field
            ("deviceId") String deviceId, @Field("deviceName") String deviceName, @Field("osName") String osName);


    /*==============================================*/

    /*发送验证码OK*/
    @FormUrlEncoded
    @POST("/app/user/smsmessage")
    Observable<HttpResult<String>> sms(@Field("mobile") String mobile, @Field("flag") int flag);


    /*修改密码*/
    @FormUrlEncoded
    @POST("/app/user/changepasswd")
    Observable<HttpResult<Object>> modifyPassword(@Field("oldpasswd") String oldpasswd, @Field("passwd") String
            password);


    /*修改昵称*/

    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded
    @POST("/app/user/changeinfo")
    Observable<HttpResult<Object>> modifyNickname(@Field("nickname") String nickname);


    /*退出登录OK*/
    @FormUrlEncoded
    @POST("/app/user/logout")
    Observable<HttpResult<Object>> logout(@Field("userId") String userId, @Field("osName") String osName);

    /*我的消息*/
    @FormUrlEncoded
    @POST("/app/message/list")
    Observable<HttpResult<List<Message>>> message(@Field("page") Integer page, @Field("pageSize") Integer pageSize);

    /*游客列表ERROR*/
    @FormUrlEncoded
    @POST("/app/tourist/list")
    Observable<HttpResult<List<Visitor>>> getVisitors(@Field("page") Integer page, @Field("pageSize") Integer pageSize);


    /*新增游客信息*/
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded
    @POST("/app/tourist/add")
    Observable<HttpResult<Visitor>> addVisitor(@Field("name") String name, @Field("mobile") String mobile,
                                               @Field("idcode") String idcode);

    /*删除游客信息*/
    @FormUrlEncoded
    @POST("/app/tourist/delete")
    Observable<HttpResult<Object>> deleteVisitor(@Field("touristId") String touristId);

    /*设为默认游客*/
    @FormUrlEncoded
    @POST("/app/tourist/default")
    Observable<HttpResult<Object>> defaultVisitor(@Field("touristId") String touristId);

    /*编辑游客信息*/
    @FormUrlEncoded
    @POST("/app/tourist/change")
    Observable<HttpResult<Visitor>> editVisitor(@Field("touristId") String touristId, @Field("name") String name, @Field
            ("mobile") String mobile, @Field("idcode") String idcode);

    /*上传头像*/
    @Multipart
    @POST("/app/user/changeHeadimg")
    Observable<HttpResult<String>> uploadHeadImg(@Part MultipartBody.Part headImg);

    /*获取订单列表*/
    @FormUrlEncoded
    @POST("/app/order/list")
    Observable<HttpResult<List<Order>>> getOrders(@Field("page") Integer page, @Field("pageSize") Integer pageSize,
                                                  @Field("status") Integer status);

    /*获取订单详情*/
    @FormUrlEncoded
    @POST("/app/order/ticketcode")
    Observable<HttpResult<OrderDetail>> getOrderDetail(@Field("orderId") String orderId);

    /*删除/已读消息 1 已读2 删除*/
    @FormUrlEncoded
    @POST("/app/message/confirm")
    Observable<HttpResult<Object>> confirmMsg(@Field("messageId") String messageId, @Field("flag") Integer flag);


    /*获取优惠券列表*/
    @POST("/app/product/list")
    Observable<HttpResult<List<Ticket>>> getTicketList(@Body RequestBody requestBody);

    /*获取热搜*/
    @FormUrlEncoded
    @POST("/app/product/hotsearch")
    Observable<HttpResult<List<HotSearch>>> getHotSearch(@Field("limit") int areaId);

    /*获取优惠券详情*/
    @FormUrlEncoded
    @POST("/app/product/details")
    Observable<HttpResult<TicketDetail>> getTicketDetail(@Field("productId") String productId);

    /*获取景区介绍*/
    @FormUrlEncoded
    @POST("/app/product/scenic")
    Observable<HttpResult<SceneryIntroduce>> getSceneryIntroduce(@Field("scenicId") String scenicId);

    /*订单保存*/
    @FormUrlEncoded
    @POST("/app/order/submit")
    Observable<HttpResult<OrderResult>> sumbitOrder(@Field("productId") String productId, @Field("touristIds")
            String touristIds, @Field("quantity") String quantity, @Field("from") String from);

    /*支付宝支付*/
    @FormUrlEncoded
    @POST("/app/pay/alipay")
    Observable<HttpResult<String>> alipay(@Field("orderId") String orderId);

    /*微信支付*/
    @FormUrlEncoded
    @POST("/app/pay/weixinpay")
    Observable<HttpResult<WeixinPayResult>> weixinpay(@Field("orderId") String orderId);

    /*忘记密码*/
    @FormUrlEncoded
    @POST("/app/user/findpasswd")
    Observable<HttpResult<Object>> forgetPassword(@Field("mobile") String mobile, @Field("passwd") String passwd,
                                                  @Field("code") String code);

    /*获取景区类型*/
    @FormUrlEncoded
    @POST("/app/dict/data")
    Observable<HttpResult<List<ScenicType>>> getScenicType(@Field("dictCategoryId") String dictCategoryId);

    /*获取地区*/
    @POST("/app/area/list")
    Observable<HttpResult<List<City>>> getCity();


    /*版本检测*/
    @FormUrlEncoded
    @POST("/app/version/detail")
    Observable<HttpResult<VersionInfo>> getVersion(@Field("version") String version, @Field("flag") int flag);


    /*获取美文列表*/
    @POST("/app/article/list")
    Observable<HttpResult<List<Article>>> getArticleList(@Body RequestBody requestBody);

    /*获取美文搜索列表*/
    @POST("/app/article/search")
    Observable<HttpResult<List<ArticleSimpleItem>>> getArticleSearchList(@Body RequestBody requestBody);

    /*获取鸡答搜索列表*/
    @POST("/app/paper/nouser/list")
    Observable<HttpResult<List<Question>>> getQuestionSearchList(@Body RequestBody requestBody);

    /*获取美文详情*/
    @FormUrlEncoded
    @POST("/app/article/details")
    Observable<HttpResult<ArticleDetail>> getArticleDetail(@Field("articleId") String articleId);

    /*获取鸡答列表*/
    @POST("/app/paper/list")
    Observable<HttpResult<List<Question>>> getQuestionList(@Body RequestBody requestBody);

    /*获取问答单题题目及选择项*/
    @FormUrlEncoded
    @POST("/app/paper/question")
    Observable<HttpResult<QuestionDetail>> getQuestionDetail(@Field("paperId") String paperId);


    /*提交答案*/
    @POST("/app/paper/submitAnswer")
    Observable<HttpResult<AnswerResult>> submitAnswer(@Body RequestBody requestBody);

    /*分享后复活*/
    @FormUrlEncoded
    @POST("/app/paper/reborn")
    Observable<HttpResult<Object>> reLife(@Field("paperId") String paperId);

    /*删除订单*/
    @FormUrlEncoded
    @POST("/app/order/deleteOrder")
    Observable<HttpResult<Object>> deleteOrder(@Field("orderId") String orderId);

    /*获取推荐列表*/
    @POST("/app/product/recommend")
    Observable<HttpResult<List<Ticket>>> getRecommendList();


    /*我要反馈*/
    @POST("/app/feedback/addFeedback")
    Observable<HttpResult<Object>> sendFeedback(@Body RequestBody requestBody);

    /*我要反馈-无图*/
    @POST("/app/feedback/addNoImgFeedback")
    Observable<HttpResult<Object>> sendNoImgFeedback(@Body RequestBody requestBody);

    /*我要合作*/
    @POST("/app/cooperate/addCooperate")
    Observable<HttpResult<Object>> doBusiness(@Body RequestBody requestBody);

    /*领取成功补全游客信息*/
    @POST("/app/paper/completeTourist")
    Observable<HttpResult<Order>> prefectVisitor(@Body RequestBody requestBody);


    /*上传定位*/
    @FormUrlEncoded
    @POST("/app/user/addLocation")
    Observable<HttpResult<Object>> uploadLocation(@Field("lat") String lat, @Field("lng") String lng);
    //=================================================================================


    /*鸡赞列表-未登陆*/
    @POST("/app/like/nouser/list")
    Observable<HttpResult<List<PraiseItem>>> getPraiseListByVisitor(@Body RequestBody requestBody);

    /*鸡赞列表-已登陆*/
    @POST("/app/like/list")
    Observable<HttpResult<List<PraiseItem>>> getPraiseList(@Body RequestBody requestBody);

//    /*审查*/
//    @FormUrlEncoded
//    @POST("/app/like/clickShare")
//    Observable<HttpResult<Object>> uploadLocation(@Field("lat") String lat, @Field("lng") String lng);


}
