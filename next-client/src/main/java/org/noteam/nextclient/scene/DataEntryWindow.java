package org.noteam.nextclient.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * A scaffold for creating data entry windows that return results to the main
 * window.
 *
 * @param <T> The type of object this window will create/return
 */
public class DataEntryWindow<T> {

    private final Stage stage;
    private final BorderPane root;
    private final VBox contentArea;
    private final HBox buttonBar;

    private T resultData;
    private boolean submitted = false;
    private Consumer<T> onSubmitCallback;
    private Runnable onCancelCallback;

    public DataEntryWindow(String title, Stage owner) {
        this(title, owner, 1280, 720);
    }

    public DataEntryWindow(String title, Stage owner, double width, double height) {
        stage = new Stage();
        stage.setTitle(title);
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Main layout
        root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Content area - this is where you'll add your form fields
        contentArea = new VBox(15);
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        root.setCenter(contentArea);

        // Button bar at bottom
        buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPadding(new Insets(15, 0, 0, 0));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> handleCancel());
        cancelBtn.setStyle("-fx-padding: 10 25; -fx-font-size: 13px;");

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> handleSubmit());
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-padding: 10 25; -fx-font-size: 13px; -fx-font-weight: bold;");

        buttonBar.getChildren().addAll(cancelBtn, submitBtn);
        root.setBottom(buttonBar);

        // Scene setup
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Handle window close button (X)
        stage.setOnCloseRequest(e -> {
            e.consume();
            handleCancel();
        });
    }

    /**
     * Sets the content of this window
     */
    public DataEntryWindow<T> setContent(Region content) {
        buttonBar.setVisible(false);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
        VBox.setVgrow(content, Priority.ALWAYS);
        return this;
    }

    /**
     * Gets the content area for adding form fields
     */
    public VBox getContentArea() {
        return contentArea;
    }

    /**
     * Sets the callback to be executed when data is submitted
     */
    public DataEntryWindow<T> onSubmit(Consumer<T> callback) {
        this.onSubmitCallback = callback;
        return this;
    }

    /**
     * Sets the callback to be executed when cancelled
     */
    public DataEntryWindow<T> onCancel(Runnable callback) {
        this.onCancelCallback = callback;
        return this;
    }

    /**
     * Sets the data extraction logic - how to get T from the form
     */
    private DataExtractor<T> dataExtractor;

    public DataEntryWindow<T> setDataExtractor(DataExtractor<T> extractor) {
        this.dataExtractor = extractor;
        return this;
    }

    /**
     * Shows the window and waits for user action
     *
     * @return Optional containing the result data if submitted, empty if cancelled
     */
    public Optional<T> showAndWaitForResult() {
        submitted = false;
        resultData = null;
        stage.showAndWait();
        return submitted ? Optional.ofNullable(resultData) : Optional.empty();
    }

    /**
     * Shows the window without blocking
     */
    public void show() {
        stage.show();
    }

    /**
     * Programmatically submit the form
     */
    public void submit(T data) {
        this.resultData = data;
        this.submitted = true;

        if (onSubmitCallback != null) {
            onSubmitCallback.accept(data);
        }

        stage.close();
    }

    /**
     * Gets the underlying stage for advanced customization
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Customizes the button bar
     */
    public HBox getButtonBar() {
        return buttonBar;
    }

    private void handleSubmit() {
        if (dataExtractor != null) {
            try {
                T data = dataExtractor.extractData();
                if (data != null) {
                    submit(data);
                }
            } catch (ValidationException e) {
                // Handle validation errors - you can add error display logic here
                showValidationError(e.getMessage());
            }
        } else {
            stage.close();
        }
    }

    private void handleCancel() {
        submitted = false;
        resultData = null;

        if (onCancelCallback != null) {
            onCancelCallback.run();
        }

        stage.close();
    }

    private void showValidationError(String message) {
        // Simple error display - customize as needed
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    /**
     * Functional interface for extracting data from the form
     */
    @FunctionalInterface
    public interface DataExtractor<T> {
        T extractData() throws ValidationException;
    }

    /**
     * Exception for validation errors
     */
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
