package com.example.yesno;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;

import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://yesno.wtf/";
    private Button button;
    private TextView answer;
    private Retrofit retrofitAnswer;
    private ProgressBar progressBar;
    private ImageView image;
    private EditText plainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button = findViewById(R.id.button);
        this.answer = findViewById(R.id.answer);
        this.image = findViewById(R.id.image);
        this.image.setVisibility(View.INVISIBLE);
        this.plainText = findViewById(R.id.field);

        this.progressBar = findViewById(R.id.progressBar);
        this.progressBar.setVisibility(View.INVISIBLE);

        this.retrofitAnswer = new Retrofit.Builder()
                .baseUrl(URL) // Endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) // Conversor para usar Gson
                .build();


        button.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        closeKeyboard();
        if(plainText == null || plainText.length() == 0) {
            Toast.makeText(getApplicationContext(), "Você deve digitar uma pergunta!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v.getId() == R.id.button) {
            consultarResposta();
        }
    }

    private void consultarResposta() {

        RestService restService = this.retrofitAnswer.create(RestService.class);

        Call<Answer> call = restService.getAnswer();

        image.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                if (response.isSuccessful()) {
                    Answer answerResponse = response.body();
                    answer.setText(answerResponse.getAnswer());


                    Glide.with(MainActivity.this)
                            .asGif().placeholder(R.drawable.placeholder)
                            .load(answerResponse.getImage())
                            .into(image);

                    Toast.makeText(getApplicationContext(), "Resposta consultada com sucesso", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar consultar a resposta. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                image.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}