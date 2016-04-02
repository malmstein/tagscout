package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public interface TagDataSource {

    List<Tag> getTags();

}
