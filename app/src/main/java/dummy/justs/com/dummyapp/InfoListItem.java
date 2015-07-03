package dummy.justs.com.dummyapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by eptron on 7/1/2015.
 */
public class InfoListItem extends LinearLayout{
    private TextView mNameView;
    private TextView mValueView;

    public InfoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InfoListItem(Context context, String name, String value)
    {
        this(context);
        init();
        setText(name,value);
    }

    public InfoListItem(Context context) {
        super(context);
        init();
    }

    public void setText(String name,String value){
        mNameView.setText(name);
        mValueView.setText(value);
    }

    private void init(){
        inflate(getContext(),R.layout.info_list_item,this);
        mNameView=(TextView)findViewById(R.id.name);
        mValueView=(TextView)findViewById(R.id.value);
    }
}
