public class KarakterVote {
    private String nama;
    private int jumlahVote;

    public KarakterVote(String nama, int jumlahVote) {
        this.nama = nama;
        this.jumlahVote = jumlahVote;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlahVote() {
        return jumlahVote;
    }
}