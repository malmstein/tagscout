package com.malmstein.sample.tagscout.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteTagDataSource implements TagDataSource {

    private static RemoteTagDataSource INSTANCE;

    private final OkHttpClient client;
    private final Gson gson;

    public static RemoteTagDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteTagDataSource(new OkHttpClient(), new Gson());
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private RemoteTagDataSource(OkHttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    @Override
    public void getTags(LoadTagsCallback callback) {
        Request request = new Request.Builder()
                .get()
                .url("http://mockbin.org/bin/8053044c-a645-4b17-b020-6d53fa5abedd")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                callback.onDataNotAvailable();
            } else {
                List<Tag> value;
                Type listType = new TypeToken<ArrayList<Tag>>() {
                }.getType();
                value = gson.fromJson(response.body().charStream(), listType);
                callback.onTagsLoaded(value);
            }

        } catch (IOException e) {
            callback.onDataNotAvailable();
        }

    }

}
