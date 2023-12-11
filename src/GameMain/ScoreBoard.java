/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Tongyu Yang, Peter Unrein, Hung Giang, Adrian Berg
 * Date: Apr 23, 2016
 * Time: 2:15:33 AM
 *
 * Project: csci205FinalProject
 * Package: GameMain
 * File: ScoreBoard
 * Description: A class for showing the totalScore
 *
 * ****************************************
 */
package GameMain;

import static GameMain.Menu.loadFont;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A class for showing the totalScore
 *
 * @author Tongyu
 */
public class ScoreBoard extends JPanel implements ActionListener, KeyListener {

    /**
     * Initialize instance variables for the ScoreBoard
     */
    private GameView theView;
    private int stage, totalTankNum;
    private int totalScore = 0;
    private final int SHIFT = 80;
    private JButton restartButton;
    private final ImageUtility imageInstance = ImageUtility.getInstance();
    private int[] tankScoreList = {0, 0, 0, 0};
    private int[] tankNumList = {0, 0, 0, 0};

    /**
     * Constructor for the ScoreBoard. A restart button is added for the player
     * to restart the game
     *
     * @param theView GameView that represents the frame of the game
     */
    public ScoreBoard(GameView theView) {
        this.theView = theView;
        this.setFocusable(true);
        theView.setForeground(Color.BLACK);
        this.setLayout(null);

        restartButton = new JButton();
        restartButton.setText("Restart");
        this.add(restartButton);
        restartButton.setBounds(400, 400,
                100, 30);
        restartButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        loadScore();
        stage = Board.getStage();

        super.paintComponent(g);

        Font font = loadFont();

        ArrayList<Image> tankList = getTankList();

        setGraphicsProperties(g, font);

        drawStageInfo(g);
        drawPlayerInfo(g);
        drawTankInfo(g, tankList);
        drawTotalInfo(g);

        resetGraphicsProperties(g, font);
    }

    ArrayList<Image> getTankList() {
        return new ArrayList<>(Arrays.asList(
                imageInstance.getTankBasic(),
                imageInstance.getTankFast(),
                imageInstance.getTankPower(),
                imageInstance.getTankArmor()
        ));
    }

    void setGraphicsProperties(Graphics g, Font font) {
        g.setFont(font);
    }

    private void drawStageInfo(Graphics g) {
        drawStringWithColor(g, "STAGE   " + stage, 97 + SHIFT, 60, Color.WHITE);
    }

    private void drawPlayerInfo(Graphics g) {
        drawStringWithColor(g, "1-PLAYER", 37 + SHIFT, 95, Color.RED);
        drawStringWithColor(g, String.valueOf(totalScore), 121 + SHIFT, 130, Color.ORANGE);
    }

    private void drawTankInfo(Graphics g, ArrayList<Image> tankList) {
        for (int i = 0; i < 4; i++) {
            g.drawImage(tankList.get(i), 226 + SHIFT, 160 + (i * 45), this);
            g.drawImage(imageInstance.getArrow(), 206 + SHIFT, 168 + (i * 45), this);

            drawStringWithColor(g, String.valueOf(tankScoreList[i]), 55 + SHIFT, 180 + (i * 45), Color.WHITE);
            drawStringWithColor(g, "PTS", 115 + SHIFT, 180 + (i * 45), Color.WHITE);
        }
    }

    private void drawTotalInfo(Graphics g) {
        g.drawLine(170, 330, 307, 330);
        drawStringWithColor(g, "TOTAL", 85 + SHIFT, 355, Color.WHITE);
        drawStringWithColor(g, String.valueOf(totalTankNum), 180 + SHIFT, 355, Color.WHITE);
    }

    private void resetGraphicsProperties(Graphics g, Font font) {
        g.setFont(font);
        g.setColor(Color.WHITE);
    }

    private void drawStringWithColor(Graphics g, String text, int x, int y, Color color) {
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void loadScore() {
        for (int i = 0; i < 4; i++) {
            int[] enemyTankNum = CollisionUtility.getEnemyTankNum();
            tankNumList[i] = enemyTankNum[i];
        }
        for (int i = 0; i < 4; i++) {
            tankScoreList[i] = tankNumList[i] * 100 * (i + 1);
        }
        for (Integer score : tankScoreList) {
            totalScore += score;
        }
        for (Integer num : tankNumList) {
            totalTankNum += num;
        }
    }
    /**
     * Restart the game, load the menu and reset player's totalScore.
     */


    public void restart() {
        Board.gameOver = false;
        CollisionUtility.resetScore();
        loadMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            restart();
        }
    }

    /**
     * Load the menu to the game panel if the player chooses to restart the
     * game.
     */
    private void loadMenu() {
        theView.getGamePanel().removeAll();
        Menu menu = new Menu(theView);
        menu.revalidate();
        menu.repaint();
        theView.getGamePanel().add(menu);
        menu.requestFocusInWindow();
        theView.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loadMenu();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loadMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loadMenu();
        }
    }
}
