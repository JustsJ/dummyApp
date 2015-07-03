package dummy.justs.com.dummyapp.retrofit.model;

import java.util.List;

/**
 * Created by eptron on 7/2/2015.
 */
public class TestModel {
    public String title;
    public List<Dataset> dataset;

    public class Dataset{
        public String curator_title;
        public String curator_tagline;
    }
}
