package com.example.wheeler.AppActions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.wheeler.R;

public class Feedback extends Fragment implements View.OnClickListener{

    View views;
    Button submitFeedback;
    EditText inputFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_feedback, container, false);

        inputFeedback = views.findViewById(R.id.writtenFeedbackId);
        submitFeedback = views.findViewById(R.id.submitFeedbackID);
        submitFeedback.setOnClickListener(this);

        return views;
    }

    @Override
    public void onClick(View v) {
        String feedbackText = inputFeedback.getText().toString();

        if(feedbackText.isEmpty()){
            inputFeedback.setError("Give your feedback");
        }

        else {
            Toast.makeText(getActivity(), "Thanks for your valuable comment", Toast.LENGTH_SHORT).show();
            inputFeedback.setText("");
        }
    }
}
