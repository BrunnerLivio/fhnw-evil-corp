package com.meco.evil.pm;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

import com.meco.evil.pm.changeAction.ChangeActionService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BirdListPM {
    public static ObservableList<String> BIRD_POPULATION_TREND = FXCollections.observableList(BirdPMPopulationTrend.stringValues());
    public static ObservableList<String> BIRD_POPULATION_STATUS = FXCollections.observableList(BirdPMPopulationStatus.stringValues());
    private static final String DELIMITER = "\t";
    private final ObservableList<BirdPM> birds = FXCollections.observableArrayList();
    private final IntegerProperty birdsCount = new SimpleIntegerProperty();
    private final DoubleProperty maxSpeed = new SimpleDoubleProperty();
    private final String fileName;
    private String headerLine;
    private final ChangeActionService changeActionService;
    private final ApplicationPM applicationPM;
    private final ObjectProperty<SearchResult> searchResult = new SimpleObjectProperty<>(
            new SearchResult(FXCollections.emptyObservableList())
    );

    public BirdListPM(String fileName, ChangeActionService changeActionService, ApplicationPM applicationPM) {
        this.applicationPM = applicationPM;
        this.changeActionService = changeActionService;
        this.fileName = fileName;
        birds.addAll(readFromFile(fileName));
        changeListener();
        birds.forEach(bird -> {
            bird.topSpeedInKmhProperty().addListener((observable, oldValue, newValue) -> changeListener());
        });
        birds.addListener((ListChangeListener<? super BirdPM>) observable -> changeListener());
        search("");
    }

    public void save() {
        try (BufferedWriter writer = getWriter(fileName)) {
            writer.write(headerLine);
            writer.newLine();
            birds.stream()
                    .map(resultat -> resultat.contentAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });

            Notifications.create()
                    .title(applicationPM.getI18n().getTranslationFor("birdOverview.msg.saveBirds.title").get())
                    .text(applicationPM.getI18n().getTranslationFor("birdOverview.msg.saveBirds.message").get())
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
        } catch (IOException e) {
            Utility.showInfo(
                    applicationPM.getI18n().getTranslationFor("birdOverview.msg.saveBirdsError.title").get(),
                    applicationPM.getI18n().getTranslationFor("birdOverview.msg.saveBirdsError.message").get()
            );
        }
    }

    public void addBird(BirdPM birdPM) {
        birds.add(birdPM);
    }

    public void removeBird(BirdPM birdPM) {
        birds.remove(birdPM);
    }

    public ObservableList<BirdPM> getBirds() {
        return birds;
    }

    public ObjectProperty<SearchResult> getSearchResult() {
        return searchResult;
    }

    public IntegerProperty getAmountOfBirds() {
        return birdsCount;
    }

    public DoubleProperty getMaxSpeed() {
        return maxSpeed;
    }

    public void search(String text) {
        // FIXME replace actual birds with search function to that all entries matches an empty search
        var searchAttributes = text.trim().split(" ");
        var searchResult = new SearchResult<BirdPM>();
        var data = searchAttributes.length == 0 ? birds : birds
                .filtered(b -> Arrays.stream(searchAttributes)
                        .anyMatch(attr -> {
                            var inName = b.getName().toLowerCase().contains(attr.toLowerCase());
                            var inShortDescription = b.getShortDescription().toLowerCase().contains(attr.toLowerCase());
                            if (inName) {
                                searchResult.addMatch(SearchResult.SearchMatchType.Name);
                            }
                            if (inShortDescription) {
                                searchResult.addMatch(SearchResult.SearchMatchType.Description);
                            }
                            return inName || inShortDescription;
                        })
                ).sorted(Comparator.comparing(BirdPM::getName));
        searchResult.setData(data);
        this.searchResult.set(searchResult);
    }

    private void changeListener() {
        birdsCount.set(birds.size());
        birds.stream()
                .map(bird -> {
                    var topSpeed = 0.0;
                    try {
                        topSpeed = Double.parseDouble(bird.getTopSpeedInKmh());
                    } catch (NumberFormatException ignored) {
                    }
                    return topSpeed;
                })
                .max(Comparator.naturalOrder())
                .ifPresent(maxSpeed::set);
    }

    private BufferedReader getReader(String fileName) {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(reader);
    }

    private BufferedWriter getWriter(String fileName) {
        try {
            String file = getClass().getResource(fileName).getFile();
            return new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("wrong file " + fileName);
        }
    }

    private List<BirdPM> readFromFile(String fileName) {
        try (BufferedReader reader = getReader(fileName)) {
            return reader.lines()
                    .peek(line -> {
                        if (headerLine == null) {
                            headerLine = line;
                        }
                    })
                    .skip(1)
                    .map(line -> line.split(DELIMITER))
                    .map(line -> new BirdPM(line, changeActionService))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
