package org.example.assigment1;

public class AvailableState implements VinylState{
    @Override
    public void reserve(Vinyl vinyl, String userId) {
        if(vinyl.getPendingRemoval())
            return;
        vinyl.setReservedBy(userId);
        vinyl.setCurrentState(new ReservedState());
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
        if(vinyl.getPendingRemoval())
            return;
        vinyl.setBorrowedBy(userId);
        vinyl.setCurrentState(new BorrowedState());
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {

    }

    @Override
    public void requestRemoval(Vinyl vinyl) {
        vinyl.setPendingRemoval(true);

    }
}
