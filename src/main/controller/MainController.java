package main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import main.model.Moneda;
import main.model.Temperatura;

public class MainController implements Initializable {

   
    private Moneda monedaOrigen;
    private Moneda monedaDestino;
    private Double inputValue;
    private Double outputValue;
    private Temperatura temperatureOrigen;
    private Temperatura temperatureDestino;
    private Double inputTemperature;
    private Double outputTemperature;

    @FXML
    private TextArea txtInputCurrency, txtOutputCurrency, txtInputTemperature, txtOutputTemperature;
    @FXML
    private Button btnConvertCurrency, btnConvertTemperatureButton;
    @FXML
    private ComboBox<Moneda> cbCurrency1, cbCurrency2;
    @FXML
    private ComboBox<Temperatura> cbTemperature1, cbTemperature2;


    @FXML
    private void cbEventCurrOrigen(ActionEvent e) {
        Object evento = e.getSource();
        if (evento.equals(cbCurrency1)) {
            this.monedaOrigen = (cbCurrency1.getSelectionModel().getSelectedItem());
            System.out.println(this.monedaOrigen.getTag());
        }
    }

    @FXML
    private void cbEventCurrDestino(ActionEvent e) {
        Object evento = e.getSource();
        if (evento.equals(cbCurrency2)) {
            this.monedaDestino = (cbCurrency2.getSelectionModel().getSelectedItem());
            System.out.println(this.monedaDestino.getTag());
        }
    }

    @FXML
    private void cbEventTempOrigen(ActionEvent e) {
        Object evento = e.getSource();
        if (evento.equals(cbTemperature1)) {
            this.temperatureOrigen = (cbTemperature1.getSelectionModel().getSelectedItem());
            System.out.println(this.temperatureOrigen.getTag());
        }
    }

    @FXML
    private void cbEventTempDestino(ActionEvent e) {
        Object evento = e.getSource();
        if (evento.equals(cbTemperature2)) {
            this.temperatureDestino = (cbTemperature2.getSelectionModel().getSelectedItem());
            System.out.println(this.temperatureDestino.getTag());
        }
    }

    @FXML
    private void btnEventCurrency() {
        try {
            System.out.println("convertir moneda");
            this.inputValue = (Double.parseDouble(txtInputCurrency.getText()));
            currencyConverter();
            txtOutputCurrency.setText(String.valueOf(outputValue));
            System.out.println(this.outputValue);
        } catch (RuntimeException err) {
            if (err.getClass() == NumberFormatException.class) {
                txtOutputCurrency.setText("ERROR: debe ingresar numeros unicamente");
                txtInputCurrency.setText("");
            }
            if (err.getClass() == NullPointerException.class) {
                txtOutputCurrency.setText("ERROR: debe seleccionar ambos campos");
            }
        }
    }

    @FXML
    private void btnEventTemperature() {
        try {
            System.out.println("convertir a Temperatura");
            this.inputTemperature = Double.parseDouble(this.txtInputTemperature.getText());
            temperaturaConverterMejorado();
            txtOutputTemperature.setText(String.valueOf(this.outputTemperature));
            System.out.println(this.outputTemperature);
        } catch (RuntimeException err) {
            if (err.getClass() == NumberFormatException.class) {
                txtOutputTemperature.setText("ERROR: debe ingresar numeros unicamente");
                txtInputTemperature.setText("");
            }
            if (err.getClass() == NullPointerException.class) {
                txtOutputTemperature.setText("ERROR: debe seleccionar ambos campos");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbCurrency1.getItems().addAll(createCollection());
        cbCurrency2.getItems().addAll(createCollection());
        cbTemperature1.getItems().addAll(createCollectionTemperatures());
        cbTemperature2.getItems().addAll(createCollectionTemperatures());

        cbCurrency1.setConverter(new monedaConverter());
        cbCurrency2.setConverter(new monedaConverter());
        cbTemperature1.setConverter(new temperaturaConverter());
        cbTemperature2.setConverter(new temperaturaConverter());
    }

    private ArrayList<Moneda> createCollection() {
        ArrayList<Moneda> monedas = new ArrayList<>();
        monedas.add(new Moneda("Dolar USA", "USD", 1.0));
        monedas.add(new Moneda("Peso Mexicano", "MXN", 18.23));
        monedas.add(new Moneda("Peso Colombiano", "COP", 4600.0));
        monedas.add(new Moneda("Libra Esterlina", "GBP", 0.8));
        monedas.add(new Moneda("Euro", "EUR", 0.92));
        monedas.add(new Moneda("Yen Japonés", "YEN", 132.6));
        monedas.add(new Moneda("Won Surcoreano", "KRW", 1305.65));
        return monedas;
    }

    public Double conversionToDollarValue(Moneda moneda, Double value) {
        return value / moneda.getValue();
    }

    public Double conversionFromDollarValue(Moneda moneda, Double value) {
        return value * moneda.getValue();
    }

    public void currencyConverter() {
        Double result;
        if (monedaOrigen.getTag() != "USD") {
            Double toDollarValue = conversionToDollarValue(this.monedaOrigen, inputValue);
            result = conversionFromDollarValue(this.monedaDestino, toDollarValue);
            this.outputValue = result;
        } else {
            result = conversionFromDollarValue(this.monedaDestino, inputValue);
            this.outputValue = result;
        }
    }

    private ArrayList<Temperatura> createCollectionTemperatures() {
        ArrayList<Temperatura> temperaturasList = new ArrayList<>();
        temperaturasList.add(new Temperatura("Grados Centigrados", "C"));
        temperaturasList.add(new Temperatura("Grados Fahrenheit", "F"));
        temperaturasList.add(new Temperatura("Grados Kelvin", "K"));
        return temperaturasList;
    }

    public Double conversionToCelsiusValue(Temperatura temperatura, Double value) {
        Double result;
        if (temperatura.getTag() == "K") {
            return result = value - 273.15;
        } else if (temperatura.getTag() == "F") {
            return result = (value - 32) * 5 / 9;
        } else {
            return value;
        }
    }

    public Double conversionFromCelsiusValue(Temperatura temperatura, Double value) {
        Double result;
        if (temperatura.getTag() == "K") {
            return result = value + 273.15;
        } else if (temperatura.getTag() == "F") {
            return result = (value * 9 / 5) + 32;
        } else {
            return value;
        }
    }

    public void temperaturaConverterMejorado() {
        Double celsiusValue = conversionToCelsiusValue(temperatureOrigen, inputTemperature);
        Double result = conversionFromCelsiusValue(temperatureDestino, celsiusValue);
        this.outputTemperature = result;
    }

}
