package dummy.justs.com.dummyapp.library.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justs on 6/2/2015.
 */
public class NewsEvent extends MainEvent {

    private ArrayList<NewsItem> mNewsItems;

    public NewsEvent(){
        mJSONArray= new JSONArray();
    };

    @Override
    public void parseArray() {
        mNewsItems = new ArrayList<>();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<NewsItem>>() {
        }.getType();
        mNewsItems = gson.fromJson(this.getTimeline().toString(), collectionType);
    }

    public List<NewsItem> getmNewsItems() {
        return mNewsItems;
    }

    public JSONArray getTimeline(){
        return mJSONArray;
    }
}
