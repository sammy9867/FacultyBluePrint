package com.example.samue.facultyblueprint.Maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samue.facultyblueprint.Login.LoginActivity;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**This is the MAIN activity based at the center which displays the map**/
public class MapsActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final int MIN_FLOOR_NUM = 0;
    private static final int MAX_FLOOR_NUM = 3;

    private static final String TAG="MapsActivity";
    private static final int ACTIVITY_NUM = 1; //Since it's in the center.
    private Context mContext = MapsActivity.this;
    private int currentViewId = -1;
    private int floor_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentViewById(R.layout.activity_maps);

        Log.i(">>>>", "Before Login");
        if(!alreadyLoggedIn()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
        Log.i(">>>>", "After Login");

        floor_number = 1;
        setupBottomNavigationView();

        int currentResource = -1 ;
        TouchImageView imageView = (TouchImageView) findViewById (R.id.floor_shown);

        //Switching the floor based on the floor_number
        if(floor_number == 1){
            currentResource = R.drawable.floor1;
        }else if(floor_number == 2){
            currentResource = R.drawable.floor2;
        }else if(floor_number == 3){
            currentResource = R.drawable.floor3;
        }

        imageView.setImageResource (currentResource);
        imageView.setTag (currentResource);
        imageView.setOnTouchListener(this);

    }

    /**
     * Checks if the user is already logged in
     * @return FALSE  // to be implemented later
     */
    private boolean alreadyLoggedIn() {
        if(User.accessToken == null)
            return false;
        return true;
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

        TouchImageView touchImageView = (TouchImageView) v.findViewById (R.id.floor_shown);

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                return true;

            case MotionEvent.ACTION_UP :
                //GETHOTSPOTCOLOR JUST DETECTS COLORS SO NO PROBS
                int touchColor = -1;
                if(floor_number == 1){
                    touchColor = getHotspotColor(v, R.id.floor_one_image_areas, evX, evY);
                    Log.i("floor one: ",String.valueOf(touchColor));
                }else if(floor_number == 2 ){
                    //   touchColor = getHotspotColor(BitmapFactory.decodeResource(getResources(),R.drawable.floor2_color),evX,evY);
                    touchColor = getHotspotColor(v, R.id.floor_two_image_areas, evX, evY);
                    Log.i("floor two: ",String.valueOf(touchColor));
                }else if(floor_number == 3){
                    //   touchColor = getHotspotColor(BitmapFactory.decodeResource(getResources(),R.drawable.floor2_color),evX,evY);
                    touchColor = getHotspotColor(v, R.id.floor_three_image_areas, evX, evY);
                    Log.i("floor two: ",String.valueOf(touchColor));
                }
                else {
                    //TODO: Set for the rest of the floors
                }


                //FOR DETECTING AGAIN
                ColorTool ct = new ColorTool ();
                int tolerance = 1;

                if(floor_number == 1) {
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
                        touchImageView.resetZoom();
                        switchingFloors(1);

                        //   if(imageView2 == null) return
                    } else if (ct.closeMatch(Color.parseColor("#0094FF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Lift DOWN", Toast.LENGTH_SHORT).show();

                }

                else if(floor_number == 2) {
                    //Floor2 Colors
                    if (ct.closeMatch(Color.parseColor("#7F0000"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 210", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#7F6A00"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 211", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#5B7F00"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 212", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#007F46"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 213", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#007F7F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 214", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#21007F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 216", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#57007F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 217", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#7F006E"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 218", Toast.LENGTH_SHORT).show();


                    else if (ct.closeMatch(Color.parseColor("#FF7F7F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 219", Toast.LENGTH_SHORT).show();


                    else if (ct.closeMatch(Color.parseColor("#FFB27F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 201", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#DAFF7F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 202", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#A17FFF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 203", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF7FB6"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 222", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#D67FFF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 227", Toast.LENGTH_SHORT).show();

                        //Floor up
                    else if (ct.closeMatch(Color.parseColor("#FF00DC"), touchColor, tolerance)) {
                        touchImageView.resetZoom();
                        switchingFloors(1);
                    }

                        //2nd floor lift down
                    else if (ct.closeMatch(Color.parseColor("#7F3300"), touchColor, tolerance)) {
                        touchImageView.resetZoom();
                        switchingFloors(0);
                    }
                }

                else if(floor_number == 3) {
                    //Floor 3 colors
                    //    if (ct.closeMatch(Color.parseColor("#77F3F3F"), touchColor, tolerance))
                    //     Toast.makeText(getApplicationContext(), "Room 310", Toast.LENGTH_SHORT).show();

                    if (ct.closeMatch(Color.parseColor("#7F593F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 311", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#7F743F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 312", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#6D7F3F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 313", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#527F3F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 314", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#3F7F62"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 316", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#0094FF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 317", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF006E"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 318", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#00137F"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 319", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#00FF21"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 320", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF0000"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 301", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF6A00"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 302", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#B6FF00"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 303", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#00FF90"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 304", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#0026FF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 323", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#B200FF"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 328", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF00DC"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Room 329", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FFD800"), touchColor, tolerance))
                        Toast.makeText(getApplicationContext(), "Elevator up", Toast.LENGTH_SHORT).show();

                    else if (ct.closeMatch(Color.parseColor("#FF7F7F"), touchColor, tolerance)){
                        touchImageView.resetZoom();
                        switchingFloors(0);
                    }

                }

                break;

            default:
                return  false;
        }

        /* DO NOT CHANGE */
        int currentResource = -1 ;
//        TouchImageView touchImageView = (TouchImageView) v.findViewById (R.id.floor_shown);
        //Switching the floor based on the floor_number
        if(floor_number == 1){
            currentResource = R.drawable.floor1;
        }else if(floor_number == 2){
            currentResource = R.drawable.floor2;
        }else if(floor_number == 3){
            currentResource = R.drawable.floor3;
        }

        touchImageView.setImageResource (currentResource);
        touchImageView.setTag (currentResource);

        return false;
    }

    /**
     * The mothod is called when any ROOM is clicked
     * @param room_number is sent to distinguish rooms
     */
    private void RoomClick(String room_number) {
        Intent intent = new Intent(mContext, PopupDetails.class);
        intent.putExtra("roomNumber", room_number);
        startActivity(intent);
    }

    /**
     * Switching the visible image (the shown floor)
     * Change the floor number
     * @param dir If dir is 0, it goes one level down
     *            if dir is 1, it goes one level up
     *            otherwise, nothing is executed
     */
    public void switchingFloors(int dir){
        switch (dir){
            case 0: // go down
                if(floor_number == MIN_FLOOR_NUM) return;
                floor_number--;
                break;
            case 1: //go up
                if(floor_number == MAX_FLOOR_NUM)return;
                floor_number++;
                break;
        }
    }

    /**
     * Get the coordinates of the invisible map image
     * @param v current view
     * @param hotspotId id of the colored map image of the floor
     * @param x position x of the colored map image
     * @param y position y of the colored map image
     * @return Pixel of the particular color at the given (x,y) coordinates.
     */
    public int getHotspotColor (View v, int hotspotId, int x, int y) {

        TouchImageView hotspot_img = (TouchImageView) findViewById (hotspotId);
        TouchImageView map_img = (TouchImageView) v;

        //Get rectangle which is visible on the screen at the moment
        RectF rectf = map_img.getZoomedRect();

        //Scale this rectangle to the whole TouchImageView
        rectf.left*=map_img.getWidth();
        rectf.right*=map_img.getWidth();
        rectf.bottom*=map_img.getHeight();
        rectf.top*=map_img.getHeight();

        //Calculate event location with respect to the whole TouchImageView
        int xx =(int) ( rectf.left + (float)((float)x/(float)map_img.getWidth())*(rectf.right-rectf.left));
        int yy =(int) (rectf.top + (float)((float)y/(float)map_img.getHeight())*(rectf.bottom-rectf.top));

        if (hotspot_img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            hotspot_img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(hotspot_img.getDrawingCache());

            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                hotspot_img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(xx, yy);
            }
        }
    }

    /**
     * Get the coordinates of the invisible map image
     * @param hotspots Bitmap of the colored map image of the floor
     * @param x position x of the colored map image
     * @param y position y of the colored map image
     * @return Pixel of the particular color at the given (x,y) coordinates.     */
    public int getHotspotColor(Bitmap hotspots, int x, int y) {
        if (hotspots == null) {
            return -1;
        } else {
            //img.setDrawingCacheEnabled(true);
            Log.i("(x,y))", String.valueOf(x)+" "+ String.valueOf(y));
            return hotspots.getPixel(x, y);
        }
    }

//  public static int[] getBitmapPositionInsideImageView(ImageView imageView) {
//        int[] ret = new int[4];
//
//        if (imageView == null || imageView.getDrawable() == null)
//            return ret;
//
//        // Get image dimensions
//        // Get image matrix values and place them in an array
//        float[] f = new float[9];
//        imageView.getImageMatrix().getValues(f);
//
//        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
//        final float scaleX = f[Matrix.MSCALE_X];
//        final float scaleY = f[Matrix.MSCALE_Y];
//
//        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
//        final Drawable d = imageView.getDrawable();
//        final int origW = d.getIntrinsicWidth();
//        final int origH = d.getIntrinsicHeight();
//
//        // Calculate the actual dimensions
//        final int actW = Math.round(origW * scaleX);
//        final int actH = Math.round(origH * scaleY);
//
//        ret[2] = actW;
//        ret[3] = actH;
//
//        // Get image position
//        // We assume that the image is centered into ImageView
//        int imgViewW = imageView.getWidth();
//        int imgViewH = imageView.getHeight();
//
//        int top = (int) (imgViewH - actH)/2;
//        int left = (int) (imgViewW - actW)/2;
//
//        ret[0] = left;
//        ret[1] = top;
//
//        return ret;
//    }
}