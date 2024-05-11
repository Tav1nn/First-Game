import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{

    Thread thread;
    KeyHandler kHandler = new KeyHandler();

    final int origTileSize = 16;
    final int scale = 4;

    final int tileSize = origTileSize * scale;
    final int maxCol = 16;
    final int maxRow = 12;
    final int width = tileSize * maxCol;
    final int height = tileSize * maxRow;

    public int player_X = 100;
    public int player_Y = 100;
    public int playerSpeed = 4;

    public Panel() {
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(kHandler);
        this.setFocusable(true);
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(this.thread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime/1000000);
                nextDrawTime += drawInterval;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if(kHandler.upPressed == true) {
            player_Y -= playerSpeed;
        }
        if(kHandler.downPressed == true) {
            player_Y += playerSpeed;
        }
        if(kHandler.rightPressed == true) {
            player_X += playerSpeed;
        }
        if(kHandler.leftPressed == true) {
            player_X -= playerSpeed;
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;

        g2D.setColor(Color.black);
        g2D.fillRect(player_X, player_Y, tileSize, tileSize);
        g2D.dispose();
    }
}
