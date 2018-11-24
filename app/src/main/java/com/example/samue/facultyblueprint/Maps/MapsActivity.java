package com.example.samue.facultyblueprint.Maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MapsActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG="MapsActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = MapsActivity.this;
    private int currentViewId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentViewById(R.layout.activity_maps);

        setupBottomNavigationView();
      //  buttonClick();

        int[] x = {R.id.floor_two,R.id.floor_one};
        for(int i :x){
            ImageView iv = (ImageView) findViewById (i);
            if (iv != null) {
                iv.setOnTouchListener(this);
            }
        }


    }
    public void setCurrentViewById(int id)
    {
        setContentView(id);
        currentViewId = id;
    }

    public int getCurrentViewById()
    {
        return currentViewId;
    }
    //Demo on how to go to PopUpActivity for each button
 /*   public void buttonClick(){
        int[] arr = {R.id.room101, R.id.room102};
        for(int i : arr){
            final Button room = (Button) findViewById(i);
            room.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PopupDetails.class);

                    // putExtra() is required to send the room number to the new activity
                    intent.putExtra("roomNumber", room.getText()) ;

                    // Start a new activity for the clicked room
                    startActivity(intent);
                }
            });
        }
    }*/


    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();


        ImageView imageView = (ImageView) v.findViewById (R.id.floor_one);
        ImageView imageView2 = (ImageView)  v.findViewById(R.id.floor_two);

        int currentResource = -1 ;

        if(getCurrentViewById() == R.layout.activity_maps){
            currentResource = R.drawable.floor1;

            imageView.setImageResource (currentResource);
            imageView.setTag (currentResource);
        }else if(getCurrentViewById() == R.layout.layout_floor_two){
            currentResource = R.drawable.floor2;
            imageView2.setImageResource (currentResource);
            imageView2.setTag (currentResource);
        }

        switch (action) {
           case MotionEvent.ACTION_DOWN :

                if (currentResource == R.drawable.floor1 || currentResource == R.drawable.floor2) {
                   return true;
                }
                break;

            case MotionEvent.ACTION_UP :
                //GETHOTSPOTCOLOR JUST DETECTS COLORS SO NO PROBS
                int touchColor = getHotspotColor (R.id.floor_one_image_areas, evX, evY);
                int touchColor2 = getHotspotColor(R.id.floor_two_image_areas,evX,evY);

               //fOR DETECTING AGAIN
                ColorTool ct = new ColorTool ();
                int tolerance = 25;

                   //Floor1 Colors
                   if (ct.closeMatch(Color.parseColor("#FF006E"), touchColor, tolerance)) {
                       Intent intent = new Intent(mContext, PopupDetails.class);
                       intent.putExtra("roomNumber", "101");
                       startActivity(intent);


                   } //Toast.makeText (getApplicationContext(), "Room 101", Toast.LENGTH_SHORT).show ();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 102", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF6A00"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 103", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FFD800"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 105", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#B6FF00"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 105a", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#4CFF00"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 113", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#00FFFF"), touchColor, tolerance)) {
                  //     Log.i(String.valueOf(currentResource),"cs before");
                       setCurrentViewById(R.layout.layout_floor_two);



                    //   if(imageView2 == null) return
                   } else if (ct.closeMatch(Color.parseColor("#0094FF"), touchColor, tolerance))
                       Toast.makeText(getApplicationContext(), "Lift DOWN", Toast.LENGTH_SHORT).show();



                   //Floor2 Colors
                  else if (ct.closeMatch(Color.parseColor("#7F0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 210", Toast.LENGTH_SHORT).show();

                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 211", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 212", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 213", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 214", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 216", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 217", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 218", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 219", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 201", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 202", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 203", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 222", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "Room 227", Toast.LENGTH_SHORT).show();
                   else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor2, tolerance))
                       Toast.makeText(getApplicationContext(), "2nd Floor Lift up", Toast.LENGTH_SHORT).show();

                       //2nd floor lift down
                   else if (ct.closeMatch(Color.parseColor("#7F3300"), touchColor2, tolerance)) {
                       setCurrentViewById(R.layout.activity_maps);
                   }

                break;

            default:
               return  false;
        }

        return false;
    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}
