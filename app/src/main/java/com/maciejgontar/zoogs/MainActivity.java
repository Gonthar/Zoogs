package com.maciejgontar.zoogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageButton btn = (ImageButton)findViewById(R.id.open_activity_button);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MyOtherActivity.class));
//            }
//        });
    }

    public void animalTapped(View button)
    {
        Intent intent = new Intent(MainActivity.this, ShowGifsActivity.class);
        switch(button.getId())
        {
            case R.id.bear :
                intent.putExtra("DESIRED_ANIMAL", "bear");
                break;
            case R.id.koala :
                intent.putExtra("DESIRED_ANIMAL", "koala");
                break;
            case R.id.cat :
                intent.putExtra("DESIRED_ANIMAL", "cat");
                break;
            case R.id.dog :
                intent.putExtra("DESIRED_ANIMAL", "dog");
                break;
            case R.id.hedgehog :
                intent.putExtra("DESIRED_ANIMAL", "hedgehog");
                break;
            case R.id.raccoon :
                intent.putExtra("DESIRED_ANIMAL", "raccoon");
                break;
            case R.id.kangaroo :
                intent.putExtra("DESIRED_ANIMAL", "kangaroo");
                break;
            case R.id.owl :
                intent.putExtra("DESIRED_ANIMAL", "owl");
                break;
            case R.id.tiger :
                intent.putExtra("DESIRED_ANIMAL", "tiger");
                break;
            case R.id.sloth :
                intent.putExtra("DESIRED_ANIMAL", "sloth");
                break;
            case R.id.panda_bear :
                intent.putExtra("DESIRED_ANIMAL", "panda");
                break;
            case R.id.lemur :
                intent.putExtra("DESIRED_ANIMAL", "lemur");
                break;
            default :
                break;
        }
        startActivity(intent);
    }
}
