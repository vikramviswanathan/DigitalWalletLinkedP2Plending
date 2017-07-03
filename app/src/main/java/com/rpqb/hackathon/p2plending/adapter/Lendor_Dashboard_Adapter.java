package com.rpqb.hackathon.p2plending.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rpqb.hackathon.p2plending.R;
import com.rpqb.hackathon.p2plending.model.Project;

import java.util.ArrayList;

/**
 * Created by Vikramv on 6/16/2017.
 */

public class Lendor_Dashboard_Adapter extends RecyclerView.Adapter<Lendor_Dashboard_Adapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private ArrayList<Project> projectList;

    public Lendor_Dashboard_Adapter(Context mContext, ArrayList<Project> projectList) {
        this.mContext = mContext;
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public CardView dashboard_cardView;
        public LinearLayout linearMainHolder;
        public RelativeLayout relativeListHolder;
        public TextView projectTitle, loanAmount, interest, userId;

        public ViewHolder(View itemView) {
            super(itemView);
            //dashboard_cardView = (CardView) itemView.findViewById(R.id.dashboard_cardView);
            linearMainHolder = (LinearLayout) itemView.findViewById(R.id.lendor_dashboard_linearMainHolder);
            relativeListHolder = (RelativeLayout) itemView.findViewById(R.id.lendor_dashboard_relativeListHolder);
            projectTitle = (TextView) itemView.findViewById(R.id.lendor_dashboard_projectTitle);
            loanAmount = (TextView) itemView.findViewById(R.id.lendor_dashboard_loanAmount);
            interest = (TextView) itemView.findViewById(R.id.lendor_dashboard_interest);
            userId = (TextView) itemView.findViewById(R.id.lendor_dashboard_userId);
            linearMainHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView);
            }
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lendor_dashboard_grid_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.dashboard_cardView.setCardBackgroundColor(Color.MAGENTA);
        Project project = projectList.get(position);
        holder.projectTitle.setText(project.getTitle());
        holder.loanAmount.setText(String.valueOf(project.getLoanamount()));
        holder.interest.setText(String.format("%0.2f", String.valueOf(project.getInterest()) + "%"));
        holder.userId.setText(project.getUserid());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}