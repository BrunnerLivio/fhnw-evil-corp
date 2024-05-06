package com.meco.evil.pm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.util.UUID;

import com.meco.evil.pm.changeAction.ChangeActionService;

public class BirdPM {
    public static final String PLACEHOLDER_URL = "https://i0.wp.com/theperfectroundgolf.com/wp-content/uploads/2022/04/placeholder.png?fit=1200%2C800&ssl=1";
    private final StringProperty name = new SimpleStringProperty("Bird " + UUID.randomUUID());
    private final StringProperty imageUrl = new SimpleStringProperty(PLACEHOLDER_URL);
    private final StringProperty shortDescription = new SimpleStringProperty("");
    private final StringProperty populationSize = new SimpleStringProperty("");
    private final StringProperty maximumLifeSpanInYears = new SimpleStringProperty("");
    private final StringProperty topSpeedInKmh = new SimpleStringProperty("0");
    private final StringProperty weight = new SimpleStringProperty("");
    private final StringProperty length = new SimpleStringProperty("");
    private final StringProperty wingspan = new SimpleStringProperty("");
    private final StringProperty continents = new SimpleStringProperty("");
    private final StringProperty diet = new SimpleStringProperty("");
    private final StringProperty seasonalBehavior = new SimpleStringProperty("");
    private final StringProperty independentAge = new SimpleStringProperty("");
    private final StringProperty populationTrend = new SimpleStringProperty("");
    private final StringProperty populationStatus = new SimpleStringProperty("");
    private final StringProperty incubationPeriod = new SimpleStringProperty("");
    private final ChangeActionService actionService;

    public BirdPM(ChangeActionService actionService) {
        this.actionService = actionService;
        registerChangeListener();
    }

    public BirdPM(String[] line, ChangeActionService actionService) {
        this.actionService = actionService;
        name.set(line[0]);
        imageUrl.set(line[1]);
        shortDescription.set(line[2]);
        populationSize.set(line[3]);
        maximumLifeSpanInYears.set(line[4]);
        topSpeedInKmh.set(line[5]);
        weight.set(line[6]);
        length.set(line[7]);
        wingspan.set(line[8]);
        continents.set(line[9]);
        diet.set(line[10]);
        seasonalBehavior.set(line[11]);
        independentAge.set(line[12]);
        populationTrend.set(line[13]);
        populationStatus.set(line[14]);
        incubationPeriod.set(line[15]);
        registerChangeListener();
    }

    public String contentAsLine(String delimiter) {
        StringBuffer buffer = new StringBuffer();
        addToBuffer(buffer, delimiter, name);
        addToBuffer(buffer, delimiter, imageUrl);
        addToBuffer(buffer, delimiter, shortDescription);
        addToBuffer(buffer, delimiter, populationSize);
        addToBuffer(buffer, delimiter, maximumLifeSpanInYears);
        addToBuffer(buffer, delimiter, topSpeedInKmh);
        addToBuffer(buffer, delimiter, weight);
        addToBuffer(buffer, delimiter, length);
        addToBuffer(buffer, delimiter, wingspan);
        addToBuffer(buffer, delimiter, continents);
        addToBuffer(buffer, delimiter, diet);
        addToBuffer(buffer, delimiter, seasonalBehavior);
        addToBuffer(buffer, delimiter, independentAge);
        addToBuffer(buffer, delimiter, populationTrend);
        addToBuffer(buffer, delimiter, populationStatus);

        // Add last element without appending the delimiter
        buffer.append(incubationPeriod.get());
        buffer.append(delimiter);
        return buffer.toString();
    }

    private void addToBuffer(StringBuffer buffer, String delimiter, StringProperty property) {
        buffer.append(property.get());
        buffer.append(delimiter);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPopulationTrend(String populationTrend) {
        this.populationTrend.set(populationTrend);
    }

    public void setPopulationStatus(String populationStatus) {
        this.populationStatus.set(populationStatus);
    }

    public String getName() {
        return name.get();
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public String getShortDescription() {
        return shortDescription.get();
    }

    public StringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    public String getPopulationSize() {
        return populationSize.get();
    }

    public StringProperty populationSizeProperty() {
        return populationSize;
    }

    public String getMaximumLifeSpanInYears() {
        return maximumLifeSpanInYears.get();
    }

    public StringProperty maximumLifeSpanInYearsProperty() {
        return maximumLifeSpanInYears;
    }

    public String getTopSpeedInKmh() {
        return topSpeedInKmh.get();
    }

    public StringProperty topSpeedInKmhProperty() {
        return topSpeedInKmh;
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public String getLength() {
        return length.get();
    }

    public StringProperty lengthProperty() {
        return length;
    }

    public String getWingspan() {
        return wingspan.get();
    }

    public StringProperty wingspanProperty() {
        return wingspan;
    }

    public String getContinents() {
        return continents.get();
    }

    public StringProperty continentsProperty() {
        return continents;
    }

    public String getDiet() {
        return diet.get();
    }

    public StringProperty dietProperty() {
        return diet;
    }

    public String getSeasonalBehavior() {
        return seasonalBehavior.get();
    }

    public StringProperty seasonalBehaviorProperty() {
        return seasonalBehavior;
    }

    public String getIndependentAge() {
        return independentAge.get();
    }

    public StringProperty independentAgeProperty() {
        return independentAge;
    }

    public String getPopulationTrend() {
        return populationTrend.get();
    }

    public StringProperty populationTrendProperty() {
        return populationTrend;
    }

    public String getPopulationStatus() {
        return populationStatus.get();
    }

    public StringProperty populationStatusProperty() {
        return populationStatus;
    }

    public String getIncubationPeriod() {
        return incubationPeriod.get();
    }

    public StringProperty incubationPeriodProperty() {
        return incubationPeriod;
    }

    public StringProperty nameProperty() {
        return name;
    }

    private void registerChangeListener() {
        name.addListener(forAttribute(name));
        imageUrl.addListener(forAttribute(imageUrl));
        shortDescription.addListener(forAttribute(shortDescription));
        populationSize.addListener(forAttribute(populationSize));
        maximumLifeSpanInYears.addListener(forAttribute(maximumLifeSpanInYears));
        topSpeedInKmh.addListener(forAttribute(topSpeedInKmh));
        weight.addListener(forAttribute(weight));
        length.addListener(forAttribute(length));
        wingspan.addListener(forAttribute(wingspan));
        continents.addListener(forAttribute(continents));
        diet.addListener(forAttribute(diet));
        seasonalBehavior.addListener(forAttribute(seasonalBehavior));
        independentAge.addListener(forAttribute(independentAge));
        populationTrend.addListener(forAttribute(populationTrend));
        populationStatus.addListener(forAttribute(populationStatus));
        incubationPeriod.addListener(forAttribute(incubationPeriod));
    }

    private ChangeListener<String> forAttribute(StringProperty property) {
        return (observable, oldValue, newValue) -> actionService.addChange(property, oldValue, newValue);
    }
}
