package com.example.chatappv2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LlamaChat {
    @POST("chat")
    Call<ResponseMessage> postMessage(@Body PostBody postBody);
}
