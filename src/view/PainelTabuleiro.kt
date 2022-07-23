package view

import model.Tabuleiro
import java.awt.GridLayout
import javax.swing.JPanel

class PainelTabuleiro(tabuleiro: Tabuleiro) : JPanel() {

    init {
        layout = GridLayout(tabuleiro.quantidadeLinhas, tabuleiro.quantidadeColunas)
        tabuleiro.forEachCampo { campo ->
            val botao = BotaoCampo(campo)
            add(botao)
        }
    }

}