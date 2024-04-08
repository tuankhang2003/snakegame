import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Highscore {

    JFrame frame;
    JTable Table;
    String[] columnName={"Name", "Score"};
    String[][] data;
    String path="Score.json";
    public Highscore(){
        frame=new JFrame();
        frame.setSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        frame.setBackground(Color.black);

        frame.setTitle("High Score Table");


        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray jsonArray= new JSONArray(jsonStr);
            data=new String[jsonArray.length()][2];
            for (int i=0;i<data.length;i++){
                data[i][0]= (String) ((JSONObject) jsonArray.get(i)).get("name");
                data[i][1]= String.valueOf(((JSONObject) jsonArray.get(i)).get("score"));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DefaultTableModel model = new DefaultTableModel(data, columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        // Zellenformatierung
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(Color.LIGHT_GRAY); // Hintergrundfarbe der Zellen
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Zentrierte Ausrichtung des Textes in den Zellen
        table.setDefaultRenderer(Object.class, cellRenderer);

        // Headerformatierung
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 30)); // Ändern der Schriftart für den Tabellenkopf
        header.setForeground(Color.BLUE); // Ändern der Textfarbe für den Tabellenkopf
        header.setBackground(Color.WHITE); // Ändern der Hintergrundfarbe für den Tabellenkopf

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
