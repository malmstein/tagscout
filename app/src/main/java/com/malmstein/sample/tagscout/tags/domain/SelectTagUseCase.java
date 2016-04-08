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
        Tag selectedTag = requestValues.getSelectedTag();
        tagRepository.toggleTagSelection(selectedTag);
        Tag toggleTag = new Tag(selectedTag.getId(), selectedTag.getTag(), selectedTag.getColor(), !selectedTag.isSelected());
        getUseCaseCallback().onSuccess(new ResponseValue(tagRepository.getCachedTags(), toggleTag));
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
        private Tag toggledTag;

        public ResponseValue(List<Tag> tags, Tag toggledTag) {
            this.tags = tags;
            this.toggledTag = toggledTag;
        }

        public List<Tag> getTags() {
            return tags;
        }

        public Tag getToggledTag() {
            return toggledTag;
        }
    }

}
