package lista

import entities.Garcom

class ListaGarcons @JvmOverloads constructor(tamanho: Int = 10) {
    private val ponteiroInicio = 0
    private var ponteiroFim: Int
    private val dados: Array<Garcom?>
    private var quantidade = 0

    init {
        ponteiroFim = -1
        dados = arrayOfNulls(tamanho)
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

    fun anexar(dado: Garcom?) {
        if (!estaCheia()) {
            ponteiroFim++
            if (ponteiroFim == dados.size) {
                ponteiroFim = 0
            }
            quantidade++
            dados[ponteiroFim] = dado
        } else {
            println("Lista Cheia")
        }
    }


    fun selecionarTodos(): Array<Garcom?>? {
        var dadosAux: Array<Garcom?>? = null
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


    fun selecionar(posicao: Int): Garcom? {
        var dadoAux: Garcom? = null
        if (!estaVazia()) {
            if ((posicao >= 0) && (posicao < quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size
                dadoAux = dados[posicaoFisica]
            } else {
                System.err.println("Indice Invalido!")
            }
        } else {
            println("Lista Vazia")
        }
        return dadoAux
    }


    fun atualizar(posicao: Int, novoDado: Garcom?) {
        if (!estaVazia()) {
            if ((posicao >= 0) && (posicao < quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size
                dados[posicaoFisica] = novoDado
            } else {
                println("Indice Invalido!")
            }
        } else {
            println("Lista Vazia")
        }
    }


    fun inserir(posicao: Int, dado: Garcom?) {
        if (!estaCheia()) {
            if ((posicao >= 0) && (posicao <= quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size

                var i = ponteiroFim + 1
                while (i != posicaoFisica) {
                    val anterior = i - 1

                    if (i == dados.size) {
                        i = 0
                    }
                    val atual = i

                    dados[atual] = dados[anterior]
                    i--
                }

                dados[posicaoFisica] = dado
                ponteiroFim++
                if (ponteiroFim == dados.size) {
                    ponteiroFim = 0
                }
                quantidade++
            } else {
                println("Indice Invalido")
            }
        } else {
            println("Lista Cheia!")
        }
    }


    fun apagar(posicao: Int): Garcom? {
        var dadoAux: Garcom? = null
        if (!estaVazia()) {
            if ((posicao >= 0) && (posicao < quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size
                dadoAux = dados[posicaoFisica]

                var i = posicaoFisica
                while (i != ponteiroFim) {
                    val atual = i
                    if (i == dados.size - 1) {
                        i = -1
                    }
                    val proximo = i + 1

                    dados[atual] = dados[proximo]
                    i++
                }
                ponteiroFim--
                if (ponteiroFim == -1) {
                    ponteiroFim = dados.size - 1
                }
                quantidade--
            } else {
                println("Indice Invalido!")
            }
        } else {
            println("Lista Vazia!")
        }
        return dadoAux
    }
}