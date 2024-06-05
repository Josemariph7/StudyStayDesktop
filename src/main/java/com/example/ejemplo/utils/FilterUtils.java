package com.example.ejemplo.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterUtils {

    public static <T> void populateList(VBox vbox, List<T> items, String fxmlPath, FilterItemInitializer<T> initializer) {
        vbox.getChildren().clear();
        for (T item : items) {
            try {
                FXMLLoader loader = new FXMLLoader(FilterUtils.class.getResource(fxmlPath));
                Node node = loader.load();
                initializer.initData(loader.getController(), item, node, vbox);
                vbox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> void filterList(String filter, List<T> allItems, VBox vbox, String fxmlPath, Predicate<T> predicate, FilterItemInitializer<T> initializer) {
        List<T> filteredItems;
        if (filter == null || filter.isEmpty()) {
            filteredItems = allItems;
        } else {
            filteredItems = allItems.stream().filter(predicate).collect(Collectors.toList());
        }
        populateList(vbox, filteredItems, fxmlPath, initializer);
    }

    public interface FilterItemInitializer<T> {
        void initData(Object controller, T item, Node node, VBox vbox);
    }
}
