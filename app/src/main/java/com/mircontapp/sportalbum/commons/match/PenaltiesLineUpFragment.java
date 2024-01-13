package com.mirco.sportalbum.ui.tournaments.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.mirco.sportalbum.R;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.main.AlbumFragment;
import com.mirco.sportalbum.models.PlayerModel;
import com.mirco.sportalbum.models.TeamModel;
import com.mirco.sportalbum.models.TiratoreModel;
import com.mirco.sportalbum.sticker_album.LineUpFragment;
import com.mirco.sportalbum.ui.AlbumDialog;
import com.mirco.sportalbum.utils.EnumAdapter;
import com.mirco.sportalbum.utils.Enums;

import java.util.Arrays;

public class PenaltiesLineUpFragment extends AlbumFragment {

    ListView homeList;
    ListView awayList;
    Button confirmBtn;

    PenaltiesLineUpAdapter homeAdapter;
    PenaltiesLineUpAdapter awayAdapter;

    private PenaltiesViewModel penaltiesViewModel;
    private MatchViewModel matchViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_penalties_selection, null, false);
        penaltiesViewModel = ViewModelProviders.of(getActivity()).get(PenaltiesViewModel.class);
        matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);
        penaltiesViewModel.init(matchViewModel);
        penaltiesViewModel.getHomeEleven().observe(getViewLifecycleOwner(), integer -> updateUI());
        penaltiesViewModel.getAwayEleven().observe(getViewLifecycleOwner(), integer -> updateUI());
        return root;
    }

    @Override
    public void uiBinding(View root) {
        homeList = root.findViewById(R.id.homeList);
        awayList = root.findViewById(R.id.awayList);
        confirmBtn = root.findViewById(R.id.confirmBtn);
    }

    @Override
    public void updateUI() {
        TeamModel home = penaltiesViewModel.getHomeTeam().getValue();

        homeAdapter = new PenaltiesLineUpAdapter(
                penaltiesViewModel.getTiratoriHome().getValue(),
                getContext(), home);
        homeList.setAdapter(homeAdapter);
        homeList.setBackgroundColor(getResources().getColor(TeamDataManager.getTeamSecondaryColor(home)));

        TeamModel away = penaltiesViewModel.getAwayTeam().getValue();

        awayAdapter = new PenaltiesLineUpAdapter(penaltiesViewModel.getTiratoriAway().getValue(),
                    getContext(), away);
        awayList.setAdapter(awayAdapter);
        awayList.setBackgroundColor(getResources().getColor(TeamDataManager.getTeamSecondaryColor(away)));


    }

    @Override
    public void uiListeners() {
        confirmBtn.setOnClickListener(v -> {
            //if before the match start
            goToFragment(new PenaltiesFragment());
        });

        homeList.setOnItemClickListener((parent, view, position, id) -> {
            TiratoreModel tiratoreModel = homeAdapter.getItem(position);
            penaltiesViewModel.homeSelection(tiratoreModel);
        });

        awayList.setOnItemClickListener((parent, view, position, id) -> {
            TiratoreModel tiratoreModel = awayAdapter.getItem(position);
            penaltiesViewModel.homeSelection(tiratoreModel);
        });


    }

}
