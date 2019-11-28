package com.integrador.cliente.model;

import java.io.Serializable;

public class Fechapedido implements Serializable {

    private String st_pedido;

    public Fechapedido() {
    }

    public Fechapedido(String st_pedido) {
        this.st_pedido = st_pedido;
    }

    public String getSt_pedido() {
        return st_pedido;
    }

    public void setSt_pedido(String st_pedido) {
        this.st_pedido = st_pedido;
    }

    @Override
    public String toString() {
        return "Fechapedido{" +
                "st_pedido='" + st_pedido + '\'' +
                '}';
    }
}
