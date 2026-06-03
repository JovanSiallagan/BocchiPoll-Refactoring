import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton btnVote, btnExport, btnReset;
    private final Color bgDark = new Color(30, 30, 30);

    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(bgDark);

        btnVote = createButton("Kirim Suara!", new Color(255, 105, 180), new Dimension(200, 50));
        btnVote.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        btnExport = createButton("Export Laporan", new Color(40, 167, 69), new Dimension(150, 45));
        btnReset = createButton("Reset Data", new Color(220, 53, 69), new Dimension(150, 45));

        add(btnVote);
        add(btnExport);
        add(btnReset);
    }

    private JButton createButton(String text, Color bg, Dimension dim) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(dim);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    // Metode untuk menerima perintah (listener) dari kelas utama
    public void setVoteListener(ActionListener listener) { btnVote.addActionListener(listener); }
    public void setExportListener(ActionListener listener) { btnExport.addActionListener(listener); }
    public void setResetListener(ActionListener listener) { btnReset.addActionListener(listener); }
}