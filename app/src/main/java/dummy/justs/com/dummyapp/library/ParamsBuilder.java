package dummy.justs.com.dummyapp.library;

import com.loopj.android.http.RequestParams;

/**
 * Helper class for building @RequestParams for RestCalls handled by @RestController
 * Created by Claudiu on 13/05/2015.
 */
public class ParamsBuilder {
    public static final String REGISTER_PUSH = "PushNotification/SetPushNotificationDetails";
    //public static final String GET_SETTINGS= "Associate/GetAssociateSettings";
    public static final String REGISTER_USER = "Account/RegisterMobileAppUser";

    public static final String GET_ACTIVITY = "Activities/GetClubActivitiesWithOffset";
    public static final String GET_CALENDAR_ACTIVITY= "Activities/GetActivityListCalendar";

    public static final String GET_CONTENT ="Content/GetContentByCategoryWithoutContent";
    public static final String GET_VIDEO_OTW = "Content/GetVideoOfWeek";
    public static final String GET_LATEST_CONTENT="Content/GetLatestContent";
    public static final String GET_CONTENT_BODY="Content/GetContentNative";

    public static final String GET_SURVEYS = "Survey/GetSurveyArchive";
    public static final String GET_SURVEY_RESULTS= "Survey/GetSurveyResultsByPercentage";
    public static final String POST_SURVEY="Survey/SurveyAttempt";

    public static final String GET_PROFILE= "Member/GetMemberProfile";
    public static final String POST_PROFILE="Member/SaveMemberProfile";

    public static final String GET_TEST = "ExternalLink/GetTestExternalLink";
    public static final String GET_LEARNING = "ExternalLink/GetELearningExternalLink";

    public static final String GET_ASSOCIATE_SETTINGS = "Associate/GetAssociateSettings";

    public static final String POST_PROMOTION_CODE_STATUS = "/Account/CheckPromotionCodeStatus";

    public static RequestParams getRequestAuth() {
        RequestParams params = new RequestParams("code",
                "93bf6f85bd82791a");
        return params;
    }


    /**********************************************************************
     ******************** First/Registration Params ***********************
     *********************************************************************/

    public static RequestParams buildRegForPushParams(String token){
        RequestParams params = new RequestParams("deviceMacAddress",
                "93bf6f85bd82791a");
        params.put("notificationToken",token);
        params.put("deviceOS",2);
        params.put("enableNotifications",true);
        return params;
    }

    public static RequestParams
    buildSaveMemberProfileParams(String phoneNumber, String firstName,String lastName,String email,
                                 String dateOfBirth,String languageID,String gender,Boolean privacy) {
        RequestParams params = new RequestParams();
        params.put("PhoneNumber", phoneNumber);
        params.put("LanguageID", languageID);
        params.put("FirstName", firstName);
        params.put("LastName", lastName);
        params.put("Gender", gender);
        params.put("DateOfBirth", dateOfBirth);
        params.put("Email", email);
        params.put("Subscribed", privacy);
        params.put("MacAddress", "93bf6f85bd82791a");
        return params;
    }

    /**********************************************************************
     ************************* List Params ********************************
     *********************************************************************/

    public static RequestParams buildListParams(int offset,int count) {
        RequestParams params = getRequestAuth();
        params.put("offset", offset);
        params.put("count", count);
        params.put("categoryId",23);
        return params;
    }

    public static RequestParams buildUpdateListParams(long timestamp){
        RequestParams params = buildListParams(0, 200);
        params.put("endTimestamp",timestamp);
        return params;
    }

    /***********************************************************************
     ************************* Main Screen Params **************************
     **********************************************************************/

    public static RequestParams buildLatestContentParams(int count){
        RequestParams params = getRequestAuth();
        params.put("count",count);
        return params;
    }

    public static RequestParams buildSurveyResultParams(int surveyId){
        RequestParams params = getRequestAuth();
        params.put("id", surveyId);
        return params;
    }

    public static RequestParams buildExternalLinkResultParams(int surveyId) {
        RequestParams params = getRequestAuth();
        params.put("id",surveyId);
        return params;
    }

    /*********************************************************************
     ************************** Other Calls ******************************
     *********************************************************************/

    public static RequestParams buildSurveyAtemptParams(int surveyID,int answerID){
        RequestParams params = new RequestParams();
        params.put("code","93bf6f85bd82791a");
        params.put("surveyId",surveyID);
        params.put("answerId",answerID);
        return params;
    }

    public static RequestParams buildCalendarActivityParams(int activityId, final Integer month, final String year){
        RequestParams params = getRequestAuth();
        params.put("month", Integer.toString(month + 1));
        params.put("year", year);
        params.put("activityId", activityId);
        return params;
    }

    public static RequestParams buildParamsContentBody(String id){
        RequestParams params = getRequestAuth();
        params.put("id",id);
        return params;
    }
}
