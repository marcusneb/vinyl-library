package org.example.assigment1;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class VinylListController {

    private static final String CURRENT_USER = "GUI_USER";

    @FXML private TableView<VinylWrapper> vinylTable;
    @FXML private TableColumn<VinylWrapper, String> titleColumn;
    @FXML private TableColumn<VinylWrapper, String> artistColumn;
    @FXML private TableColumn<VinylWrapper, String> releaseYearColumn;
    @FXML private TableColumn<VinylWrapper, String> stateColumn;

    @FXML private Button reserveButton;
    @FXML private Button borrowButton;
    @FXML private Button returnButton;
    @FXML private Button removeButton;

    private VinylViewModel viewModel;


    public void init(VinylViewModel viewModel) {
        this.viewModel = viewModel;

        // bind each column to the matching property in VinylWrapper
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        artistColumn.setCellValueFactory(data -> data.getValue().artistProperty());
        releaseYearColumn.setCellValueFactory(data -> data.getValue().releaseYearProperty());
        stateColumn.setCellValueFactory(data -> data.getValue().state());

        // bind the table to the ViewModel's ObservableList
        vinylTable.setItems(viewModel.getVinylWrappers());
    }



    @FXML
    private void onReserve() {
        VinylWrapper selected = vinylTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.reserve(selected.getVinyl(), CURRENT_USER);
        }
    }

    @FXML
    private void onBorrow() {
        VinylWrapper selected = vinylTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.borrow(selected.getVinyl(), CURRENT_USER);
        }
    }

    @FXML
    private void onReturn() {
        VinylWrapper selected = vinylTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.returnVinyl(selected.getVinyl());
        }
    }

    @FXML
    private void onRemove() {
        VinylWrapper selected = vinylTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // first flag it for removal via state pattern
            selected.getVinyl().requestRemoval();
            // then attempt actual removal (only works if available with no reservation)
            viewModel.remove(selected.getVinyl());
        }
    }
}