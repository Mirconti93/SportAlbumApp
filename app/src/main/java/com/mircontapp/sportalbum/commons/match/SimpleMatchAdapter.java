//package com.mirco.sportalbum.ui.tournaments.match;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.mirco.sportalbum.R;
//import com.mirco.sportalbum.file_manager.PlayerDataManager;
//import com.mirco.sportalbum.models.MatchModel;
//import com.mirco.sportalbum.models.PlayerModel;
//import com.mirco.sportalbum.models.TeamModel;
//
//import java.util.List;
//
//public class SimpleMatchAdapter extends BaseAdapter {
//    private List<MatchModel> matches;
//    private Context context;
//
//
//    public SimpleMatchAdapter(List<MatchModel> matches, Context context) {
//        this.context = context;
//        this.matches = matches;
//    }
//
//    @Override
//    public int getCount() {
//        return matches.size();
//    }
//
//    @Override
//    public MatchModel getItem(int position) {
//        return matches.get(position);
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
//        convertView = LayoutInflater.from(context).inflate(R.layout.match_simple, null);
//
//        MatchModel match = getItem(position);
//
//        TextView homeName =  convertView.findViewById(R.id.homeName);
//        TextView awayName =  convertView.findViewById(R.id.awayName);
//
//        homeName.setText(match.getHomeTeam().getName());
//        awayName.setText(match.getAwayTeam().getName());
//
//        return convertView;
//    }
//
//
//
//}
