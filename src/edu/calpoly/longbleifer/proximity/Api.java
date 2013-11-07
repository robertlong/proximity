package edu.calpoly.longbleifer.proximity;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

interface Api {
	@GET("/triggers/{uuid}")
	void findBeacon(@Path("uuid") String uuid, Callback<Trigger> cb);
}
