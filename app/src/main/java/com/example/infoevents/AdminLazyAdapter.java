package com.example.infoevents;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class AdminLazyAdapter extends BaseAdapter {
    
    private Activity activity;
    
   // private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private List<Events> eventItems;
    
    public AdminLazyAdapter(Activity a,  List<Events> eventItems) {
        activity = a;
      //  data=d;
        this.eventItems = eventItems;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return eventItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    
    
    public View getView( int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_listview_item, null);

        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        
        Events event = eventItems.get(position);
        UrlPaths u=new UrlPaths();
        text.setText(event.getEvent_name());
        imageLoader.DisplayImage(u.urladdr()+"/upload/"+ event.getImgnm(), image);
       
        vi.setOnClickListener(new OnItemClickListener(position) );
        
       
        
        
        return vi;
    }
    public class OnItemClickListener extends Activity implements OnClickListener {


        private int position;

        public OnItemClickListener(int p) {

            position = p;   
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), AdminViewEvent.class);
            Events ev = eventItems.get(position);
		    intent.putExtra("Eventnm", ev.Event_name);
		    intent.putExtra("Evdate", ev.E_date);
		    intent.putExtra("Uemail", ev.Uemail);
		    intent.putExtra("Evinfo", ev.E_info);
		    intent.putExtra("Addr", ev.Address);
		    intent.putExtra("Eventinm", ev.Imgnm);
            v.getContext().startActivity(intent);

        }

    }
   
}