package main.controller;

import javafx.util.StringConverter;
import main.model.Moneda;

public class monedaConverter extends StringConverter<Moneda> {

    @Override
    public Moneda fromString(String monedaString) {
        return null;
    }

   /**
    * 
    * @param moneda El objeto a convertir.
    * @return El nombre de la moneda.
    */
    @Override
    public String toString(Moneda moneda) {
        return moneda == null ? null : moneda.getName();
    }

}
