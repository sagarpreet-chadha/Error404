package thuglife.teamerror404;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by Lenovo on 29-12-2016.
 */

public class BakcgroundActivity extends Application implements BootstrapNotifier {

    private RegionBootstrap regionBootstrap;


    @Override
    public void onCreate() {
        super.onCreate();
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
          beaconManager.getBeaconParsers().add(new BeaconParser().
               setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Region region = new Region("com.example.myapp.boostrapRegion", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void didEnterRegion(Region region) {
        regionBootstrap.disable();
        Intent intent = new Intent(this, HomeScreen.class);
        // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
        // created when a user launches the activity manually and it gets launched from here.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
//
//    private static void showNotification(String message){
//        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle("Status!") // title for notification
//                .setContentText(message) // message for notification
//                .setAutoCancel(true); // clear notification after click
//        NotificationManager mNotificationManager =
//                (NotificationManager) instance.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(0, mBuilder.build());
//
//    }


}

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        }
}
