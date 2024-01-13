package com.mirco.sportalbum.ui.tournaments.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirco.sportalbum.R;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.main.AlbumFragment;
import com.mirco.sportalbum.models.PlayerModel;
import com.mirco.sportalbum.models.TeamModel;
import com.mirco.sportalbum.ui.AlbumDialog;
import com.mirco.sportalbum.utils.EnumAdapter;
import com.mirco.sportalbum.utils.Enums;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class MatchLineUpFragment extends AlbumFragment {

    TextView titleList;
    GridView playersGrid;
    Button moduleBtn;
    Button confirmBtn;
    Button benchBtn;
    MatchLineUpAdapter adapter;
    View peerPlaceHolder;

    private MatchViewModel matchViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match_line_up, null, false);
        matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);
        matchViewModel.getCurrentFocus().observe(getViewLifecycleOwner(), integer -> updateUI());
        matchViewModel.getHomeEleven().observe(getViewLifecycleOwner(), integer -> updateUI());
        matchViewModel.getAwayEleven().observe(getViewLifecycleOwner(), integer -> updateUI());
        matchViewModel.getLineUpChoice().observe(getViewLifecycleOwner(), lineUpChoice -> updateUI());
        return root;
    }

    @Override
    public void uiBinding(View root) {
        titleList = root.findViewById(R.id.titleList);
        playersGrid = root.findViewById(R.id.playersGrid);
        confirmBtn = root.findViewById(R.id.confirmBtn);
        moduleBtn = root.findViewById(R.id.moduleBtn);
        benchBtn = root.findViewById(R.id.benchBtn);
        peerPlaceHolder = root.findViewById(R.id.peerPlaceHolder);

    }

    @Override
    public void updateUI() {
        TeamModel teamModel = matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME ? matchViewModel.getHomeTeam().getValue() : matchViewModel.getAwayTeam().getValue();
        getView().setBackgroundColor(getResources().getColor(TeamDataManager.getTeamSecondaryColor(teamModel)));
        titleList.setText(getString(R.string.lineUp) + " " + teamModel.getName());
        titleList.setTextColor(getResources().getColor(TeamDataManager.getTextColor(teamModel.getColor2())));
        titleList.setVisibility(View.VISIBLE);

        if (matchViewModel.getLineUpChoice().getValue() == Enums.LineUpChoice.FIELD) {
             adapter = new MatchLineUpAdapter(matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME ?
                    matchViewModel.getHomeEleven().getValue() :
                    matchViewModel.getAwayEleven().getValue(),
                    getContext(), matchViewModel.getLineUpChoice().getValue(),
                    matchViewModel.isLegend());

        } else {
             adapter = new MatchLineUpAdapter(matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME ?
                    matchViewModel.getHomeBench().getValue() :
                    matchViewModel.getAwayBench().getValue(),
                    getContext(), matchViewModel.getLineUpChoice().getValue(),
                    matchViewModel.isLegend());
        }
        playersGrid.setAdapter(adapter);

        peerPlaceHolder.setVisibility(matchViewModel.getLineUpChoice().getValue() == Enums.LineUpChoice.BENCH ? View.VISIBLE : View.GONE);

        moduleBtn.setText(matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME ? matchViewModel.getModHome().getText() : matchViewModel.getModAway().getText());
        benchBtn.setText(matchViewModel.getLineUpChoice().getValue() == Enums.LineUpChoice.FIELD ?
                getString(R.string.bench):
                getString(R.string.field));

    }

    @Override
    public void uiListeners() {
        confirmBtn.setOnClickListener(v -> {
            //if before the match start
            if (matchViewModel.getMinute().getValue() <= 0) {
                if (matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME) {
                    matchViewModel.getCurrentFocus().setValue(MatchViewModel.AWAY);
                } else {
                    goToFragment(new MatchFragment());
                }
            } else {
                getActivity().onBackPressed();
            }
        });

        playersGrid.setOnItemClickListener((parent, view, position, id) -> {
            PlayerModel playerModel = adapter.getItem(position);
            matchViewModel.checkSelection(playerModel);
            if (matchViewModel.getLineUpChoice().getValue() == Enums.LineUpChoice.BENCH) {
                matchViewModel.getLineUpChoice().setValue(Enums.LineUpChoice.FIELD);
            }
        });

        moduleBtn.setOnClickListener(v -> {
            EnumAdapter adapter = new EnumAdapter(Arrays.asList(Enums.MatchModule.values()), getContext());
            AlbumDialog albumDialog = new AlbumDialog(getContext());
            albumDialog.openAlbumPopupList(adapter, (parent, view, position, id) -> {
                Enums.EnumWriteble ew = adapter.getItem(position);
                if (ew instanceof Enums.MatchModule) {
                    Enums.MatchModule mod = (Enums.MatchModule) ew;
                    matchViewModel.changeModule(mod);
                    albumDialog.dismiss();
                }
            });
        });

        benchBtn.setOnClickListener(v -> {
            if (matchViewModel.getLineUpChoice().getValue() == Enums.LineUpChoice.FIELD) {
                matchViewModel.getLineUpChoice().setValue(Enums.LineUpChoice.BENCH);
            } else {
                matchViewModel.getLineUpChoice().setValue(Enums.LineUpChoice.FIELD);
            }
        });

    }

}
