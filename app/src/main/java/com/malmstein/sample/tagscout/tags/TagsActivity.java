package com.malmstein.sample.tagscout.tags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.injection.Injection;
import com.malmstein.sample.tagscout.tags.view.TagsContainerView;
import com.malmstein.sample.tagscout.tags.view.TagsListView;

public class TagsActivity extends AppCompatActivity {

    private TagsPresenter tagsPresenter;
    private TagsListView tagsView;
    private TagsContainerView tagsContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        tagsView = (TagsListView) findViewById(R.id.tags_view);
        tagsContainerView = (TagsContainerView) findViewById(R.id.tags_container);

        tagsPresenter = new TagsPresenter(
                Injection.provideUseCaseHandler(),
                Injection.provideRetrieveTagsUseCase(this),
                Injection.provideSelectTagUseCase(this),
                Injection.provideFilterTagsUseCase(this),
                tagsView,
                tagsContainerView
        );

        tagsView.setPresenter(tagsPresenter);
        tagsContainerView.setPresenter(tagsPresenter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tagsPresenter.loadTags();
    }
}
