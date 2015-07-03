package dummy.justs.com.dummyapp.retrofit.API;

import java.util.ArrayList;

import dummy.justs.com.dummyapp.library.model.MainEvent;
import dummy.justs.com.dummyapp.library.model.NewsModel;
import dummy.justs.com.dummyapp.retrofit.model.TestModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by eptron on 7/2/2015.
 */
public interface TestAPI {
    @GET("/get/curators.json")
    TestModel getCurators(
            @Query("api_key") String key
    );

    @GET("/get/curators.json")
    void getCurators(
            @Query("api_key") String key, Callback<TestModel> cb
    );

    @GET("/api/Content/GetContentByCategoryWithoutContent")
    ArrayList<NewsModel> getNews(
            @Query("code") String code, @Query("categoryId") int categoryId,
            @Query("offset") int offset, @Query("count") int count
    );
    @GET("/api/Content/GetContentByCategoryWithoutContent")
    void getNews(
            @Query("code") String code, @Query("categoryId") int categoryId,
            @Query("offset") int offset, @Query("count") int count, Callback<ArrayList<NewsModel>> cb
    );
    //end result from the call:
    //http://freemusicarchive.org/api/get/curators.json?api_key=-------

}
