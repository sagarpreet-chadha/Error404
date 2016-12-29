package thuglife.teamerror404;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-12-2016.
 */

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.OurHolder>{

    ArrayList<DashboardItem> dashboardItemArrayList;
    Context context;
    AdapterInterface adapterInterface;

    public interface AdapterInterface {
        void notifyItemClicked(DashboardItem m);
    }



    public AdapterDashboard() {
    }

    public AdapterDashboard(ArrayList<DashboardItem> dashboardItemArrayList, Context context, AdapterInterface adapterInterface) {
        this.dashboardItemArrayList = dashboardItemArrayList;
        this.context = context;
        this.adapterInterface = adapterInterface;
    }

    public class OurHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemCategory;

        public OurHolder(View itemView) {
            super(itemView);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemCategory = (TextView)itemView.findViewById(R.id.item_category);

        }
    }
    @Override
    public AdapterDashboard.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_dashboard,parent , false);
        return new OurHolder(v);

    }

    @Override
    public void onBindViewHolder(AdapterDashboard.OurHolder holder, final int position) {
        holder.itemName.setText(dashboardItemArrayList.get(position).name);
        holder.itemCategory.setText(dashboardItemArrayList.get(position).category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterInterface.notifyItemClicked(dashboardItemArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashboardItemArrayList.size();
    }
}
