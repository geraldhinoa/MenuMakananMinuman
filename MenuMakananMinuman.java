import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuMakananMinuman extends JFrame {
    private JComboBox<String> comboMakanan, comboMinuman;
    private JTextField txtHarga, txtJumlah;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTotal;
    private boolean pilihMakanan = true; // untuk cek pilihan saat update harga

    public MenuMakananMinuman() {
        setTitle("Aplikasi Menu Makanan & Minuman");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Atas
        JPanel panelAtas = new JPanel(new GridLayout(5, 2, 8, 8));
        panelAtas.setBackground(Color.WHITE);
        panelAtas.setBorder(BorderFactory.createTitledBorder("Form Pemesanan"));

        // ComboBox Makanan
        comboMakanan = new JComboBox<>(new String[]{
                "Nasi Goreng - 20000",
                "Mie Ayam - 15000",
                "Ayam Geprek - 18000"
        });
        comboMakanan.addActionListener(e -> {
            pilihMakanan = true;
            updateHarga();
        });

        // ComboBox Minuman
        comboMinuman = new JComboBox<>(new String[]{
                "Es Teh - 5000",
                "Es Jeruk - 6000",
                "Kopi - 8000"
        });
        comboMinuman.addActionListener(e -> {
            pilihMakanan = false;
            updateHarga();
        });

        txtHarga = new JTextField();
        txtHarga.setEditable(false);
        txtJumlah = new JTextField();

        JButton btnTambah = new JButton("Tambah ke Keranjang");
        btnTambah.setBackground(new Color(0, 153, 76));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFont(new Font("Arial", Font.BOLD, 12));
        btnTambah.addActionListener(this::tambahKeKeranjang);

        panelAtas.add(new JLabel("Pilih Makanan:"));
        panelAtas.add(comboMakanan);
        panelAtas.add(new JLabel("Pilih Minuman:"));
        panelAtas.add(comboMinuman);
        panelAtas.add(new JLabel("Harga Satuan:"));
        panelAtas.add(txtHarga);
        panelAtas.add(new JLabel("Jumlah:"));
        panelAtas.add(txtJumlah);
        panelAtas.add(new JLabel(""));
        panelAtas.add(btnTambah);

        // Tabel Keranjang
        model = new DefaultTableModel(new Object[]{"Menu", "Harga", "Jumlah", "Total"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(table);

        // Panel Bawah
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBackground(Color.WHITE);
        panelBawah.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel panelButton = new JPanel();
        panelButton.setBackground(Color.WHITE);

        JButton btnHapus = new JButton("Hapus Item");
        btnHapus.setBackground(new Color(204, 0, 0));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.addActionListener(e -> hapusItem());

        JButton btnSelesai = new JButton("Selesai");
        btnSelesai.setBackground(new Color(0, 102, 204));
        btnSelesai.setForeground(Color.WHITE);
        btnSelesai.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Pesanan selesai!\nTotal Bayar: Rp " + hitungTotal()));

        panelButton.add(btnHapus);
        panelButton.add(btnSelesai);

        lblTotal = new JLabel("Total: Rp 0", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(0, 102, 204));

        panelBawah.add(lblTotal, BorderLayout.NORTH);
        panelBawah.add(panelButton, BorderLayout.SOUTH);

        panel.add(panelAtas, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBawah, BorderLayout.SOUTH);

        add(panel);

        pilihMakanan = true;
        updateHarga();
    }

    private void updateHarga() {
        String menu;
        if (pilihMakanan) {
            menu = (String) comboMakanan.getSelectedItem();
        } else {
            menu = (String) comboMinuman.getSelectedItem();
        }
        if (menu != null) {
            String harga = menu.split("-")[1].trim();
            txtHarga.setText(harga);
        }
    }

    private void tambahKeKeranjang(ActionEvent e) {
        try {
            String menu;
            if (pilihMakanan) {
                menu = (String) comboMakanan.getSelectedItem();
            } else {
                menu = (String) comboMinuman.getSelectedItem();
            }

            int harga = Integer.parseInt(txtHarga.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText());
            int total = harga * jumlah;

            model.addRow(new Object[]{menu.split("-")[0].trim(), harga, jumlah, total});
            lblTotal.setText("Total: Rp " + hitungTotal());

            txtJumlah.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!");
        }
    }

    private void hapusItem() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            lblTotal.setText("Total: Rp " + hitungTotal());
        } else {
            JOptionPane.showMessageDialog(this, "Pilih item yang akan dihapus!");
        }
    }

    private int hitungTotal() {
        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (int) model.getValueAt(i, 3);
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuMakananMinuman().setVisible(true));
    }
}
