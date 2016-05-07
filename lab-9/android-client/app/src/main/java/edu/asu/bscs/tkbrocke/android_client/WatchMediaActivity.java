/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 9 - Android
 * @version				April 19, 2016
 * @project-description	Get movie data from two sources and play movie if file exists.
 * @class-name			WatchMediaActivity.java
 * @class-description	Plays the movie file if it exists (it always should if it gets to this point).
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Tyler Brockett
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.asu.bscs.tkbrocke.android_client;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class WatchMediaActivity extends AppCompatActivity {

    VideoView player;
    private MediaController controller;

    String filename = "";
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_watch_media);

        try {
            filename = getIntent().getStringExtra("filename");
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving URL for video", Toast.LENGTH_LONG).show();
            finish();
        }

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position", 0);
        }

        String fileUrl = getString(R.string.mediastreamerserver) + "/" + filename;

        player = (VideoView)findViewById(R.id.player);
        player.setVideoPath(fileUrl);
        controller = new MediaController(this);
        controller.setAnchorView(player);
        player.setMediaController(controller);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(position);
                mp.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putInt("position", player.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

}
