package com.wiser.frame;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IHttp {

	@GET("v/a") Call<ABean> get();

}
