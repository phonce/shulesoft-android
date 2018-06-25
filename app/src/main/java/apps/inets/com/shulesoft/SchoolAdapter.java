package com.example.android.shulesoft;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 20 Jun 2018.
 */

public class SchoolAdapter extends ArrayAdapter<School> implements Filterable {


    private ArrayList<School> mSchools;

    private CustomFilter mFilter;

    ArrayList<School> mFilterList;


    public SchoolAdapter(Activity context, ArrayList<School> schools){
        super(context,0,schools);
        mSchools = schools;
        mFilterList = schools;

    }

    @Override
    public int getCount(){
        return mSchools.size();
    }
    @Override
    public School getItem(int pos){
        return mSchools.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return mSchools.indexOf(getItem(pos));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.schools_list_item, parent, false);

        }

         TextView nameView = (TextView) listItemView.findViewById(R.id.school_name);

        School currentSchool = getItem(position);
        nameView.setText(currentSchool.getName().toString());

        return listItemView;
    }

    @Override
    public Filter getFilter(){
        if(mFilter == null){
            mFilter = new CustomFilter();
        }

        return mFilter;
    }

    private class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<School> filters=new ArrayList<School>();

                //get specific items
                for(int i=0;i<mFilterList.size();i++)
                {
                    if(mFilterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                         School school=new School(mFilterList.get(i).getName(), mFilterList.get(i).getUrl());

                        filters.add(school);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=mFilterList.size();
                results.values=mFilterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mSchools = (ArrayList<School>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
