package io.github.kopake.catchphrase.game.event;

public class CategoryChangeEvent implements Event {
    private String categoryName;

    public CategoryChangeEvent(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
