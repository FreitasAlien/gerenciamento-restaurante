package lista

import entities.Mesa

// Classe que representa uma lista de mesas
class ListaMesas @JvmOverloads constructor(tamanho: Int = 10) {
    // Ponteiro para o início da lista
    private val ponteiroInicio = 0
    // Ponteiro para o fim da lista
    private var ponteiroFim: Int
    // Array que armazena as mesas
    private val dados: Array<Mesa?>
    // Quantidade de mesas na lista
    private var quantidade = 0

    // Inicializa a lista de mesas
    init {
        ponteiroFim = -1
        dados = arrayOfNulls(tamanho)
    }

    // Verifica se a lista está cheia
    fun estaCheia(): Boolean {
        return (quantidade == dados.size)
    }

    // Verifica se a lista está vazia
    fun estaVazia(): Boolean {
        return (quantidade == 0)
    }

    // Retorna uma string com todas as mesas na lista
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

    // Adiciona uma mesa à lista
    fun anexar(dado: Mesa?) {
        if (!estaCheia()) {
            ponteiroFim++
            if (ponteiroFim == dados.size) {
                ponteiroFim = 0
            }
            quantidade++
            dados[ponteiroFim] = dado
        } else {
            println("Lista Cheia!")
        }
    }


    // Retorna todas as mesas na lista
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

    // Retorna a mesa na posição especificada
    fun selecionar(posicao: Int): Mesa? {
        var dadoAux: Mesa? = null
        if (!estaVazia()) {
            if ((posicao >= 0) && (posicao < quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size
                dadoAux = dados[posicaoFisica]
            } else {
                println("Indice Invalido!")
            }
        } else {
            println("Lista Vazia!")
        }
        return dadoAux
    }

    // Atualiza a mesa na posição especificada
    fun atualizar(posicao: Int, novoDado: Mesa?) {
        if (!estaVazia()) {
            if ((posicao >= 0) && (posicao < quantidade)) {
                val posicaoFisica = (ponteiroInicio + posicao) % dados.size
                dados[posicaoFisica] = novoDado
            } else {
                    println("Indice Invalido!")
            }
        } else {
            println("Lista Vazia!")
        }
    }


    // Insere uma mesa na posição especificada
    fun inserir(posicao: Int, dado: Mesa?) {
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


    // Remove e retorna a mesa na posição especificada
    fun apagar(posicao: Int): Mesa? {
        var dadoAux: Mesa? = null
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