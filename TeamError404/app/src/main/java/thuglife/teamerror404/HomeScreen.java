package thuglife.teamerror404;

import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeandroid.query.Delete;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity implements AdapterDashboard.AdapterInterface,BeaconConsumer {

    AdapterDashboard adapterDashboard;
    RecyclerView recyclerView;
    ArrayList<DashboardItem> dashboardItems;
    private View addItem;
    private BeaconManager beaconManager;
    ArrayList<String> beaconIDList;
    ArrayList<Region> regionList;
    private Region TestRoom,Git,Android,iOS,Python,Office,Ruby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.bind(this);
        beaconIDList=new ArrayList<>();
        regionList = new ArrayList<>();


        Identifier nameSpaceId = Identifier.parse("0x5dc33487f02e477d4058");

         TestRoom  = new Region("Test Room",nameSpaceId, Identifier.parse("0x0117c59825E9"),null);
         Git = new Region("Git Room",nameSpaceId,Identifier.parse("0x0117c55be3a8"),null);
         Android = new Region("Android Room",nameSpaceId,Identifier.parse("0x0117c552c493"),null);
         iOS = new Region("iOS Room",nameSpaceId,Identifier.parse("0x0117c55fc452"),null);
         Python = new Region("Python Room",nameSpaceId,Identifier.parse("0x0117c555c65f"),null);
         Office = new Region("Office",nameSpaceId,Identifier.parse("0x0117c55d6660"),null);
         Ruby = new Region("Ruby Room",nameSpaceId,Identifier.parse("0x0117c55ec086"),null);


        dashboardItems = new ArrayList<>();
        dashboardItems.addAll(new DashboardItem().getAllItems());
        Log.i("----","Adapter Size "+dashboardItems.size());
        adapterDashboard = new AdapterDashboard(dashboardItems,this,this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapterDashboard);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                addItem = View.inflate(HomeScreen.this,R.layout.dialog_add_item,null);
                AlertDialog.Builder db = new AlertDialog.Builder(HomeScreen.this);
                final EditText itemName = (EditText) addItem.findViewById(R.id.edittext_name);
                final EditText itemCategory = (EditText) addItem.findViewById(R.id.edittext_category);
                db.setView(addItem);
                db.setTitle("Add Item");
                db.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DashboardItem dashboardItem = new DashboardItem();
                        dashboardItem.setUUID(UUID.randomUUID());
                        Log.i("----","Saving with UUID"+dashboardItem.getUUID());
                        dashboardItem.setName(itemName.getText().toString());
                        dashboardItem.setCategory(itemCategory.getText().toString());
                        dashboardItem.save();
                        dashboardItems.add(dashboardItem);
                        updateData();
                        
                        //adding to server..
                        FileUploadService service = Client.getService();
                        ArrayList<DashboardItem> temp = new ArrayList<DashboardItem>();
                        temp.add(dashboardItem);
                        Call<ResponseBody> call = service.postItem(temp);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.i("----","Added to server");
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i("----","Failed "+t.getMessage());

                            }
                        });

                        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        dialogInterface.dismiss();
                    }
                });
                db.setNeutralButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                db.create().show();

            }
        });

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN  | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int i = viewHolder.getAdapterPosition();
                int j = target.getAdapterPosition();
                DashboardItem first = dashboardItems.get(i);
                DashboardItem second = dashboardItems.get(j);
                DashboardItem temp = first;
                first = second;
                second=temp;
                dashboardItems.set(i,second);
                dashboardItems.set(j,first);

                adapterDashboard.notifyItemMoved(i,j);
                return true;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder db = new AlertDialog.Builder(HomeScreen.this);
                db.setTitle("Delete");
                db.setMessage("Sure , you want to delete?");
                db.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int j = viewHolder.getAdapterPosition();
                        //first deleting from database..
                        new Delete().from(DashboardItem.class).where("name = ?",dashboardItems.get(j).getName()).execute();

                        dashboardItems.remove(j);
                        adapterDashboard.notifyItemRemoved(j);
                    }
                });
                db.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        adapterDashboard.notifyDataSetChanged();
                    }
                });
                db.create().show();

            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    void updateData(){
        dashboardItems.clear();
        dashboardItems.addAll(new DashboardItem().getAllItems());
        adapterDashboard.notifyDataSetChanged();
    }

    @Override
    public void notifyItemClicked(DashboardItem m) {

        
    }

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Toast.makeText(HomeScreen.this,"I just saw an beacon !"+region.getUniqueId(),Toast.LENGTH_SHORT).show();
                Log.i("----","Entered Region "+region.getUniqueId());
                //adding to server..
                FileUploadService service = Client.getService();
                Call<ItemResponse> call = service.getShops(region.getId2().toString());
                call.enqueue(new Callback<ItemResponse>() {
                    @Override
                    public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                        if (response.isSuccessful()) {
                            ItemResponse i = response.body();
                            Log.i("----", "Available Item" + i.getItemAvailableArrayList().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemResponse> call, Throwable t) {
                        Log.i("----","Response Failed :"+t.getMessage());

                    }
                });

            }

            @Override
            public void didExitRegion(Region region) {
                Toast.makeText(HomeScreen.this,"I no longer see an beacon !"+region.getUniqueId(),Toast.LENGTH_SHORT).show();
                Log.i("----","Exit Region "+region.getUniqueId());

            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                switch (state){
                    case INSIDE:
                        Log.i("----","Enter " + region.getUniqueId());
                        beaconIDList.add(region.getId2().toString());
                        regionList.add(region);
                        //adding to server..
                    FileUploadService service = Client.getService();
                    Call<ItemResponse> call = service.getShops(region.getId2().toString());
                    call.enqueue(new Callback<ItemResponse>() {
                        @Override
                        public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                            if (response.isSuccessful()) {
                                ItemResponse i = response.body();
                                
                            }}

                        @Override
                        public void onFailure(Call<ItemResponse> call, Throwable t) {
                            Log.i("----","Response Failed :"+t.getMessage());

                        }
                    });

                        break;
                    case OUTSIDE:
                        Log.i("----","Outside " + region.getUniqueId());
                        if(regionList.contains(region)){
                            regionList.remove(region);
                        }
                        if(beaconIDList!=null)
                        if(beaconIDList.contains(region.getUniqueId())) {
                            beaconIDList.remove(region.getUniqueId());

                        }
                        break;
                }

//                if(state==INSIDE){
//                    Log.i("----","INSIDE:"+region.getUniqueId());
//
//                }
//                if(state==OUTSIDE){
//                    Log.i("----","OUTSIDE:"+region.getUniqueId());
//                }

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(Git);
            beaconManager.startMonitoringBeaconsInRegion(Android);
            beaconManager.startMonitoringBeaconsInRegion(Python);
            beaconManager.startMonitoringBeaconsInRegion(Office);
            beaconManager.startMonitoringBeaconsInRegion(iOS);
            beaconManager.startMonitoringBeaconsInRegion(Ruby);
            beaconManager.startMonitoringBeaconsInRegion(TestRoom);

        } catch (RemoteException e) {    }
    }

    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
}
