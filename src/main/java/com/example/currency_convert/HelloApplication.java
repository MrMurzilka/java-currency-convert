package com.example.currency_convert;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Label label = new Label("Сумма в EUR");
        TextField textField = new TextField();
        Label toCurrency = new Label("В валюту: ");
        ComboBox<String> toCurrencies = new ComboBox<>();
        toCurrencies.getItems().addAll("USD", "EUR", "JPY", "CNU", "CHF", "PLN", "RUB", "BYN");
        Button convertButton = new Button("Конвертировать");
        Label result = new Label("Результат: ");
        GridPane gridTable = new GridPane();
        gridTable.setPadding(new Insets(10));
        gridTable.setHgap(10);
        gridTable.setVgap(10);
        gridTable.add(label,0, 0);
        gridTable.add(textField,2, 0);
        gridTable.add(toCurrency, 0, 2);
        gridTable.add(toCurrencies, 2, 2);
        gridTable.add(convertButton, 1, 3);
        gridTable.add(result, 1, 4);

        convertButton.setOnAction(actionEvent -> {
            String amountText = textField.getText();
            String toCurr = toCurrencies.getValue();

            if (amountText.isEmpty() || toCurr == null) {
                result.setText("Все поля должны быть заполнены");
            }

        try {
            Double amount = Double.parseDouble(amountText);
            Double rate = ExchangeRate(toCurr);
            Double result1 = amount * rate;
            result.setText(Double.toString(result1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        });

        Scene scene = new Scene(gridTable, 600, 400);
        stage.setTitle("Конвертёр валют");
        stage.setScene(scene);
        stage.show();
    }

    private Double ExchangeRate(String to) throws Exception {
        String urlStr = "http://api.exchangeratesapi.io/v1/latest?access_key=12a7f0392d6e3bfc35b4d887259bc891&symbols=" + to;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        return jsonObject.getJSONObject("rates").getDouble(to);
    }

    public static void main(String[] args) {
        launch();
    }
}