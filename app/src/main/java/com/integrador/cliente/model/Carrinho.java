package com.integrador.cliente.model;

import java.io.Serializable;

public class Carrinho implements Serializable {

    private Integer nr_pedido;
    private Integer nr_produto;
    private Integer qtd;
    private String valor_unidade;
    private String sub_total;

    public Carrinho() {
    }

    public Carrinho(Integer nr_pedido, Integer nr_produto, Integer qtd, String valor_unidade) {
        this.nr_pedido = nr_pedido;
        this.nr_produto = nr_produto;
        this.qtd = qtd;
        this.valor_unidade = valor_unidade;
    }

    public Integer getNr_pedido() {
        return nr_pedido;
    }

    public void setNr_pedido(Integer nr_pedido) {
        this.nr_pedido = nr_pedido;
    }

    public Integer getNr_produto() {
        return nr_produto;
    }

    public void setNr_produto(Integer nr_produto) {
        this.nr_produto = nr_produto;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public String getValor_unidade() {
        return valor_unidade;
    }

    public void setValor_unidade(String valor_unidade) {
        this.valor_unidade = valor_unidade;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "nr_pedido=" + nr_pedido +
                ", nr_produto=" + nr_produto +
                ", qtd=" + qtd +
                ", valor_unidade='" + valor_unidade + '\'' +
                ", sub_total='" + sub_total + '\'' +
                '}';
    }
}
