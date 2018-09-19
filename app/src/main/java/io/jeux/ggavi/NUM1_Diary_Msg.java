package io.jeux.ggavi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class NUM1_Diary_Msg extends AppCompatActivity {
    private int Day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num1_diary_msg_activity);
        // ActionBar Hide!
        Initiate_Diary();
    }

    private void Initiate_Diary() {
        SharedPreferences Data_Box = getApplicationContext().getSharedPreferences("Data_Box", MODE_PRIVATE);
        Day = Data_Box.getInt("Day", 0);

//   Reducing Hungry And Increasing Damage According To Day
        if (Day >= 1) {
            int Temp_One_Hungry = Data_Box.getInt("Family_One_Hungry", 100);
            int Temp_Two_Hungry = Data_Box.getInt("Family_Two_Hungry", 100);
            int Temp_One_Thirst = Data_Box.getInt("Family_One_Thirst", 100);
            int Temp_Two_Thirst = Data_Box.getInt("Family_Two_Thirst", 100);


            /*
            // 하루가 지나면 배고픔, 목마름 지수가 마이너스 시킨다.
            // 그런데 이 구간에서 마이너스 시킬 경우 불러오기 할때마다 마이너스 되는 버그 발생
            Random r = new Random();
            int Random_One_Value = r.nextInt(30 - 10) + 10;
            Temp_One_Hungry=Temp_One_Hungry-Random_One_Value;

            int Random_Two_Value = r.nextInt(30 - 10) + 10;
            Temp_Two_Hungry=Temp_Two_Hungry-Random_Two_Value;

            int Random_One_Value2 = r.nextInt(30 - 10) + 10;
            Temp_One_Thirst=Temp_One_Thirst-Random_One_Value2;

            int Random_Two_Value2 = r.nextInt(30 - 10) + 10;
            Temp_Two_Thirst=Temp_Two_Thirst-Random_Two_Value2;
            */


            int Temp_Global_Damage, Temp_Family_One_Damage, Temp_Family_Two_Damage;
            Temp_Global_Damage = Data_Box.getInt("Global_Damage", -1);
            Temp_Global_Damage = Temp_Global_Damage - Day;
            Temp_Family_One_Damage = Data_Box.getInt("Family_One_Damage", -1);
            Temp_Family_One_Damage = Temp_Family_One_Damage - Day;
            Temp_Family_Two_Damage = Data_Box.getInt("Family_Two_Damage", -1);
            Temp_Family_Two_Damage = Temp_Family_Two_Damage - Day;
            int Temp_One_Hp = Data_Box.getInt("Family_One_Hp", 100);
            int Temp_Two_Hp = Data_Box.getInt("Family_Two_Hp", 100);
            Temp_One_Hp = Temp_One_Hp + Temp_Family_One_Damage;
            Temp_Two_Hp = Temp_Two_Hp + Temp_Family_Two_Damage;

            SharedPreferences.Editor editor = Data_Box.edit();
            editor.putInt("Family_One_Hungry", Temp_One_Hungry);
            editor.putInt("Family_Two_Hungry", Temp_Two_Hungry);

            editor.putInt("Family_One_Thirst", Temp_One_Thirst);
            editor.putInt("Family_Two_Thirst", Temp_Two_Thirst);


            editor.putInt("Family_One_Hp", Temp_One_Hp);
            editor.putInt("Family_Two_Hp", Temp_Two_Hp);
            editor.putInt("Global_Damage", Temp_Global_Damage);
            editor.putInt("Family_One_Damage", Temp_Family_One_Damage);
            editor.putInt("Family_Two_Damage", Temp_Family_Two_Damage);
            editor.commit();
        }
        TextView Day_No_View = findViewById(R.id.Day_No_View_Id);
        TextView Diary_Message_View = findViewById(R.id.Diary_View_Id);
        Button Next_Button = findViewById(R.id.Next_Button_Id);

// Getting Day No And Set Diary Message From String Array Saved In String File

        String Diary_Message[] = getResources().getStringArray(R.array.Diary_Messages);
        Day_No_View.setText("Day " + Day);
        Diary_Message_View.setText(Diary_Message[Day]);


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        Day_No_View.clearAnimation();
        Day_No_View.setAnimation(anim);

        Animation anim_1 = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        Diary_Message_View.clearAnimation();
        Diary_Message_View.setAnimation(anim_1);

        Typeface Day_font = Typeface.createFromAsset(getAssets(), "fonts/font2.ttf");
        Typeface Msg_font = Typeface.createFromAsset(getAssets(), "fonts/msg_font.otf");
        Day_No_View.setTypeface(Day_font);
        //  Diary_Message_View.setTypeface(Day_font);


        Next_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Launch_Activity = new Intent(NUM1_Diary_Msg.this, NUM2_Game_Main_Page.class);
                startActivity(Launch_Activity);
                finish();
            }
        });
    }

}

