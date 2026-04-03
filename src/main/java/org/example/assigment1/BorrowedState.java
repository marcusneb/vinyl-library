package org.example.assigment1;

public class BorrowedState implements VinylState {
    @Override
    public void reserve(Vinyl vinyl, String userId) {
        if(vinyl.getReservedBy() != null)
            return;
        else if(vinyl.getPendingRemoval())
            return;
        else {
            vinyl.setReservedBy(userId);
            vinyl.setCurrentState(new BorrowedAndReservedState());
        }
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {

    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
        vinyl.setBorrowedBy(null);
        vinyl.setCurrentState(new AvailableState());
        if(vinyl.getPendingRemoval())
        {
            vinyl.fireRemovalReady();
        }
    }

    @Override
    public void requestRemoval(Vinyl vinyl) {
     vinyl.setPendingRemoval(true);
    }
}
