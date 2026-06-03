import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HasilChartPanel extends JPanel {
    private JPanel resultsContainer;
    private Timer animTimer;
    
    private final Color bgDark = new Color(30, 30, 30);       
    private final Color panelDark = new Color(43, 43, 43);    
    private final Color textLight = new Color(235, 235, 235); 
    private final Color borderDark = new Color(70, 70, 70); 

    public HasilChartPanel() {
        setLayout(new BorderLayout());
        setBackground(panelDark);
        setPreferredSize(new Dimension(420, 0));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(borderDark, 2), " Live Results & Stats ", 0, 0, 
            new Font("Segoe UI", Font.BOLD, 18), textLight
        ));

        resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
        resultsContainer.setBackground(panelDark);
        resultsContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollResults = new JScrollPane(resultsContainer);
        scrollResults.setBorder(null);
        scrollResults.setBackground(panelDark);
        scrollResults.getViewport().setBackground(panelDark);
        scrollResults.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollResults, BorderLayout.CENTER);
    }

    public void renderDataBaru(List<KarakterVote> dataKandidat, int totalVotes) {
        resultsContainer.removeAll(); 
        int maxVote = 1;
        for (KarakterVote kv : dataKandidat) {
            if (kv.getJumlahVote() > maxVote) maxVote = kv.getJumlahVote();
        }

        List<JProgressBar> barsList = new ArrayList<>();
        List<Integer> targetVotes = new ArrayList<>();

        for (KarakterVote kv : dataKandidat) {
            JPanel row = new JPanel(new BorderLayout(15, 0));
            row.setMaximumSize(new Dimension(420, 45)); 
            row.setBackground(panelDark);

            JLabel lblPhoto = new JLabel();
            String path = "images/" + kv.getNama().replace(" ", "_") + ".jpg";
            if (new File(path).exists()) {
                // Menggunakan metode enkapsulasi baru untuk memutus rantai pemanggilan objek
                lblPhoto.setIcon(buatIkonAman(path, 35, 35));
            } else {
                lblPhoto.setPreferredSize(new Dimension(35, 35));
                lblPhoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }

            JLabel lblName = new JLabel(kv.getNama());
            lblName.setForeground(textLight);
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblName.setPreferredSize(new Dimension(110, 20));

            double persentase = (totalVotes == 0) ? 0 : (kv.getJumlahVote() * 100.0) / totalVotes;
            String textBar = String.format("%d suara (%.1f%%)", kv.getJumlahVote(), persentase);

            JProgressBar bar = new JProgressBar(0, maxVote);
            bar.setValue(0); 
            bar.setStringPainted(true);
            bar.setString(textBar); 
            bar.setForeground(new Color(255, 105, 180));
            bar.setBackground(bgDark);
            bar.setBorderPainted(false);
            
            barsList.add(bar);
            targetVotes.add(kv.getJumlahVote());

            row.add(lblPhoto, BorderLayout.WEST);
            
            JPanel barWithText = new JPanel(new BorderLayout(5, 0));
            barWithText.setBackground(panelDark);
            barWithText.add(lblName, BorderLayout.WEST);
            barWithText.add(bar, BorderLayout.CENTER);
            
            row.add(barWithText, BorderLayout.CENTER);
            resultsContainer.add(row);
            resultsContainer.add(Box.createVerticalStrut(12));
        }

        resultsContainer.revalidate();
        resultsContainer.repaint();
        jalankanAnimasi(barsList, targetVotes);
    }

    private void jalankanAnimasi(List<JProgressBar> barsList, List<Integer> targetVotes) {
        if (animTimer != null && animTimer.isRunning()) animTimer.stop();

        animTimer = new Timer(20, e -> {
            boolean isAllBarsFinished = true;
            for (int i = 0; i < barsList.size(); i++) {
                JProgressBar pb = barsList.get(i);
                int target = targetVotes.get(i);
                int current = pb.getValue();
                
                if (current < target) {
                    int step = Math.max(1, (target - current) / 10); 
                    pb.setValue(Math.min(current + step, target));
                    isAllBarsFinished = false; 
                }
            }
            if (isAllBarsFinished) ((Timer) e.getSource()).stop();
        });
        animTimer.start();
    }

    // Metode delegasi baru untuk menyembunyikan rantai pemanggilan objek (Hide Delegate)
    private ImageIcon buatIkonAman(String path, int width, int height) {
        ImageIcon ikonAsli = new ImageIcon(path);
        
        // Validasi keamanan jika data gambar tidak terbaca atau korup
        if (ikonAsli.getIconWidth() == -1) {
            return null;
        }
        
        // Memecah rantai pemanggilan menjadi baris-baris eksekusi yang terisolasi
        Image gambarMentah = ikonAsli.getImage();
        Image gambarSkala = gambarMentah.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        return new ImageIcon(gambarSkala);
    }
}