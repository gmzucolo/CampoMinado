package view

import model.Campo
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener(
    private val campo: Campo,
    private val onClickLeft: (Campo) -> Unit,
    private val onClickRight: (Campo) -> Unit
) : MouseListener {
    override fun mouseClicked(e: MouseEvent?) {
        //Not implemented
    }

    override fun mousePressed(e: MouseEvent?) {
        when (e?.button) {
            MouseEvent.BUTTON1 -> onClickLeft(campo)
            MouseEvent.BUTTON3 -> onClickRight(campo)
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        //Not implemented
    }

    override fun mouseEntered(e: MouseEvent?) {
        //Not implemented
    }

    override fun mouseExited(e: MouseEvent?) {
        //Not implemented
    }
}