package com.malmstein.sample.tagscout.tags;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsAdapter extends BaseAdapter {

    private List<Tag> tags;

    private TagItemListener tagItemListener;

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
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Tag getItem(int i) {
        return tags.get(i);
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
