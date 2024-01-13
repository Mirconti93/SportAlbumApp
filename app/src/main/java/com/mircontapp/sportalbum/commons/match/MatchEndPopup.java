package com.mirco.sportalbum.ui.tournaments.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDialog;

import com.mirco.sportalbum.R;

public class MatchEndPopup extends AppCompatDialog {


    public MatchEndPopup(Context context) {
        super(context);
    }

    public void showConfirmPopup(MatchEndListener matchEndListener) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.popup_match_end, null);

        Button endMatch = root.findViewById(R.id.endMatch);
        Button extraTime = root.findViewById(R.id.extraTime);
        Button penalties = root.findViewById(R.id.penalties);

        endMatch.setOnClickListener((v)-> matchEndListener.onMatchEnd());
        if (matchEndListener.showExtraTime()) {
            extraTime.setOnClickListener((v) -> matchEndListener.onExtraTime());
        } else {
            extraTime.setVisibility(View.GONE);
        }

        penalties.setOnClickListener((v) -> matchEndListener.onPenalties());

        setContentView(root);
        show();

    }

    public interface MatchEndListener {
        void onMatchEnd();
        void onExtraTime();
        void onPenalties();
        boolean showExtraTime();
    }

}
