package org.example.assigment1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VinylLibrary implements PropertyChangeSubject {

    private List<Vinyl> vinylList;
    private PropertyChangeSupport support;

    public VinylLibrary()
    {
        vinylList = new CopyOnWriteArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    public void addVinyl(Vinyl vinyl)
    {
        vinylList.add(vinyl);
        // listen for auto-removal when a pending-removal vinyl is returned
        vinyl.addPropertyChangeListener("removalReady", evt -> {
            vinylList.remove(vinyl);
            support.firePropertyChange("vinyls", null, vinylList);
        });
        support.firePropertyChange("vinyls", null, vinylList);
    }

    public void removeVinyl(Vinyl vinyl)
    {
        if(vinyl.getCurrentState() instanceof AvailableState && vinyl.getReservedBy() == null)
        {
            vinylList.remove(vinyl);
            support.firePropertyChange("vinyls", null, vinylList);
        }
    }

    public List<Vinyl> getVinyls()
    {
        return vinylList;
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
