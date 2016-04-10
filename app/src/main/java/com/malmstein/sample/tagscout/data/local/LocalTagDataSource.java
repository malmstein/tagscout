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

package com.malmstein.sample.tagscout.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalTagDataSource implements TagDataSource {

    private static LocalTagDataSource INSTANCE;

    private TagDbHelper dbHelper;

    // Prevent direct instantiation.
    private LocalTagDataSource(@NonNull Context context) {
        dbHelper = new TagDbHelper(context);
    }

    public static LocalTagDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalTagDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Note: {@link com.malmstein.sample.tagscout.data.TagDataSource.LoadTagsCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getTags(LoadTagsCallback callback) {
        List<Tag> tags = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TagPersistenceContract.TagEntry.COLUMN_NAME_ENTRY_ID,
                TagPersistenceContract.TagEntry.COLUMN_NAME_TAG,
                TagPersistenceContract.TagEntry.COLUMN_NAME_COLOR,
                TagPersistenceContract.TagEntry.COLUMN_NAME_SELECTED
        };

        Cursor c = db.query(
                TagPersistenceContract.TagEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int itemId = c.getInt(c.getColumnIndexOrThrow(TagPersistenceContract.TagEntry.COLUMN_NAME_ENTRY_ID));
                String tagText = c.getString(c.getColumnIndexOrThrow(TagPersistenceContract.TagEntry.COLUMN_NAME_TAG));
                String color = c.getString(c.getColumnIndexOrThrow(TagPersistenceContract.TagEntry.COLUMN_NAME_COLOR));
                boolean selected = c.getInt(c.getColumnIndexOrThrow(TagPersistenceContract.TagEntry.COLUMN_NAME_SELECTED)) == 1;
                Tag tag = new Tag(itemId, tagText, color, selected);
                tags.add(tag);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (tags.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onTagsLoaded(tags);
        }
    }

    @Override
    public void filterTags(String query, LoadTagsCallback callback) {
        // Not required for the local data source because the {@link TagRepository} handles
        // filtering using the cached data.
    }

    @Override
    public void deleteAllTags() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TagPersistenceContract.TagEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void toggleTagSelection(Tag tag) {

    }

}
