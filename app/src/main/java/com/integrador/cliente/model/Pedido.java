package com.integrador.cliente.model;

import java.io.Serializable;

public class Pedido implements Serializable {

    private Integer num_pedido;
    private String data_pedido;
    private String cpf_cliente;
    private Integer st_pedido;

    public Pedido() {
    }

    public Pedido(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public Integer getNum_pedido() {
        return num_pedido;
    }

    public void setNum_pedido(Integer num_pedido) {
        this.num_pedido = num_pedido;
    }

    public String getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(String data_pedido) {
        this.data_pedido = data_pedido;
    }

    public String getCpf_cliente() {
        return cpf_cliente;
    }

    public void setCpf_cliente(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public Integer getSt_pedido() {
        return st_pedido;
    }

    public void setSt_pedido(Integer st_pedido) {
        this.st_pedido = st_pedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "num_pedido=" + num_pedido +
                ", data_pedido='" + data_pedido + '\'' +
                ", cpf_cliente='" + cpf_cliente + '\'' +
                ", st_pedido=" + st_pedido +
                '}';
    }
}
