//package com.mirco.sportalbum.ui.tournaments.match;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.mirco.sportalbum.R;
//import com.mirco.sportalbum.file_manager.TeamDataManager;
//import com.mirco.sportalbum.main.AlbumFragment;
//import com.mirco.sportalbum.models.MatchModel;
//import com.mirco.sportalbum.models.PlayerModel;
//import com.mirco.sportalbum.models.TeamModel;
//import com.mirco.sportalbum.sticker_album.LineUpFragment;
//import com.mirco.sportalbum.utils.Enums;
//import com.mirco.sportalbum.utils.GUIHelper;
//
//import java.util.List;
//
//public class MatchFragment extends AlbumFragment {
//
//    MatchViewModel matchViewModel;
//
//    ListView homePlayerList;
//    ListView awayPlayerList;
//
//    Button changeHomeBtn;
//    Button changeAwayBtn;
//    Button nextBtn;
//    TextView tvHomeName;
//    TextView tvAwayName;
//    TextView tvHomeMini;
//    TextView tvAwayMini;
//    ImageView homeLogo;
//    ImageView awayLogo;
//    TextView tvMintute;
//    TextView tvHomeResult;
//    TextView tvawayResult;
//    TextView telMessage;
//    GridView marcatoriGrid;
//    ImageView protagonista;
//    ImageView coprotagonista;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View root = inflater.inflate(R.layout.fragment_match, null, false);
//        uiBinding(root);
//
//        matchViewModel = ViewModelProviders.of(getActivity()).get(MatchViewModel.class);
//
//        return root;
//    }
//
//    @Override
//    public void uiBinding(View root) {
//        homePlayerList = root.findViewById(R.id.homePlayerList);
//        awayPlayerList = root.findViewById(R.id.awayPlayerList);
//        changeHomeBtn = root.findViewById(R.id.changeHomeBtn);
//        changeAwayBtn = root.findViewById(R.id.changeAwayBtn);
//        nextBtn = root.findViewById(R.id.nextBtn);
//        tvHomeName = root.findViewById(R.id.homeName);
//        tvAwayName = root.findViewById(R.id.awayName);
//        tvMintute = root.findViewById(R.id.minuteText);
//        tvHomeResult = root.findViewById(R.id.homeResult);
//        tvawayResult = root.findViewById(R.id.awayResult);
//        homeLogo = root.findViewById(R.id.homeLogo);
//        awayLogo = root.findViewById(R.id.awayLogo);
//        telMessage = root.findViewById(R.id.telMessage);
//        tvHomeMini = root.findViewById(R.id.homeMini);
//        tvAwayMini = root.findViewById(R.id.awayMini);
//        protagonista = root.findViewById(R.id.protagonista);
//        coprotagonista = root.findViewById(R.id.coprotagonista);
//        marcatoriGrid = root.findViewById(R.id.marcatoriGrid);
//    }
//
//    @Override
//    public void updateUI() {
//        MatchScreenAdapter homeScreenAdapter = new MatchScreenAdapter(matchViewModel.getHomeEleven().getValue(), getContext(), matchViewModel.getMatchInfo(matchViewModel.getHomeTeam().getValue().getName()));
//        homePlayerList.setAdapter(homeScreenAdapter);
//
//        MatchScreenAdapter awayScreenAdapter = new MatchScreenAdapter(matchViewModel.getAwayEleven().getValue(), getContext(), matchViewModel.getMatchInfo(matchViewModel.getAwayTeam().getValue().getName()));
//        awayPlayerList.setAdapter(awayScreenAdapter);
//
//        String homeTop = matchViewModel.getHomeTeam().getValue().getName();
//        if (matchViewModel.getCoachHome() != null) {
//            homeTop = homeTop + " " + getString(R.string.coach) + " " + matchViewModel.getCoachHome().getName();
//        }
//        tvHomeName.setText(homeTop);
//
//        String awayTop = matchViewModel.getAwayTeam().getValue().getName();
//        if (matchViewModel.getCoachAway() != null) {
//            awayTop = awayTop + " " + getString(R.string.coach) + " " + matchViewModel.getCoachAway().getName();
//        }
//        tvAwayName.setText(awayTop);
//
//        tvHomeMini.setText(matchViewModel.getHomeTeam().getValue().getShortnerText().toUpperCase());
//        tvAwayMini.setText(matchViewModel.getAwayTeam().getValue().getShortnerText().toUpperCase());
//
//        drawTeamLogo(homeLogo, matchViewModel.getHomeTeam().getValue(), getContext());
//        drawTeamLogo(awayLogo, matchViewModel.getAwayTeam().getValue(), getContext());
//
//        tvHomeResult.setText(matchViewModel.getMatch().getValue().getScoreHome() + "");
//        tvawayResult.setText(matchViewModel.getMatch().getValue().getScoreAway() + "");
//
//        tvMintute.setText(matchViewModel.getMinute().getValue().toString() + "'");
//
//        MatchModel matchModel = matchViewModel.getMatch().getValue();
//
//        if (matchModel.getProtagonista() != null && !matchModel.getProtagonista().isEmpty()) {
//            protagonista.setImageDrawable(GUIHelper.getPlayerDrawableByName(matchModel.getProtagonista(), getContext()));
//        } else {
//            protagonista.setVisibility(View.GONE);
//        }
//
//        if (matchModel.getCoprotagonista() != null && !matchModel.getCoprotagonista().isEmpty()) {
//            coprotagonista.setImageDrawable(GUIHelper.getPlayerDrawableByName(matchModel.getCoprotagonista(), getContext()));
//        } else {
//            coprotagonista.setVisibility(View.GONE);
//        }
//
//        telMessage.setText(matchModel.getMessaggio());
//        if (matchModel.getStato() == Enums.Possesso.HOME.ordinal()) {
//            telMessage.setBackgroundColor(getResources().getColor(TeamDataManager.getTeamColor(matchModel.getHomeTeam())));
//            telMessage.setTextColor(getResources().getColor(TeamDataManager.getTextColor(matchModel.getHomeTeam().getColor1())));
//        } else {
//            telMessage.setBackgroundColor(getResources().getColor(TeamDataManager.getTeamColor(matchModel.getAwayTeam())));
//            telMessage.setTextColor(getResources().getColor(TeamDataManager.getTextColor(matchModel.getAwayTeam().getColor1())));
//        }
//
//        MarcatoreAdapter marcatoreAdapter = new MarcatoreAdapter(matchViewModel.getMatch().getValue().getMarcatori(), getActivity());
//        marcatoriGrid.setAdapter(marcatoreAdapter);
//
//    }
//
//    @Override
//    public void uiListeners() {
//
//        matchViewModel.getMinute().observe(getViewLifecycleOwner(), integer -> matchViewModel.nextAction());
//        matchViewModel.getHomeEleven().observe(getViewLifecycleOwner(), playerModels -> updateUI());
//        matchViewModel.getAwayEleven().observe(getViewLifecycleOwner(), playerModels -> updateUI());
//        matchViewModel.getMatch().observe(getViewLifecycleOwner(), matchModel -> updateUI());
//
//        changeHomeBtn.setOnClickListener(v -> {
//            matchViewModel.getCurrentFocus().setValue(MatchViewModel.HOME);
//            goToFragment(new MatchLineUpFragment());
//        });
//
//        changeAwayBtn.setOnClickListener(v -> {
//            matchViewModel.getCurrentFocus().setValue(MatchViewModel.AWAY);
//            goToFragment(new MatchLineUpFragment());
//        });
//
//        tvMintute.setOnClickListener(v -> showEndPopup());
//
//
//        nextBtn.setOnClickListener(v -> {
//            switch (matchViewModel.getMinute().getValue()) {
//                case 90:
//                   showEndPopup();
//                    break;
//                case 120:
//                    showEndPopup();
//                    break;
//                default:
//                    matchViewModel.getMinute().setValue(matchViewModel.getMinute().getValue() + 1);
//                    break;
//            }
//
//        });
//    }
//
//    public void showEndPopup() {
//        MatchEndPopup matchEndPopup = new MatchEndPopup(getContext());
//        matchEndPopup.showConfirmPopup(new MatchEndPopup.MatchEndListener() {
//            @Override
//            public void onMatchEnd() {
//                getActivity().finish();
//            }
//
//            @Override
//            public void onExtraTime() {
//                matchViewModel.getMinute().setValue(matchViewModel.getMinute().getValue() + 1);
//                matchEndPopup.dismiss();
//            }
//
//            @Override
//            public void onPenalties() {
//                goToFragment(new PenaltiesLineUpFragment());
//                matchEndPopup.dismiss();
//            }
//
//            @Override
//            public boolean showExtraTime() {
//                return matchViewModel.getMinute().getValue()< 120
//                        && matchViewModel.getMatch().getValue().getScoreHome() == matchViewModel.getMatch().getValue().getScoreAway() ;
//            }
//        });
//    }
//
//    public void drawTeamLogo(ImageView iv, TeamModel team, Context context) {
//        String imageName = team.getName().toLowerCase().replace(" ","_");
//        if (team.getArea().getArea() == Enums.Area.CLUBFEMMINILI || team.getArea().getArea() == Enums.Area.NAZIONALIFEMMINILI) {
//            imageName = TeamDataManager.checkFeminine(imageName);
//        }
//        int idDrawable = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
//        if (idDrawable <= 0) {
//            idDrawable = R.drawable.empty_logo;
//        }
//        iv.setImageResource(idDrawable);
//    }
//}
