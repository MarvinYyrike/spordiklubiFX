package com.example.spordiklubifx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Esipaneel extends Application {

    Osalemised osalemised = new Osalemised();
    Isik aktiivneIsik = null;
    Yritus aktiivneYritus = null;
    Text valitudIsik = new Text(valitudIsikuNimi());

    Text valitudYritus = new Text(valitudYrituseNimi());

  public static void main(String[] args) throws Exception {
        //Loon mõned spordiesemed, mis on spordiklubil laos olemas ja on võimalik laenutada
        Spordivahendid.loeSpordivahendid("spordivahendid.txt");
        Yritused.loeYritused("yritused.txt");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        Text pealkiri = new Text("Mida soovid teha?");
        pealkiri.setTextAlignment(TextAlignment.CENTER);
        pealkiri.setWrappingWidth(600);
        root.setTop(pealkiri);

        Button valju = new Button("Välju");
        valju.setTextFill(Color.RED);
        valju.setPrefSize(200, 25);
        root.setRight(valju);
        root.getRight().maxHeight(30);
        valju.setOnMousePressed(event -> {
            pealkiri.setText("Välju");
            primaryStage.close();
        });

        //TODO siia serLeft kasutaja loomine/sisselogimine ja näitamine, kellena sisse logitud oled
        Button sisselogimine = new Button("Logi sisse");
        sisselogimine.setPrefSize(200, 25);
        root.setLeft(sisselogimine);
        root.getLeft().maxHeight(30);
        sisselogimine.setOnMouseClicked(event -> {
            looUuskonto();
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(400);
        valitudIsik.setTextAlignment(TextAlignment.LEFT);
        valitudYritus.setTextAlignment(TextAlignment.LEFT);
        hbox.getChildren().addAll(valitudIsik, valitudYritus);

        TilePane nupud = new TilePane(2, 2);
        nupud.setPrefRows(4);
        nupud.setPrefColumns(2);

        VBox box = new VBox();
        box.setSpacing(8);
        box.getChildren().addAll(hbox, nupud);
        root.setBottom(box);

        Button b1 = new Button("Sisesta üritusel osalemine");
        b1.setTextFill(Color.ROYALBLUE);
        b1.setPrefSize(300, 100);
        b1.setOnMousePressed(event -> {
            pealkiri.setText("Sisesta üritusel osalemine");
            //järgnev, kui ei ole veel isikut loodud
            //siin saab valida üritused
            vaataYritusi();
            if(aktiivneIsik == null) {
              looUuskonto();
            }
            //TODO looUuskonto ja valiYritus vajavad handle'imist
        });

        Button b2 = new Button("Otsi üritusi osaleja järgi");
        b2.setTextFill(Color.GREY);
        b2.setPrefSize(300, 100);
        b2.setOnMousePressed(event -> {
            pealkiri.setText("Otsi üritusi osaleja järgi");
        });

        Button b3 = new Button("Vaata ürituse tulemusi");
        b3.setTextFill(Color.ROYALBLUE);
        b3.setPrefSize(300, 100);
        b3.setOnMousePressed(event -> {
            pealkiri.setText("Vaata ürituse tulemusi");
        });

        Button b4 = new Button("Vaata laenutatavaid esemeid");
        b4.setTextFill(Color.ROYALBLUE);
        b4.setPrefSize(300, 100);
        b4.setOnMousePressed(event -> {
            pealkiri.setText("Vaata laenutatud esemeid");
            vaataSpordivahendeid();
        });

        Button b5 = new Button("Laenuta ese");
        b5.setTextFill(Color.ROYALBLUE);
        b5.setPrefSize(300, 100);
        b5.setOnMousePressed(event -> {
            pealkiri.setText("Laenuta ese");
            Stage laenutaEse = new Stage();
            BorderPane uusRoot =new BorderPane();
            Scene uusaken =new Scene(uusRoot,300,300);
            laenutaEse.setResizable(false);
            laenutaEse.setScene(uusaken);
            laenutaEse.setTitle("Mida soovid laenutada?");
            // Create two TextField controls for item and money input
            TextField itemInput = new TextField();
            itemInput.setPromptText("Sisesta ese");
            TextField moneyInput = new TextField();
            moneyInput.setPromptText("Sisesta summa");

            // Add the text fields to the BorderPane layout
            VBox vbox = new VBox(10, itemInput, moneyInput);
            vbox.setAlignment(Pos.CENTER);
            uusRoot.setCenter(vbox);
            //make an enter button to enter data for method public void laenutab(Spordivahend spordivahend, LocalDate kuupäev, int tasutudTagatisRaha)
            // Create the enter button
            Button enterButton = new Button("Enter");
            enterButton.setOnAction(e -> {
                // Get the input values from the text fields
                String itemName = itemInput.getText();
                int money = Integer.parseInt(moneyInput.getText());

                // Find the Spordivahend object with the given name
                Spordivahend item = otsiSpordivahend(itemName);

                // Call the laenutab method with the input values
                LocalDate currentDate = LocalDate.now();
                aktiivneIsik.laenutab(item, currentDate, money);

                // Close the laenutaEse stage
                laenutaEse.close();
            });

            // Add the enter button to the VBox layout
            vbox.getChildren().add(enterButton);
            laenutaEse.show();
        });


        Button b6 = new Button("Tagasta ese");
        b6.setTextFill(Color.ROYALBLUE);
        b6.setPrefSize(300, 100);
        b6.setOnMousePressed(event -> {
            pealkiri.setText("Tagasta ese");
            Stage tagastaEse = new Stage();
            BorderPane uusRoot = new BorderPane();
            Scene uusaken = new Scene(uusRoot,300,300);
            tagastaEse.setResizable(false);
            tagastaEse.setScene(uusaken);
            tagastaEse.setTitle("Mida soovid tagastada?");
            // Create two TextField controls for item and money input
            TextField itemInput = new TextField();
            itemInput.setPromptText("Sisesta ese");
            // Add the text fields to the BorderPane layout
            VBox vbox = new VBox(10, itemInput);
            vbox.setAlignment(Pos.CENTER);
            uusRoot.setCenter(vbox);
            // Create the enter button
            Button enterButton = new Button("Enter");
            enterButton.setOnAction(e -> {
                        // Get the input values from the text fields
                        String itemName = itemInput.getText();

                        // Find the Spordivahend object with the given name
                        Spordivahend item = otsiSpordivahend(itemName);

                        // Call the tagastab method with the input values
                        aktiivneIsik.tagastab(item);

                        // Close the laenutaEse stage
                        tagastaEse.close();
                    });
            // Add the enter button to the VBox layout
            vbox.getChildren().add(enterButton);
            tagastaEse.show();
        });


        Button b7 = new Button("Loo uus konto");
        b7.setTextFill(Color.BLUE);
        b7.setPrefSize(300, 100);
        b7.setOnMousePressed(event -> {
            pealkiri.setText("Loo uus konto");
            looUuskonto();
        });

        Button b8 = new Button("Vaheta kontot");
        b8.setTextFill(Color.ROYALBLUE);
        b8.setPrefSize(300, 100);
        b8.setOnMousePressed(event -> {
            pealkiri.setText("Vaheta kontot");


        });

        nupud.getChildren().addAll(b1, b2, b3, b4, b5, b6, b7, b8);


        root.setPadding(new Insets(5, 5, 5, 5));
        Scene kaader = new Scene(root, 620, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(kaader);
        primaryStage.setTitle("Tere tulemast spordiklubi andmebaasi!");
        primaryStage.show();

    }

  private String valitudIsikuNimi() {
      Isik aktiivne = aktiivneIsik;
      if(aktiivne != null) {
          return "Valitud isik: \n" + aktiivne.getEesnimi() + " " + aktiivne.getPerenimi();
        }
        return "Valitud isik: - ";
    }

  private String valitudYrituseNimi() {
    Yritus aktiivne = aktiivneYritus;
    if(aktiivne != null) {
      return "Valitud üritus: \n" + aktiivne.getNimi();
    }
    return "Valitud üritus: - ";
  }

    public void looUuskonto() {
        //Konto loomise aken
        Stage uusKonto = new Stage();
        BorderPane uusRoot1 = new BorderPane();
        TilePane kastid = new TilePane(2, 2);
        kastid.setPrefRows(2);
        kastid.setPrefColumns(2);
        uusRoot1.setBottom(kastid);
        Scene uusaken = new Scene(uusRoot1, 400, 150);

        TextField eesnimi = new TextField();
        eesnimi.setPromptText("Eesnimi");
        eesnimi.setPrefSize(200, 25);
        eesnimi.setAlignment(Pos.TOP_RIGHT);
        TextField perenimi = new TextField();
        perenimi.setPromptText("Perenimi");
        perenimi.setPrefSize(200, 25);
        perenimi.setAlignment(Pos.TOP_RIGHT);
        TextField synniaeg = new TextField();
        synniaeg.setPromptText("Sünniaeg (yyyy-mm-dd)");
        synniaeg.setPrefSize(200, 25);
        synniaeg.setAlignment(Pos.TOP_RIGHT);
        TextField isikukood = new TextField();
        isikukood.setPromptText("Isikukood");
        isikukood.setPrefSize(200, 25);
        isikukood.setAlignment(Pos.TOP_RIGHT);
        kastid.getChildren().addAll(eesnimi, perenimi, synniaeg, isikukood);

        Button valju1 = looValjuNupp(uusRoot1);
        valju1.setOnMousePressed( event2 -> {
            uusKonto.close();
        });

        Button salvesta = looSalvestaNupp(uusRoot1);
        salvesta.setOnMousePressed( event2 -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String eesnimiValue = eesnimi.getText();
            String perenimiValue = perenimi.getText();
            String synniaegValue = synniaeg.getText();
            LocalDate synniaegDate = LocalDate.parse(synniaegValue, formatter);
            String isikukoodValue = isikukood.getText();

            // konstruktor lisab kohe ka liikmete listi
            Isik uusIsik;
            try {
                uusIsik = new Isik(eesnimiValue, perenimiValue, synniaegDate, isikukoodValue);
            } catch (Exception e) {
                displayMessage("Viga: " + e.getMessage());
                return;
            }


            //Isik uusIsik = new Isik(eesnimiValue, perenimiValue, synniaegDate, isikukoodValue);

            if(Liikmed.getLiikmed().size() == 1) {
                aktiivneIsik = uusIsik;
                valitudIsik.setText(valitudIsikuNimi());
            }
            uusKonto.close();
        });

        uusKonto.setResizable(false);
        uusKonto.setScene(uusaken);
        uusKonto.setTitle("Kasutajat veel ei ole, sisesta palun andmed");
        uusKonto.show();
    }

    private static Button looValjuNupp(BorderPane uusRoot1) {
        Button valju1 = new Button("Välju");
        valju1.setTextFill(Color.RED);
        valju1.setPrefSize(100, 25);
        uusRoot1.setRight(valju1);
        uusRoot1.getBottom().maxHeight(30);
        return valju1;
    }

    private static Button looSalvestaNupp(BorderPane uusRoot1) {
        Button salvesta = new Button("Salvesta");
        salvesta.setPrefSize(100, 25);
        uusRoot1.setLeft(salvesta);
        uusRoot1.getBottom().maxHeight(30);
        return salvesta;
    }

    private void vaataYritusi() {
        //To print the Spordivahendid list names
        List<Yritus> list = Yritused.getYritused();
        // Create a new Stage object
        Stage newStage = new Stage();

        // Create a new ListView control
        ListView<String> listView = new ListView<>();

        // Populate the ListView control with the list of Spordivahend objects
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Yritus yritus : list) {
            items.add(yritus.getNimi());
        }

        listView.setOnMouseClicked(event -> {
            String nimi = listView.getSelectionModel().getSelectedItem();
            aktiivneYritus = Yritused.otsiNimeKaudu(nimi);
            osalemised.lisaOsalemine(aktiivneIsik, aktiivneYritus);
            valitudYritus.setText(valitudYrituseNimi());

            newStage.close();
        });

        listView.setItems(items);
        // Create a new Scene object that contains the ListView control
        Scene scene = new Scene(listView, 400, 400);

        // Set the scene of the new stage to the Scene object
        newStage.setScene(scene);

        // Show the new stage on the screen
        newStage.show();
    }


    private static void vaataSpordivahendeid() {
        //To print the Spordivahendid list names
        List<Spordivahend> list = Spordivahendid.getSpordivahendList();
        // Create a new Stage object
        Stage newStage = new Stage();

        // Create a new ListView control
        ListView<String> listView = new ListView<>();

        // Populate the ListView control with the list of Spordivahend objects
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Spordivahend spordivahend : list) {
            items.add(spordivahend.getNimi());
        }
        listView.setItems(items);
        // Create a new Scene object that contains the ListView control
        Scene scene = new Scene(listView, 400, 400);

        // Set the scene of the new stage to the Scene object
        newStage.setScene(scene);

        // Show the new stage on the screen
        newStage.show();
    }
    public static Spordivahend otsiSpordivahend(String spordivahendNimi) {
        //System.out.println("Sisesta, mida tahad laenutada/tagastada");
        //Scanner scanner = new Scanner(System.in);
        String spordivahendiNimi = spordivahendNimi;
        System.out.println("Võtan eseme: " + spordivahendiNimi);
        List<Spordivahend> spordivahendid = Spordivahendid.getSpordivahendList();
        Spordivahend valitudSpordivahend = null;
        for (Spordivahend spordivahend : spordivahendid) {
            if (spordivahendiNimi.equalsIgnoreCase(spordivahend.getNimi())) {
                valitudSpordivahend = spordivahend;
            }
        }
        if (valitudSpordivahend == null) {
            System.out.println("Sellist eset ei ole...");
        }
        return valitudSpordivahend;
    }
    public void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }


}


