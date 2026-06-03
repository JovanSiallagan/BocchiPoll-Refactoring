import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

public class ReportExporter {

    public static void exportToTXT(String filePath, List<KarakterVote> dataKandidat, int totalSuara) throws IOException {
        try (PrintWriter pw = new PrintWriter(new File(filePath))) {
            pw.println("===============================================================");
            pw.println("            LAPORAN HASIL POLLING BOCCHI THE ROCK!             ");
            pw.println("===============================================================");
            pw.println();
            
            pw.printf("%-10s | %-20s | %-10s | %-10s%n", "PERINGKAT", "NAMA KARAKTER", "SUARA", "PERSENTASE");
            pw.println("---------------------------------------------------------------");

            int peringkat = 1;
            for (KarakterVote kv : dataKandidat) {
                double persentase = (totalSuara == 0) ? 0 : (kv.getJumlahVote() * 100.0) / totalSuara;
                String stringPersentase = String.format("%.1f%%", persentase);
                
                pw.printf("%-10d | %-20s | %-10d | %-10s%n", peringkat, kv.getNama(), kv.getJumlahVote(), stringPersentase);
                peringkat++;
            }
            
            pw.println("---------------------------------------------------------------");
            pw.println("Total Semua Suara Masuk : " + totalSuara);
            pw.println("===============================================================");
        }
    }
}