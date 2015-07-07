package dummy.justs.com.dummyapp.library.model;

import android.content.ContentValues;
import android.database.Cursor;

public class NewsItem {
    private Integer categoryId;
    private String categoryName;
    private Integer contentId;
    private String contentStatus;
    private String contentTitle;
    private long publishTimestamp;
    private long expiryTimestamp;
    private long updateTimestamp;
    private String fileURL;
    private int isRead=0;
    private boolean isDeleted;

    public NewsItem(Integer categoryId, String categoryName, Integer contentId, String contentStatus, String contentTitle, long publishTimestamp, long expiryTimestamp, long updateTimestamp, String fileURL, int isRead, boolean isDeleted) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.contentId = contentId;
        this.contentStatus = contentStatus;
        this.contentTitle = contentTitle;
        this.publishTimestamp = publishTimestamp;
        this.expiryTimestamp = expiryTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.fileURL = fileURL;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
    }
}