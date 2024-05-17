package com.example.chatappv2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    Button btn_send;
    EditText et_message;
    RecyclerView rv_chat;

    // Variables for tracking the conversation
    String userMessage;
    List<MessageTuple> chatHistory = new ArrayList<MessageTuple>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);


        // Set up our connection to the chat API
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        LlamaChat chat = retrofit.create(LlamaChat.class);

        // Find our screen elements
        btn_send = findViewById(R.id.btn_send);
        et_message = findViewById(R.id.et_message);
        rv_chat = findViewById(R.id.rv_chat);
        rv_chat.setLayoutManager(new LinearLayoutManager(this));

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMessage = et_message.getText().toString();

                PostBody payload = new PostBody(userMessage, chatHistory);
                Call<ResponseMessage> call = chat.postMessage(payload);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        // Handle unsuccessful requests.
                        if (!response.isSuccessful()) {
                            String responseCode = response.code() + "";
                            Toast.makeText(ChatActivity.this, responseCode, Toast.LENGTH_LONG).show();
                        }
                        // Handle successful requests
                        else {
                            ResponseMessage responseMessage = response.body();
                            chatHistory.add(new MessageTuple(userMessage, responseMessage.getMessage()));

                            ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, chatHistory);
                            rv_chat.setAdapter(chatAdapter);
                            et_message.setText("");
                        }
                    }

                    @Override
                    // Handle failures
                    public void onFailure(Call<ResponseMessage> call, Throwable throwable) {
                        // Handle call failure by displaying error message
                        Toast.makeText(ChatActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}