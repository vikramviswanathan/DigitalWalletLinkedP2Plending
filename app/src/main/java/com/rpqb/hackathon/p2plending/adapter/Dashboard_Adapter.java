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

public class Dashboard_Adapter extends RecyclerView.Adapter<Dashboard_Adapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public Dashboard_Adapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearMainHolder;
        public RelativeLayout relativeListHolder;
        public TextView projectTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            linearMainHolder = (LinearLayout) itemView.findViewById(R.id.dashboard_linearMainHolder);
            relativeListHolder = (RelativeLayout) itemView.findViewById(R.id.dashboard_relativeListHolder);
            projectTitle = (TextView) itemView.findViewById(R.id.dashboard_projectTitle);
            linearMainHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView);
            }
        }
    }

    public ArrayList<Project> getProjectList() {
        ArrayList<Project> projectList = new ArrayList<Project>();
        String[] placeNameArray = {"Test1", "Test2"};
        for (String projectTitle : placeNameArray) {
            Project project = new Project();
            project.setProjectTitle(projectTitle);

            projectList.add(project);
        }

        return projectList;
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
        Project project = getProjectList().get(position);
        holder.projectTitle.setText(project.getProjectTitle());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return getProjectList().size();
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