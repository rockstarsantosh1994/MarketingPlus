package com.soulsoft.marketingplus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soulsoft.marketingplus.R;
import com.soulsoft.marketingplus.model.consumer.ConsumerBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsumerAdapter extends RecyclerView.Adapter<ConsumerAdapter.ConsumerAdapterViewHolder>{

    private Context context;
    private ArrayList<ConsumerBO> consumerBOArrayList;

    public ConsumerAdapter(Context context, ArrayList<ConsumerBO> consumerBOArrayList) {
        this.context = context;
        this.consumerBOArrayList = consumerBOArrayList;
    }

    @NonNull
    @Override
    public ConsumerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_consumer_layout,parent,false);
        return new ConsumerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumerAdapterViewHolder holder, int position) {

        holder.tvName.setText(consumerBOArrayList.get(position).getFirstname()+" "+consumerBOArrayList.get(position).getLastname());
        holder.tvEmail.setText(consumerBOArrayList.get(position).getEmailid());
        holder.tvMobileNumber.setText(consumerBOArrayList.get(position).getPricontact()+" , "+consumerBOArrayList.get(position).getSeccontact());
        holder.tvAddress.setText(consumerBOArrayList.get(position).getUaddress());

        if(consumerBOArrayList.get(position).getBirthdate()!=null){
            holder.tvBirthDate.setVisibility(View.VISIBLE);
            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format2.parse(consumerBOArrayList.get(position).getBirthdate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strDate=format1.format(date);
            holder.tvBirthDate.setText(strDate);
        }else{
            holder.tvBirthDate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return consumerBOArrayList.size();
    }

    public class ConsumerAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.tv_mobile_number)
        TextView tvMobileNumber;
        @BindView(R.id.tv_birthdate)
        TextView tvBirthDate;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ConsumerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
