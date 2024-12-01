package mob.longnd.slide6;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BackgroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Handler().postDelayed(() -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://caodang.fpt.edu.vn"));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(browserIntent);
        }, 5000);

        return START_NOT_STICKY;
    }
}