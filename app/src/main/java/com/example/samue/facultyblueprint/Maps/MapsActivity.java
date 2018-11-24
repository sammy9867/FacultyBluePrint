package com.example.samue.facultyblueprint.Maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private int floor_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentViewById(R.layout.activity_maps);

        floor_number = 1;
        setupBottomNavigationView();
        //  buttonClick();
        int currentResource = -1 ;
        ImageView imageView = (ImageView) findViewById (R.id.floor_one);
        //Switching the floor based on the floor_number
        if(floor_number == 1){
            imageView = (ImageView) findViewById (R.id.floor_one);
            currentResource = R.drawable.floor1;
        }else if(floor_number == 2){
            imageView = (ImageView) findViewById (R.id.floor_two);
            currentResource = R.drawable.floor2;
        }

        imageView.setImageResource (currentResource);
        imageView.setTag (currentResource);
        imageView.setOnTouchListener(this);


    /*    int[] x = {R.id.floor_two,R.id.floor_one};
        for(int i :x){
            ImageView iv = (ImageView) findViewById (i);
            if (iv != null) {

            }
        }*/




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



        //    ImageView imageView2 = (ImageView)  v.findViewById(R.id.floor_two);




        //  }
        /*else if(getCurrentViewById() == R.layout.layout_floor_two){
            currentResource = R.drawable.floor2;
            imageView2.setImageResource (currentResource);
            imageView2.setTag (currentResource);
        }*/

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                return true;

            case MotionEvent.ACTION_UP :
                //GETHOTSPOTCOLOR JUST DETECTS COLORS SO NO PROBS
                int touchColor = -1;
                if(floor_number == 1){
                    //  touchColor =  getHotspotColor(BitmapFactory.decodeResource(getResources(),R.drawable.floor1_color), evX, evY);
                    touchColor = getHotspotColor(R.id.floor_one_image_areas, evX, evY);
                    Log.i("floor one: ",String.valueOf(touchColor));
                }else if(floor_number ==2 ){
                    //     touchColor = getHotspotColor(BitmapFactory.decodeResource(getResources(),R.drawable.floor2_color),evX,evY);
                    touchColor = getHotspotColor(R.id.floor_two_image_areas, evX, evY);
                    Log.i("floor two: ",String.valueOf(touchColor));
                }else {
                    //TODO: Set for the rest of the floors
                }


                //fOR DETECTING AGAIN
                ColorTool ct = new ColorTool ();
                int tolerance = 25;

                //Floor1 Colors
                if (ct.closeMatch(Color.parseColor("#FF006E"), touchColor, tolerance))
                    RoomClick("101");
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    RoomClick("102");
                else if (ct.closeMatch(Color.parseColor("#FF6A00"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 103", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FFD800"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 105", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#B6FF00"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 105a", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#4CFF00"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 113", Toast.LENGTH_SHORT).show();

                    //ELEVATOR UP
                else if (ct.closeMatch(Color.parseColor("#00FFFF"), touchColor, tolerance)) {
                    //     Log.i(String.valueOf(currentResource),"cs before");
                    //setCurrentViewById(R.layout.layout_floor_two);
                    switchingFloors(1);

                    //   if(imageView2 == null) return
                } else if (ct.closeMatch(Color.parseColor("#0094FF"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Lift DOWN", Toast.LENGTH_SHORT).show();



                    //Floor2 Colors
                else if (ct.closeMatch(Color.parseColor("#7F0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 210", Toast.LENGTH_SHORT).show();

                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 211", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 212", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 213", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 214", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 216", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 217", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 218", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 219", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 201", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 202", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 203", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 222", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "Room 227", Toast.LENGTH_SHORT).show();
                else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                    Toast.makeText(getApplicationContext(), "2nd Floor Lift up", Toast.LENGTH_SHORT).show();

                    //2nd floor lift down
                else if (ct.closeMatch(Color.parseColor("#7F3300"), touchColor, tolerance)) {
                    Log.i("floorno",String.valueOf(floor_number));
                    switchingFloors(0);
                    Log.i("floorno",String.valueOf(floor_number));
                }



                //Toast.makeText (getApplicationContext(), "Room 101", Toast.LENGTH_SHORT).show ();
                break;

            default:
                return  false;
        }

        int currentResource = -1 ;
        ImageView imageView = (ImageView) v.findViewById (R.id.floor_one);
        //Switching the floor based on the floor_number
        if(floor_number == 1){
            currentResource = R.drawable.floor1;
        }else if(floor_number == 2){
            currentResource = R.drawable.floor2;
        }

        imageView.setImageResource (currentResource);
        imageView.setTag (currentResource);

        return false;
    }

    /**
     *
     * @param room_number
     */
    private void RoomClick(String room_number) {
        Intent intent = new Intent(mContext, PopupDetails.class);
        intent.putExtra("roomNumber", room_number);
        startActivity(intent);
    }

    /**
     *
     * @param dir
     */
    public void switchingFloors(int dir){
        switch (dir){
            case 0: // go down
                if(floor_number == 0) return;
                floor_number--;
                break;
            case 1: //go up
                if(floor_number == 3)return;
                floor_number++;
                break;
        }
    }

    /**
     *
     * @param hotspotId id of the colored map image of the floor
     * @param x position x of the colored map image
     * @param y position y of the colored map image
     * @return Pixel of the particular color at the given (x,y) coordinates.
     */
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

    public int getHotspotColor(Bitmap hotspots, int x, int y) {
        if (hotspots == null) {
            return -1;
        } else {
            //img.setDrawingCacheEnabled(true);
            Log.i("(x,y))", String.valueOf(x)+" "+ String.valueOf(y));
            return hotspots.getPixel(x, y);
        }
    }
}
