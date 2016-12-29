package thuglife.teamerror404;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class MonitoringActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    TextView beacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        beacons =(TextView)findViewById(R.id.beacons);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
               setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        Identifier myBeaconNameSapceID = Identifier.parse("0x5dc33487f02e477d4058");

        Region Git = new Region("Test Room",myBeaconNameSapceID, Identifier.parse("0x0117c55be3a8"),null);
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
                Toast.makeText(MonitoringActivity.this,"I just saw an beacon !",Toast.LENGTH_SHORT).show();
                beacons.setText(region.getUniqueId());
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
                Toast.makeText(MonitoringActivity.this,"I no longer see an beacon !",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
                Toast.makeText(MonitoringActivity.this,"Swtiched !",Toast.LENGTH_SHORT).show();
                beacons.setText(region.getUniqueId());
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(Git);
        } catch (RemoteException e) {    }
    }

}
