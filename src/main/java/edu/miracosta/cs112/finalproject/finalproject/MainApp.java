package edu.miracosta.cs112.finalproject.finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application implements EventHandler<ActionEvent> {
    public static void main(String[] args) {
        launch();
    }

    static final int MAX_WIDTH = 64;
    static final int MAX_HEIGHT = 64;

    Stage primaryStage;

    File imageFile;
    Label fileChooserLabel;
    Button fileChooserButton;
    FileChooser fileChooser;

    Slider intensitySlider;
    Label intensityLabel;

    Slider temperatureSlider;
    Label temperatureLabel;

    Label labelDeterministic;
    Button toggleDeterministic;
    boolean isDeterministic;

    Button genButton;

    GridPane gridPane;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        stage.setTitle("ArtSweeper");

        BorderPane root = new BorderPane();

        // Control Panel
        VBox controlPanel = new VBox(10); // 20px spacing
        controlPanel.setPadding(new Insets(20));
        controlPanel.setPrefWidth(360);
        controlPanel.setStyle("-fx-background-color: #333;");

        // title
        Label title = new Label("ArtSweeper");
        title.setStyle("-fx-font: 24 arial; -fx-text-fill: white;");

        // upload file
        fileChooserLabel = new Label("File: none");
        fileChooserLabel.setStyle("-fx-font: 16 arial; -fx-text-fill: white;");
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Images", "*.jpg"));
        fileChooser.setTitle("Upload Image");
        fileChooserButton = new Button("Upload Image");
        fileChooserButton.setStyle("-fx-background-color: #555; -fx-font: 16 arial; -fx-text-fill: white;");
        fileChooserButton.setOnAction(this);


        // intensity
        intensityLabel = new Label("Intensity: 0");
        intensityLabel.setStyle("-fx-font: 16 arial; -fx-text-fill: white;");
        intensitySlider = new Slider();
        intensitySlider.setMin(0);
        intensitySlider.setMax(100);
        intensitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            intensityLabel.setText("Intensity: " + newVal.intValue());
        });

        // temperature
        temperatureLabel = new Label("Temperature: 0");
        temperatureLabel.setStyle("-fx-font: 16 arial; -fx-text-fill: white;");
        temperatureSlider = new Slider();
        temperatureSlider.setMin(0);
        temperatureSlider.setMax(50);
        temperatureSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            temperatureLabel.setText("Temperature: " + newVal.intValue());
        });

        // deterministic
        labelDeterministic = new Label("Deterministic: false");
        labelDeterministic.setStyle("-fx-font: 16 arial; -fx-text-fill: white;");
        toggleDeterministic = new Button("Toggle Deterministic");
        toggleDeterministic.setStyle("-fx-background-color: #555; -fx-font: 14 arial; -fx-text-fill: white;");
        toggleDeterministic.setOnAction(this);
        toggleDeterministic.setPrefWidth(175);

        // generate button
        genButton = new Button("Generate Minefield");
        genButton.setStyle("-fx-background-color: #555; -fx-font: 18 arial; -fx-text-fill: white;");
        genButton.setOnAction(this);
        genButton.setPrefWidth(225);

        // spacers
        Region spacer1 = new Region();
        spacer1.setPrefHeight(40);
        Region spacer2 = new Region();
        spacer2.setPrefHeight(25);
        Region spacer3 = new Region();
        spacer3.setPrefHeight(10);
        Region spacer4 = new Region();
        spacer4.setPrefHeight(25);
        Region spacer5 = new Region();
        spacer5.setPrefHeight(200);

        controlPanel.getChildren().addAll(title, spacer1);
        controlPanel.getChildren().addAll(fileChooserLabel, fileChooserButton, spacer2);
        controlPanel.getChildren().addAll(intensityLabel, intensitySlider, spacer3);
        controlPanel.getChildren().addAll(temperatureLabel, temperatureSlider, spacer4);
        controlPanel.getChildren().addAll(labelDeterministic, toggleDeterministic, spacer5);
        controlPanel.getChildren().add(genButton);
        controlPanel.setAlignment(Pos.TOP_CENTER);

        // ===== Right Minefield Area =====
        StackPane minefieldPane = new StackPane();
        minefieldPane.setPrefSize(720, 720);
        minefieldPane.setStyle("-fx-background-color: #555;");

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        minefieldPane.getChildren().add(gridPane);

        root.setLeft(controlPanel);
        root.setCenter(minefieldPane);

        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        // generate
        if (event.getSource() == genButton) {
            if(imageFile == null) {return;}
            String imagePath = "/images/" + imageFile.getName();
            int intensity = (int)intensitySlider.getValue()+1;
            int temp = (int)temperatureSlider.getValue()+1;
            ImageMinefieldGenerator imageMinefieldGenerator = new ImageMinefieldGenerator(imagePath, intensity, temp, isDeterministic);
            Minefield imageMinefield = imageMinefieldGenerator.generateMinefield();
            System.out.println(imageMinefield.toString());
            displayMinefield(imageMinefield.getField());
        }

        // ui updates
        if (event.getSource() == fileChooserButton) {
            imageFile = fileChooser.showOpenDialog(primaryStage);
            if(imageFile != null) {
                fileChooserLabel.setText("File: " + imageFile.getName());
            }
            else {
                fileChooserLabel.setText("File: none");
            }
        }
        if (event.getSource() == intensitySlider) {
            intensityLabel.setText("Intensity: " + intensitySlider.getValue());
        }
        if (event.getSource() == temperatureSlider) {
            temperatureLabel.setText("Temperature: " + temperatureSlider.getValue());
        }
        if (event.getSource() == toggleDeterministic) {
            isDeterministic = !isDeterministic;
            labelDeterministic.setText("Deterministic: " + isDeterministic);
        }
    }

    private void displayMinefield(Tile[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        if (map.length > MAX_HEIGHT || map[0].length > MAX_WIDTH) {
            System.out.println("Minefield exceeded maximum width or height.");
            return;
        }

        gridPane.getChildren().clear();

        double tileSize = 720.0 / Math.max(rows, cols);
        System.out.println("tileSize: " + tileSize);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = map[row][col];

                Label tileUI = new Label();
                tileUI.setMinSize(tileSize, tileSize);
                tileUI.setMaxSize(tileSize, tileSize);
                tileUI.setAlignment(Pos.CENTER);
                tileUI.setPadding(Insets.EMPTY);
                tileUI.setText("");

                tile.setLabel(tileUI);

                map[row][col].setLabel(tileUI);

                tile.getLabel().setStyle("-fx-background-color: #5ED500FF;");

                tileUI.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        try {
                            Player.clickTile(map, tile);
                        } catch (GameOverException exception) {
                            System.out.println(exception.getMessage());
                            primaryStage.close();
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        Player.flagTile(tile);
                    }
                });
                gridPane.add(tileUI, col, row);
            }
        }
    }
}