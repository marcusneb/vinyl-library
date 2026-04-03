package org.example.assigment1;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Vinyl implements PropertyChangeSubject {
    private String title;
    private String artist;
    private String releaseYear;
    private String borrowedBy;
    private String reservedBy;
    private boolean pendingRemoval;
    private VinylState currentState;
    private PropertyChangeSupport support;
    // used to mark whether the simulated threads should interact with this vinyl or not
    private boolean simulated;

    public Vinyl(String title, String artist, String releaseYear)
    {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.borrowedBy = null;
        this.reservedBy = null;
        this.pendingRemoval = false;
        support = new PropertyChangeSupport(this);
        this.currentState = new AvailableState();
        this.simulated = true; // by default all vinyls can be touched by simulated users


     }

     //getters

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public VinylState getCurrentState() {
        return currentState;
    }

    public boolean getPendingRemoval()
    {
        return pendingRemoval;
    }

    public boolean isSimulated()
    {
        return simulated;
    }

    //setters

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCurrentState(VinylState currentState) {
        VinylState oldState = this.currentState;
        this.currentState = currentState;
        support.firePropertyChange("state", oldState, currentState); // notify the listeners when the state of the vinyl changes
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public void setPendingRemoval(boolean pendingRemoval) {
        this.pendingRemoval = pendingRemoval;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    //STATE methods

     public synchronized void reserve(String userId)
     {
         currentState.reserve(this,userId);
     }

     public synchronized void borrow(String userId)
     {
         currentState.borrow(this, userId);
     }

     public synchronized void returnVinyl()
     {
        currentState.returnVinyl(this);
     }

     public synchronized void requestRemoval()
     {
         currentState.requestRemoval(this);
     }

     public void fireRemovalReady()
     {
         support.firePropertyChange("removalReady", false, true);
     }


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
       support.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
       support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
      support.removePropertyChangeListener(name, listener);
    }
}
