# Exercício prático - Testes de Caixa Branca

**Grupo 3: Anna Julya e Caio Moraes**  
**Tema:** Cobertura de linhas e cobertura de ramificações com JUnit e JaCoCo.

## Objetivo

Escrever testes para o método `finalizarCompra()` e acompanhar a evolução da
cobertura no relatório do JaCoCo. Ao final, o método deve atingir **100% de
linhas e 100% de ramificações**.

No teste de caixa branca, usamos a estrutura interna do código para escolher
os casos de teste. Nesta atividade, vamos observar cada `if` e pensar em entradas
que façam sua condição ser verdadeira e falsa.

## 1. Preparar o projeto

É necessário ter **JDK 21** e **Maven** instalados. Confira no terminal:

```bash
java -version
mvn -version
```

Abra a pasta `mercadinho` no editor e o terminal nessa mesma pasta.
O arquivo `pom.xml` precisa estar no diretório em que o comando Maven é executado.

Se você estiver na pasta `teste-caixa-branca`, entre na subpasta:

```bash
cd mercadinho
```

O JUnit e o JaCoCo já estão configurados no `pom.xml`. Na primeira execução,
o Maven pode precisar de internet para baixar as dependências.

## 2. Entender o método

Os caminhos abaixo são relativos à pasta `mercadinho`.

Abra:

```text
src/main/java/com/soft/developer/CarrinhoCompras.java
```

Cada novo carrinho começa com **10 unidades em estoque**. Cada unidade custa
**R$ 10**, e o desconto, quando aplicado, é de **10%**.

A chamada recebe dois argumentos:

```java
carrinho.finalizarCompra(2, true);
```

- `2`: quantidade que será comprada.
- `true`: a compra tem desconto. Use `false` para comprar sem desconto.

O método devolve o total da compra e reduz o estoque. Antes disso, ele verifica:

| Condição                         | Comportamento                    |
| -------------------------------- | -------------------------------- |
| Quantidade menor ou igual a zero | Lança `IllegalArgumentException` |
| Quantidade maior que o estoque   | Lança `IllegalStateException`    |
| Compra válida com desconto       | Aplica 10% de desconto           |
| Compra válida sem desconto       | Mantém o preço normal            |

**Antes de executar:** localize os três `if` e identifique os dois resultados
possíveis de cada condição.

## 3. Executar o teste inicial

Abra o arquivo que será completado durante a atividade:

```text
src/test/java/com/soft/developer/CarrinhoComprasTest.java
```

Ele já contém o teste `compraComDesconto`. Esse teste cria um carrinho, compra
duas unidades com desconto e confere o total de R$ 18 e o estoque restante de 8.

Execute:

```bash
mvn clean verify
```

O resultado esperado é **BUILD SUCCESS**. Um teste passar significa que suas
verificações foram atendidas, mas isso **não significa que todo o método foi testado**.

## 4. Abrir o relatório do JaCoCo

Abra o arquivo abaixo no navegador:

```text
target/site/jacoco/index.html
```

No Linux, você também pode usar:

```bash
xdg-open target/site/jacoco/index.html
```

Navegue por **com.soft.developer -> CarrinhoCompras**. Observe a linha do método
**finalizarCompra** e clique nela para ver o código colorido.
A meta da atividade é a cobertura desse método, não o total do projeto.

| Indicação       | Significado                                             |
| --------------- | ------------------------------------------------------- |
| Linha vermelha  | Nenhuma instrução daquela linha foi executada           |
| Linha amarela   | Apenas parte das instruções daquela linha foi executada |
| Linha verde     | Todas as instruções daquela linha foram executadas      |
| Losango amarelo | Ainda falta executar algum resultado de uma decisão     |

## 5. Atividade: completar a cobertura de linhas

Adicione dois métodos de teste em `CarrinhoComprasTest.java`, dentro
da classe e sem apagar o teste inicial. Coloque `@Test` antes de cada método.

### Teste A - Quantidade inválida

- Crie um novo `CarrinhoCompras`.
- Tente comprar **zero unidades**.
- Verifique se ocorre `IllegalArgumentException`.
- Confira se o estoque continua em **10**.

### Teste B - Estoque insuficiente

- Crie outro `CarrinhoCompras`.
- Tente comprar **11 unidades**.
- Verifique se ocorre `IllegalStateException`.
- Confira se o estoque continua em **10**.

Para testar uma exceção, o formato do JUnit é:

```java
assertThrows(TipoDaExcecao.class, () -> chamadaDoMetodo());
```

Esse trecho é um modelo: substitua o tipo e a chamada pelos dados do seu teste.
A expressão `() ->` permite que o JUnit execute a chamada e confira a exceção.
Para conferir o estoque, use `assertEquals(valorEsperado, carrinho.getEstoque())`.

Execute novamente:

```bash
mvn clean verify
```

Atualize a página do relatório no navegador.

## 6. Atividade: completar a cobertura de ramificações

Observe este if do método:

```java
if (temDesconto) {
    total = total * 0.9;
}
```

Até agora, as compras válidas usaram `true`. O teste inicial executou tanto a
decisão quanto o cálculo, que estão na mesma linha. Por isso, a cobertura de
linhas chegou a 100% sem executar o resultado `false` dessa decisão.
A escrita em uma única linha foi escolhida de propósito para esta demonstração.

### Teste C - Compra sem desconto

- Crie um novo `CarrinhoCompras`.
- Compre **duas unidades**, passando `false` no argumento do desconto.
- Confira se o total é **R$ 20**.
- Confira se o estoque restante é **8**.

Para comparar o total, use `assertEquals(20.0, total, 0.001)`.
O último argumento é a tolerância da comparação entre números decimais.

Execute novamente e atualize o relatório:

```bash
mvn clean verify
```

Agora, o método deve apresentar **100% de linhas e 100% de ramificações**.
