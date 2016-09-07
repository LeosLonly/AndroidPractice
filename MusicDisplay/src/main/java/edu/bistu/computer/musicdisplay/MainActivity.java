package edu.bistu.computer.musicdisplay;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myTag";
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start = (Button) findViewById(R.id.start);
        final Button pause = (Button) findViewById(R.id.pause);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button loop = (Button) findViewById(R.id.loop);
        final TextView isLoopText = (TextView) findViewById(R.id.isLooping);

        player = new MediaPlayer();

        pause.setEnabled(false);
        stop.setEnabled(false);
        loop.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "start");
                player.reset();
                AssetManager manager = getAssets();
                try {
                    AssetFileDescriptor descriptor = manager.openFd("a.mp3");
                    player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                            descriptor.getLength());
                    player.prepare();
                    player.start();

                    pause.setEnabled(true);
                    stop.setEnabled(true);
                    loop.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    pause.setText("Play");
                    player.pause();
                } else {
                    pause.setText("Pause");
                    player.start();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.stop();
                }
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Looping");
                boolean isLooping = player.isLooping();
                player.setLooping(!isLooping);

                if (!isLooping) {
                    isLoopText.setText("循环播放");
                } else {
                    isLoopText.setText("一次播放");
                }
            }
        });
    }
}
