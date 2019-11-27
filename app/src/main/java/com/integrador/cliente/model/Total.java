package com.integrador.cliente.model;

import java.io.Serializable;

public class Total implements Serializable {


    private String valor_total;

    public Total() {
    }

    public Total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    @Override
    public String toString() {
        return "Total{" +
                "valor_total='" + valor_total + '\'' +
                '}';
    }
}
