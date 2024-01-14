//package com.mirco.sportalbum.ui.tournaments.match;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.mirco.sportalbum.R;
//import com.mirco.sportalbum.file_manager.TeamDataManager;
//import com.mirco.sportalbum.main.AlbumFragment;
//import com.mirco.sportalbum.models.MatchModel;
//import com.mirco.sportalbum.models.TeamModel;
//import com.mirco.sportalbum.utils.Enums;
//import com.mirco.sportalbum.utils.GUIHelper;
//
//public class PenaltiesFragment extends AlbumFragment {
//
//    PenaltiesViewModel penaltiesViewModel;
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
//    ImageView protagonista;
//    ImageView coprotagonista;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View root = inflater.inflate(R.layout.fragment_penalties, null, false);
//        uiBinding(root);
//
//        penaltiesViewModel = ViewModelProviders.of(getActivity()).get(PenaltiesViewModel.class);
//        penaltiesViewModel.getMatch().getValue().setPossesso(Enums.Possesso.HOME);
//
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
//        tvHomeResult = root.findViewById(R.id.homeResult);
//        tvawayResult = root.findViewById(R.id.awayResult);
//        tvMintute = root.findViewById(R.id.minuteText);
//        homeLogo = root.findViewById(R.id.homeLogo);
//        awayLogo = root.findViewById(R.id.awayLogo);
//        telMessage = root.findViewById(R.id.telMessage);
//        tvHomeMini = root.findViewById(R.id.homeMini);
//        tvAwayMini = root.findViewById(R.id.awayMini);
//        protagonista = root.findViewById(R.id.protagonista);
//        coprotagonista = root.findViewById(R.id.coprotagonista);
//
//    }
//
//    @Override
//    public void updateUI() {
//        PenaltiesScreenAdapter homeScreenAdapter = new PenaltiesScreenAdapter(penaltiesViewModel.getTiratoriHome().getValue(), getContext(),
//                penaltiesViewModel.getMatchInfo(penaltiesViewModel.getHomeTeam().getValue().getName()));
//        homePlayerList.setAdapter(homeScreenAdapter);
//
//        PenaltiesScreenAdapter awayScreenAdapter = new PenaltiesScreenAdapter(penaltiesViewModel.getTiratoriAway().getValue(), getContext(),
//                penaltiesViewModel.getMatchInfo(penaltiesViewModel.getAwayTeam().getValue().getName()));
//        awayPlayerList.setAdapter(awayScreenAdapter);
//
//        tvHomeName.setText(penaltiesViewModel.getHomeTeam().getValue().getName());
//        tvAwayName.setText(penaltiesViewModel.getAwayTeam().getValue().getName());
//
//        tvHomeMini.setText(penaltiesViewModel.getHomeTeam().getValue().getShortnerText().toUpperCase());
//        tvAwayMini.setText(penaltiesViewModel.getAwayTeam().getValue().getShortnerText().toUpperCase());
//
//        drawTeamLogo(homeLogo, penaltiesViewModel.getHomeTeam().getValue(), getContext());
//        drawTeamLogo(awayLogo, penaltiesViewModel.getAwayTeam().getValue(), getContext());
//
//        tvHomeResult.setText(penaltiesViewModel.getMatch().getValue().getScoreHome() + "");
//        tvawayResult.setText(penaltiesViewModel.getMatch().getValue().getScoreAway() + "");
//
//        if (penaltiesViewModel.getTurn() != null) {
//            tvMintute.setText((penaltiesViewModel.getTurn().intValue() + 1) + "");
//        }
//
//        MatchModel matchModel = penaltiesViewModel.getMatch().getValue();
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
//
//    }
//
//    @Override
//    public void uiListeners() {
//
//        penaltiesViewModel.getMatch().observe(getViewLifecycleOwner(), matchModel -> updateUI());
//
//        nextBtn.setOnClickListener(v -> {
//            if (penaltiesViewModel.isOk()) {
//                MatchEndPopup matchEndPopup = new MatchEndPopup(getContext());
//                matchEndPopup.showConfirmPopup(new MatchEndPopup.MatchEndListener() {
//                    @Override
//                    public void onMatchEnd() {
//                        getActivity().finish();
//                    }
//
//                    @Override
//                    public void onExtraTime() {
//                        matchEndPopup.dismiss();
//                    }
//
//                    @Override
//                    public void onPenalties() {
//                        matchEndPopup.dismiss();
//                    }
//
//                    @Override
//                    public boolean showExtraTime() {
//                        return false;
//                    }
//                });
//            } else {
//                penaltiesViewModel.nextAction();
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
