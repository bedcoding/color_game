package io.jeux.ggavi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;


public class NUM4_Run_And_Find extends AppCompatActivity implements SensorEventListener {


    private int Collected_Food,Collected_Water;
    private int View_Collected_Food,View_Collected_Water;

    int Temp_Step_Taken;
    TextView Steps_View,Collected_Food_View,Collected_Water_View;
    SharedPreferences Data_Box;




    // 만보기 변수선언 투척
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    //////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num4_run_find_activity);
        Temp_Step_Taken=Collected_Food=Collected_Water=0;
        View_Collected_Food=View_Collected_Water=0;

        Data_Box= getApplicationContext().getSharedPreferences("Data_Box",MODE_PRIVATE);
        Image_Setting();
        Initiating_Views();





        // 만보기 기능 투척
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Steps_View = (TextView) findViewById(R.id.Steps_Taken_View_Id);
        Steps_View.setText("" + Temp_Step_Taken);
        /////////////////////////
    }







    // 만보기 함수 투척


    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 120) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Temp_Step_Taken++;

                    if(Temp_Step_Taken%100==0)
                    {
                        Collected_Food=Collected_Food+2;
                        Collected_Water++;

                    }else
                    {
                        Collected_Food=0;
                        Collected_Water=0;
                    }
                    Put_And_Update();
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    // ↑ 여기까지가 만보기 관련 함수
    /////////////////////////////////////////////////////////////////////






    private void Initiating_Views()
    {
        Chronometer Chronometer_View=findViewById(R.id.Chronometer_View_Id);
        Steps_View=findViewById(R.id.Steps_Taken_View_Id);
        Collected_Food_View=findViewById(R.id.Collected_Food_View_Id);
        Collected_Water_View=findViewById(R.id.Collected_Water_View_Id);
        Button Click_Button=findViewById(R.id.Run_Button_Id);
        Button End_Walk=findViewById(R.id.Stop_walk_Button_Id);
        Put_And_Update();

        Chronometer_View.setBase(SystemClock.elapsedRealtime());
        Chronometer_View.start();


        // 마우스 클릭
        Click_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp_Step_Taken++;
                if(Temp_Step_Taken%100==0)
                {
                    Collected_Food=Collected_Food+2;
                    Collected_Water++;

                }else
                {
                    Collected_Food=0;
                    Collected_Water=0;
                }
                Put_And_Update();

            }
        });





        End_Walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Temp_Day=Data_Box.getInt("Day",0);
                Temp_Day++;
                SharedPreferences.Editor editor = Data_Box.edit();
                editor.putInt("Day",Temp_Day);
                editor.commit();
                startActivity(new Intent(NUM4_Run_And_Find.this,NUM1_Diary_Msg.class));
                finish();
            }
        });
    }


    private void Put_And_Update()
    {
        View_Collected_Food=View_Collected_Food+Collected_Food;
        View_Collected_Water=View_Collected_Water+Collected_Water;

        Steps_View.setText(Temp_Step_Taken+"");
        Collected_Food_View.setText(View_Collected_Food+"");
        Collected_Water_View.setText(View_Collected_Water+"");

        int Food=Data_Box.getInt("Food",1);
        int Water=Data_Box.getInt("Water",1);

        Food=Food+Collected_Food;
        Water=Water+Collected_Water;

        SharedPreferences.Editor editor = Data_Box.edit();
        editor.putInt("Food",Food);
        editor.putInt("Water",Water);
        editor.commit();
    }

    private void Image_Setting()
    {
        int Day=Data_Box.getInt("Day",0);
        TypedArray Images_Array=getResources().obtainTypedArray(R.array.Images_Data);
        String Image_Description[]=getResources().getStringArray(R.array.Image_Description);

        ImageView Status_Image=findViewById(R.id.WalkImage_View_Id);
        TextView Status_Image_Description=findViewById(R.id.WalkImage_Des_View_Id);
        Status_Image.setImageResource(Images_Array.getResourceId(Day,-1));
        Status_Image_Description.setText(Image_Description[Day]);
        Images_Array.recycle();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        startActivity(new Intent(NUM4_Run_And_Find.this,NUM2_Game_Main_Page.class));
        finish();
    }

}
