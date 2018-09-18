package io.jeux.ggavi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NUM2_Game_Main_Page extends AppCompatActivity {

    private int Family_One_Hungry,Family_One_Thirst,Family_One_Hp,Family_One_Damage;
    private int Family_Two_Hungry,Family_Two_Thirst,Family_Two_Hp,Family_Two_Damage;
    private int Food,Water,Damage;
    private TextView Family_One_Hungry_View,Family_One_Thirst_View,Family_One_Hp_View,Family_One_Damage_View;
    private TextView Family_Two_Hungry_View,Family_Two_Thirst_View,Family_Two_Hp_View,Family_Two_Damage_View;
    private TextView Food_View,Water_View,Damage_View;
    private Boolean Family_One_Not_Died=true;
    private Boolean Family_Two_Not_Died=true;

    private SharedPreferences Data_Box;
    AlertDialog.Builder New_Alert_Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num2_main_game_activity);
        New_Alert_Dialog = new AlertDialog.Builder(NUM2_Game_Main_Page.this);
        Data_Box= getApplicationContext().getSharedPreferences("Data_Box",MODE_PRIVATE);
        Getting_Data();
    }


//     Getting Data From Shared Preference For A New Or A Loaded Game
    private void Getting_Data()
    {
            Family_One_Hungry=Data_Box.getInt("Family_One_Hungry",100);
            Family_One_Thirst=Data_Box.getInt("Family_One_Thirst",100);
            Family_One_Hp=Data_Box.getInt("Family_One_Hp",100);
            Family_One_Damage=Data_Box.getInt("Family_One_Damage",-1);
            Family_Two_Hungry=Data_Box.getInt("Family_Two_Hungry",100);
            Family_Two_Thirst=Data_Box.getInt("Family_Two_Thirst",100);
            Family_Two_Hp=Data_Box.getInt("Family_Two_Hp",100);
            Family_Two_Damage=Data_Box.getInt("Family_Two_Damage",-1);
            Water=Data_Box.getInt("Water",1);
            Food=Data_Box.getInt("Food",1);
            Damage=Data_Box.getInt("Global_Damage",-1);
        Family_One_Not_Died=Data_Box.getBoolean("Family_One_Not_Died",true);
        Family_Two_Not_Died=Data_Box.getBoolean("Family_Two_Not_Died",true);

        Initiating_Views();

    }


//     Initiating and Views And Button
    private void Initiating_Views()
    {

//        Family One View Initiating
        Family_One_Hungry_View=findViewById(R.id.Family_One_Hungry_View_Id);
        Family_One_Thirst_View=findViewById(R.id.Family_One_Thirst_View_Id);
        Family_One_Hp_View=findViewById(R.id.Family_One_Hp_View_Id);
        Family_One_Damage_View=findViewById(R.id.Family_One_Damage_View_Id);
        Button family_One_Food_Button = findViewById(R.id.Family_One_Food_Button_Id);
        Button family_One_Water_Button = findViewById(R.id.Family_One_Water_Button_Id);
//        Family Two View Initiating
        Family_Two_Hungry_View=findViewById(R.id.Family_Two_Hungry_View_Id);
        Family_Two_Thirst_View=findViewById(R.id.Family_Two_Thirst_View_Id);
        Family_Two_Hp_View=findViewById(R.id.Family_Two_Hp_View_Id);
        Family_Two_Damage_View=findViewById(R.id.Family_Two_Damage_View_Id);
        Button family_Two_Food_Button = findViewById(R.id.Family_Two_Food_Button_Id);
        Button family_Two_Water_Button = findViewById(R.id.Family_Two_Water_Button_Id);
        Food_View=findViewById(R.id.Food_Amount_View_ID);
        Water_View=findViewById(R.id.Water_Amount_View_Id);
        Damage_View=findViewById(R.id.Damage_Amount_View_Id);
        Button Get_Item_Button,Running_Button;

        Get_Item_Button=findViewById(R.id.Item_List_Button_Id);
        Running_Button=findViewById(R.id.Start_Running_Button_Id);

        Get_Item_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(NUM2_Game_Main_Page.this,NUM3_Get_Item_Menu.class));
            finish();

            }
        });

        Running_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NUM2_Game_Main_Page.this,NUM4_Run_And_Find.class));
                finish();
            }
        });

        Put_And_Update_Data();

        family_One_Food_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food--;
                Random r = new Random();
                int Random_One_Value = r.nextInt(30 - 10) + 10;
                Family_One_Hungry=Family_One_Hungry+Random_One_Value;
                Put_And_Update_Data();

            }
        });
        family_One_Water_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Water--;
                Random r = new Random();
                int Random_One_Value = r.nextInt(30 - 10) + 10;
                Family_One_Thirst=Family_One_Thirst+Random_One_Value;
                Put_And_Update_Data();

            }
        });

        family_Two_Food_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food--;
                Random r = new Random();
                int Random_One_Value = r.nextInt(30 - 10) + 10;
                Family_Two_Hungry=Family_Two_Hungry+Random_One_Value;
                Put_And_Update_Data();

            }
        });

        family_Two_Water_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Water--;
                Random r = new Random();
                int Random_One_Value = r.nextInt(30 - 10) + 10;
                Family_Two_Thirst=Family_Two_Thirst+Random_One_Value;
                Put_And_Update_Data();

            }
        });

    }
//     Updating Data Into Views And Shared Preference
    private void Put_And_Update_Data()
    {
        SharedPreferences.Editor Editor=Data_Box.edit();
        // Set Data

            if (Family_One_Hungry <= 0) {
                Family_One_Hungry_View.setText(" _");
                Family_One_Thirst_View.setText(" _ ");
                Family_One_Hp_View.setText(" _ ");
                Family_One_Damage_View.setText(" _ ");
                if(Family_One_Not_Died) {
                Rate_Us_Dialogue("Family One Died");
                        Family_One_Not_Died=false;
                    Editor.putBoolean("Family_One_Not_Died", Family_One_Not_Died);


            } } else {
                Family_One_Hungry_View.setText(String.valueOf(Family_One_Hungry));
                Family_One_Thirst_View.setText(String.valueOf(Family_One_Thirst));
                Family_One_Hp_View.setText(String.valueOf(Family_One_Hp));
                Family_One_Damage_View.setText(String.valueOf(Family_One_Damage));
                Editor.putInt("Family_One_Hungry", Family_One_Hungry);
                Editor.putInt("Family_One_Thirst", Family_One_Thirst);
                Editor.putInt("Family_One_Hp", Family_One_Hp);
                Editor.putBoolean("Family_One_Not_Died", Family_One_Not_Died);

            }


            if (Family_Two_Hungry <= 0) {
                Family_Two_Hungry_View.setText(" _ ");
                Family_Two_Thirst_View.setText(" _ ");
                Family_Two_Hp_View.setText(" _ ");
                Family_Two_Damage_View.setText(" _ ");
                if(Family_Two_Not_Died) {
                Rate_Us_Dialogue("Family Two Died");
                    Family_Two_Not_Died=false;
                    Editor.putBoolean("Family_Two_Not_Died", Family_Two_Not_Died);
                }


            } else {
                Family_Two_Hungry_View.setText(String.valueOf(Family_Two_Hungry));
                Family_Two_Thirst_View.setText(String.valueOf(Family_Two_Thirst));
                Family_Two_Hp_View.setText(String.valueOf(Family_Two_Hp));
                Family_Two_Damage_View.setText(String.valueOf(Family_Two_Damage));
                Editor.putInt("Family_Two_Hungry", Family_Two_Hungry);
                Editor.putInt("Family_Two_Thirst", Family_Two_Thirst);
                Editor.putInt("Family_Two_Hp", Family_Two_Hp);
                Editor.putBoolean("Family_Two_Not_Died", Family_Two_Not_Died);

            }


        Food_View.setText(String.valueOf(Food));
        Water_View.setText(String.valueOf(Water));
        Damage_View.setText(String.valueOf(Damage));
        Editor.putInt("Food",Food);
        Editor.putInt("Water",Water);
        Editor.commit();
    }

    @Override
    protected void onResume() {
        Getting_Data();
        super.onResume();
    }


    private void Rate_Us_Dialogue(String Family_No)
    {
        New_Alert_Dialog.setCancelable(false);
        New_Alert_Dialog.setTitle("Family Died");
        New_Alert_Dialog.setMessage(Family_No);
        New_Alert_Dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }


    // end onCreate()
}   // end MainActivity