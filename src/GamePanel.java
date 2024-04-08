import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import org.json.JSONObject;
import org.json.JSONArray;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNIT];
    final int[] y = new int[GAME_UNIT];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    JButton resetButton;
    public String name="khang";

    public GamePanel() {
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        resetButton=new JButton("exit");

        resetButton.setFont(new Font("Ink Free", Font.BOLD, 65));
        resetButton.setForeground(Color.RED);
        resetButton.setBackground(Color.black);
        resetButton.addActionListener(this);

        this.add(resetButton);
        resetButton.setVisible(false);


        this.setVisible(true);
        startgame();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(resetButton)){
            return;
        }


        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public boolean isRunning(){
        return running;
    }

    public void startgame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApple() {
        appleX = (random.nextInt((int) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = (random.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public JSONObject getjson(String s, int t){
        JSONObject json=new JSONObject();
        json.put(s,t);
        return json;
    }
    public  void updateScoreInJSON(String path) throws IOException {
        // Read JSON data from file
        String jsonStr = new String(Files.readAllBytes(Paths.get(path)));

        JSONArray jsonArray= new JSONArray(jsonStr);

        boolean check=false;
        Map<String, Integer> map=new HashMap<>();
        map.put(name, applesEaten);
        for (int i=0;i<jsonArray.length();i++){
            map.put((String) ((JSONObject) jsonArray.get(i)).get("name"), (int)((JSONObject) jsonArray.get(i)).get("score"));
        }
        Object[] jsonObjects= map.keySet().stream().sorted(Comparator.comparingInt(s-> (-map.get(s)))).limit(5).map(string -> {
            JSONObject object=new JSONObject();
            object.put("name", string);
            object.put("score", map.get(string));
            return object;
        }).toArray();
        JSONArray res=new JSONArray();
        for (int i=0;i<jsonObjects.length;i++){
            res.put((JSONObject) jsonObjects[i]);
        }
        try (FileWriter file = new FileWriter(path)) {
            file.write(res.toString());
            file.flush();
        }


    }
    public static void main(String[] args){

    }

    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i <= SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 45));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, SCREEN_HEIGHT+g.getFont().getSize());
        } else {

            try {
                updateScoreInJSON("Score.json");
                resetButton.setBounds(125,400,350,100);
                resetButton.setVisible(false);
                gameOver(g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'R':
                x[0] = (x[0] + UNIT_SIZE) % SCREEN_WIDTH;
                break;

            case 'L':
                x[0] = (x[0] - UNIT_SIZE);
                if (x[0] < 0) {
                    x[0] += SCREEN_WIDTH;
                }
                break;
            case 'U':
                y[0] = (y[0] + UNIT_SIZE) % SCREEN_HEIGHT;
                break;
            case 'D':
                y[0] = (y[0] - UNIT_SIZE);
                if (y[0] < 0) {
                    y[0] += SCREEN_HEIGHT;
                }
                break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        //collide with body
        for (int i = bodyParts; i > 0; i--) {
            if (x[i] == x[0] && y[i] == y[0]) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g) {

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //cannot return 180 grad
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
            }
        }
    }


}
