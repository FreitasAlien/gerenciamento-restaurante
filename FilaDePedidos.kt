package lista

import entities.Pedido

class FilaDePedidos @JvmOverloads constructor(tamanho: Int = 10) {
    private var ponteiroInicio = 0
    private var ponteiroFim: Int
    private var quantidade = 0
    private val dados: Array<Pedido?>

    init {
        ponteiroFim = -1
        dados = arrayOfNulls(tamanho)
    }


    fun enfileirar(dado: Pedido?) {
        if (!estaCheia()) {
            ponteiroFim++
            if (ponteiroFim == dados.size) {
                ponteiroFim = 0
            }
            quantidade++
            dados[ponteiroFim] = dado
        } else {
            println("Fila Cheia!")
        }
    }


    fun desenfileirar(): Pedido? {
        var dadoInicio: Pedido? = null
        if (!estaVazia()) {
            dadoInicio = dados[ponteiroInicio]
            ponteiroInicio++
            if (ponteiroInicio == dados.size) {
                ponteiroInicio = 0
            }
            quantidade--
        } else {
            println("Fila Vazia!")
        }
        return dadoInicio
    }


    fun espiar(): Pedido? {
        var dadoInicio: Pedido? = null
        if (!estaVazia()) {
            dadoInicio = dados[ponteiroInicio]
        } else {
            println("Fila Vazia!")
        }
        return dadoInicio
    }


    fun estaCheia(): Boolean {
        return (quantidade == dados.size)
    }


    fun estaVazia(): Boolean {
        return (quantidade == 0)
    }


    fun imprimir(): String {
        var resultado: String? = "["
        var i = 0
        var ponteiroAux = ponteiroInicio
        while (i < quantidade) {
            if (i == quantidade - 1) {
                resultado += dados[ponteiroAux % dados.size]
            } else {
                resultado += dados[ponteiroAux % dados.size].toString() + ","
            }
            i++
            ponteiroAux++
        }
        return "$resultado]"
    }

    fun selecionarTodos(): Array<Pedido?>? {
        var dadosAux: Array<Pedido?>? = null
        if (!estaVazia()) {
            dadosAux = arrayOfNulls(quantidade)
            var i = 0
            var ponteiroAux = ponteiroInicio
            while (i < quantidade) {
                dadosAux[i] = dados[ponteiroAux % dados.size]
                i++
                ponteiroAux++
            }
        }
        return dadosAux
    }
}