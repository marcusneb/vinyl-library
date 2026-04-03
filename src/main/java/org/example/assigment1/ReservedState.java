package org.example.assigment1;

public class ReservedState implements VinylState {
    @Override
    public void reserve(Vinyl vinyl, String userId) {

    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
          if(userId.equals(vinyl.getReservedBy())) {
              vinyl.setCurrentState(new BorrowedState());
              vinyl.setBorrowedBy(userId);
              vinyl.setReservedBy(null);
          }
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {

    }

    @Override
    public void requestRemoval(Vinyl vinyl) {
        vinyl.setPendingRemoval(true);
    }
}
