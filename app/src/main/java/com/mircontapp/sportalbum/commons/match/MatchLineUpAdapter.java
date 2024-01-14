//package com.mirco.sportalbum.ui.tournaments.match;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.core.content.res.ResourcesCompat;
//import androidx.fragment.app.FragmentActivity;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.mirco.sportalbum.R;
//import com.mirco.sportalbum.file_manager.LineUpDataManager;
//import com.mirco.sportalbum.file_manager.PlayerDataManager;
//import com.mirco.sportalbum.file_manager.TeamDataManager;
//import com.mirco.sportalbum.models.PlayerModel;
//import com.mirco.sportalbum.models.TeamModel;
//import com.mirco.sportalbum.ui.AlbumDialog;
//import com.mirco.sportalbum.utils.EnumAdapter;
//import com.mirco.sportalbum.utils.Enums;
//
//import java.util.Arrays;
//import java.util.List;
//
////public class MatchLineUpAdapter extends BaseAdapter {
//
//    private List<PlayerModel> players;
//    private Context context;
//    Enums.LineUpChoice lineUpChoice;
//
//    boolean isLegend = false;
//
//    public MatchLineUpAdapter(List<PlayerModel> players, Context context, Enums.LineUpChoice lineUpChoice, boolean isLegend) {
//        this.context = context;
//        this.lineUpChoice = lineUpChoice;
//        this.isLegend = isLegend;
//        updatePlayers(players);
//    }
//
//    public MatchLineUpAdapter(List<PlayerModel> players, Context context, Enums.LineUpChoice lineUpChoice) {
//        this(players, context, lineUpChoice, false);
//    }
//
//    public void updatePlayers(List<PlayerModel> players) {
//        this.players = players;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return players.size();
//    }
//
//    @Override
//    public PlayerModel getItem(int position) {
//        return players.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        convertView = LayoutInflater.from(context).inflate(R.layout.adapter_player_match, null);
//
//        PlayerModel playerModel = getItem(position);
//
//        MatchViewModel matchViewModel = ViewModelProviders.of((FragmentActivity) context).get(MatchViewModel.class);
//
//        CardView itemCard = convertView.findViewById(R.id.itemCard);
//        TextView roleText =  convertView.findViewById(R.id.role);
//        TextView playerText =  convertView.findViewById(R.id.playerText);
//        TextView valText =  convertView.findViewById(R.id.val);
//
//        playerText.setText(playerModel.getName());
//        valText.setText(isLegend ? playerModel.getValueLegend().toString() : playerModel.getValue().toString());
//
//        //if in starting XI
//        if (lineUpChoice == Enums.LineUpChoice.FIELD) {
//            roleText.setText(playerModel.getRoleLineUp() != null ? playerModel.getRoleLineUp().name() : LineUpDataManager.getRoleLineUp(playerModel.getRole()).name());
//            roleText.setOnClickListener(v -> {
//                EnumAdapter adapter = new EnumAdapter(Arrays.asList(Enums.RoleLineUp.values()), context);
//                AlbumDialog albumDialog = new AlbumDialog(context);
//                albumDialog.openAlbumPopupList(adapter, (parent1, view, position1, id) -> {
//                    Enums.EnumWriteble ew = adapter.getItem(position1);
//                    if (ew instanceof Enums.RoleLineUp) {
//                        Enums.RoleLineUp roleLineUp = (Enums.RoleLineUp) ew;
//                        playerModel.setRoleLineUp(roleLineUp);
//                        if (albumDialog != null) {
//                            albumDialog.dismiss();
//                        }
//                        updatePlayers(PlayerDataManager.sortPlayerByRoleLU(players));
//                    }
//                });
//
//            });
//        } else {
//            roleText.setText(playerModel.getRole().name());
//        }
//
//        if (playerModel.isSelected()) {
//            itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
//        } else {
//            if (playerModel.isEspuslo()) {
//                itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.gray3));
//            } else {
//                if (lineUpChoice == Enums.LineUpChoice.FIELD) {
//                    TeamModel teamModel = matchViewModel.getCurrentFocus().getValue() == MatchViewModel.HOME ? matchViewModel.getHomeTeam().getValue() : matchViewModel.getAwayTeam().getValue();
//                    int color =  context.getResources().getColor(TeamDataManager.getTeamColor(teamModel));
//                    int textColor = context.getResources().getColor(TeamDataManager.getTextColor(teamModel.getColor1()));
//
//                    itemCard.setCardBackgroundColor(color);
//                    playerText.setTextColor(textColor);
//                    valText.setTextColor(textColor);
//
//                } else {
//                    itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.gray2));
//                    playerText.setTextColor(context.getResources().getColor(R.color.black));
//                    valText.setTextColor(context.getResources().getColor(R.color.black));
//                }
//
//            }
//        }
//
//        roleText.setTextColor(context.getResources().getColor(R.color.black));
//
//
//        return convertView;
//    }
//
//
//}
