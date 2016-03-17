package edu.asu.bscs.tkbrocke.lab_5;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CustomDialog extends Dialog {

    Button leftButton, middleButton, rightButton;
    TextView title, subtitle;
    ProgressBar titleProgress, subtitleProgress;

    public CustomDialog(Context context){
        super(context);
    }

    public void setContentView(int resId){
        super.setContentView(resId);
        title = (TextView)findViewById(R.id.title);
        subtitle = (TextView)findViewById(R.id.subtitle);
        titleProgress = (ProgressBar)findViewById(R.id.title_progress);
        subtitleProgress = (ProgressBar)findViewById(R.id.subtitle_progress);
        leftButton = (Button)findViewById(R.id.left_button);
        middleButton = (Button)findViewById(R.id.middle_button);
        rightButton = (Button)findViewById(R.id.right_button);
    }

    public void createTitle(String text){
        title.setText(text);
        title.setVisibility(View.VISIBLE);
    }

    public void createSubtitle(String text){
        subtitle.setText(text);
        subtitle.setVisibility(View.VISIBLE);
    }

    public void createTitleProgress(){
        titleProgress.setVisibility(View.VISIBLE);
    }

    public void createSubtitleProgress(){
        subtitleProgress.setVisibility(View.VISIBLE);
    }

    public void createLeftButton(String text){
        leftButton.setText(text);
        leftButton.setVisibility(View.VISIBLE);
    }

    public void createMiddleButton(String text){
        middleButton.setText(text);
        middleButton.setVisibility(View.VISIBLE);
    }

    public void createRightButton(String text){
        rightButton.setText(text);
        rightButton.setVisibility(View.VISIBLE);
    }

    public Button getLeftButton(){
        return leftButton;
    }

    public Button getMiddleButton(){
        return middleButton;
    }

    public Button getRightButton(){
        return rightButton;
    }
    
}
