package model

import java.util.*

enum class TabuleiroEvento {
    VITORIA,
    DERROTA
}

class Tabuleiro(val quantidadeLinhas: Int, val quantidadeColunas: Int, private val quantidadeMinas: Int) {
    private val campos = ArrayList<ArrayList<Campo>>()
    private val callbacks = ArrayList<(TabuleiroEvento) -> Unit>()

    init {
        geraCampos()
        associaVizinhos()
        sorteiaMinas()
    }

    private fun geraCampos() {
        for (linha in 0 until quantidadeLinhas) {
            campos.add(ArrayList())
            for (coluna in 0 until quantidadeColunas) {
                val novoCampo = Campo(linha, coluna)
                novoCampo.onEvento(this::verificaDerrotaOuVitoria)
                campos[linha].add(novoCampo)
            }
        }
    }

    private fun associaVizinhos() {
        forEachCampo { associaVizinhos(it) }
    }

    private fun associaVizinhos(campo: Campo) {
        val (linha, coluna) = campo
        val linhas = arrayOf(linha - 1, linha, linha + 1)
        val colunas = arrayOf(coluna - 1, coluna, coluna + 1)

        linhas.forEach { l ->
            colunas.forEach { c ->
                val atual = campos.getOrNull(l)?.getOrNull(c)
                atual?.takeIf { campo != it }?.let { campo.adicionaVizinho(it) }
            }
        }
    }

    private fun sorteiaMinas() {
        val gerador = Random()

        var linhaSorteada = -1
        var colunaSorteada = -1
        var quantidadeMinasAtual = 0

        while (quantidadeMinas < this.quantidadeMinas) {
            linhaSorteada = gerador.nextInt(quantidadeLinhas)
            colunaSorteada = gerador.nextInt(quantidadeColunas)

            val campoSorteado = campos[linhaSorteada][colunaSorteada]
            if (campoSorteado.seguro) {
                campoSorteado.minar()
                quantidadeMinasAtual++
            }
        }
    }

    private fun objetivoAlcancado(): Boolean {
        var jogadorGanhou = true
        // para cada campo se o objetivo alcancado for negativo, vai ser atribuido falso para a variavel
        forEachCampo { if (!it.objetivoAlcancado) jogadorGanhou = false }
        return jogadorGanhou
    }

    private fun verificaDerrotaOuVitoria(campo: Campo, campoEvento: CampoEvento) {
        if (campoEvento == CampoEvento.EXPLOSAO) {
            callbacks.forEach { it(TabuleiroEvento.DERROTA) }
        } else if (objetivoAlcancado()) {
            callbacks.forEach { it(TabuleiroEvento.VITORIA) }
        }
    }

    fun forEachCampo(callback: (Campo) -> Unit) {
        campos.forEach { linha -> linha.forEach(callback) }
    }

    fun onEvento(callback: (TabuleiroEvento) -> Unit) {
        callbacks.add(callback)
    }

    fun reiniciaTabuleiro() {
        forEachCampo { it.reiniciar() }
        sorteiaMinas()
    }
}