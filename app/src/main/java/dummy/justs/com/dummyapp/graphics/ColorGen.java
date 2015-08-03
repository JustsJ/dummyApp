package dummy.justs.com.dummyapp.graphics;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by eptron on 8/3/2015.
 */
public class ColorGen {

    private static final int COLOR_WHITE=0xFFFFFF;
    private static Random r=new Random();
    public static int generateRandomColor(){
       return new Random().nextInt(0x00FFFFFF)+0xFF000000;
    }

    public static int generateColorKeepSat(int color){

        int maxTone=Math.max(Math.max(Color.red(color),Color.green(color)),Color.blue(color));
        int minTone=Math.min(Math.min(Color.red(color), Color.green(color)), Color.blue(color));

        int newRed=r.nextInt(maxTone-minTone)+minTone;
        int newGreen=r.nextInt(maxTone-minTone)+minTone;
        int newBlue=r.nextInt(maxTone-minTone)+minTone;

        return Color.rgb(newRed,newGreen,newBlue);
    }
    public static int generateComplimentaryColor(int color, int range){

        int newRed=r.nextInt(range)+Color.red(color)-range/2;
        int newGreen=r.nextInt(range)+Color.green(color)-range/2;
        int newBlue=r.nextInt(range)+Color.blue(color)-range/2;

        return Color.rgb(newRed,newGreen,newBlue);
    }

    public static int getOppositeColor(int color){
        int contrastSum=COLOR_WHITE-color;

        int newRed=r.nextInt(0xFF-Color.red(color));
        int newGreen=r.nextInt(0xFF-Color.green(color));
        int newBlue=r.nextInt(0xFF-Color.blue(color));

        return Color.rgb(newRed,newGreen,newBlue);
    }

    public static int getExactOpposite(int color){
        return  0xFF000000 + (COLOR_WHITE - (color - 0xFF000000));
    }

}
