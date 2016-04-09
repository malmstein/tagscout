package com.malmstein.sample.tagscout.tags;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends BaseAdapter implements Filterable {

    private List<Tag> tags;
    private List<Tag> filteredTags;

    private TagItemListener tagItemListener;
    private TagsFilter tagsFilter;

    public TagsAdapter(List<Tag> tags, TagItemListener itemListener) {
        setList(tags);
        this.tagItemListener = itemListener;
    }

    public void replaceData(List<Tag> tags) {
        setList(tags);
        notifyDataSetChanged();
    }

    private void setList(List<Tag> tags) {
        this.tags = tags;
        this.filteredTags = tags;
    }

    @Override
    public int getCount() {
        return filteredTags.size();
    }

    @Override
    public Tag getItem(int i) {
        return filteredTags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;

        if (rowView == null) {
            rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        final Tag tag = getItem(position);

        holder.textView.setText(tag.getTag());
        holder.selectedView.setVisibility(tag.isSelected() ? View.VISIBLE : View.GONE);
        holder.rowView.setSelected(tag.isSelected());
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagItemListener.onTagSelected(tag);
            }
        });

        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (tagsFilter == null) {
            tagsFilter = new TagsFilter();
        }

        return tagsFilter;
    }

    private class TagsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = tags;
                results.count = tags.size();
            } else {
                ArrayList<Tag> filteredTags = new ArrayList<>();
                for (Tag tag : tags) {
                    if (tag.getTag().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filteredTags.add(tag);
                    }
                }
                results.values = filteredTags;
                results.count = filteredTags.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredTags = (ArrayList<Tag>) results.values;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder {
        public final View rowView;
        public final TextView textView;
        public final ImageView selectedView;

        public ViewHolder(View view) {
            rowView = view;
            textView = (TextView) view.findViewById(R.id.tag_title);
            selectedView = (ImageView) view.findViewById(R.id.tag_selected);
        }
    }

    public interface TagItemListener {

        void onTagSelected(Tag clickedTag);

    }

}
