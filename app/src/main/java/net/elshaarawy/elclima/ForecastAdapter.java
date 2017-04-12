package net.elshaarawy.elclima;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by elshaarawy on 03-Apr-17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<String> forecastDataList;
    private ListItemClickListener clickListener;

    public ForecastAdapter(List<String> forecastDataList, ListItemClickListener clickListener) {
        this.forecastDataList = forecastDataList;
        this.clickListener = clickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int position,List<String> dataSet);
    }
    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_forecast;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view = layoutInflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        ForecastViewHolder forecastViewHolder = new ForecastViewHolder(view);

        return forecastViewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(forecastDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return forecastDataList.size();
    }

    public void resetData(List<String> strings){
        forecastDataList.clear();
        forecastDataList.addAll(strings);
        this.notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView listItemForecast;
        public ForecastViewHolder(View itemView) {
            super(itemView);
            listItemForecast = (TextView) itemView.findViewById(R.id.list_item_forecast_textview);
            listItemForecast.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onListItemClick(getAdapterPosition(),forecastDataList);
        }

        void bind(String s){
            listItemForecast.setText(String.valueOf(s));
        }
    }
}
