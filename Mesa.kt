package entities

import enums.StatusDaMesa
import lista.FilaDePedidos
import java.time.LocalDateTime

// Classe que representa uma mesa no restaurante
class Mesa(var numero: Int) {
    // Garçom responsável pela mesa
    var garcomResponsavel: Garcom? = null
    // Fila de pedidos da mesa
    private val filaDePedido: FilaDePedidos = FilaDePedidos(10)
    // Fila de pedidos da mesa
    private var status: StatusDaMesa
    // Cliente responsável pela mesa
    var clienteResponsavel: Cliente? = null
    // Quantidade de clientes na mesa
    var quantidadeDeClientes: Int = 0
    // Hora de início do atendimento da mesa
    var hora: LocalDateTime? = null
        private set


    // Inicializa a mesa com status LIVRE
    init {
        status = StatusDaMesa.LIVRE
    }

    // Retorna o status atual da mesa
    fun getStatus(): StatusDaMesa {
        return status
    }

    // Define o status da mesa
    fun setStatus(status: StatusDaMesa) {
        this.status = status
    }

    // Retorna a fila de pedidos da mesa
    fun getFilaDePedido(): FilaDePedidos {
        return filaDePedido
    }

    // Inicia o atendimento da mesa com o garçom, cliente e quantidade de clientes
    fun iniciarMesa(garcomResponsavel: Garcom?, clienteResponsavel: Cliente?, quantidadeDeClientes: Int) {
        this.garcomResponsavel = garcomResponsavel
        this.clienteResponsavel = clienteResponsavel
        this.quantidadeDeClientes = quantidadeDeClientes
        this.hora = LocalDateTime.now()
    }

    // Recebe um pedido e o adiciona à fila de pedidos
    fun receberPedido(pedido: Pedido?) {
        if (pedido != null) {
            filaDePedido.enfileirar(pedido)
        }
    }

    // Atualiza o status da mesa para o próximo estado
    fun atualizarStatus() {
        when (status) {
            StatusDaMesa.LIVRE -> status = StatusDaMesa.OCUPADA
            StatusDaMesa.OCUPADA -> status = StatusDaMesa.AGUARDANDO
            StatusDaMesa.AGUARDANDO -> status = StatusDaMesa.COMENDO
            StatusDaMesa.COMENDO -> status = StatusDaMesa.FINALIZANDO
            StatusDaMesa.FINALIZANDO -> status = StatusDaMesa.LIVRE
        }
    }

    // Imprime o histórico de pedidos da mesa
    fun historicoPedidos() {
        val pedidos = filaDePedido.selecionarTodos()
        if (pedidos != null) {
            for (pedido in pedidos) {
                if (pedido != null) {
                    println(pedido.detalhes)
                }
            }
        }
    }

    // Retorna uma representação em string da mesa
    override fun toString(): String {
        return "MESA $numero"
    }
}