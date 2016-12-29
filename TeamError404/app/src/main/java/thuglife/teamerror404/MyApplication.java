package thuglife.teamerror404;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 29-12-2016.
 */

public class MyApplication extends com.activeandroid.app.Application implements BootstrapNotifier {
    private static final String TAG = ".MyApplicationName";
    private RegionBootstrap regionBootstrap;
    private ArrayList<ItemResponse.ItemAvailable> availbleItems;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
               beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Identifier nameSpaceId = Identifier.parse("0x5dc33487f02e477d4058");

        Region TestRoom  = new Region("Dell",nameSpaceId, Identifier.parse("0x0117c59825E9"),null);
        Region Git = new Region("Raymonds",nameSpaceId,Identifier.parse("0x0117c55be3a8"),null);
        Region Android = new Region("Nike",nameSpaceId,Identifier.parse("0x0117c552c493"),null);
        Region iOS = new Region("Mc Donald",nameSpaceId,Identifier.parse("0x0117c55fc452"),null);
        Region Python = new Region("Peter England",nameSpaceId,Identifier.parse("0x0117c555c65f"),null);
        Region Office = new Region("OnePlus",nameSpaceId,Identifier.parse("0x0117c55d6660"),null);
        Region Ruby = new Region("Ruby Room",nameSpaceId,Identifier.parse("0x0117c55ec086"),null);


        regionBootstrap = new RegionBootstrap(this, TestRoom);
        regionBootstrap = new RegionBootstrap(this, Git);
        regionBootstrap = new RegionBootstrap(this, Android);
        regionBootstrap = new RegionBootstrap(this, iOS);
        regionBootstrap = new RegionBootstrap(this, Python);
        regionBootstrap = new RegionBootstrap(this, Office);
        regionBootstrap = new RegionBootstrap(this, Python);


    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        // Don't care
    }

    @Override
    public void didEnterRegion(Region arg0) {
        // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
        // if you want the Activity to launch every single time beacons come into view, remove this call.
        regionBootstrap.disable();
        availbleItems = new ArrayList<>();
        FileUploadService service = Client.getService();
        Call<ItemResponse> call = service.getShops(arg0.getId2().toString());
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("----","Yes Response is successful !");
                    ItemResponse i = response.body();
                    if(i.getItemAvailableArrayList()!=null) {
                        availbleItems.addAll(i.getItemAvailableArrayList());
                        Intent intent = new Intent(MyApplication.this, NearBuyActivity.class);
                        intent.putExtra("NearBuyList",availbleItems);
                        // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
                        // created when a user launches the activity manually and it gets launched from here.
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyApplication.this.startActivity(intent);                    }

                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.i("----","Response Failed :"+t.getMessage());

            }
        });

    }

    @Override
    public void didExitRegion(Region arg0) {
        // Don't care
    }        }
