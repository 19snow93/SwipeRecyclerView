package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import lly.com.swiperecyclerview.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> implements SimpleItemTouchHelperCallback.ItemTouchHelperAdapter {

    private Context context;
    private List<String> data;
    private View headerView;
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_NOMAL = 0;

    public RecycleViewAdapter(Context context,List<String> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_HEAD && headerView != null){
            view = headerView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_recycleview, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.MyViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(holder);
        holder.tv.setText(data.get(pos));
        if (mOnItemClickListener != null) {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position -1;
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 :data.size());
        if(headerView != null){
            count++;
        }
        return count;
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            if(view == headerView)
                return;
            tv = (TextView)view.findViewById(R.id.tv_recyclerview);
        }
    }


    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(data, from, to);
        notifyItemMoved(from, to);

    }

    @Override
    public int getItemViewType(int position) {
        if(headerView == null)
            return TYPE_NOMAL;
        if(position == 0)
            return TYPE_HEAD;
        return TYPE_NOMAL;
    }

    public void setHeaderView(View headerView){
        this.headerView = headerView;
        headerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        notifyItemInserted(0);
    }

    public View getHeaderView(){
        return headerView;
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
