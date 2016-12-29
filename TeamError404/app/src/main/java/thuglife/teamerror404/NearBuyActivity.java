package thuglife.teamerror404;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class NearBuyActivity extends AppCompatActivity {

    TextView nearByShops;
    ArrayList<ItemResponse.ItemAvailable> arrayList;
    RecyclerView recyclerView;
    private AdapterNearBuy adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_buy);
        nearByShops = (TextView)findViewById(R.id.textStatic);
        recyclerView = (RecyclerView)findViewById(R.id.nearByItemsRecyclerView);
        arrayList = new ArrayList<>();
        arrayList = (ArrayList<ItemResponse.ItemAvailable>) getIntent().getSerializableExtra("NearBuyList");

        adapter = new AdapterNearBuy(arrayList,this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        Log.i("----","Receiving data size "+arrayList.size());
        if(arrayList.size()!=0)
        Log.i("----","Receiving data  "+arrayList.get(0).getName());

        adapter.notifyDataSetChanged();

    }
}
