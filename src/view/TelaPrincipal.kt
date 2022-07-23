package view

import model.Tabuleiro
import model.TabuleiroEvento
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main() {
    TelaPrincipal()
}

class TelaPrincipal : JFrame() {
    private val tabuleiro = Tabuleiro(quantidadeLinhas = 16, quantidadeColunas = 30, quantidadeMinas = 89)
    private val painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {
        tabuleiro.onEvento(this::mostraResultado)
        add(painelTabuleiro)

        setSize(690, 438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Campo Minado"
        isVisible = true

    }

    private fun mostraResultado(evento: TabuleiroEvento) {
        SwingUtilities.invokeLater {
            val msg = when (evento) {
                TabuleiroEvento.VITORIA -> "Win"
                TabuleiroEvento.DERROTA -> "Lose"
            }

            JOptionPane.showMessageDialog(this, msg)
            tabuleiro.reiniciaTabuleiro()

            painelTabuleiro.repaint()
            painelTabuleiro.validate()
        }
    }
}