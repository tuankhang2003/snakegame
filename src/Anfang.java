import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anfang implements ActionListener {
    JTextField textField;
    JFrame frame;
    JButton button;
    JLabel label;
    JLabel label1;
    private final String regex="^[a-zA-Z]+[a-zA-Z0-9]+$";


    public Anfang(){
        frame=new JFrame();
        frame.setSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        frame.setLayout(new FlowLayout());
        frame.setBackground(Color.black);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        button=new JButton("OK");
        button.addActionListener(this);


        textField=new JTextField();
        textField.setPreferredSize(new Dimension(250,40));

        textField.setFont(new Font("Consolas", Font.PLAIN, 35));
        textField.setForeground(Color.BLACK);

        label=new JLabel("Enter your name: ");


        frame.add(label);

        frame.add(textField);
        frame.add(button);

        frame.setVisible(true);

    }
    public static void main(String[] args){
        Anfang anfang=new Anfang();
    }
    boolean regcheck(String s){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(s);
        return matcher.find();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)){
            boolean schon=false;

                if (!regcheck(textField.getText())){
                    label1=new JLabel("Der Name muss keine Sonderzeichen und keine Zahl am Anfang besitzen");
                    frame.add(label1);
                    label1.setVisible(true);
                    schon=true;
                }

            FrontFrame frontFrame=new FrontFrame();
            frame.dispose();
            frontFrame.name=textField.getText();
        }
    }
}
