package entities

import enums.StatusDaMesa
import lista.FilaDeMesas

// Classe que representa um garçom
class Garcom(val nome: String, private val senha: String) {
    private val mesasAtendidas: FilaDeMesas = FilaDeMesas()

    // Autentica o garçom com base no nome e senha
    fun autenticarGarcom(nomeInformado: String, senhaInformada: String): Boolean {
        val verificaLogin = this.nome == nomeInformado
        val verificaSenha = this.senha == senhaInformada

        return verificaLogin && verificaSenha
    }

    // Adiciona uma mesa ao garçom
    fun addMesa(mesa: Mesa, cliente: Cliente?, numeroDeClientes: Int) {
        mesa.iniciarMesa(this, cliente, numeroDeClientes)
        mesasAtendidas.enfileirar(mesa)
    }


    fun atenderMesa(mesa: Mesa, pedido: Pedido?) {
        mesa.receberPedido(pedido)
    }

    fun finalizarAtendimento() {
        mesasAtendidas.espiar()?.setStatus(StatusDaMesa.FINALIZANDO)
        mesasAtendidas.desenfileirar()
    }

    // Exibe o histórico de pedidos das mesas atendidas pelo garçom
    fun historicoDePedidos() {
        for (mesa in mesasAtendidas.selecionarTodos()!!) {
            if (mesa != null) {
                mesa.historicoPedidos()
            }
        }
    }

    fun mesasAtendidas() {
        for (mesa in mesasAtendidas.selecionarTodos()!!) {
            System.out.println(mesa)
        }
    }
}