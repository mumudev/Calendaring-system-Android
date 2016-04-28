package com.mumudev.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mumudev.timemanager.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 木木工程师 on 2016/4/27.
 */
public class CourseAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public CourseAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Module{
        public TextView course_name;
        public TextView teacher;
        public TextView course_time;
        public TextView classroom;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Module module = null;
        if(convertView == null){
            module = new Module();
            convertView = layoutInflater.inflate(R.layout.course_list,null);
            module.course_name = (TextView)convertView.findViewById(R.id.course_name);
            module.teacher = (TextView)convertView.findViewById(R.id.teacher);
            module.course_time = (TextView)convertView.findViewById(R.id.course_time);
            module.classroom = (TextView)convertView.findViewById(R.id.classroom);
        }else{
            module = (Module)convertView.getTag();
        }
        module.course_name.setText((String)data.get(position).get("course_name"));
        module.teacher.setText((String)data.get(position).get("teacher"));
        module.course_time.setText((String)data.get(position).get("course_time"));
        module.classroom.setText((String)data.get(position).get("classroom"));
        return convertView;
    }
}
