package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.DataNotAvailableError;
import com.malmstein.sample.tagscout.domain.UseCase;

import java.util.List;

public class FilterTagsUseCase extends UseCase<FilterTagsUseCase.RequestValues, FilterTagsUseCase.ResponseValue> {

    private final TagRepository tagRepository;

    public FilterTagsUseCase(TagRepository tasksRepository) {
        tagRepository = tasksRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        tagRepository.filterTags(getRequestValues().getFilter(), new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                ResponseValue responseValue = new ResponseValue(tags);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError(new DataNotAvailableError());
            }
        });
    }

    public static class RequestValues extends UseCase.RequestValues {
        private final String filter;

        public RequestValues(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }
    }

    public class ResponseValue extends UseCase.ResponseValue {
        private List<Tag> tags;

        public ResponseValue(List<Tag> tags) {
            this.tags = tags;
        }

        public List<Tag> getTags() {
            return tags;
        }
    }
}
