package com.soft.developer;

public class CarrinhoCompras {
    private int estoque = 10;

    public int getEstoque() {
        return estoque;
    }

    public double finalizarCompra(int quantidade, boolean temDesconto) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        if (quantidade > estoque) {
            throw new IllegalStateException("Estoque insuficiente.");
        }

        double total = quantidade * 10.0;

        if (temDesconto) {
            total = total * 0.9;
        }

        estoque = estoque - quantidade;
        return total;
    }
}
