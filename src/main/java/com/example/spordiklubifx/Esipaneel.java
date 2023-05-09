package com.example.spordiklubifx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Esipaneel extends Application {

    public static void main(String[] args) throws Exception {
        //Loon mõned spordiesemed, mis on spordiklubil laos olemas ja on võimalik laenutada
        Spordivahendid.loeSpordivahendid("spordivahendid.txt");

        Yritused.loeYritused("yritused.txt");
        Osalemised osalemised = new Osalemised();
        Isik aktiivneIsik = null;
        Yritus aktiivneYritus = null;

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        TextField pealkiri = new TextField("Mida soovid teha?");
        pealkiri.setPrefSize(600, 25);
        pealkiri.setAlignment(Pos.TOP_RIGHT);
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


        TilePane nupud = new TilePane(2, 2);
        nupud.setPrefRows(4);
        nupud.setPrefColumns(2);
        root.setBottom(nupud);

        Button b1 = new Button("Sisesta üritusel osalemine");
        b1.setTextFill(Color.ROYALBLUE);
        b1.setPrefSize(300, 100);
        b1.setOnMousePressed(event -> {
            pealkiri.setText("Sisesta üritusel osalemine");
            //järgnev, kui ei ole veel isikut loodud
            looUuskonto();
            //siin saab valida üritused
            valiYritus();
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
            laenutaEse.show();
        });


        Button b6 = new Button("Tagasta ese");
        b6.setTextFill(Color.ROYALBLUE);
        b6.setPrefSize(300, 100);
        b6.setOnMousePressed(event -> {
            pealkiri.setText("Tagasta ese");
        });


        Button b7 = new Button("Loo uus konto");
        b7.setTextFill(Color.BLUE);
        b7.setPrefSize(300, 100);
        b7.setOnMousePressed(event -> {
            pealkiri.setText("Loo uus konto");
        });

        Button b8 = new Button("Vaheta kontot");
        b8.setTextFill(Color.ROYALBLUE);
        b8.setPrefSize(300, 100);
        b8.setOnMousePressed(event -> {
            pealkiri.setText("Vaheta kontot");


        });

        nupud.getChildren().addAll(b1, b2, b3, b4, b5, b6, b7, b8);


        root.setPadding(new Insets(5, 5, 5, 5));
        Scene kaader = new Scene(root, 620, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(kaader);
        primaryStage.setTitle("Tere tulemast spordiklubi andmebaasi!");
        primaryStage.show();

    }

    public void valiYritus() {
        Stage yrituseValik =new Stage();
        BorderPane uusRoot = new BorderPane();
        Scene uusaken =new Scene(uusRoot,400,150);

        //Siia tuleb nimekiri üritustest Flowpane
        TextField yritus = new TextField("Vali ürituse number (1-2)");
        yritus.setPrefSize(200, 25);
        yritus.setAlignment(Pos.TOP_RIGHT);
        uusRoot.setBottom(yritus);

        Button valju1 = new Button("Välju");
        valju1.setTextFill(Color.RED);
        valju1.setPrefSize(100, 25);
        uusRoot.setRight(valju1);
        uusRoot.getBottom().maxHeight(30);
        valju1.setOnMousePressed( event2 -> {
            yrituseValik.close();});
        yrituseValik.setResizable(false);
        yrituseValik.setScene(uusaken);
        yrituseValik.setTitle("Kasutajat veel ei ole, sisesta palun andmed");
        yrituseValik.show();
    }

    public void looUuskonto(){
        Stage uusKonto = new Stage();
        BorderPane uusRoot1 = new BorderPane();
        TilePane kastid =new TilePane(2,2);
        kastid.setPrefRows(2);
        kastid.setPrefColumns(2);
        uusRoot1.setBottom(kastid);
        Scene uusaken =new Scene(uusRoot1,400,150);

        TextField eesnimi = new TextField("Eesnimi");
        eesnimi.setPrefSize(200, 25);
        eesnimi.setAlignment(Pos.TOP_RIGHT);
        TextField perenimi = new TextField("Perenimi");
        perenimi.setPrefSize(200, 25);
        perenimi.setAlignment(Pos.TOP_RIGHT);
        TextField synniaeg = new TextField("Sünniaeg (yyyy-mm-dd)");
        synniaeg.setPrefSize(200, 25);
        synniaeg.setAlignment(Pos.TOP_RIGHT);
        TextField isikukood = new TextField("Isikukood");
        isikukood.setPrefSize(200, 25);
        isikukood.setAlignment(Pos.TOP_RIGHT);
        kastid.getChildren().addAll(eesnimi,perenimi,synniaeg,isikukood);

        Button valju1 = new Button("Välju");
        valju1.setTextFill(Color.RED);
        valju1.setPrefSize(100, 25);
        uusRoot1.setRight(valju1);
        uusRoot1.getBottom().maxHeight(30);
        valju1.setOnMousePressed( event2 -> {
            uusKonto.close();});

        uusKonto.setResizable(false);
        uusKonto.setScene(uusaken);
        uusKonto.setTitle("Kasutajat veel ei ole, sisesta palun andmed");
        uusKonto.show();


    }
    private static void vaataSpordivahendeid() {
        //To print the Spordivahendid list names
        List<Spordivahend> list = Spordivahendid.getSpordivahendList();
        for (Spordivahend spordivahend : list) {
            System.out.println(spordivahend.getNimi());
        }
    }
}


