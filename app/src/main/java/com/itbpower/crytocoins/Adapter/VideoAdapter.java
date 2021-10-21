package com.itbpower.crytocoins.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itbpower.crytocoins.Pojo.DanceArray;
import com.itbpower.crytocoins.R;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VieHolder> {
    private List<DanceArray> danceArrayList=new ArrayList<>();
    Context context;
    itemClickListner clickOnInterface;
    String YOUTUBE_IMAGE_FRONT = "http://img.youtube.com/vi/";
    String YOUTUBE_IMAGE_BACK = "/hqdefault.jpg";





    public VideoAdapter(Context context, List<DanceArray> danceArrayList) {
        this.danceArrayList=danceArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bitcoin_list, parent, false);
        VieHolder vieHolder = new VieHolder(view);
        return vieHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull VieHolder holder, final int position) {
        holder.tv_title.setText(danceArrayList.get(position).getTitle());
        Glide.with(context).load(YOUTUBE_IMAGE_FRONT+ danceArrayList.get(position).getVideoID()+YOUTUBE_IMAGE_BACK).placeholder(R.drawable.ic_launcher_background).into(holder.iv_image);
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnInterface.listeners(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return danceArrayList.size();
    }

    public class VieHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_title;
        LinearLayout ll_layout;

        public VieHolder(@NonNull View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
            tv_title=itemView.findViewById(R.id.tv_title);
            ll_layout=itemView.findViewById(R.id.ll_layout);


        }
    }

    public interface itemClickListner{
        public void listeners(int position);
    }
    public void setClickOnInterface(itemClickListner clickOnInterface){
        this.clickOnInterface=clickOnInterface;
    }
}