import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class FrontFrame implements ActionListener {
    JFrame frame=new JFrame();
    JButton start=new JButton("Start");
    JButton exit=new JButton("Exit");
    JButton highscore=new JButton("HighScore");
    String name;
    Highscore highscoreTable;

    public FrontFrame(){
        start.setBounds(150,50,300,80);
        start.setFont(new Font("Ink Free", Font.BOLD, 60));
        start.setForeground(Color.RED);
        start.setFocusable(false);
        start.addActionListener(this);


        exit.setBounds(150, 200,300,80);
        exit.setFont(new Font("Ink Free", Font.BOLD, 60));
        exit.setForeground(Color.RED);
        exit.setFocusable(false);
        exit.addActionListener(this);

        highscore.setBounds(125,350,350,100);
        highscore.setFont(new Font("Ink Free", Font.BOLD, 60));
        highscore.setForeground(Color.RED);
        highscore.setFocusable(false);
        highscore.addActionListener(this);

        frame.add(start);
        frame.add(exit);
        frame.add(highscore);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(start)) {
            frame.dispose();
            GameFrame gameFrame=new GameFrame();
        }
        else if (e.getSource().equals(exit)){
            frame.dispose();
        }
        else if (e.getSource().equals(highscore)){
             highscoreTable=new Highscore();
             frame.dispose();
        }
    }
}
