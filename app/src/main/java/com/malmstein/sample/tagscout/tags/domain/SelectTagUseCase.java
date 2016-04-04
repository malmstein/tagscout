package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCase;

import java.util.List;

public class SelectTagUseCase extends UseCase<SelectTagUseCase.RequestValues, SelectTagUseCase.ResponseValue> {

    private final TagRepository tagRepository;

    public SelectTagUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        tagRepository.toggleTagSelection(requestValues.getSelectedTag());
        getUseCaseCallback().onSuccess(new ResponseValue(tagRepository.getCachedTags()));
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

        private List<Tag> tags;

        public ResponseValue(List<Tag> tags) {
            this.tags = tags;
        }

        public List<Tag> getTags() {
            return tags;
        }
    }

}
