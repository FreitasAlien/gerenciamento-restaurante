package application

// Sistema de Gerenciamento de Restaurante

import entities.Cliente
import entities.Garcom
import entities.Mesa
import entities.Pedido
import enums.StatusDaMesa
import lista.ListaGarcons
import lista.ListaMesas
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

// Objeto que gerencia o sistema do restaurante
object SistemaGerenciamento {
    // Scanner para entrada de dados
    val input: Scanner = Scanner(System.`in`)
    // Lista de mesas do restaurante
    val mesasRestaurante: ListaMesas = ListaMesas()
    // Lista de garçons do restaurante
    val garcons: ListaGarcons = ListaGarcons(4)


    // Função principal que inicia o sistema
    @JvmStatic
    fun main(args: Array<String>) {
        // Adiciona mesas ao restaurante
        mesasRestaurante.anexar(Mesa(1))
        mesasRestaurante.anexar(Mesa(2))
        mesasRestaurante.anexar(Mesa(3))
        mesasRestaurante.anexar(Mesa(4))
        mesasRestaurante.anexar(Mesa(5))
        mesasRestaurante.anexar(Mesa(6))
        mesasRestaurante.anexar(Mesa(7))
        mesasRestaurante.anexar(Mesa(8))
        mesasRestaurante.anexar(Mesa(9))
        mesasRestaurante.anexar(Mesa(10))

        // Adiciona garçons ao restaurante
        garcons.anexar(Garcom("Marcos", "Marcos"))
        garcons.anexar(Garcom("Juliana", "juljul"))
        garcons.anexar(Garcom("Alan", "12072010"))
        garcons.anexar(Garcom("Joab", "sabrina123"))


        // Autentica o garçom
        var garconAutenticado: Garcom? = null
        do {
            garconAutenticado = login()
            if(garconAutenticado == null) {
                println("LOGIN OU SENHA INCORRETOS - TENTE NOVAMENTE")
            }
        } while (garconAutenticado == null)


        // Loop principal do sistema
        var encerrar = false
        var opcao: Int

        do {
            menu()
            opcao = input.nextLine().toInt()

            when (opcao) {
                1 -> adicionarMesa(garconAutenticado)
                2 -> atenderMesa(garconAutenticado)
                3 -> historicoPedidos(garconAutenticado)
                4 -> entregarPedido(garconAutenticado)
                5 -> finalizarAtendimento(garconAutenticado)
                6 -> verPedidosDaMesa(garconAutenticado)
                7 -> {
                    encerrar = true
                    println("ENCERRANDO A APLICAÇÃO...")
                }

                else -> println("OPÇÃO INVÁLIDA - TENTE NOVAMENTE")
            }
        } while (encerrar != true)

        input.close()
    }

    // Exibe o menu de operações
    fun menu() {
        println("\n=========== MENU DE OPERAÇÕES =============")
        //Mostrar as mesas em atendimento e seus status
        println("OPÇÃO 1 - ADICIONAR MESA")
        println("OPÇÃO 2 - ATENDER MESA")
        println("OPÇÃO 3 - HISTÓRICO DE PEDIDOS")
        println("OPÇÃO 4 - ENTREGAR PEDIDO")
        println("OPÇÃO 5 - FINALIZAR ATENDIMENTO")
        println("OPÇÃO 6 - VER PEDIDOS DAS MESAS")
        println("OPÇÃO 7 - ENCERRAR A APLICAÇÃO")
        println("===============================================")
        print("ESCOLHA A OPÇÃO DESEJADA: ")
    }

    // Realiza o login do garçom
    fun login(): Garcom? {
        var garcomAutenticado: Garcom? = null
        println("\n========== TELA DE LOGIN ========== ")
        print("\nLogin: ")
        val nomeInformado = input.nextLine()
        print("Senha: ")
        val senhaInformada = input.nextLine()

        for (garcom in garcons.selecionarTodos()!!) {
            if (garcom != null) {
                if (garcom.autenticarGarcom(nomeInformado, senhaInformada)) {
                    garcomAutenticado = garcom
                    System.out.printf("Bem Vindo, %s!\n", garcom.nome)
                }
            }

        }
        return garcomAutenticado
    }

    // Adiciona uma mesa ao atendimento
    fun adicionarMesa(garcomAutenticado: Garcom) {
        println("\n===== MESAS LIVRES =====")
        for (mesaLivre in mesasRestaurante.selecionarTodos()!!) {
            if (mesaLivre?.getStatus() == StatusDaMesa.LIVRE) {
                println(mesaLivre)
            }
        }
        print("ESCOLHA UMA MESA PARA ADICIONAR: ")
        val nMesa = input.nextLine().toInt()

        var achou: Boolean = false
        for (mesaLivre in mesasRestaurante.selecionarTodos()!!) {
            if ((mesaLivre?.numero == nMesa) && (mesaLivre.getStatus() == StatusDaMesa.LIVRE)) {
                print("Nome do Cliente responsável pela Mesa: ")
                val cliente = Cliente(input.nextLine())
                print("Quantidade de Clientes na Mesa: ")
                val qtdClientes = input.nextLine().toInt()
                garcomAutenticado.addMesa(mesaLivre, cliente, qtdClientes)
                mesaLivre.atualizarStatus()
                achou = true
                break
            }
        }
        if (!achou) {
            println("MESA NÃO ENCONTRADA OU JÁ ESTÁ OCUPADA")
        }
    }

    // Exibe a tela de pedido para a mesa em atendimento
    fun telaDePedido(mesaEmAtendimento: Mesa, cliente: Cliente, quantidadeDeClientes: Int) {
        println("\n====== TELA DE PEDIDO ======")
        print("NOME DO CLIENTE NA MESA: " + cliente.nome)
        println("\nQUANTOS CLIENTES TEM NA MESA: $quantidadeDeClientes")
        System.out.printf("ESCREVA O PEDIDO DA MESA %d: \n", mesaEmAtendimento.numero)
        val descricao = input.nextLine()
        print("VALOR DO PEDIDO: ")
        val valorDoPedido = input.nextLine().toDouble()
        System.out.printf("MESA %d ATENDIDA COM SUCESSO!\n", mesaEmAtendimento.numero)
        mesaEmAtendimento.receberPedido(Pedido(descricao, valorDoPedido))
        mesaEmAtendimento.setStatus(StatusDaMesa.AGUARDANDO)
    }

    // Exibe os pedidos da mesa
    fun verPedidosDaMesa(garcomAutenticado: Garcom?) {
        println("\n================= MESAS AGUARDANDO PEDIDO ================")
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.getStatus() == StatusDaMesa.AGUARDANDO) {
                System.out.printf("%s = %s\n", mesaEmAtendimento, mesaEmAtendimento.getStatus())
            }
        }
        print("DIGITE A MESA QUE DESEJA VER OS PEDIDOS: ")
        val nMesa = input.nextLine().toInt()


        var achou: Boolean = false
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.numero == nMesa && mesaEmAtendimento.getStatus() == StatusDaMesa.AGUARDANDO) {
                println(mesaEmAtendimento)
                for (pedido in mesaEmAtendimento.getFilaDePedido().selecionarTodos()!!) {
                    println(pedido?.detalhes)
                    achou = true
                }
            }
        }
        if (!achou) {
            println("MESA NÃO ENCONTRADA OU NÃO ESTÁ AGUARDANDO PEDIDO")
        }
    }

    // Atende uma mesa
    fun atenderMesa(garcomAutenticado: Garcom?) {
        println("\n=========== MESAS EM ATENDIMENTO ===========")
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.getStatus() != StatusDaMesa.LIVRE) {
                System.out.printf("%s = %s\n", mesaEmAtendimento, mesaEmAtendimento?.getStatus())
            }
        }

        print("DIGITE A MESA QUE DESEJA ATENDER: ")
        val nMesa = input.nextLine().toInt()

        var achou: Boolean = false
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.numero == nMesa && mesaEmAtendimento.getStatus() != StatusDaMesa.LIVRE) {
                telaDePedido(
                    mesaEmAtendimento,
                    mesaEmAtendimento.clienteResponsavel!!, mesaEmAtendimento.quantidadeDeClientes
                )
                achou = true
            }
        }
        if (!achou) {
            println("MESA NÃO ENCONTRADA OU JÁ ESTÁ LIVRE")
        }
    }

    // Exibe o histórico de pedidos do garçom autenticado
    fun historicoPedidos(garcomAutenticado: Garcom) {
        garcomAutenticado.historicoDePedidos()
    }

    // Entrega o pedido para a mesa
    fun entregarPedido(garcomAutenticado: Garcom?) {
        println("\n=========== MESAS AGUARDANDO ===========")
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.getStatus() == StatusDaMesa.AGUARDANDO) {
                System.out.printf("%s = %s\n", mesaEmAtendimento, mesaEmAtendimento.getStatus())
            }
        }
        print("DIGITE A MESA QUE DESEJA ENTREGAR O PEDIDO: ")
        val nMesa = input.nextLine().toInt()

        var achou: Boolean = false
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.numero == nMesa && mesaEmAtendimento.getStatus() == StatusDaMesa.AGUARDANDO) {
                mesaEmAtendimento.atualizarStatus()

                val pedidoFinalizado = mesaEmAtendimento.getFilaDePedido().espiar()


                val horaDeFinalizacao = LocalDateTime.now()
                val intervaloDeTempo = Duration.between(pedidoFinalizado!!.hora, horaDeFinalizacao)

                val hours = intervaloDeTempo.toHours()
                val minutes = intervaloDeTempo.toMinutes() % 60
                println(
                    """
                        ${mesaEmAtendimento.toString()}
                        TEMPO DE ENTREGA DO PEDIDO: ${hours}h:$minutes
                        """.trimIndent()
                )
                achou = true
            }
        }
        if (!achou) {
            println("MESA NÃO ENCONTRADA OU NÃO ESTÁ AGUARDANDO PEDIDO")
        }
    }

    // Finaliza o atendimento da mesa
    fun finalizarAtendimento(garcomAutenticado: Garcom?) {
        println("\n=========== MESAS Á SEREM FINALIZADAS ===========")
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.getStatus() == StatusDaMesa.COMENDO) {
                System.out.printf("%s = %s\n", mesaEmAtendimento, mesaEmAtendimento.getStatus())
            }
        }
        print("DIGITE A MESA QUE FINALIZAR O PEDIDO: ")
        val nMesa = input.nextLine().toInt()

        var achou: Boolean = false
        for (mesaEmAtendimento in mesasRestaurante.selecionarTodos()!!) {
            if (mesaEmAtendimento?.numero == nMesa && mesaEmAtendimento.getStatus() == StatusDaMesa.COMENDO) {
                mesaEmAtendimento.atualizarStatus()
                System.out.printf("Mesa %d = %s\n", mesaEmAtendimento.numero, mesaEmAtendimento.getStatus())
                val horaDeFinalizacao = LocalDateTime.now()
                val intervaloDeTempo = Duration.between(mesaEmAtendimento.hora, horaDeFinalizacao)

                val hours = intervaloDeTempo.toHours()
                val minutes = intervaloDeTempo.toMinutes() % 60
                println(
                    """
                        ${mesaEmAtendimento.toString()}
                        TEMPO DE ATENDIMENTO DA MESA: ${hours}h:$minutes
                        """.trimIndent()
                )
                mesaEmAtendimento.atualizarStatus()
                achou = true
            }
        }
        if (!achou) {
            println("MESA NÃO ENCONTRADA OU NÃO ESTÁ COMENDO")
        }
    }
}