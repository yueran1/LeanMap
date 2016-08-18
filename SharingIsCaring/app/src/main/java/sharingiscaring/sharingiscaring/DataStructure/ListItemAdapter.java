package sharingiscaring.sharingiscaring.DataStructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.R;

/**
 * Created by Ryan on 2016-04-03.
 * Reference code: FabiF and raam86 @
 * http://stackoverflow.com/questions/11106418/how-to-set-adapter-in-case-of-multiple-textviews-per-listview
 */
public class ListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ListItem> objects;

    private class ViewHolder {
        TextView name;
        TextView offer;
        TextView status;
        TextView description;
        TextView footer;
    }

    public ListItemAdapter(Context context, ArrayList<ListItem> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public ListItem getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.nameListItem);
            holder.offer = (TextView) convertView.findViewById(R.id.offerListItem);
            holder.status = (TextView) convertView.findViewById(R.id.statusListItem);
            holder.description = (TextView) convertView.findViewById(R.id.descriptionListItem);
            holder.footer = (TextView) convertView.findViewById(R.id.footerListItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(objects.get(position).getName());
        holder.offer.setText(objects.get(position).getOffer());
        holder.status.setText(objects.get(position).getStatus());
        holder.description.setText(objects.get(position).getDescription());
        return convertView;
    }
}
