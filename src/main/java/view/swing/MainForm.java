package view.swing;

import model.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Основная форма игры
 */
public class MainForm {

    /**
     * Основная панель
     */
    public JPanel panel;
    private JLabel gameStateLabel;
    private JPanel fieldPanel;

    public MainForm(Game game) {
        super();
        gameStateLabel.setText(game.getState().toString());
        game.listeners.add(state -> gameStateLabel.setText(state.toString()));

        GridLayout grid = new GridLayout(game.getSize(), game.getSize());
        fieldPanel.setLayout(grid);
        for (int y = 0; y < game.getSize(); y++) {
            for (int x = 0; x < game.getSize(); x++) {
                fieldPanel.add(new CellView(x, y, game));
            }
        }
    }
}
