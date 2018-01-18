package com.majoapps.serendipity;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mark on 13/02/2016.
 */
public class LevelsAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String,List<String>> Levels_Category;
    private List<String> Levels_List;


    public LevelsAdapter(Context ctx, HashMap<String,List<String>> Levels_Category, List<String> Levels_List)
    {
        this.ctx = ctx;
        this.Levels_Category = Levels_Category;
        this.Levels_List = Levels_List;
    }


    @Override
    public Object getChild(int parent, int child) {

        return Levels_Category.get(Levels_List.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public View getChildView(final int parentPosition, final int childPosition, boolean lastChild, View convertView, ViewGroup parentView) {

        final String child_title = (String) getChild(parentPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(R.layout.child_layout, parentView, false);

        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);

        return convertView;
    }

    @Override
    public int getChildrenCount(int arg0) {

        return Levels_Category.get(Levels_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Levels_List.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return Levels_List.size();
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String group_title = (String) getGroup(parent);
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


}
