package com.malmstein.sample.tagscout.tags;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.injection.Injection;

public class TagsActivity extends AppCompatActivity {

    private TagsPresenter tagsPresenter;
    private TagsView tagsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        tagsView = (TagsView) findViewById(R.id.tags_view);

        tagsPresenter = new TagsPresenter(Injection.provideUseCaseHandler(), Injection.provideRetrieveTagsUseCase(), tagsView);

        if (savedInstanceState == null) {
            tagsPresenter.loadTags();
        }
    }

}
