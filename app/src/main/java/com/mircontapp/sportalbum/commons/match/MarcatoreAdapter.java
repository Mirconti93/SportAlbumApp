package com.mirco.sportalbum.ui.tournaments.match;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.mirco.sportalbum.R;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.models.MarcatoreModel;
import com.mirco.sportalbum.models.TeamModel;

import java.util.List;

public class MarcatoreAdapter extends BaseAdapter {
    List<MarcatoreModel> marcatori;
    FragmentActivity activity;

    public MarcatoreAdapter(List<MarcatoreModel> marcatori, FragmentActivity activity) {
        this.marcatori = marcatori;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return marcatori != null ? marcatori.size() : 0;
    }

    @Override
    public MarcatoreModel getItem(int position) {
        return marcatori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MarcatoreModel marcatore = marcatori.get(position);

        convertView = LayoutInflater.from(activity).inflate(R.layout.adapter_marcatore, null);

        CardView marcatoreCard = convertView.findViewById(R.id.marcatoreCard);
        TextView marcatoreName = convertView.findViewById(R.id.marcatoreName);

        TeamModel teamModel = TeamDataManager.teamFromName(marcatore.getTeamName());
        marcatoreName.setText(marcatore.getMarcatoreText());

        marcatoreCard.setCardBackgroundColor(activity.getResources().getColor(TeamDataManager.getTeamColor(teamModel)));
        marcatoreName.setTextColor(activity.getResources().getColor(TeamDataManager.getTextColor(teamModel.getColor1())));

        return convertView;

    }
}
