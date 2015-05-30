package view.swing;

import model.Game;
import model.UserException;

import javax.swing.*;

/**
 * Клетка поля
 */
public class CellView extends JButton {

    final int x, y;

    public CellView(int x, int y, Game game) {
        super(game.field[x][y].toString());
        this.x = x;
        this.y = y;

        addActionListener(actionEvent -> {
            try {
                game.move(x, y);
                setText(game.field[x][y].toString());
            } catch (UserException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
            // Если мы уже нажали на эту кнопку =>
            // ход уже произошел =>
            // блокируем кнопку
            setEnabled(false);
        });

        game.listeners.add(state -> {
            if (game.isOver())
                CellView.this.setEnabled(false);
        });

        game.field[x][y].addListener(newState -> {
            setText(newState.toString());
            setEnabled(false);
        });
    }
}
