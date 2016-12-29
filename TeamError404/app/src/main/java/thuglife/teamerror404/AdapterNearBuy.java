package thuglife.teamerror404;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-12-2016.
 */

public class AdapterNearBuy extends RecyclerView.Adapter<AdapterNearBuy.OurHolder>{

        ArrayList<ItemResponse.ItemAvailable> arrayList;
        Context context;

    public AdapterNearBuy(ArrayList<ItemResponse.ItemAvailable> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public AdapterNearBuy() {
    }

    public class OurHolder extends RecyclerView.ViewHolder{

    TextView itemName, itemCategory ,itemShop;

    public OurHolder(View itemView) {
        super(itemView);
        itemName = (TextView)itemView.findViewById(R.id.nearby_item_name);
        itemCategory = (TextView)itemView.findViewById(R.id.nearby_item_category);
        itemShop = (TextView)itemView.findViewById(R.id.nearby_item_Shop);

    }
}
    @Override
    public AdapterNearBuy.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_nearby,parent , false);
        return new OurHolder(v);

    }

    @Override
    public void onBindViewHolder(AdapterNearBuy.OurHolder holder, final int position) {
        holder.itemName.setText(arrayList.get(position).getName());
        holder.itemCategory.setText("Category: " +arrayList.get(position).getCategory());
        holder.itemShop.setText("Shop Name: "+arrayList.get(position).getShop());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
