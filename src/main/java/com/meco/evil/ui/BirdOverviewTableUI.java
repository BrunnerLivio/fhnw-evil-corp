package com.meco.evil.ui;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdListPM;
import com.meco.evil.pm.BirdPM;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BirdOverviewTableUI extends TableView<BirdPM> implements BaseUI {
    private final ApplicationPM appModel;
    private TableColumn<BirdPM, String> name;
    private TableColumn<BirdPM, String> populationTrend;
    private TableColumn<BirdPM, String> populationStatus;

    public BirdOverviewTableUI(ApplicationPM appModel) {
        this.appModel = appModel;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    @Override
    public Object getModel() {
        return null;
    }

    @Override
    public void initializeSelf() {

    }

    @Override
    public void initializeControls() {
        name = new TableColumn<>();
        populationTrend = new TableColumn<>();
        populationStatus = new TableColumn<>();
    }

    @Override
    public void layoutControls() {
        VBox.setVgrow(this, Priority.ALWAYS);
        name.setCellValueFactory(cell -> cell.getValue().nameProperty());
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });
        populationTrend.setCellValueFactory(cell -> cell.getValue().populationTrendProperty());
        populationTrend.setCellFactory(ComboBoxTableCell.forTableColumn(BirdListPM.BIRD_POPULATION_TREND));
        populationTrend.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPopulationTrend(e.getNewValue());
        });
        populationStatus.setCellValueFactory(cell -> cell.getValue().populationStatusProperty());
        populationStatus.setCellFactory(ComboBoxTableCell.forTableColumn(BirdListPM.BIRD_POPULATION_STATUS));
        populationStatus.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPopulationStatus(e.getNewValue());
        });
        getColumns().addAll(
                name,
                populationTrend,
                populationStatus
        );
        setEditable(true);
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {
        name.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverview.table.col.name"));
        populationTrend.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverview.table.col.populationTrend"));
        populationStatus.textProperty().bind(appModel.getI18n().getTranslationFor("birdOverview.table.col.populationStatus"));
    }
}
