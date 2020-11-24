package com.aysenur.samplecase.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.db.model.Event;

import java.util.List;

public class ExpTestAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<Event> expandableListDetail;

    public ExpTestAdapter(Context context, List<Event> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;

    }
    public void setEvents(List<Event> events){
        this.expandableListDetail=events;
        notifyDataSetChanged();

    }
    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle= (String) getGroup(listPosition);
        if (convertView == null){
            LayoutInflater layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView listTitleTextview=convertView.findViewById(R.id.tvPackedName);
        listTitleTextview.setTypeface(null, Typeface.BOLD);
        listTitleTextview.setText(expandableListDetail.get(listPosition).getJobName());
        //listTitleTextview.setText(listTitle);

        return convertView;
    }
    @Override
    public int getGroupCount() {
        return this.expandableListDetail.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }


    //adress bilgisi set edilcek
    @Override
    public Object getGroup(int listPosition) {
        return expandableListDetail.get(listPosition).getJobName();
    }

    @Override
    public Object getChild(int listPosition, int expandedListPostion) {
        return expandedListPostion;
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=layoutInflater.inflate(R.layout.list_child,null);
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
