package com.jtmcompany.waist_guard_project.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jtmcompany.waist_guard_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotiRecyclerAdapter extends RecyclerView.Adapter<NotiRecyclerAdapter.ViewHoler> {

    ArrayList<String> names=new ArrayList<String>();

    //각 아이템을 클릭했을때 선택한사람의 정보(이름,uid)를 전달하기위해 해쉬맵사용
    Map<String,String> name_Uid=new HashMap<>();

    public interface NotiRecyclerViewClcikListener{
        void onAcceptBtClicked(int position, String name,String Uid);
        void onDenyBtClicked(int position, String name,String Uid);
    }

    private NotiRecyclerViewClcikListener mListener;

    public void setOnClickListener(NotiRecyclerViewClcikListener listener){ mListener=listener;}
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.noti_item,parent,false);
        Log.d("TAAK","TEST: "+"onCreateViewHolder");
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoler holder, int position) {
        final String name=names.get(position);
        holder.setItem(name);

        if(mListener!=null){
            holder.friendAcceptBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Uid= name_Uid.get(name);
                    mListener.onAcceptBtClicked(holder.getAdapterPosition(),name,Uid);
                }
            });

            holder.friendDenyBt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String Uid= name_Uid.get(name);
                    mListener.onDenyBtClicked(holder.getAdapterPosition(),name,Uid);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public void addItem(String item){
        if(!names.contains(item))
        names.add(item);
    }

    //해쉬맵을 이용해서 addItem을한 유저의 이름과 uid를저장
    public void addItemHash(String name, String uid){
        if(!(name_Uid.containsKey(name) && name_Uid.containsValue(uid)))
        name_Uid.put(name,uid);
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView requestName;
        Button friendAcceptBt;
        Button friendDenyBt;
        public ViewHoler(View itemView) {
            super(itemView);
            requestName=itemView.findViewById(R.id.Noti_Item_Name);
            friendAcceptBt=itemView.findViewById(R.id.friend_accept);
            friendDenyBt=itemView.findViewById(R.id.friend_deny);

        }

        public void setItem(String name) {
            Log.d("TAAK","TEST: "+name);
            requestName.setText(name);
        }
    }
}
