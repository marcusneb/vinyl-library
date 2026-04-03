package org.example.assigment1;

public class BorrowedAndReservedState implements VinylState {
    @Override
    public void reserve(Vinyl vinyl, String userId) {

    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {

    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
          vinyl.setBorrowedBy(null);
          vinyl.setCurrentState(new ReservedState());
    }

    @Override
    public void requestRemoval(Vinyl vinyl) {
        vinyl.setPendingRemoval(true);
    }
}
