package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCase;

public class SelectTagUseCase extends UseCase<SelectTagUseCase.RequestValues, SelectTagUseCase.ResponseValue> {

    private final TagRepository tagRepository;

    public SelectTagUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Tag selectedTag = requestValues.getSelectedTag();
        tagRepository.selectTag(selectedTag);
        getUseCaseCallback().onSuccess(new ResponseValue());
    }

    public static class RequestValues extends UseCase.RequestValues {

        private final Tag selectedTag;

        public RequestValues(Tag selectedTag) {
            this.selectedTag = selectedTag;
        }

        public Tag getSelectedTag() {
            return selectedTag;
        }
    }

    public class ResponseValue extends UseCase.ResponseValue {
    }

}
