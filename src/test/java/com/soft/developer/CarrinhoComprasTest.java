package com.soft.developer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarrinhoComprasTest {
    @Test
    void compraComDesconto() {
        CarrinhoCompras carrinho = new CarrinhoCompras();
        double total = carrinho.finalizarCompra(2, true);
        assertEquals(18.0, total, 0.001);
        assertEquals(8, carrinho.getEstoque());
    }
}
