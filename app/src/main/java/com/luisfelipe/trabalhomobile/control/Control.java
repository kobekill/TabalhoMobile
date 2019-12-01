package com.luisfelipe.trabalhomobile.control;

import android.app.Activity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.luisfelipe.trabalhomobile.R;
import com.luisfelipe.trabalhomobile.dao.AnimalDao;
import com.luisfelipe.trabalhomobile.dao.RacaDao;
import com.luisfelipe.trabalhomobile.model.Animal;
import com.luisfelipe.trabalhomobile.model.Raca;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Control {
    private Activity activity;

    private Spinner spinnerRaca;
    private EditText nomeedit;

    private AnimalDao animalDao;

    private RacaDao racaDao;
    private List<Raca> racas;
    private ArrayAdapter<Raca> adapterRaca;

    public Control(Activity activity) {
        this.activity = activity;

        animalDao = new AnimalDao(activity);
        racaDao = new RacaDao(activity);
        racas = new ArrayList<>();


        initcomponets();

        requisicaoRaca();



    }

    private void initcomponets(){
        spinnerRaca = activity.findViewById(R.id.sppinerRaca);
        nomeedit = activity.findViewById(R.id.editNome);
    }

    public void configListView() {

        adapterRaca = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                racas
        );
        spinnerRaca.setAdapter(adapterRaca);
    }

    public void requisicaoRaca() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.56.1:8080/teste1/api/raca", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(activity, "Iniciando requisição", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                racas.clear();
                String racaJson = new String(bytes);

                Gson g = new Gson();

                Type type = new TypeToken<List<Raca>>() {
                }.getType();

                               racas = g.fromJson(racaJson, type);


                adapterRaca = new ArrayAdapter<>(
                        activity,
                        android.R.layout.simple_list_item_1,
                        racas
                );
                Toast.makeText(activity, ""+ racaJson, Toast.LENGTH_SHORT).show();
                spinnerRaca.setAdapter(adapterRaca);
                for (Raca r : racas) {

                    try {
                        racaDao.getDao().createIfNotExists(r);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }



            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(activity, "Erro na requisição", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }
        });
    }

    public void requisicaoPostAnimal() {
        final Animal animal = new Animal();
        animal.setNome(nomeedit.getText().toString());
        animal.setRaca((Raca)spinnerRaca.getSelectedItem());


        Gson g = new Gson();

        RequestParams params = new RequestParams("params", g.toJson(animal));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.56.1:8080/teste1/api/animal", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(activity, "Iniciando requisição", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    animalDao.getDao().create(animal);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(activity, "Sucesso na requisição", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(activity, "Erro na requisição", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void salvarAction() {
        requisicaoPostAnimal();

    }

}
