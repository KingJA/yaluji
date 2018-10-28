package com.kingja.yaluji.model.api;



import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.model.entiy.HttpResult;
import com.kingja.yaluji.model.entiy.Login;
import com.kingja.yaluji.model.service.ApiService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：和ApiService相关联
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/13 15:11
 * 修改备注：
 */
public class UserApi {

    private ApiService apiService;

    public UserApi() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new TokenHeadInterceptor())
//                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public Observable<HttpResult<Login>> login(String userName, String password, String deviceId, String deviceName,
                                               String osName) {
        return apiService.login(userName, password, deviceId, deviceName, osName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<HttpResult<Object>> register(String mobile, String password, String code) {
        return apiService.register(mobile, password, code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
