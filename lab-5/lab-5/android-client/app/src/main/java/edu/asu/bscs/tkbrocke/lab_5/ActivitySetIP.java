package edu.asu.bscs.tkbrocke.lab_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySetIP extends AppCompatActivity {

    Button saveIP;
    EditText ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);
        saveIP = (Button)findViewById(R.id.btn_save_ip);
        ipAddress = (EditText)findViewById(R.id.et_ip_address);
        ipAddress.setText(MainActivity.ipAddress);
        saveIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ipAddress = ipAddress.getText().toString();
                ServerRequest request = new ServerRequest(v.getContext(), MainActivity.ipAddress, ServerRequest.GET_ALL, new String[]{});
                new NetworkAsyncTask(v.getContext(), "Successfully Loaded Movies", "Failure Loading Movies").execute(request);
                Toast.makeText(v.getContext(), "New IP Address Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
