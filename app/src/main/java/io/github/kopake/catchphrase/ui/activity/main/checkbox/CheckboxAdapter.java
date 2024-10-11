package io.github.kopake.catchphrase.ui.activity.main.checkbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import io.github.kopake.catchphrase.R;

public class CheckboxAdapter<T> extends RecyclerView.Adapter<CheckboxAdapter.CheckboxViewHolder> {

    private final List<CheckboxItem<T>> checkboxItems;
    private final Context context;

    public CheckboxAdapter(Context context, List<CheckboxItem<T>> checkboxItems) {
        this.context = context;
        this.checkboxItems = checkboxItems;
    }

    @NonNull
    @Override
    public CheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CheckboxAdapter", "onCreateViewHolder called"); // Add logging here
        View view = LayoutInflater.from(context).inflate(R.layout.item_checkbox, parent, false);
        return new CheckboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckboxViewHolder holder, int position) {
        Log.d("CheckboxAdapter", "onBindViewHolder called for position " + position); // Add logging here
        CheckboxItem item = checkboxItems.get(position);
        holder.checkBox.setText(item.getItemString());
        holder.checkBox.setChecked(item.isChecked());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkboxItems.get(position).setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return checkboxItems.size();
    }

    public List<T> getCheckedItems() {
        return checkboxItems.stream()
                .filter(CheckboxItem::isChecked)
                .map(CheckboxItem::getItem)
                .collect(Collectors.toList());
    }


    static class CheckboxViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        CheckboxViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
