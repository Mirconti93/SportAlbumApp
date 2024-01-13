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
import com.mirco.sportalbum.models.TiratoreModel;

import java.util.List;

public class PenaltiesScreenAdapter extends BaseAdapter {
    private List<TiratoreModel> players;
    private Context context;
    MatchInfo matchInfo;

    public PenaltiesScreenAdapter(List<TiratoreModel> players, Context context, MatchInfo matchInfo) {
        this.context = context;
        this.matchInfo = matchInfo;
        updateRank(players);
    }

    public void updateRank(List<TiratoreModel> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public TiratoreModel getItem(int position) {
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

        TiratoreModel tiratore = getItem(position);
        PlayerModel player = tiratore.getPlayerModel();

        TeamModel teamModel = TeamDataManager.teamFromName(matchInfo.getTeamName());
        convertView.setBackgroundColor(ResourcesCompat.getColor(getContext().getResources(), TeamDataManager.getTeamColor(teamModel), null));
        int textColor = ResourcesCompat.getColor(getContext().getResources(), teamModel != null ? TeamDataManager.getTextColor(teamModel.getColor1()) : R.color.black, null);

        roleLineUpText.setText(player.getRoleLineUp().getText());
        roleLineUpText.setTextColor(textColor);
        areaText.setText(player.getMinifiedName());
        areaText.setTextColor(textColor);
        valText.setText(matchInfo.isLegend() ? player.getValueLegend().toString() : player.getValue().toString());
        valText.setTextColor(textColor);

        if (tiratore.isHasShot()) {
            golIcon.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                tiratore.isGol()?
                    R.drawable.baloon_icon_transparent :
                    R.drawable.error,
                    null));
            golIcon.setVisibility(View.VISIBLE);
        } else {
            golIcon.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
