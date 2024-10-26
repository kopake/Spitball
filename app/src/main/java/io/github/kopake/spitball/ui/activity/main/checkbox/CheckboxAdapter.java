package io.github.kopake.spitball.ui.activity.main.checkbox;

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

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.WordListSelectEvent;
import io.github.kopake.spitball.game.model.WordList;

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
        CheckboxItem<T> checkboxItem = checkboxItems.get(position);
        holder.checkBox.setText(checkboxItem.getItemString());
        holder.checkBox.setChecked(checkboxItem.isChecked());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkboxItem.setChecked(isChecked);
            T item = checkboxItem.getItem();
            if (item instanceof WordList) {
                EventManager.getInstance().dispatchEvent(new WordListSelectEvent((WordList) item, isChecked));
            }
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
