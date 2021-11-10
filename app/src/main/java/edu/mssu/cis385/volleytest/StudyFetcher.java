package edu.mssu.cis385.volleytest;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import edu.mssu.cis385.volleytest.model.Subject;


public class StudyFetcher {

    // (Component #1 in the slides)
    // "manages worker threads that make HTTP requests and parse HTTP responses"
    private final RequestQueue mRequestQueue;

    public StudyFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public interface OnStudyDataReceivedListener {
        void onSubjectsReceived(List<Subject> subjectList);
        void onErrorResponse(VolleyError error);
    }

    private final String WEBAPI_BASE_URL = "https://wp.zybooks.com/study-helper.php";
    private final String TAG = "StudyFetcher";

    public void fetchSubjects(final OnStudyDataReceivedListener listener) {

        // Our full URL is https://wp.zybooks.com/study-helper.php?type=subjects
        // Uri.parse(WEBAPI_BASE_URL) is https://wp.zybooks.com/study-helper.php
        // appendQueryParameter("type", "subjects" adds the type=subjects parameter
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("type", "subjects").build().toString();

        // Request all subjects (Component #2 in the slides)
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                // Component #3 in the slides
                response -> listener.onSubjectsReceived(jsonToSubjects(response)),
                // Component #5 in the slides
                error -> listener.onErrorResponse(error));

        mRequestQueue.add(request);
    }

    private List<Subject> jsonToSubjects(JSONObject json) {

        // Create a list of subjects
        List<Subject> subjectList = new ArrayList<>();

        try {
            // Component #4 in the slides
            JSONArray subjectArray = json.getJSONArray("subjects");

            for (int i = 0; i < subjectArray.length(); i++) {
                JSONObject subjectObj = subjectArray.getJSONObject(i);

                Subject subject = new Subject(subjectObj.getString("subject"));
                subject.setUpdateTime(subjectObj.getLong("updatetime"));
                subjectList.add(subject);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }

        return subjectList;
    }
}