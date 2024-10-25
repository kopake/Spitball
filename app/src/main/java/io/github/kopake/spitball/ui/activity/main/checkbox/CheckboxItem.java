package io.github.kopake.spitball.ui.activity.main.checkbox;

public class CheckboxItem<T> {
    private T item;
    private boolean isChecked;

    public CheckboxItem(T item, boolean isChecked) {
        this.item = item;
        this.isChecked = isChecked;
    }

    public String getItemString() {
        return item.toString();
    }

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
