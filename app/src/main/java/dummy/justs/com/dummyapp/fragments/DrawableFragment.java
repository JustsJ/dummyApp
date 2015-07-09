package dummy.justs.com.dummyapp.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

import dummy.justs.com.dummyapp.InfoListItem;
import dummy.justs.com.dummyapp.R;

/**
 * Created by eptron on 7/1/2015.
 */
public class DrawableFragment extends Fragment {
    private LinearLayout mInfoView;
    private boolean mImageClicked = false;
    private ImageView mImage;
    private LayerDrawable mDrawable;
    private TransitionDrawable mTransition;
    private Canvas mCanvas;
    private TextureView mTextureView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_for_drawables, null);

        mInfoView = (LinearLayout) view.findViewById(R.id.image_info);

        mImage = (ImageView) view.findViewById(R.id.image);

        Drawable backgrounds[] = new Drawable[2];
        Resources res = getResources();
        backgrounds[0] =  new BitmapDrawable(res, createBitmap(R.drawable.wombat));
        backgrounds[1] = new BitmapDrawable(res, createBitmap(R.drawable.wombat2));

        mTransition = new TransitionDrawable(backgrounds);
        mImage.setImageDrawable(mTransition);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageClicked = !mImageClicked;
                if (mImageClicked) {
                    mTransition.startTransition(500);

                } else {
                    mTransition.reverseTransition(500);
                }
            }
        });


        return view;
    }

    public void doMagic(){
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

    }


    public void getImageInfo(int id) {
        /**
         * option inJustDecodeBounds lets you get the dimensions of the image without
         * loading it in memory
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), id, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Log.i("Drawable Fragment", "Height: " + imageHeight);
        Log.i("Drawable Fragment", "Width: " + imageWidth);
        Log.i("Drawable Fragment", "MIME: " + imageType);

    }

    private Bitmap circleBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = bitmap.getHeight();

        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.circle_black, options);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        canvas.drawBitmap(Bitmap.createScaledBitmap(mask, bitmap.getHeight(), bitmap.getHeight(), false), 0, 0, paint);
        // We do not need the mask bitmap anymore.
        mask.recycle();
        return bitmap;
    }


    private Point getScreenSize() {
        WindowManager window = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }


    private Bitmap createBitmap(int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Starting with Honeycomb, we can load the bitmap as mutable.
            options.inMutable = true;
        }
        // We could also use ARGB_4444, but not RGB_565 (we need an alpha layer).
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap source = BitmapFactory.decodeResource(getResources(), id, options);

        int top;
        int left;
        if (source.getWidth()>=source.getHeight()){
            top=0;
            left= (source.getWidth() - source.getHeight()) / 2;
        }
        else
        {
            top=(source.getHeight() - source.getWidth()) / 2;
            left= 0;
        }

        Bitmap bitmap = Bitmap.createBitmap(source, left, top, source.getHeight(), source.getHeight());

        // The bitmap is opaque, we need to enable alpha compositing.
        bitmap.setHasAlpha(true);
        bitmap = circleBitmap(bitmap);
        return bitmap;
    }

}
