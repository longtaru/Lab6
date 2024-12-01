package mob.longnd.slide6;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "NotificationChannel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBai1 = findViewById(R.id.btn_bai1);
        Button btnBai2Start = findViewById(R.id.btn_bai2_start);
        Button btnBai2Stop = findViewById(R.id.btn_bai2_stop);
        Button btnBai3 = findViewById(R.id.btn_bai3);
        Button btnBai4 = findViewById(R.id.btn_bai4);

        btnBai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });

        btnBai2Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, ForegroundService.class);
                startService(serviceIntent);
            }
        });

        btnBai2Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, ForegroundService.class);
                stopService(serviceIntent);
            }
        });
        btnBai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
                startService(serviceIntent);
            }
        });

        btnBai4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(Worker.class).build();
                WorkManager.getInstance(MainActivity.this).enqueue(workRequest);
            }
        });
    }

    private void createNotification() {
        createNotificationChannel();

        // Tạo Bitmap từ tài nguyên hình ảnh
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_fpt_polytechnic_hn); // Đảm bảo bạn có hình ảnh này trong `res/drawable`

        // Tạo Notification với Bitmap
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay) // Biểu tượng nhỏ cho notification
                .setContentTitle("Android 2")
                .setContentText("Welcome to FPT Polytechnic")
                .setLargeIcon(largeIcon) // Đặt Bitmap làm hình ảnh lớn
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeIcon) // Hiển thị Bitmap lớn trong notification
                        .bigLargeIcon((Bitmap) null)) // Không hiển thị icon lớn mặc định
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .addAction(R.drawable.button_icon, BUTTON_TEXT, actionPendingIntent);
                .build();

        // Hiển thị notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, notification);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Lab 6",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}