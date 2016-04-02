package com.malmstein.sample.tagscout.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FakeTagRemoteDataSource implements TagDataSource {

    private static FakeTagRemoteDataSource INSTANCE;

    private final Gson gson;

    public static FakeTagRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTagRemoteDataSource(new Gson());
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FakeTagRemoteDataSource(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> value;
        Type listType = new TypeToken<ArrayList<Tag>>() {}.getType();
        try {
            value = gson.fromJson(FakeJson.tags, listType);
        } catch (Exception e) {
            return null;
        }
        return value;
    }

}
