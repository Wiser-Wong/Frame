package com.wiser.frame;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wiser.library.base.IWISERBind;
import com.wiser.library.manager.WISERManage;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtBind implements IWISERBind {

	@Override public Retrofit getRetrofit(Retrofit.Builder builder) {
		OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
		// 超时
		okHttpBuilder.connectTimeout(180, TimeUnit.SECONDS);
		okHttpBuilder.readTimeout(180, TimeUnit.SECONDS);
		okHttpBuilder.writeTimeout(180, TimeUnit.SECONDS);


//		//组装参数
//		requParams.addBodyParameter("websiteCode", SharedPreferencesUtils.get(QuantityString.WEBSITECODE));
//		requParams.addBodyParameter("data", requesxml);
//		requParams.addBodyParameter("service", methodname);
//		requParams.addBodyParameter("version", QuantityString.VERSION);
//		requParams.addBodyParameter("responseType", "2");
////			if(methodname.indexOf("PAY")>-1){
////				requParams.addBodyParameter("channel", "IOS");//测试支付先
////			}else {
//		requParams.addBodyParameter("channel", QuantityString.CHANNEL);//测试支付先
////			}
//		requParams.addBodyParameter("compid",SharedPreferencesUtils.get(QuantityString.COMPID));
//
////			String account = SharedPreferencesUtils.get(QuantityString.ACCOUNT) + Md5Encrypt.md5(SharedPreferencesUtils.get(QuantityString.KEY));
////			String md5 = Md5Encrypt.md5(Md5Encrypt.md5(account)+ requesxml + "VETRIP_B2G");// 接口授权
////			String md5 =Md5Encrypt.md5(requesxml+Md5Encrypt.md5(SharedPreferencesUtils.get(QuantityString.KEY)));
//		String md5=Md5Encrypt.md5(Md5Encrypt.md5(SharedPreferencesUtils.get(QuantityString.COMPID))+requesxml+"VETRIP_B2G");
//		requParams.addBodyParameter("sign", md5);
//		requParams.addBodyParameter("account", SharedPreferencesUtils.get(QuantityString.ACCOUNT));
//		requParams.addBodyParameter("mkbh", mkbh);//"0114008013"
//		requParams.addBodyParameter("gnbh",gnbh);//"0688"
//		requParams.addBodyParameter("adsbid", AppInfoUtils.getANDROID_ID());//安卓设备ID
//		requParams.addBodyParameter("bbh",AppInfoUtils.getAppInfo().getVersionCode()+"");//版本号
//		String jpush_rid =   SharedPreferencesUtils.get(QuantityString.JPUSH_RID);
//		if(!TextUtils.isEmpty(jpush_rid)){
//			requParams.addBodyParameter("zcid","ADM_"+jpush_rid);//注册ID
//		}else{
//			requParams.addBodyParameter("zcid","");//注册ID
//		}

		builder.client(okHttpBuilder.build());

		// 服务器地址域名
		builder.baseUrl("http://116.204.24.77:10000/");
		// Gson转换器
		Gson gson = new GsonBuilder().setLenient().create();
		builder.addConverterFactory(GsonConverterFactory.create(gson));

		return builder.build();
	}

	@Override public WISERManage getManage() {
		return new WISERManage();
	}
}
