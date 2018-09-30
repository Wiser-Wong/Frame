package com.wiser.frame;

import retrofit2.Call;
import retrofit2.http.POST;

public interface IHttp {

	@POST("openapi/b2couter/api/airticket/queryCabin") Call<String> getData();

}
