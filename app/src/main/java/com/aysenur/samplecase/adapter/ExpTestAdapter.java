package com.aysenur.samplecase.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.view.ExpTestMain;
import com.aysenur.samplecase.view.NotEditable;

import java.util.List;

public class ExpTestAdapter extends BaseExpandableListAdapter  {
    public static final String EXTRA_TITLE ="EXTRA_TITLE";
    public static final String EXTRA_DESC ="EXTRA_DESC";
    private Context context;
    List<Event> expandableListDetail;
    Button btn_location;
    Button btn_detail;

    public ExpTestAdapter(Context context) {
        this.context=context;

    }

    public void setData(List<Event> eventList){
        this.expandableListDetail=eventList;
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
        listTitleTextview.setText(listTitle);

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
        return expandableListDetail.get(listPosition).getJobName();
        //return expandedListPostion;
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
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }



    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=layoutInflater.inflate(R.layout.list_child,null);

        }
        btn_location=convertView.findViewById(R.id.btn_go_location);
        btn_detail=convertView.findViewById(R.id.btn_detail);

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=expandableListDetail.get(listPosition).getJobName();
                String desc= expandableListDetail.get(listPosition).getJobDescription();

                Intent data= new Intent(context, NotEditable.class);
                data.putExtra(EXTRA_TITLE,title);
                data.putExtra(EXTRA_DESC,desc);
                context.startActivity(data);
                /*if (callback !=null){
                    callback.detailButtonOnClick(view,listPosition);
                }*/
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=expandableListDetail.get(listPosition).getJobName();
                Intent data= new Intent(context, ExpTestMain.class);
                data.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                data.putExtra(EXTRA_TITLE,title);
                context.startActivity(data);
                Toast.makeText(context, "okay"+expandableListDetail.get(listPosition).getJobName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    public interface ButtonClick{
        void detailButtonOnClick(View v);
    }

    private ButtonClick callback;

    public void setButtonClickListener(ButtonClick listener){
        this.callback=listener;
    }
}
