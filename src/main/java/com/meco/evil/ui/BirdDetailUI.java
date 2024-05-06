package com.meco.evil.ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdListPM;
import com.meco.evil.pm.BirdPM;

public class BirdDetailUI extends VBox implements BaseUI<BirdPM> {

    private final BirdPM birdPM;
    private final ApplicationPM applicationPM;
    private Text pName;
    private TextField txtName;
    private TextField txtImageUrl;
    private TextField txtShortDescription;
    private TextField txtPopulationSize;
    private TextField txtMaximumLifeSpanInYears;
    private TextField txtTopSpeedInKmh;
    private TextField txtWeight;
    private TextField txtLength;
    private TextField txtWingspan;
    private Text pContinents;
    private TextField txtContinents;
    private TextField txtDiet;
    private TextField txtSeasonalBehavior;
    private TextField txtIndependentAge;
    private ComboBox cmbPopulationTrend;
    private ComboBox cmbPopulationStatus;
    private TextField txtIncubationPeriod;
    private ImageView imageView;
    private GridPane birdAttributes;
    private Label lblName;
    private Label lblImageUrl;
    private Label lblShortDescription;
    private Label lblPopulationSize;
    private Label lblMaximumLifeSpanInYears;
    private Label lblTopSpeedInKmh;
    private Label lblWeight;
    private Label lblLength;
    private Label lblWingspan;
    private Label lblContinents;
    private Label lblDiet;
    private Label lblSeasonalBehavior;
    private Label lblIndependentAge;
    private Label lblPopulationTrend;
    private Label lblPopulationStatus;
    private Label lblIncubationPeriod;

    public BirdDetailUI(BirdPM birdPM, ApplicationPM applicationPM) {
        this.applicationPM = applicationPM;
        this.birdPM = birdPM;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    @Override
    public BirdPM getModel() {
        return null;
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("detail-ui");

    }

    @Override
    public void initializeControls() {
        pName = new Text("test");
        pContinents = new Text();

        imageView = new ImageView(new Image(birdPM.getImageUrl()));
        updateImage(birdPM.getImageUrl());
        imageView.setPreserveRatio(true);
        birdAttributes = new GridPane();

        txtName = new TextField();
        txtImageUrl = new TextField();
        txtShortDescription = new TextField();
        txtPopulationSize = new TextField();
        txtMaximumLifeSpanInYears = new TextField();
        txtTopSpeedInKmh = new TextField();
        txtWeight = new TextField();
        txtLength = new TextField();
        txtWingspan = new TextField();
        txtContinents = new TextField();
        txtDiet = new TextField();
        txtSeasonalBehavior = new TextField();
        txtIndependentAge = new TextField();
        cmbPopulationTrend = new ComboBox(BirdListPM.BIRD_POPULATION_TREND);
        cmbPopulationStatus = new ComboBox(BirdListPM.BIRD_POPULATION_STATUS);
        txtIncubationPeriod = new TextField();
        lblImageUrl = new Label();
        lblName = new Label();
        lblShortDescription = new Label();
        lblPopulationSize = new Label();
        lblMaximumLifeSpanInYears = new Label();
        lblTopSpeedInKmh = new Label();
        lblWeight = new Label();
        lblLength = new Label();
        lblWingspan = new Label();
        lblContinents = new Label();
        lblDiet = new Label();
        lblSeasonalBehavior = new Label();
        lblIndependentAge = new Label();
        lblPopulationTrend = new Label();
        lblPopulationStatus = new Label();
        lblIncubationPeriod = new Label();
    }

    @Override
    public void layoutControls() {
        HBox right = new HBox();
        HBox.setHgrow(right, Priority.ALWAYS);
        right.setAlignment(Pos.CENTER_LEFT);
        right.getChildren().addAll(
                imageView
        );

        List<Pair<Node, Node>> attributes = new ArrayList<>() {{
            add(new Pair<>(pContinents, right));
            add(new Pair<>(lblName, txtName));
            add(new Pair<>(lblShortDescription, txtShortDescription));
            add(new Pair<>(lblPopulationSize, txtPopulationSize));
            add(new Pair<>(lblMaximumLifeSpanInYears, txtMaximumLifeSpanInYears));
            add(new Pair<>(lblTopSpeedInKmh, txtTopSpeedInKmh));
            add(new Pair<>(lblWeight, txtWeight));
            add(new Pair<>(lblLength, txtLength));
            add(new Pair<>(lblWingspan, txtWingspan));
            add(new Pair<>(lblContinents, txtContinents));
            add(new Pair<>(lblDiet, txtDiet));
            add(new Pair<>(lblSeasonalBehavior, txtSeasonalBehavior));
            add(new Pair<>(lblIndependentAge, txtIndependentAge));
            add(new Pair<>(lblPopulationTrend, cmbPopulationTrend));
            add(new Pair<>(lblPopulationStatus, cmbPopulationStatus));
            add(new Pair<>(lblIncubationPeriod, txtIncubationPeriod));
            add(new Pair<>(lblImageUrl, txtImageUrl));
        }};

        for (int i = 0; i < attributes.size(); i++) {
            var pair = attributes.get(i);
            birdAttributes.add(pair.getKey(), 0, i);
            birdAttributes.add(pair.getValue(), 1, i);
        }

        birdAttributes.getColumnConstraints()
                .addAll(
                        new ColumnConstraints(200, Control.USE_COMPUTED_SIZE, 200),
                        new ColumnConstraints(400, Control.USE_PREF_SIZE, Double.MAX_VALUE)
                );

        pName.setFont(Font.font(30));

        ScrollPane sp = new ScrollPane();
        sp.setContent(birdAttributes);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        getChildren().addAll(
                pName,
                sp
        );
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {
        widthProperty().addListener((observable, oldValue, newValue) -> {
            updateImageDimensions();
            birdAttributes.getColumnConstraints().set(
                    1, new ColumnConstraints(
                            0,
                            ((double) newValue) - 250,
                            ((double) newValue) - 250)
            );

        });
        birdPM.imageUrlProperty().addListener((observable, oldValue, newValue) -> {
            updateImage(newValue);
        });
    }

    private void updateImage(String imageUrl) {
        try {
            imageView.setImage(new Image(imageUrl));
            if (imageView.getImage().getHeight() == 0) {
                imageView.setImage(new Image(BirdPM.PLACEHOLDER_URL));
            }
        } catch (IllegalArgumentException e) {
            imageView.setImage(new Image(BirdPM.PLACEHOLDER_URL));
        }
        updateImageDimensions();
    }

    private void updateImageDimensions() {
        if (!imageView.getImage().isError()) {
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(300);
        }
    }

    @Override
    public void setupBindings() {
        pName.textProperty().bind(birdPM.nameProperty());
        pContinents.textProperty().bindBidirectional(birdPM.continentsProperty());

        lblName.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.name"));
        txtName.textProperty().bindBidirectional(birdPM.nameProperty());

        lblImageUrl.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.imageUrl"));
        txtImageUrl.textProperty().bindBidirectional(birdPM.imageUrlProperty());

        lblShortDescription.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.shortDescription"));
        txtShortDescription.textProperty().bindBidirectional(birdPM.shortDescriptionProperty());

        lblPopulationSize.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.populationSize"));
        txtPopulationSize.textProperty().bindBidirectional(birdPM.populationSizeProperty());

        lblMaximumLifeSpanInYears.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.maximumLifeSpanInYears"));
        txtMaximumLifeSpanInYears.textProperty().bindBidirectional(birdPM.maximumLifeSpanInYearsProperty());

        lblTopSpeedInKmh.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.topSpeedInKmh"));
        txtTopSpeedInKmh.textProperty().bindBidirectional(birdPM.topSpeedInKmhProperty());

        lblWeight.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.weight"));
        txtWeight.textProperty().bindBidirectional(birdPM.weightProperty());

        lblLength.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.length"));
        txtLength.textProperty().bindBidirectional(birdPM.lengthProperty());

        lblWingspan.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.wingspan"));
        txtWingspan.textProperty().bindBidirectional(birdPM.wingspanProperty());

        lblContinents.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.continents"));
        txtContinents.textProperty().bindBidirectional(birdPM.continentsProperty());

        lblDiet.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.diet"));
        txtDiet.textProperty().bindBidirectional(birdPM.dietProperty());

        lblSeasonalBehavior.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.seasonalBehaviour"));
        txtSeasonalBehavior.textProperty().bindBidirectional(birdPM.seasonalBehaviorProperty());

        lblIndependentAge.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.independentAge"));
        txtIndependentAge.textProperty().bindBidirectional(birdPM.independentAgeProperty());

        lblPopulationTrend.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.populationTrend"));
        cmbPopulationTrend.valueProperty().bindBidirectional(birdPM.populationTrendProperty());

        lblPopulationStatus.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.populationStatus"));
        cmbPopulationStatus.valueProperty().bindBidirectional(birdPM.populationStatusProperty());

        lblIncubationPeriod.textProperty().bind(applicationPM.getI18n().getTranslationFor("birdDetail.lbl.incubationPeriod"));
        txtIncubationPeriod.textProperty().bindBidirectional(birdPM.incubationPeriodProperty());
    }

    public void unbind() {
        pName.textProperty().unbind();
        pContinents.textProperty().unbind();

        lblName.textProperty().unbind();
        txtName.textProperty().unbind();

        lblImageUrl.textProperty().unbind();
        txtImageUrl.textProperty().unbind();

        lblShortDescription.textProperty().unbind();
        txtShortDescription.textProperty().unbind();

        lblPopulationSize.textProperty().unbind();
        txtPopulationSize.textProperty().unbind();

        lblMaximumLifeSpanInYears.textProperty().unbind();
        txtMaximumLifeSpanInYears.textProperty().unbind();

        lblTopSpeedInKmh.textProperty().unbind();
        txtTopSpeedInKmh.textProperty().unbind();

        lblWeight.textProperty().unbind();
        txtWeight.textProperty().unbind();

        lblLength.textProperty().unbind();
        txtLength.textProperty().unbind();

        lblWingspan.textProperty().unbind();
        txtWingspan.textProperty().unbind();

        lblContinents.textProperty().unbind();
        txtContinents.textProperty().unbind();

        lblDiet.textProperty().unbind();
        txtDiet.textProperty().unbind();

        lblSeasonalBehavior.textProperty().unbind();
        txtSeasonalBehavior.textProperty().unbind();

        lblIndependentAge.textProperty().unbind();
        txtIndependentAge.textProperty().unbind();

        lblPopulationTrend.textProperty().unbind();
        cmbPopulationTrend.valueProperty().unbind();

        lblPopulationStatus.textProperty().unbind();
        cmbPopulationStatus.valueProperty().unbind();

        lblIncubationPeriod.textProperty().unbind();
        txtIncubationPeriod.textProperty().unbind();
    }
}
