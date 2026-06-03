import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
    
    public void setupDatabase(String[] waifus) {
        try (Connection conn = DatabaseConfig.getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS waifu_votes (nama TEXT PRIMARY KEY, jumlah_vote INTEGER DEFAULT 0)");
            for (String w : waifus) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT OR IGNORE INTO waifu_votes (nama, jumlah_vote) VALUES (?, 0)")) {
                    ps.setString(1, w);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<KarakterVote> ambilSemuaData() {
        List<KarakterVote> list = new ArrayList<>();
        String sql = "SELECT nama, jumlah_vote FROM waifu_votes ORDER BY jumlah_vote DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KarakterVote(rs.getString("nama"), rs.getInt("jumlah_vote")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void tambahSuara(String nama) {
        String sql = "UPDATE waifu_votes SET jumlah_vote = jumlah_vote + 1 WHERE nama = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int ambilTotalSuara() {
        int total = 0;
        String sql = "SELECT SUM(jumlah_vote) AS total FROM waifu_votes";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void resetSemuaData() {
        String sql = "UPDATE waifu_votes SET jumlah_vote = 0";
        try (Connection conn = DatabaseConfig.getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}