package com.wiser.frame;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IHttp {

	@POST("openapi/b2couter/api/airticket/queryCabin") Call<String> getData();

	@POST("openapi/b2couter/api/airticket/queryCabin") Observable<String> getObservableData();

	@POST("openapi/b2couter/api/airticket") Observable<String> getObservableData1(@Query("value") String value);

	@GET("claim/20190921/7175_5d85e1beeb998e467caaddf2.pdf") Observable<String> getPDF();

}
