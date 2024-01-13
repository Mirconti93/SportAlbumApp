package com.mirco.sportalbum.ui.tournaments.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.mirco.sportalbum.R;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.models.PlayerModel;
import com.mirco.sportalbum.models.TeamModel;

import java.util.List;

public class MatchScreenAdapter extends BaseAdapter {
    private List<PlayerModel> players;
    private Context context;
    MatchInfo matchInfo;

    public MatchScreenAdapter(List<PlayerModel> players, Context context, MatchInfo matchInfo) {
        this.context = context;
        this.matchInfo = matchInfo;
        updateRank(players);
    }

    public void updateRank(List<PlayerModel> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public PlayerModel getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.adapter_match_screen, null);
        TextView roleLineUpText = convertView.findViewById(R.id.roleLineUpText);
        TextView areaText = convertView.findViewById(R.id.nameText);
        TextView valText = convertView.findViewById(R.id.valText);
        ImageView golIcon = convertView.findViewById(R.id.golIcon);
        ImageView eventoIcon = convertView.findViewById(R.id.eventoIcon);
        TextView numGolText = convertView.findViewById(R.id.numGolText);

        PlayerModel player = getItem(position);

        TeamModel teamModel = TeamDataManager.teamFromName(matchInfo.getTeamName());
        convertView.setBackgroundColor(ResourcesCompat.getColor(getContext().getResources(), TeamDataManager.getTeamColor(teamModel), null));
        int textColor = ResourcesCompat.getColor(getContext().getResources(), teamModel != null ? TeamDataManager.getTextColor(teamModel.getColor1()) : R.color.black, null);

        roleLineUpText.setText(player.getRoleLineUp().getText());
        roleLineUpText.setTextColor(textColor);
        areaText.setText(player.getMinifiedName());
        areaText.setTextColor(textColor);
        valText.setText(matchInfo.isLegend() ? player.getValueLegend().toString() : player.getValue().toString());
        valText.setTextColor(textColor);
        int numGoals = matchInfo.checkMarcatore(player);
        if (numGoals == 1) {
            golIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.baloon_icon, null));
            numGolText.setVisibility(View.GONE);
        } else if (numGoals > 1) {
            golIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.baloon_icon, null));
            numGolText.setVisibility(View.VISIBLE);
            numGolText.setText(String.valueOf(numGoals));
        } else {
            golIcon.setImageDrawable(null);
            numGolText.setVisibility(View.GONE);
        }

        if (player.isAmmonito()) {
            eventoIcon.setBackgroundColor(ResourcesCompat.getColor(getContext().getResources(), R.color.yellow, null));
        }
        if (player.isEspuslo()) {
            eventoIcon.setBackgroundColor(ResourcesCompat.getColor(getContext().getResources(), R.color.red1, null));
        }

        eventoIcon.setVisibility(View.VISIBLE);

        return convertView;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
