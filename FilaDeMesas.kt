package lista

import entities.Mesa

class FilaDeMesas {
    private var ponteiroInicio = 0
    private var ponteiroFim: Int
    private var quantidade = 0
    private val dados: Array<Mesa?>

    init {
        ponteiroFim = -1
        dados = arrayOfNulls(3)
    }


    fun enfileirar(dado: Mesa?) {
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


    fun desenfileirar(): Mesa? {
        var dadoInicio: Mesa? = null
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


    fun espiar(): Mesa? {
        var dadoInicio: Mesa? = null
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

    fun selecionarTodos(): Array<Mesa?>? {
        var dadosAux: Array<Mesa?>? = null
        if (!estaVazia()) {
            dadosAux = arrayOfNulls(quantidade)
            var i = 0
            var ponteiroAux = ponteiroInicio
            while (i < quantidade) {
                if (ponteiroAux == dados.size) {
                    ponteiroAux = 0
                }
                dadosAux[i] = dados[ponteiroAux]
                i++
                ponteiroAux++
            }
        }
        return dadosAux
    }
}