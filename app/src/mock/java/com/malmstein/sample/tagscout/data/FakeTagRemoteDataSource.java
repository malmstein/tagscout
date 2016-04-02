package com.malmstein.sample.tagscout.data;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.malmstein.sample.tagscout.data.model.GsonTag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FakeTagRemoteDataSource implements TagDataSource {

    private static FakeTagRemoteDataSource INSTANCE;

    private final AssetManager assetManager;
    private final Gson gson;

    public static FakeTagRemoteDataSource getInstance(AssetManager assetManager, Gson gson) {
        if (INSTANCE == null) {
            INSTANCE = new FakeTagRemoteDataSource(assetManager, gson);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FakeTagRemoteDataSource(AssetManager assetManager, Gson gson) {
        this.assetManager = assetManager;
        this.gson = gson;
    }

    @Override
    public void getTags(@NonNull LoadTagsCallback callback) {
        GsonTag value;
        try {
            value = readFileAs("tags.json", GsonTag.class);
        } catch (IOException e) {
            callback.onDataNotAvailable();
            return;
        }
        callback.onTagsLoaded(value.tags);
    }

    private <T> T readFileAs(String file, Class<T> type) throws IOException {
        InputStream is = null;
        try {
            is = assetManager.open("json/" + file);
            return gson.fromJson(new InputStreamReader(is, Charset.forName("UTF-8")), type);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
