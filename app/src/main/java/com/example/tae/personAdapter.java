package com.example.tae;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
public class personAdapter extends FirebaseRecyclerAdapter<
        person, personAdapter.personsViewholder> {

    public personAdapter(
            @NonNull FirebaseRecyclerOptions<person> options)
    {
        super(options);
    }
    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull person model)
    {
        holder.drivername.setText(model.getDrivername());
        holder.textbusno.setText(model.getBusno());
        holder.textmob.setText(model.getMob());
        holder.textfrom.setText(model.getFrom());
        holder.textto.setText(model.getTo());
    }
    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person, parent, false);
        return new personsViewholder(view);
    }
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView drivername,textbusno,textmob,textfrom,textto;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            drivername= itemView.findViewById(R.id.drivername);
            textbusno=itemView.findViewById(R.id.textbusno);
            textfrom=itemView.findViewById(R.id.textfrom);
            textto=itemView.findViewById(R.id.textto);
        }
    }
}
