package com.example.countdowntimer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countdowntimer.MainActivity;
import com.example.countdowntimer.R;
import com.example.countdowntimer.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Event> eventArrayList;
    TextView eventName;
    TextView eventDate;
    TextView eventAlarm;
    ImageButton eventDelete;


    //simple constructor
    public Adapter(ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    //we inflate view from layout file
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_countdown_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //bind data here
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        eventName.setText(event.getName());
        String date = new SimpleDateFormat("dd-MM-yyyy").format(event.getDate());
        eventDate.setText(date);
        if(event.getAlarm() == null){
            eventAlarm.setText(R.string.no_alarm);
            //TODO add some color if there is no alarm?
            //eventAlarm.setTextColor(626873);
        }else
            eventDate.setText(R.string.alarm_enabled);




    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventAlarm = itemView.findViewById(R.id.eventAlarm);
            eventDelete = itemView.findViewById(R.id.eventDelete);
            eventDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    MainActivity.getInstance().saveData();
                }
            });


        }
    }
}
