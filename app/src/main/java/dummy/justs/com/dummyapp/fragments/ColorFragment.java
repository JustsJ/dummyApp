package dummy.justs.com.dummyapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

import dummy.justs.com.dummyapp.R;
import dummy.justs.com.dummyapp.graphics.ColorGen;
import dummy.justs.com.dummyapp.graphics.DrawingView;

/**
 * Created by eptron on 8/3/2015.
 */
public class ColorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.color_fragment,null);

        TableLayout table=(TableLayout)view.findViewById(R.id.table);

        int color= ColorGen.generateRandomColor();
        int tColor=0;

        for (int i=0;i<table.getChildCount();i++)
        {
            TableRow row=(TableRow)table.getChildAt(i);

            for (int j=0;j<row.getChildCount();j++){
                LinearLayout colorContainer=(LinearLayout)row.getChildAt(j);
              //  row.getChildAt(j).setBackgroundColor(ColorGen.generateComplimentaryColor(color,0x33));
                if (i==0 && j==0) {
                    tColor=color;
                }
                else{
                    tColor=ColorGen.getOppositeColor(color);
                }
                colorContainer.setBackgroundColor(tColor);

                ((TextView)colorContainer.getChildAt(0)).setText(Integer.toHexString(Color.red(tColor)));
                ((TextView)colorContainer.getChildAt(0)).setTextColor(ColorGen.getExactOpposite(tColor));

                ((TextView)colorContainer.getChildAt(1)).setText(Integer.toHexString(Color.green(tColor)));
                ((TextView)colorContainer.getChildAt(1)).setTextColor(ColorGen.getExactOpposite(tColor));

                ((TextView)colorContainer.getChildAt(2)).setText(Integer.toHexString(Color.blue(tColor)));
                ((TextView)colorContainer.getChildAt(2)).setTextColor(ColorGen.getExactOpposite(tColor));

            }
        }

        return view;
    }
}
