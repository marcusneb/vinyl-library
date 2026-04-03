package org.example.assigment1;

public interface VinylState {

    void reserve(Vinyl vinyl, String userId);
    void borrow(Vinyl vinyl, String userId);
    void returnVinyl(Vinyl vinyl);
    void requestRemoval(Vinyl vinyl);


}
