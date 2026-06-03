import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BocchiPollMainFrame extends JFrame {

    private final VoteDAO voteDAO;
    private final KandidatGridPanel panelKiri;
    private final HasilChartPanel panelKanan;
    private final ControlPanel panelBawah;

    private final String[] waifus = {
        "Hitori Gotoh", "Nijika Ijichi", "Ryo Yamada", "Kita Ikuyo", 
        "Seika Ijichi", "PA-san", "Kikuri Hiroi", "Yoyoko Ohtsuki", 
        "Michiyo Gotoh", "Eliza Shimizu"
    };

    public BocchiPollMainFrame() {
        // Inisialisasi Database
        voteDAO = new VoteDAO();
        voteDAO.setupDatabase(waifus);

        // Pengaturan Jendela Utama
        setTitle("Bocchi the Rock! - Live Waifu Polling System");
        setSize(1250, 750); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 30));

        // Header
        JLabel headerLabel = new JLabel("Bocchi the Rock! - Live Polling", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(new Color(235, 235, 235));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Inisialisasi Panel Komponen
        panelKiri = new KandidatGridPanel(waifus);
        panelKanan = new HasilChartPanel();
        panelBawah = new ControlPanel();

        // Menyusun Panel
        JPanel mainContent = new JPanel(new BorderLayout(20, 0));
        mainContent.setBackground(new Color(30, 30, 30));
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainContent.add(panelKiri, BorderLayout.CENTER);
        mainContent.add(panelKanan, BorderLayout.EAST);

        add(mainContent, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        // Memasang Logika ke Tombol
        setupActionListeners();

        // Render Grafik Awal
        perbaruiGrafik();
        setLocationRelativeTo(null);
    }

    private void setupActionListeners() {
        panelBawah.setVoteListener(e -> {
            String pilihan = panelKiri.getPilihan();
            if (pilihan == null) {
                JOptionPane.showMessageDialog(this, "Silakan pilih satu karakter!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            voteDAO.tambahSuara(pilihan);
            panelKiri.clearPilihan();
            perbaruiGrafik();
        });

        panelBawah.setExportListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("Laporan_Voting_Bocchi.txt"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".txt")) path += ".txt";
                try {
                    ReportExporter.exportToTXT(path, voteDAO.ambilSemuaData(), voteDAO.ambilTotalSuara());
                    JOptionPane.showMessageDialog(this, "Export Sukses!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal export: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelBawah.setResetListener(e -> {
            JPasswordField pf = new JPasswordField();
            if (JOptionPane.showConfirmDialog(this, pf, "Masukkan Password Admin:", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                if (new String(pf.getPassword()).equals("987654321")) {
                    voteDAO.resetSemuaData();
                    perbaruiGrafik();
                    JOptionPane.showMessageDialog(this, "Reset Berhasil!");
                } else {
                    JOptionPane.showMessageDialog(this, "Password Salah!", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void perbaruiGrafik() {
        panelKanan.renderDataBaru(voteDAO.ambilSemuaData(), voteDAO.ambilTotalSuara());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BocchiPollMainFrame().setVisible(true));
    }
}