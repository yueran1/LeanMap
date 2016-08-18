package sharingiscaring.sharingiscaring.DataStructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.R;

/**
 * Created by Ryan on 2016-04-03.
 * Reference code: FabiF and raam86 @
 * http://stackoverflow.com/questions/11106418/how-to-set-adapter-in-case-of-multiple-textviews-per-listview
 */
public class ListOfferAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ListOffer> objects;

    private class ViewHolder {
        TextView name;
        TextView offer;
        TextView acceptButton;
        TextView declineButton;
    }

    public ListOfferAdapter(Context context, ArrayList<ListOffer> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public ListOffer getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_offer, null);
            holder.name = (TextView) convertView.findViewById(R.id.nameListOffer);
            holder.offer = (TextView) convertView.findViewById(R.id.offerListOffer);
            holder.acceptButton = (TextView) convertView.findViewById(R.id.acceptListOffer);
            holder.declineButton = (TextView) convertView.findViewById(R.id.declineListOffer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(objects.get(position).getName());
        holder.offer.setText(objects.get(position).getOffer());
        return convertView;
    }
}
