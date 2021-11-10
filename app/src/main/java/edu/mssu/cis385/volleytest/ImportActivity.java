package edu.mssu.cis385.volleytest;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import edu.mssu.cis385.volleytest.model.Subject;
import edu.mssu.cis385.volleytest.R;

public class ImportActivity extends AppCompatActivity {

    private LinearLayout mSubjectLayoutContainer;
    private StudyFetcher mStudyFetcher;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        mSubjectLayoutContainer = findViewById(R.id.subject_layout);

        // Show progress bar
        mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        mLoadingProgressBar.setVisibility(View.VISIBLE);

        mStudyFetcher = new StudyFetcher(this);
        mStudyFetcher.fetchSubjects(mFetchListener);
    }

    private final StudyFetcher.OnStudyDataReceivedListener mFetchListener =
            new StudyFetcher.OnStudyDataReceivedListener() {

                @Override
                public void onSubjectsReceived(List<Subject> subjectList) {

                    // Hide progress bar
                    mLoadingProgressBar.setVisibility(View.GONE);

                    // Create a checkbox for each subject
                    for (Subject subject : subjectList) {
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setTextSize(24);
                        checkBox.setText(subject.getText());
                        checkBox.setTag(subject);
                        mSubjectLayoutContainer.addView(checkBox);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error loading subjects. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };
}