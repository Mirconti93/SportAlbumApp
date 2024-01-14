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
//
//import com.mirco.sportalbum.R;
//import com.mirco.sportalbum.file_manager.LineUpDataManager;
//import com.mirco.sportalbum.file_manager.PlayerDataManager;
//import com.mirco.sportalbum.file_manager.TeamDataManager;
//import com.mirco.sportalbum.models.PlayerModel;
//import com.mirco.sportalbum.models.TeamModel;
//import com.mirco.sportalbum.models.TiratoreModel;
//import com.mirco.sportalbum.ui.AlbumDialog;
//import com.mirco.sportalbum.utils.EnumAdapter;
//import com.mirco.sportalbum.utils.Enums;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class PenaltiesLineUpAdapter extends BaseAdapter {
//
//    private List<TiratoreModel> players;
//    private Context context;
//    TeamModel teamModel;
//
//    public PenaltiesLineUpAdapter(List<TiratoreModel> players, Context context, TeamModel teamModel) {
//        this.context = context;
//        this.teamModel = teamModel;
//        updatePlayers(players);
//    }
//
//    public void updatePlayers(List<TiratoreModel> players) {
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
//    public TiratoreModel getItem(int position) {
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
//        TiratoreModel tiratoreModel =  getItem(position);
//        PlayerModel playerModel = tiratoreModel.getPlayerModel();
//
//        CardView itemCard = convertView.findViewById(R.id.itemCard);
//        TextView roleText =  convertView.findViewById(R.id.role);
//        TextView playerText =  convertView.findViewById(R.id.playerText);
//        TextView valText =  convertView.findViewById(R.id.val);
//
//        playerText.setText(playerModel.getName());
//        roleText.setText(playerModel.getRoleLineUp() != null ? playerModel.getRoleLineUp().name() : LineUpDataManager.getRoleLineUp(playerModel.getRole()).name());
//        if (playerModel.getPlayerStatsModel() != null) {
//            valText.setText(String.valueOf(playerModel.getPlayerStatsModel().getRig().intValue()));
//        } else {
//            valText.setVisibility(View.INVISIBLE);
//        }
//
//        if (tiratoreModel.isSelected()) {
//            itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
//        } else {
//            int color =  context.getResources().getColor(TeamDataManager.getTeamColor(teamModel));
//            int textColor = context.getResources().getColor(TeamDataManager.getTextColor(teamModel.getColor1()));
//
//            itemCard.setCardBackgroundColor(color);
//            playerText.setTextColor(textColor);
//            valText.setTextColor(textColor);
//        }
//
//        return convertView;
//    }
//
//
//}
