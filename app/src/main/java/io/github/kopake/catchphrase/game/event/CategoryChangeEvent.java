package io.github.kopake.catchphrase.game.event;

import java.util.List;

public class CategoryChangeEvent implements Event {
    private List<String> categoryNames;

    public CategoryChangeEvent(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }
}
