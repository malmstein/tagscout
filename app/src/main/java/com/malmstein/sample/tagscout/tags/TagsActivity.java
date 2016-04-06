package com.malmstein.sample.tagscout.tags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.injection.Injection;
import com.malmstein.sample.tagscout.tags.view.TagsView;

public class TagsActivity extends AppCompatActivity {

    private TagsPresenter tagsPresenter;
    private TagsView tagsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        tagsView = (TagsView) findViewById(R.id.tags_view);
        tagsPresenter = new TagsPresenter(Injection.provideUseCaseHandler(),
                                          Injection.provideRetrieveTagsUseCase(),
                                          Injection.provideSelectTagUseCase(),
                                          tagsView);
        tagsView.setPresenter(tagsPresenter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tagsPresenter.loadTags();
    }
}
