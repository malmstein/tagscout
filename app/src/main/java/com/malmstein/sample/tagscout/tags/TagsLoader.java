/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.malmstein.sample.tagscout.tags;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

/**
 * Custom {@link android.content.Loader} for a list of {@link Tag}, using the
 * {@link com.malmstein.sample.tagscout.data.TagRepository} as its source. This Loader is a {@link AsyncTaskLoader} so it queries
 * the data asynchronously.
 */
public class TagsLoader extends AsyncTaskLoader<List<Tag>> {

    private TagRepository repository;

    public TagsLoader(Context context, @NonNull TagRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    public List<Tag> loadInBackground() {
        return repository.getTags();
    }

    @Override
    public void deliverResult(List<Tag> data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }

}
