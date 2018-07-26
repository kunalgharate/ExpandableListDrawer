package info.mores.expandablelistdrawer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import info.mores.expandablelistdrawer.R;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listTitle;
    public static int childCount;
    private Map<String, List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listItem.get(listTitle.get(i)).size() > 0 ? listItem.get(listTitle.get(i)).size() : 0;

        // return listItem.size();
    }


    @Override
    public Object getGroup(int i) {
        return listTitle.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String title = (String) getGroup(i);
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_group, null);
        }

        TextView titleText = view.findViewById(R.id.listTitle);
        ImageView imageView = view.findViewById(R.id.imgView);
        titleText.setText(title);

        childCount = getChildrenCount(i);

        if (childCount > 1) {
            //INFLATE EMPTY VIEW
            imageView.setImageResource(R.drawable.drop_down_24dp);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        String title = getChild(groupPosition, childPosition).toString();
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        }

        TextView childText = view.findViewById(R.id.expandableListItem);
        childText.setText(title);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
