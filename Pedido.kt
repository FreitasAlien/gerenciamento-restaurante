package entities

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Classe que representa um pedido no restaurante
class Pedido(var descricao: String, var valor: Double) {
    // Hora do pedido
    var hora: LocalDateTime = LocalDateTime.now()

    // Formato de data e hora
    private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Retorna os detalhes do pedido
    val detalhes: String
        get() = """
HORA DO PEDIDO: ${dtf.format(hora)}
Descrição do Pedido: $descricao
VALOR: R$ ${String.format("%.2f", valor)}"""

    // Retorna uma representação em string do pedido
    override fun toString(): String {
        return "Pedido [descricao=$descricao, valor=$valor, hora=$hora, dtf=$dtf]"
    }
}