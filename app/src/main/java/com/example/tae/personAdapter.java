package com.example.tae;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class personAdapter extends FirebaseRecyclerAdapter<
        person, personAdapter.personsViewholder> {
    AdapterView.OnItemClickListener itemClickListener;
    //private final View.OnClickListener mOnClickListener;
    //= new MyOnClickListener();
    public personAdapter(
            @NonNull FirebaseRecyclerOptions<person> options, AdapterView.OnItemClickListener itemClickListener)
    {
        super(options);
        //this.onCreateViewHolder()
      this.itemClickListener=itemClickListener;
    }


    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull person model)
    {
        holder.busno.setText(model.getBusno());
        holder.drivername.setText(model.getDrivername());
        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.mob.setText(model.getMob());
        holder.from.setText(model.getFrom());
        holder.to.setText(model.getTo());
    }
    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewlayoutdesign_buslist, parent, false);
        return new personAdapter.personsViewholder(view);
    }
    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder extends RecyclerView.ViewHolder {
        TextView drivername, busno, mob,from,to;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            drivername = itemView.findViewById(R.id.textname);
            busno = itemView.findViewById(R.id.textbusno);
            mob = itemView.findViewById(R.id.textmob);
            from=itemView.findViewById(R.id.textfrom);
            to=itemView.findViewById(R.id.textto);
        }
    }
}           