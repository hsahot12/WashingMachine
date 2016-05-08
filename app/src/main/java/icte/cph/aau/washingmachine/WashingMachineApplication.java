package icte.cph.aau.washingmachine;

import android.app.Application;
import android.content.Context;

import com.auth0.core.Strategies;
import com.auth0.googleplus.GooglePlusIdentityProvider;
import com.auth0.lock.Lock;
import com.auth0.lock.LockBuilder;
import com.auth0.lock.LockProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Harjit on 28/04/16.
 */
public class WashingMachineApplication extends Application implements LockProvider {
    private static WashingMachineApplication washingMachineApplication;

    private Lock lock;

    public void onCreate() {
        super.onCreate();
        washingMachineApplication = this;

        lock = new LockBuilder()
                .loadFromApplication(this)
                .withIdentityProvider(Strategies.GooglePlus, new GooglePlusIdentityProvider(this))
                .build();

        RealmConfiguration configuration = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
/*
    }
        lock = new Lock.Builder()
                .loadFromApplication(this)
                .withIdentityProvider(Strategies.GooglePlus, new GooglePlusIdentityProvider(this))
                .closable(true)
                .build();
                */
    }

    public static WashingMachineApplication getInstance() {
        return washingMachineApplication;
    }

    public static Context getContext() {
        return washingMachineApplication.getApplicationContext();
    }


    @Override
    public Lock getLock() {
        return lock;
    }
}