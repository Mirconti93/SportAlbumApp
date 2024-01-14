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
//import com.mirco.sportalbum.models.PlayerModel;
//import com.mirco.sportalbum.ui.AlbumDialog;
//import com.mirco.sportalbum.utils.EnumAdapter;
//import com.mirco.sportalbum.utils.Enums;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class PenaltiesAdapter extends BaseAdapter {
//
//    private List<PlayerModel> players;
//    private Context context;
//    Enums.LineUpChoice lineUpChoice;
//
//    public PenaltiesAdapter(List<PlayerModel> players, Context context, Enums.LineUpChoice lineUpChoice) {
//        this.context = context;
//        this.lineUpChoice = lineUpChoice;
//        updatePlayers(players);
//    }
//
//    public void updatePlayers(List<PlayerModel> players) {
//        this.players = players;
//
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
//        CardView itemCard = convertView.findViewById(R.id.itemCard);
//        TextView roleText =  convertView.findViewById(R.id.role);
//        TextView playerText =  convertView.findViewById(R.id.playerText);
//        TextView valText =  convertView.findViewById(R.id.val);
//
//        playerText.setText(playerModel.getName());
//        roleText.setText(playerModel.getRoleLineUp() != null ? playerModel.getRoleLineUp().name() : LineUpDataManager.getRoleLineUp(playerModel.getRole()).name());
//        valText.setText(playerModel.getPlayerStatsModel().getRig().toString());
//        //if in starting XI
//
//        if (playerModel.isSelected()) {
//            itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
//        } else {
//            if (lineUpChoice == Enums.LineUpChoice.FIELD) {
//                itemCard.setCardBackgroundColor(context.getResources().getColor(position < 5 ? R.color.lightblue1 : R.color.lightblue2));
//            } else {
//                itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.white));
//            }
//
//
//        }
//
//
//        return convertView;
//    }
//
//
//}
