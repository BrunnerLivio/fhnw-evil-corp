package com.meco.evil.ui;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdOverviewPM;
import com.meco.evil.pm.BirdPM;
import com.meco.evil.pm.SearchResult;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BirdOverviewUI extends VBox implements BaseUI<BirdOverviewPM> {
    private BirdOverviewPM birdPm;
    private ApplicationPM appModel;
    private Text pTitle;
    private Label lblSpeciesAmount;
    private Text pSpeciesAmount;
    private Label lblTopHighSpeed;
    private Text pTopHighSpeed;
    private Label lblSearchResult;
    private Text pSearchResult;
    private BirdOverviewTableUI birdOverviewTableUI;
    private final TableView<BirdPM> table = new TableView<>();
    private TableColumn<BirdPM, String> name;
    private TableColumn<BirdPM, String> populationTrend;
    private TableColumn<BirdPM, String> populationStatus;

    public BirdOverviewUI(BirdOverviewPM birdPm, ApplicationPM appModel) {
        this.appModel = appModel;
        this.birdPm = birdPm;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    @Override
    public BirdOverviewPM getModel() {
        return birdPm;
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("bird-overview-ui");
    }

    @Override
    public void initializeControls() {
        pTitle = new Text();
        pTitle.setFont(Font.font(30));
        lblSpeciesAmount = new Label();
        pSpeciesAmount = new Text();
        lblTopHighSpeed = new Label();
        pTopHighSpeed = new Text();
        pSearchResult = new Text();
        lblSearchResult = new Label();
        birdOverviewTableUI = new BirdOverviewTableUI(appModel);
    }

    @Override
    public void layoutControls() {

        // Header
        VBox header = new VBox();
        GridPane birdInformation = new GridPane();
        birdInformation.add(lblSpeciesAmount, 0, 0);
        birdInformation.add(pSpeciesAmount, 1, 0);
        birdInformation.add(lblTopHighSpeed, 0, 1);
        birdInformation.add(pTopHighSpeed, 1, 1);
        header.getChildren()
                .addAll(
                        pTitle,
                        birdInformation
                );

        handleSearchResult(appModel.getdataStorage().getSearchResult().get());

        // Footer
        HBox searchResult = new HBox();
        searchResult.setSpacing(10);
        searchResult.getChildren().addAll(
                lblSearchResult,
                pSearchResult
        );

        // TODO update table when model is add to observable list
        header.setPadding(new Insets(0, 0, 30, 0));
        getChildren().addAll(
                header,
                birdOverviewTableUI,
                searchResult
        );
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {
        birdPm.currentSelection().addListener((observable, oldValue, newValue) -> {
            birdOverviewTableUI.refresh();
            birdOverviewTableUI.requestFocus();
            birdOverviewTableUI.scrollTo(newValue);
        });
        appModel.getdataStorage().getSearchResult().addListener((observable, oldValue, newValue) -> {
            handleSearchResult(newValue);
        });
        birdOverviewTableUI.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            birdPm.setCurrentSelection(newValue);
        });
    }

    @Override
    public void setupBindings() {
        pTitle.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverView.header.title"));

        lblSpeciesAmount.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverView.header.label.amount"));
        // pSpeciesAmount.textProperty().bind(appModel.getdataStorage().getAmountOfBirds().map(String::valueOf));

        lblTopHighSpeed.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverView.header.label.maxSpeed"));
        // pTopHighSpeed.textProperty().bind(appModel.getdataStorage().getMaxSpeed().map(String::valueOf));

        // Table
        lblSearchResult.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverview.table.result.label"));
    }

    private void handleSearchResult(SearchResult<BirdPM> result) {
        birdOverviewTableUI.setItems(result.getData());
        birdPm.setCurrentSelection(null);
        pSearchResult.setText(String.valueOf(result.getResultCount()));
        birdOverviewTableUI.refresh();
    }
}
