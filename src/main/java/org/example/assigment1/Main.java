package org.example.assigment1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        VinylLibrary library = new VinylLibrary();
        library.addVinyl(new Vinyl("Abbey Road", "The Beatles", "1969"));
        library.addVinyl(new Vinyl("Rumours", "Fleetwood Mac", "1977"));
        library.addVinyl(new Vinyl("Dark Side of the Moon", "Pink Floyd", "1973"));
        library.addVinyl(new Vinyl("Thriller", "Michael Jackson", "1982"));
        library.addVinyl(new Vinyl("Kind of Blue", "Miles Davis", "1959"));

        // this one won't be affected by the simulated threads, only by the GUI buttons
        Vinyl manualVinyl = new Vinyl("Blue Train", "John Coltrane", "1958");
        manualVinyl.setSimulated(false);
        library.addVinyl(manualVinyl);

        VinylViewModel viewModel = new VinylViewModel(library);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/assigment1/hello-view.fxml"));
        Scene scene = new Scene(loader.load());

        VinylListController controller = loader.getController();
        controller.init(viewModel);

        stage.setTitle("Vinyl Library");
        stage.setScene(scene);
        stage.show();


        SimulatedUsers user1 = new SimulatedUsers("USER_1", library);
        SimulatedUsers user2 = new SimulatedUsers("USER_2", library);

        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);

        // daemon threads stop automatically when the app closes
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}