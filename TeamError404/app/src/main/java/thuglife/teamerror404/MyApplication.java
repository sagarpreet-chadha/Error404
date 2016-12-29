package thuglife.teamerror404;

import android.content.Intent;

import com.activeandroid.ActiveAndroid;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by Lenovo on 29-12-2016.
 */

public class MyApplication extends com.activeandroid.app.Application implements BootstrapNotifier {
    private static final String TAG = ".MyApplicationName";
    private RegionBootstrap regionBootstrap;

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

        Region TestRoom  = new Region("Test Room",nameSpaceId, Identifier.parse("0x0117c59825E9"),null);
        Region Git = new Region("Git Room",nameSpaceId,Identifier.parse("0x0117c55be3a8"),null);
        Region Android = new Region("Android Room",nameSpaceId,Identifier.parse("0x0117c552c493"),null);
        Region iOS = new Region("iOS Room",nameSpaceId,Identifier.parse("0x0117c55fc452"),null);
        Region Python = new Region("Python Room",nameSpaceId,Identifier.parse("0x0117c555c65f"),null);
        Region Office = new Region("Office",nameSpaceId,Identifier.parse("0x0117c55d6660"),null);
        Region Ruby = new Region("Ruby Room",nameSpaceId,Identifier.parse("0x0117c55ec086"),null);


        regionBootstrap = new RegionBootstrap(this, TestRoom);
        regionBootstrap = new RegionBootstrap(this, Git);
        regionBootstrap = new RegionBootstrap(this, Android);
        regionBootstrap = new RegionBootstrap(this, iOS);
        regionBootstrap = new RegionBootstrap(this, Python);
        regionBootstrap = new RegionBootstrap(this, Office);


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
        Intent intent = new Intent(this, NearBuyActivity.class);
        // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
        // created when a user launches the activity manually and it gets launched from here.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void didExitRegion(Region arg0) {
        // Don't care
    }        }
