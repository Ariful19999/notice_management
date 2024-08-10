package com.arif19.vbd.graphQlApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CountryService {
    @Headers("Content-Type: application/json")
    @POST("/")
    Call<GraphQLResponse> getCountries(@Body GraphQLQuery query);
}
