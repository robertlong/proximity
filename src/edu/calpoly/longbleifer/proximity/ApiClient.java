package edu.calpoly.longbleifer.proximity;
import retrofit.RestAdapter;

public class ApiClient {
	private static final String API_URL = "http://proximity-server.herokuapp.com/api";
	
	public static Api buildApi() {
		RestAdapter restAdapter = new RestAdapter.Builder()
	    .setServer(API_URL)
	    .build();
		
		return restAdapter.create(Api.class);
	}
}
