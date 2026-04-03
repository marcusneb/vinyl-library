package org.example.assigment1;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static javafx.application.Platform.runLater;

public class VinylWrapper implements PropertyChangeListener {

    private Vinyl vinyl;
    private SimpleStringProperty titleStringProperty = new SimpleStringProperty();
    private SimpleStringProperty artistStringProperty = new SimpleStringProperty();
    private SimpleStringProperty releaseYearStringProperty = new SimpleStringProperty();
    private SimpleStringProperty state = new SimpleStringProperty();

    public VinylWrapper(Vinyl vinyl)
    {
        this.vinyl = vinyl;
        this.titleStringProperty.set(vinyl.getTitle());
        this.artistStringProperty.set(vinyl.getArtist());
        this.releaseYearStringProperty.set(vinyl.getReleaseYear());
        this.state.set(vinyl.getCurrentState().getClass().getSimpleName());
        vinyl.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            state.set(vinyl.getCurrentState().getClass().getSimpleName()); // for the changes that come from the simulated users
        });

    }
    public String getTitle()
    {
        return titleStringProperty.get();
    }

    public Vinyl getVinyl()
    {
        return vinyl;
    }

    public String getArtist()
    {
        return artistStringProperty.get();
    }

    public String getReleaseYear()
    {
        return releaseYearStringProperty.get();
    }

    public String getState()
    {
       return state.get();
    }

    public StringProperty titleProperty()
    {
        return titleStringProperty;
    }

    public StringProperty artistProperty()
    {
        return artistStringProperty;
    }

    public StringProperty releaseYearProperty()
    {
        return releaseYearStringProperty;
    }

    public StringProperty state()
    {
        return state;
    }
}
