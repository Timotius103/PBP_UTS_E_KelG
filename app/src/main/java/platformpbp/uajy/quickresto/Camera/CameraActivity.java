package platformpbp.uajy.quickresto.Camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.acl.Permission;

import platformpbp.uajy.quickresto.Home;
import platformpbp.uajy.quickresto.Profile;
import platformpbp.uajy.quickresto.ProfileEdit;
import platformpbp.uajy.quickresto.R;
import platformpbp.uajy.quickresto.ReservationMenu;

public class CameraActivity extends AppCompatActivity  {

    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        try
        {
            mCamera = Camera.open();
        }
        catch(Exception e)
        {
            Log.d("Error", "Failed to Get Camera" + e.getMessage());
        }
        if(mCamera != null)
        {
            mCameraView = new CameraView(this, mCamera);
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
            camera_view.addView(mCameraView);
        }
        ImageButton imageClose = (ImageButton)findViewById(R.id.imgClose);
        imageClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CameraActivity.this, ProfileEdit.class);
                startActivity(intent);
            }
        });

        ImageButton FaceCam = (ImageButton)findViewById(R.id.backCam);
        FaceCam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                }
                catch(Exception e)
                {
                    Log.d("Error", "Failed to Get Camera" + e.getMessage());
                }
                if(mCamera != null)
                {
                    mCameraView = new CameraView(CameraActivity.this, mCamera);
                    FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
                    camera_view.addView(mCameraView);
                }
            }
        });
    }

//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent)
//    {
//        Sensor mySensor = sensorEvent.sensor;
//        if(mySensor.getType() == Sensor.TYPE_PROXIMITY)
//        {
//            if(sensorEvent.values[0]==0)
//            {
//                Toast.makeText(getApplicationContext(),"Kamera Depan", Toast.LENGTH_SHORT).show();
//                try
//                {
//                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
//                }
//                catch(Exception e)
//                {
//                    Log.d("Error", "Failed to Get Camera" + e.getMessage());
//                }
//                if(mCamera != null)
//                {
//                    mCameraView = new CameraView(this, mCamera);
//                    FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
//                    camera_view.addView(mCameraView);
//                }
//                ImageButton imageClose = (ImageButton)findViewById(R.id.imgClose);
//                imageClose.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//
//                        System.exit(0);
//                        Intent intent = new Intent(CameraActivity.this, ProfileEdit.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(),"Kamera Belakang", Toast.LENGTH_SHORT).show();
//                try
//                {
//                    mCamera = Camera.open();
//                }
//                catch(Exception e)
//                {
//                    Log.d("Error", "Failed to Get Camera" + e.getMessage());
//                }
//                if(mCamera != null)
//                {
//                    mCameraView = new CameraView(this, mCamera);
//                    FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
//                    camera_view.addView(mCameraView);
//                }
//                ImageButton imageClose = (ImageButton)findViewById(R.id.imgClose);
//                imageClose.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//                        System.exit(0);
//                    }
//                });
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i)
//    {
//
//    }
//
//    protected void onResume()
//    {
//        super.onResume();
//        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    protected void onPause()
//    {
//        super.onPause();
//        mSensorManager.unregisterListener(this);
//    }
}