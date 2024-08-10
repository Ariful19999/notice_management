package com.arif19.vbd.recycleview;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arif19.vbd.R;
import com.arif19.vbd.graphQlApi.GraphQLResponse;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<GraphQLResponse.Data.Country> countries;

    public void setCountries(List<GraphQLResponse.Data.Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GraphQLResponse.Data.Country country = countries.get(position);
        holder.countryName.setText(country.getName());

        // Assuming 'country' has additional details like currencies, awsRegion, and phone
        StringBuilder details = new StringBuilder();
        if (country.getCurrencies() != null && !country.getCurrencies().isEmpty()) {
            details.append("Currency: ").append(country.getCurrencies().get(0).getCode());
        }
        if (country.getAwsRegion() != null) {
            if (details.length() > 0) details.append("\n");
            details.append("Region: ").append(country.getAwsRegion());
        }
        if (country.getPhone() != null) {
            if (details.length() > 0) details.append("\n");
            details.append("Phone Code: ").append(country.getPhone());
        }

        holder.countryDetails.setText(details.toString());
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        TextView countryDetails;

        ViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryName);
            countryDetails = itemView.findViewById(R.id.countryDetails);
        }
    }
}
