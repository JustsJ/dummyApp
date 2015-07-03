package dummy.justs.com.dummyapp.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import dummy.justs.com.dummyapp.InfoListItem;
import dummy.justs.com.dummyapp.R;

/**
 * Created by eptron on 7/1/2015.
 */
public class DrawableFragment extends Fragment {
    private LinearLayout mInfoView;
    private static final int IMAGE_DRAWABLE=R.drawable.wombat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_for_drawables,null);

        mInfoView=(LinearLayout) view.findViewById(R.id.image_info);

        ImageView image=(ImageView) view.findViewById(R.id.image);
        image.setBackgroundResource(IMAGE_DRAWABLE);

        getImageInfo();

        return view;
    }

    public void getImageInfo(){
        /**
         * option inJustDecodeBounds lets you get the dimensions of the image without
         * loading it in memory
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),IMAGE_DRAWABLE, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Log.i("Drawable Fragment","Height: "+imageHeight);
        Log.i("Drawable Fragment","Width: "+imageWidth);
        Log.i("Drawable Fragment","MIME: "+imageType);

        InfoListItem heightView= new InfoListItem(getActivity(),"Height: ",Integer.toString(imageHeight));
        mInfoView.addView(heightView);

        InfoListItem widthView= new InfoListItem(getActivity(),"Width: ",Integer.toString(imageWidth));
        mInfoView.addView(widthView);

        InfoListItem mimeView= new InfoListItem(getActivity(),"MIME: ",imageType);
        mInfoView.addView(mimeView);


    }
}
