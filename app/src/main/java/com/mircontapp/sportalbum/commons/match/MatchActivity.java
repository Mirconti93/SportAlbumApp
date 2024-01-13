package com.mirco.sportalbum.ui.tournaments.match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mirco.sportalbum.R;
import com.mirco.sportalbum.main.AlbumActivity;

public class MatchActivity extends AlbumActivity {

    MatchViewModel matchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        goToFragment(new MatchSelectionFragment(), null);

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(getFragmentContainerId());
        if (fragment instanceof MatchSelectionFragment) {
            this.setResult(RESULT_OK);
            this.finish();
            return;
        } else {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        }


    }

    @Override
    public int getFragmentContainerId() {
        return R.id.match_root_container;
    }

}
