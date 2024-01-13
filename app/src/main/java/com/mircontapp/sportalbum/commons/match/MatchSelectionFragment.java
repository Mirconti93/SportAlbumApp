package com.mirco.sportalbum.ui.tournaments.match;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mirco.sportalbum.R;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.main.AlbumFragment;
import com.mirco.sportalbum.models.GenericModel;
import com.mirco.sportalbum.models.TeamModel;
import com.mirco.sportalbum.ui.AlbumDialog;
import com.mirco.sportalbum.utils.AlbumApplication;
import com.mirco.sportalbum.utils.Enums;
import com.mirco.sportalbum.utils.GenericAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MatchSelectionFragment extends AlbumFragment {

    AlbumApplication app = AlbumApplication.getInstance();

    private MatchViewModel matchViewModel;

    private CardView homePanel;
    private ImageView homeIcon;
    private TextView homeName;

    private CardView awayPanel;
    private ImageView awayIcon;
    private TextView awayName;
    private Button legendButton;
    private Button typeButton;

    private Button confirmBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match_selection, null, false);
        matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);

        return root;
    }

    @Override
    public void uiBinding(View root) {
        homePanel = root.findViewById(R.id.homePanel);
        homeIcon = root.findViewById(R.id.homeIcon);
        homeName = root.findViewById(R.id.homeName);
        legendButton = root.findViewById(R.id.legendButton);
        typeButton = root.findViewById(R.id.typeButton);

        awayPanel = root.findViewById(R.id.awayPanel);
        awayIcon = root.findViewById(R.id.awayIcon);
        awayName = root.findViewById(R.id.awayName);
        confirmBtn = root.findViewById(R.id.confirmBtn);
    }

    @Override
    public void updateUI() {
        drawTeamLogo(homeIcon, matchViewModel.getHomeTeam().getValue(), getContext());
        drawTeamLogo(awayIcon, matchViewModel.getAwayTeam().getValue(), getContext());
        homeName.setText(matchViewModel.getHomeTeam().getValue().getName());
        homeName.setTextColor(getResources().getColor(TeamDataManager.getTextColor(matchViewModel.getHomeTeam().getValue().getColor1())));
        awayName.setText(matchViewModel.getAwayTeam().getValue().getName());
        awayName.setTextColor(getResources().getColor(TeamDataManager.getTextColor(matchViewModel.getAwayTeam().getValue().getColor1())));

        homePanel.setCardBackgroundColor(getResources().getColor(TeamDataManager.getTeamColor(matchViewModel.getHomeTeam().getValue())));
        awayPanel.setCardBackgroundColor(getResources().getColor(TeamDataManager.getTeamColor(matchViewModel.getAwayTeam().getValue())));

        legendButton.setText(getString(matchViewModel.isLegend() ? Enums.PlayerStatus.LEGENDS.getText() : Enums.PlayerStatus.ACTUAL.getText()));
        typeButton.setText(getString(matchViewModel.getMatchType() == Enums.MatchType.SIMPLE_MATCH ? R.string.matchSimple : R.string.penalties));
    }

    @Override
    public void uiListeners() {
        homePanel.setOnClickListener(v -> {
            List<TeamModel> teams = new ArrayList<>(app.getTeams());
            GenericAdapter adapter = new GenericAdapter(new ArrayList<>(TeamDataManager.teamsGroupArea(teams)), getContext());
            AlbumDialog albumDialog = new AlbumDialog(getContext());
            albumDialog.openAlbumPopupList(adapter, (parent, view, position, id) -> {
                GenericModel gm = adapter.getItem(position);
                if (gm instanceof TeamModel) {
                    TeamModel teamModel = (TeamModel) gm;
                    matchViewModel.getHomeTeam().setValue(teamModel);
                    updateUI();
                    if (albumDialog != null) {
                        albumDialog.dismiss();
                    }
                }
            });
        });

        awayPanel.setOnClickListener(v -> {
            List<TeamModel> teams = new ArrayList<>(app.getTeams());
            GenericAdapter adapter = new GenericAdapter(new ArrayList<>(TeamDataManager.teamsGroupArea(teams)), getContext());
            AlbumDialog albumDialog = new AlbumDialog(getContext());
            albumDialog.openAlbumPopupList(adapter, (parent, view, position, id) -> {
                GenericModel gm = adapter.getItem(position);
                if (gm instanceof TeamModel) {

                    TeamModel teamModel = (TeamModel) gm;
                    matchViewModel.getAwayTeam().setValue(teamModel);
                    updateUI();
                    if (albumDialog != null) {
                        albumDialog.dismiss();
                    }
                }
            });
        });

        confirmBtn.setOnClickListener(v -> {
            matchViewModel.buildTeamsPlayers(getContext());
            if (matchViewModel.getMatchType() == Enums.MatchType.PENALTIES) {
                goToFragment(new PenaltiesLineUpFragment());
            } else {
                goToFragment(new MatchLineUpFragment());
            }
        });

        legendButton.setOnClickListener(v -> {
            matchViewModel.setLegend(!matchViewModel.isLegend());
            updateUI();
        });

        typeButton.setOnClickListener(v -> {
            matchViewModel.setMatchType(matchViewModel.getMatchType() == Enums.MatchType.SIMPLE_MATCH ?
                    Enums.MatchType.PENALTIES :
                    Enums.MatchType.SIMPLE_MATCH);
            updateUI();
        });

    }

    public void drawTeamLogo(ImageView iv, TeamModel team, Context context) {
        String imageName = team.getName().toLowerCase().replace(" ","_");
        if (team.getArea().getArea() == Enums.Area.CLUBFEMMINILI || team.getArea().getArea() == Enums.Area.NAZIONALIFEMMINILI) {
            imageName = TeamDataManager.checkFeminine(imageName);
        }
        int idDrawable = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (idDrawable <= 0) {
            idDrawable = R.drawable.empty_logo;
        }
        iv.setImageResource(idDrawable);
    }
}
