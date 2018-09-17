package io.jeux.ggavi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NUM3_Get_Item_Menu extends AppCompatActivity {


    private int Food,Water;
    private SharedPreferences Data_Box;
    private TextView Food_Amount_View,Water_Amount_View;
    int Reduced_Family_One_Damage,Reduced_Family_Two_Damage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num3_get_item_activity);
        Reduced_Family_One_Damage=Reduced_Family_Two_Damage=0;
        // ActionBar Hide!
        Data_Box= getApplicationContext().getSharedPreferences("Data_Box",MODE_PRIVATE);

        Initiating_Views();
    }


    private void Initiating_Views()
    {
        Food=Data_Box.getInt("Food",1);
        Water=Data_Box.getInt("Water",1);

        Food_Amount_View=findViewById(R.id.Food_Amount_View_GetItem_Id);
        Water_Amount_View=findViewById(R.id.Water_Amount_View_GetItem_Id);
        Get_And_Update();
        final Button Family_One_Buy=findViewById(R.id.Family_One_Buy_Button_Id);
        final Button Family_Two_Buy=findViewById(R.id.Family_Two_Buy_Button_Id);

        Family_One_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Food=Food-10;
            Water=Water-5;
            Reduced_Family_One_Damage=Reduced_Family_One_Damage+3;
            Get_And_Update();
                Family_One_Buy.setEnabled(false);
            }
        });

        Family_Two_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food=Food-10;
                Water=Water-5;
                Reduced_Family_Two_Damage=Reduced_Family_Two_Damage+3;

                Get_And_Update();
                Family_Two_Buy.setEnabled(false);
            }
        });


    }

    private void Get_And_Update()
    {
        int Temp_Family_One_Damage,Temp_Family_Two_Damage;
        Temp_Family_One_Damage=Data_Box.getInt("Family_One_Damage",-1);
        Temp_Family_Two_Damage=Data_Box.getInt("Family_Two_Damage",-1);
        Temp_Family_One_Damage=Temp_Family_One_Damage+Reduced_Family_One_Damage;
        Temp_Family_Two_Damage=Temp_Family_Two_Damage+Reduced_Family_Two_Damage;
        Food_Amount_View.setText(Food+"");
        Water_Amount_View.setText(Water+"");
        SharedPreferences.Editor Editor=Data_Box.edit();
        Editor.putInt("Food",Food);
        Editor.putInt("Water",Water);
        Editor.putInt("Family_One_Damage",Temp_Family_One_Damage);
        Editor.putInt("Family_Two_Damage",Temp_Family_Two_Damage);
        Editor.commit();
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        startActivity(new Intent(NUM3_Get_Item_Menu.this,NUM2_Game_Main_Page.class));
        finish();
    }
}
