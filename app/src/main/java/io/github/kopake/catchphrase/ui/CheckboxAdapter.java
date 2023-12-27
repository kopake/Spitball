package io.github.kopake.catchphrase.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.event.CategoryChangeEvent;
import io.github.kopake.catchphrase.game.event.EventManager;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.ViewHolder> {

    private List<String> itemList;
    private List<String> selectedItems; // Keep track of selected items
    private Context context;

    public CheckboxAdapter(Context context, List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.selectedItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.textView.setText(item);

        // Handle checkbox state here (checked or unchecked)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle checkbox state change if needed
            if (isChecked) {
                // If checked, add the item to the selectedItems list
                selectedItems.add(item);
            } else {
                // If unchecked, remove the item from the selectedItems list
                selectedItems.remove(item);
            }
            EventManager.getInstance().dispatchEvent(new CategoryChangeEvent(selectedItems));
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
