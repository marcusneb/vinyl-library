package org.example.assigment1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;

public class VinylViewModel extends ViewModel {
    private VinylLibrary vinylLibrary;
    private ObservableList<VinylWrapper> vinylWrappers;


    public VinylViewModel(VinylLibrary vinylLibrary)
    {
        vinylWrappers = FXCollections.observableArrayList();
        this.vinylLibrary = vinylLibrary;

        for(Vinyl vinyl: vinylLibrary.getVinyls())
            vinylWrappers.add(new VinylWrapper(vinyl));

        vinylLibrary.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(()-> {
            vinylWrappers.clear();
            for(Vinyl vinyl: vinylLibrary.getVinyls())
            {
                vinylWrappers.add(new VinylWrapper(vinyl));
            }
        });
    }
    public void reserve(Vinyl vinyl, String userId)
    {
        vinyl.reserve(userId);
    }

    public void borrow(Vinyl vinyl, String userId)
    {
        vinyl.borrow(userId);
    }
    public void returnVinyl(Vinyl vinyl)
    {
        vinyl.returnVinyl();
    }
    public void remove(Vinyl vinyl)
    {
        vinylLibrary.removeVinyl(vinyl);
    }
    public ObservableList<VinylWrapper> getVinylWrappers() {
        return vinylWrappers;
    }
}
