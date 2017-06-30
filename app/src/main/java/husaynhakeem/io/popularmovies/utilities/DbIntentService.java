package husaynhakeem.io.popularmovies.utilities;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class DbIntentService extends IntentService {


    public DbIntentService() {
        super(DbIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String action = intent.getAction();
        DbTasks.executeTask(this, action);
    }
}
