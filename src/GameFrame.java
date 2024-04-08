import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    GamePanel gamePanel;
    JButton resetButton;
    GameFrame(){
        this.setSize(612,700);
        gamePanel=new GamePanel();


        resetButton=new JButton("reset");
        resetButton.setFont(new Font("Ink Free", Font.BOLD, 65));
        resetButton.setBounds(100, 620, 100,30);
        resetButton.setForeground(Color.RED);
        resetButton.setBackground(Color.black);
        resetButton.addActionListener(this);
        resetButton.setVisible(true);


        this.add(gamePanel);



        this.setTitle("Snake");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);


        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public static void main(String[] args){
        new GameFrame();
    }
}
