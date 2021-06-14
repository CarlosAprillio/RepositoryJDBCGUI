import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class Mobil {

	private JFrame frmMobil;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtPabrik;
	private JTextField txtPlat;
	private JTextField txtTipe;
	private JTextField txtHarga;
	private JTextField txtCari;
	private JTextField txtUnit;
	private static JTable table;
	private JTextField txtStok;
	private JTextArea textArea;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mobil window = new Mobil();
					window.frmMobil.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 *  FUNGSI UNTUK MENAMPILKAN DATA DARI DATABASE KE JTABLE
	 */
	public static void tampilkanData() {
		
		try {
			String query = "select * from stokmobil";
			Connection con = (Connection)Connector.connector();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery(query);
			
			DefaultTableModel model = (DefaultTableModel)table.getModel();
	        model.setRowCount(0);
	        
	        String [] data = new String [7];
	        
	        while(rs.next()) {
	        	 data[0] = rs.getString("idmobil");
		         data[1] = rs.getString("nama");
		         data[2] = rs.getString("pabrikan");
		         data[3] = rs.getString("plat");
		         data[4] = rs.getString("tipe");
		         data[5] = rs.getString("harga");
		         data[6] = rs.getString("stok");
		       
	           
	            model.addRow(data); 
	        }
	        
	        if (con != null) {
	        	try {
	        		con.close();
				} catch (SQLException e) {}
	        }
	        
	        if (pst != null) {
	        	try {
	        		pst.close();
				} catch (SQLException e) {}
	        }
	        
	        if (rs != null) {
	        	try {
	        		rs.close();
				} catch (SQLException e) {}
	        }
	        
	        
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Gagal Insert Data\n"+e);
		} 
	}
	

	/*
	 *  KODINGAN UNTUK FUNGSI SEARCH DATA FORM DATABASE
	 */
	public class searchFunction{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		public ResultSet find(String s){
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/mobil", "root", "");
				ps = con.prepareStatement("select * from stokmobil where idmobil= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}
	
	/*
	 *  FUNGSI UNTUK MENGOSONGKAN FORM
	 */
	private void kosongkanForm() {
		txtId.setText("");
		txtNama.setText("");
		txtPabrik.setText("");
		txtPlat.setText("");
		txtTipe.setText("");
		txtHarga.setText("");
		txtStok.setText("");
	}

	/**
	 * Create the application.
	 */
	public Mobil() {
		initialize();
		tampilkanData();
		kosongkanForm();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmMobil = new JFrame();
		frmMobil.setResizable(false);
		frmMobil.setIconImage(Toolkit.getDefaultToolkit().getImage(Mobil.class.getResource("/image/logo.png")));
		frmMobil.setTitle("MOBIL");
		frmMobil.setBounds(205, 78, 960, 640);
		frmMobil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMobil.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setOpaque(false);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 401, 924, 189);
		frmMobil.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblStokMobil = new JLabel("Tabel Stok Mobil");
		lblStokMobil.setHorizontalAlignment(SwingConstants.CENTER);
		lblStokMobil.setForeground(Color.WHITE);
		lblStokMobil.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblStokMobil.setBounds(780, 11, 134, 23);
		panel.add(lblStokMobil);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 904, 142);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Mobil", "Nama Mobil", "Pabrikan", "Plat Mobil", "Tipe Mobil", "Harga Mobil", " Stok Mobil"
			}
		) {
			
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, Long.class, int.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setGridColor(Color.BLACK);
		table.setFont(new Font("Cambria", Font.BOLD, 12));
		table.setForeground(Color.BLACK);
		
		/*
		 *   FUNGSI UNTUK KETIKA KLIK BARIS TABEL AKAN MENGAMBIL DATA DIBARIS TERSEBUT
		 *   KETIKA DIKLIK MAKA TEXTFIELD ID MOBIL TIDAK BISA DIUBAH, SISANYA DAPAN DIUBAH
		 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				boolean set = false;
				txtId.setEditable(set);
				
				int row = table.rowAtPoint(e.getPoint());
				
				String id = table.getValueAt(row, 0).toString();
				String nama = table.getValueAt(row, 1).toString();
				String pabrik = table.getValueAt(row, 2).toString();
				String plat = table.getValueAt(row, 3).toString();
				String tipe = table.getValueAt(row, 4).toString();
				String harga = table.getValueAt(row, 5).toString();
				String stok = table.getValueAt(row, 6).toString();
				
				txtId.setText(String.valueOf(id));
				txtNama.setText(String.valueOf(nama));
				txtPabrik.setText(String.valueOf(pabrik));
				txtPlat.setText(String.valueOf(plat));
				txtTipe.setText(String.valueOf(tipe));
				txtHarga.setText(String.valueOf(harga));
				txtStok.setText(String.valueOf(stok));
			}
		});
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setOpaque(false);
		panel_1.setBackground(new Color(128, 0, 0));
		panel_1.setBounds(10, 11, 448, 379);
		frmMobil.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblDataBaru = new JLabel("Data Mobil Baru");
		lblDataBaru.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataBaru.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblDataBaru.setForeground(new Color(255, 255, 255));
		lblDataBaru.setBounds(124, 0, 187, 32);
		panel_1.add(lblDataBaru);
		
		JLabel lblNewLabel = new JLabel("ID Mobil");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(64, 68, 99, 23);
		panel_1.add(lblNewLabel);
		
		JLabel lblNamaMobil = new JLabel("Nama Mobil");
		lblNamaMobil.setForeground(Color.WHITE);
		lblNamaMobil.setFont(new Font("Cambria", Font.BOLD, 15));
		lblNamaMobil.setBounds(64, 102, 99, 23);
		panel_1.add(lblNamaMobil);
		
		JLabel lblPabrikan = new JLabel("Pabrikan");
		lblPabrikan.setForeground(Color.WHITE);
		lblPabrikan.setFont(new Font("Cambria", Font.BOLD, 15));
		lblPabrikan.setBounds(64, 136, 99, 23);
		panel_1.add(lblPabrikan);
		
		JLabel lblPlat = new JLabel("Plat");
		lblPlat.setForeground(Color.WHITE);
		lblPlat.setFont(new Font("Cambria", Font.BOLD, 15));
		lblPlat.setBounds(64, 170, 99, 23);
		panel_1.add(lblPlat);
		
		JLabel lblTipe = new JLabel("Tipe");
		lblTipe.setForeground(Color.WHITE);
		lblTipe.setFont(new Font("Cambria", Font.BOLD, 15));
		lblTipe.setBounds(64, 204, 99, 23);
		panel_1.add(lblTipe);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setForeground(Color.WHITE);
		lblHarga.setFont(new Font("Cambria", Font.BOLD, 15));
		lblHarga.setBounds(64, 238, 99, 23);
		panel_1.add(lblHarga);
		
		txtId = new JTextField();
		txtId.setToolTipText("Wajib isi");
		txtId.setBounds(212, 68, 176, 23);
		txtId.setFont(new Font("Cambria", Font.BOLD, 14));
		panel_1.add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setToolTipText("Wajib isi");
		txtNama.setColumns(10);
		txtNama.setFont(new Font("Cambria", Font.BOLD, 14));
		txtNama.setBounds(212, 102, 176, 23);
		panel_1.add(txtNama);
		
		txtPabrik = new JTextField();
		txtPabrik.setToolTipText("Wajib Isi");
		txtPabrik.setColumns(10);
		txtPabrik.setFont(new Font("Cambria", Font.BOLD, 14));
		txtPabrik.setBounds(212, 136, 176, 23);
		panel_1.add(txtPabrik);
		
		txtPlat = new JTextField();
		txtPlat.setToolTipText("Wajib isi");
		txtPlat.setColumns(10);
		txtPlat.setFont(new Font("Cambria", Font.BOLD, 14));
		txtPlat.setBounds(212, 170, 176, 23);
		panel_1.add(txtPlat);
		
		txtTipe = new JTextField();
		txtTipe.setToolTipText("Wajib isi");
		txtTipe.setColumns(10);
		txtTipe.setFont(new Font("Cambria", Font.BOLD, 14));
		txtTipe.setBounds(212, 204, 176, 23);
		panel_1.add(txtTipe);
		
		txtHarga = new JTextField();
		txtHarga.setToolTipText("Wajib isi");
		
		/*
		 *  FUNGSI AGAR HANYA DAPAT MENGISIKAN ANGKA PADA TEXTFIELD HARGA
		 */
		txtHarga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if(!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))){
				    
				    e.consume();
				}
			}	
		});
		txtHarga.setFont(new Font("Cambria", Font.BOLD, 14));
		txtHarga.setColumns(10);
		txtHarga.setBounds(212, 238, 176, 23);
		panel_1.add(txtHarga);
		
		JPanel panelSimpan = new JPanel();
		panelSimpan.setBounds(37, 336, 106, 32);
		panel_1.add(panelSimpan);
		panelSimpan.setLayout(null);
		
		JLabel simpan = new JLabel("SIMPAN");
		simpan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				simpan.setForeground(new Color(255,255,255));
				panelSimpan.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				simpan.setForeground(new Color(0, 0, 0));
				panelSimpan.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				simpan.setForeground(new Color(255,255,255));
				panelSimpan.setBackground(new Color(0,0,0));
			}
			
			/*
			 *  FUNGSI TOMBOL SIMPAN, UNTUK MENYIMPAN DATA KE DATABASE
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String sql = "insert into stokmobil values('"+txtId.getText()+"','"+txtNama.getText()+"','"+txtPabrik.getText()+"','"+txtPlat.getText()+"','"+txtTipe.getText()+"','"+Long.parseLong(txtHarga.getText())+"','"+Integer.parseInt(txtStok.getText())+"')";
					Connection con = (Connection)Connector.connector();
					PreparedStatement pst = con.prepareStatement(sql);
					if (txtId.getText().equals("")) {
						JOptionPane.showMessageDialog(simpan, "ID Mobil tidak boleh kosong!");
					} else if (txtNama.getText().equals("")) {
						JOptionPane.showMessageDialog(simpan, "Nama Mobil tidak boleh kosong!");
					} else if (txtPabrik.getText().equals("")) {
						JOptionPane.showMessageDialog(simpan, "Pabrikan tidak boleh kosong!");
					} else if (txtPlat.getText().equals("")) {
						JOptionPane.showMessageDialog(simpan, "Plat tidak boleh kosong!");
					} else if (txtTipe.getText().equals("")) {
						JOptionPane.showMessageDialog(simpan, "Tipe Mobil tidak boleh kosong!");
					} else if (Long.parseLong(txtHarga.getText()) <= 500000000) {
						JOptionPane.showMessageDialog(simpan, "Harga harus 500 juta ke atas!");
					} else if (txtHarga.getText().equals("") ) {
						JOptionPane.showMessageDialog(simpan, "Harga mobil tidak boleh kosong!");
					} else if (txtStok.getText().equals("") ) {
						JOptionPane.showMessageDialog(simpan, "Jumlah unit tidak boleh kosong!");
					} else {
						pst.execute();
						JOptionPane.showMessageDialog(simpan, "Data Mobil Tersimpan");
						tampilkanData();
						kosongkanForm();
						txtId.setEditable(true);
					} 
					
					 if (con != null) {
				        	try {
				        		con.close();
							} catch (SQLException e4) {}
				        }
					 
					 if (pst != null) {
				        	try {
				        		pst.close();
							} catch (SQLException e5) {}
				        }
				        
				        
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(simpan, "Gagal simpan data", "KESALAHAN INPUT!!", 0);
					System.out.println(e1);
				}
				
			}
		});
		simpan.setBounds(0, 0, 106, 32);
		panelSimpan.add(simpan);
		simpan.setFont(new Font("Stencil", Font.PLAIN, 16));
		simpan.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelEdit = new JPanel();
		panelEdit.setLayout(null);
		panelEdit.setBounds(173, 336, 106, 32);
		panel_1.add(panelEdit);
		
		JLabel edit = new JLabel("EDIT");
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				edit.setForeground(new Color(255,255,255));
				panelEdit.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				edit.setForeground(new Color(0, 0, 0));
				panelEdit.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				edit.setForeground(new Color(255,255,255));
				panelEdit.setBackground(new Color(0,0,0));
			}
			
			/*
			 *  FUNGSI TOMBOL EDIT, UNTUK MENGUBAH DATA MOBIL DENGAN PARAMETER ID MOBIL
			 *  
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int confirm = JOptionPane.showConfirmDialog(edit, "Yakin ingin mengubah data mobil?", "Confirm", JOptionPane.YES_NO_OPTION);
				try {
					String query = "update stokmobil set nama='"+txtNama.getText()+"',pabrikan='"+txtPabrik.getText()+"',plat='"+txtPlat.getText()+"',tipe='"+txtTipe.getText()+"',harga='"+Long.parseLong(txtHarga.getText())+"',stok='"+Integer.parseInt(txtStok.getText())+"' where idmobil='"+txtId.getText()+"'";
					Connection conn = (Connection)Connector.connector();
					PreparedStatement st = conn.prepareStatement(query);
					if (txtId.getText().equals("")) {
						JOptionPane.showMessageDialog(edit, "ID Mobil tidak boleh kosong!");
					} else if (txtNama.getText().equals("")) {
						JOptionPane.showMessageDialog(edit, "Nama Mobil tidak boleh kosong!");
					} else if (txtPabrik.getText().equals("")) {
						JOptionPane.showMessageDialog(edit, "Pabrikan tidak boleh kosong!");
					} else if (txtPlat.getText().equals("")) {
						JOptionPane.showMessageDialog(edit, "Plat tidak boleh kosong!");
					} else if (txtTipe.getText().equals("")) {
						JOptionPane.showMessageDialog(edit, "Tipe Mobil tidak boleh kosong!");
					} else if (Long.parseLong(txtHarga.getText()) <= 500000000 ) {
						JOptionPane.showMessageDialog(edit, "Harga harus 500 juta ke atas!");
					} else if (txtHarga.getText().equals("") ) {
						JOptionPane.showMessageDialog(edit, "Harga mobil tidak boleh kosong!");
					} else if (txtStok.getText().equals("") ) {
						JOptionPane.showMessageDialog(edit, "Jumlah unit tidak boleh kosong!");
					} else if (confirm==0) {
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(edit, "Update data mobil berhasil");
                        tampilkanData();
                        kosongkanForm();
                        txtId.setEditable(true);
					}
					
					if (conn != null) {
			        	try {
			        		conn.close();
						} catch (SQLException e4) {}
			        }
				 
				 if (st != null) {
			        	try {
			        		st.close();
						} catch (SQLException e5) {}
			        }
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(edit, "Gagal edit data");
					System.out.println(e2);
				} finally {
					
				}
			}
		});
		edit.setHorizontalAlignment(SwingConstants.CENTER);
		edit.setFont(new Font("Stencil", Font.PLAIN, 16));
		edit.setBounds(0, 0, 106, 32);
		panelEdit.add(edit);
		
		JPanel panelHapus = new JPanel();
		panelHapus.setLayout(null);
		panelHapus.setBounds(311, 336, 106, 32);
		panel_1.add(panelHapus);
		
		JLabel hapus = new JLabel("HAPUS");
		hapus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hapus.setForeground(new Color(255,255,255));
				panelHapus.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hapus.setForeground(new Color(0, 0, 0));
				panelHapus.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				hapus.setForeground(new Color(255,255,255));
				panelHapus.setBackground(new Color(0,0,0));
			}
			
			/*
			 *  FUNGSI TOMBOL HAPUS, UNTUK MENGHAPUS DATA DARI DATABASE DAN TABEL
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int confirm = JOptionPane.showConfirmDialog(hapus, "Apakah anda yakin ingin menghapus data?", "Konfirmasi dulu ya...", JOptionPane.YES_NO_OPTION);
				
				try {
					String query = "delete from stokmobil where idmobil='"+txtId.getText()+"'";
					Connection conn = (Connection)Connector.connector();
					PreparedStatement st = conn.prepareStatement(query);
					
					if (confirm==0) {
						st.executeUpdate();
						JOptionPane.showMessageDialog(hapus, "Berhasil menghapus data...");
						tampilkanData();
						kosongkanForm();
						txtId.setEditable(true);
					}
					
					if (conn != null) {
			        	try {
			        		conn.close();
						} catch (SQLException e4) {}
			        }
				 
				    if (st != null) {
			        	try {
			        		st.close();
						} catch (SQLException e5) {}
			        }
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(edit, "Gagal Hapus");
					System.out.println(e2);
				}
			}
		});
		hapus.setHorizontalAlignment(SwingConstants.CENTER);
		hapus.setFont(new Font("Stencil", Font.PLAIN, 16));
		hapus.setBounds(0, 0, 106, 32);
		panelHapus.add(hapus);
		
		JLabel lblJumlahUnit = new JLabel("Jumlah Unit");
		lblJumlahUnit.setForeground(Color.WHITE);
		lblJumlahUnit.setFont(new Font("Cambria", Font.BOLD, 15));
		lblJumlahUnit.setBounds(64, 272, 99, 23);
		panel_1.add(lblJumlahUnit);
		
		txtStok = new JTextField();
		txtStok.setToolTipText("Wajib isi");
		txtStok.setFont(new Font("Cambria", Font.BOLD, 14));
		
		/*
		 *  FUNGSI AGAR TEXTFIELD STOK HANYA BISA DIISI DENGAN ANGKA
		 */
		txtStok.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
				    e.consume();
				}
			}	
		});
		txtStok.setText("");
		txtStok.setColumns(10);
		txtStok.setBounds(212, 272, 176, 23);
		panel_1.add(txtStok);
		
		JLabel myLogo = new JLabel("");
		myLogo.setIcon(new ImageIcon(Mobil.class.getResource("/image/mylogos.png")));
		myLogo.setHorizontalAlignment(SwingConstants.CENTER);
		myLogo.setBounds(0, 0, 87, 56);
		panel_1.add(myLogo);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setOpaque(false);
		panel_2.setBackground(new Color(0, 0, 255));
		panel_2.setBounds(468, 11, 466, 379);
		frmMobil.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblPenjualanMobil = new JLabel("Penjualan Mobil");
		lblPenjualanMobil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPenjualanMobil.setForeground(Color.WHITE);
		lblPenjualanMobil.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblPenjualanMobil.setBounds(159, 0, 165, 32);
		panel_2.add(lblPenjualanMobil);
		
		JLabel lblNewLabel_1 = new JLabel("Masukan ID Mobil ");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 15));
		lblNewLabel_1.setBounds(48, 62, 133, 23);
		panel_2.add(lblNewLabel_1);
		
		txtCari = new JTextField();
		txtCari.setToolTipText("Wajib isi");
		txtCari.setFont(new Font("Cambria", Font.BOLD, 14));
		txtCari.setColumns(10);
		txtCari.setBounds(234, 64, 179, 23);
		panel_2.add(txtCari);
		
		JPanel panelCari = new JPanel();
		panelCari.setBounds(319, 131, 94, 23);
		panel_2.add(panelCari);
		panelCari.setLayout(null);
		
		JLabel lblCari = new JLabel("Checkout");
		lblCari.setBounds(0, 0, 94, 27);
		panelCari.add(lblCari);
		lblCari.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCari.setForeground(new Color(255,255,255));
				panelCari.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCari.setForeground(new Color(0, 0, 0));
				panelCari.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblCari.setForeground(new Color(255,255,255));
				panelCari.setBackground(new Color(0,0,0));
			}
			
			/*
			 *  KODINGAN UNTUK FUNGSI TOMBOL CHECKOUT KETIKA DIKLIK AKAN MEMUNCULKAN DATA DABN MENGHITUNG JUMLAH BAYAR
			 *  JIKA MEMENUHI KONDISI YANG SUDAH DITENTUKAN...
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtCari.getText());
				
				String [] jual = new String[7];
				
				try {
					if (rs.next()) {
						jual[0] = ("-> ID Mobil\t\t: "+rs.getString("idmobil"));
						jual[1] = ("\n-> Nama Mobil\t\t: "+rs.getString("nama"));
						jual[2] = ("\n-> Pabrikan\t\t: "+rs.getString("pabrikan"));
						jual[3] = ("\n-> Plat Mobil\t\t: "+rs.getString("plat"));
						jual[4] = ("\n-> Tipe Mobil\t\t: "+rs.getString("tipe"));
				        jual[5] = ("\n-> Harga per unit\t: Rp"+rs.getString("harga"));
				        jual[6] = ("\n-> Stok unit\t\t: "+rs.getString("stok")+" unit");
				       
				        long totalBayar = Long.valueOf(txtUnit.getText()) * rs.getLong("harga");
				        
				        if (txtCari.getText().equals("")) {
				        	JOptionPane.showMessageDialog(lblCari, "ID tidak boleh kosong");
				        } else if (txtUnit.getText().equals("")) {
				        	JOptionPane.showMessageDialog(lblCari, "Jumlah unit tidak boleh kosong");
				        } else if (Integer.valueOf(rs.getString("stok")) < Integer.valueOf(txtUnit.getText())) {
				        	JOptionPane.showMessageDialog(lblCari, "Stok unit mobil tidak cukup");
				        } else {
				        	textArea.setText(jual[0]+jual[1]+jual[2]+jual[3]+jual[4]+jual[5]+jual[6]+"\n===========================================\n"+"Jumlah unit yang dibeli\t: "+txtUnit.getText()+" unit\nTotal Biaya\t\t: Rp."+String.valueOf(totalBayar)+"\n---------------------------------------------------------------------");
				        }
				    } 
        		    	if (rs != null) {
        		    		try {
        	        		rs.close();
        		    		} catch (SQLException e5) {}
        	        }		
			    } catch (Exception e1) {
			    	
			    }
			}
		});
		lblCari.setHorizontalAlignment(SwingConstants.CENTER);
		lblCari.setFont(new Font("Stencil", Font.PLAIN, 15));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(48, 165, 365, 160);
		panel_2.add(scrollPane_1);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scrollPane_1.setViewportView(textArea);
		textArea.setEditable(false);
		
		JLabel lblNewLabel_1_1 = new JLabel("Jumlah Unit yang dibeli");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(48, 96, 165, 23);
		panel_2.add(lblNewLabel_1_1);
		
		txtUnit = new JTextField();
		txtUnit.setToolTipText("Wajib isi");
		
		/*
		 *  FUNGSI AGAR TEXTFIELD UNIT HANYA BISA DIISI DENGAN ANGKA
		 */
		txtUnit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
				    e.consume();
				}
			}	
		});
		txtUnit.setBounds(234, 97, 179, 23);
		txtUnit.setFont(new Font("Cambria", Font.BOLD, 14));
		panel_2.add(txtUnit);
		txtUnit.setColumns(10);
		
		JPanel panelKonfir = new JPanel();
		panelKonfir.setLayout(null);
		panelKonfir.setBounds(145, 336, 179, 32);
		panel_2.add(panelKonfir);
		
		JLabel konfirmasi = new JLabel("KONFIRMASI PEMBELIAN");
		konfirmasi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					konfirmasi.setForeground(new Color(255,255,255));
					panelKonfir.setBackground(new Color(0,0,0));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					konfirmasi.setForeground(new Color(0, 0, 0));
					panelKonfir.setBackground(new Color(255,255,255));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					konfirmasi.setForeground(new Color(255,255,255));
					panelKonfir.setBackground(new Color(0,0,0));
				}
				
				/*
				 *  KODINGAN UNTUK FUNGSI TOMBOL KONFIRMASI BAYAR DAN AKAN MENG UPDATE DATA KETIKA TELAH MELAKUKAN PEMBELIAN
				 *  
				 */
			@Override
			public void mouseClicked(MouseEvent e) {
				searchFunction search = new searchFunction();
				ResultSet rs = null;
				rs = search.find(txtCari.getText());
				
				String [] confirm = new String[7];
				try {
					if (rs.next()) {
						confirm[0] = rs.getString("idmobil");
						confirm[1] = rs.getString("nama");
						confirm[2] = rs.getString("pabrikan");
						confirm[3] = rs.getString("plat");
						confirm[4] = rs.getString("tipe");
						confirm[5] = rs.getString("harga");
						confirm[6] = rs.getString("stok");
						
						int sisa = Integer.valueOf(rs.getString("stok")) - Integer.valueOf(txtUnit.getText());
						
						if (sisa > 0) {
							int con = JOptionPane.showConfirmDialog(textArea, "Lanjutkan pembelian", "Confirm", JOptionPane.YES_NO_OPTION);
							
							if (con == 0) {
								JOptionPane.showMessageDialog(textArea, "Pembelian Berhasil");
								try {
									String query = "update stokmobil set stok='"+(rs.getInt("stok") - Integer.valueOf(txtUnit.getText()))+"' where idmobil='"+txtCari.getText()+"'";
									Connection conn = (Connection)Connector.connector();
									PreparedStatement st = conn.prepareStatement(query);
				                    st.executeUpdate();
				                    tampilkanData();
				                    
				                    String msg = "<html>Penjualan Berhasil...."+"<br>"+" Stok Mobil "+confirm[1]+" <br>"+"Tersisa "+sisa+" unit.";
				                    JOptionPane optionpane = new JOptionPane();
				                    optionpane.setMessage(msg);
				                    optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				                    JDialog dialog = optionpane.createDialog(textArea, "Kamu telah melakukan penjualan");
				                    dialog.setVisible(true);
				                    
				                    textArea.setText("");
				                    txtCari.setText("");
				                    txtUnit.setText("");
				                    
				                    if (conn != null) {
				        	        	try {
				        	        		conn.close();
				        				} catch (SQLException e4) {}
				        	        }
				        		 
				        		    if (st != null) {
				        	        	try {
				        	        		st.close();
				        				} catch (SQLException e5) {}
				        	        }
				        		    
				        		    if (rs != null) {
				        	        	try {
				        	        		rs.close();
				        				} catch (SQLException e5) {}
				        	        }
				                    
								} catch (Exception e2) {
									System.out.println(e2);
								}
							}
						} else if (sisa == 0) {
							int con = JOptionPane.showConfirmDialog(textArea, "Lanjutkan mpembelian", "Confirm", JOptionPane.YES_NO_OPTION);
							if (con == 0) {
								JOptionPane.showMessageDialog(textArea, "Pembelian Berhasil");
								try {
									String query = "delete from stokmobil where idmobil='"+txtCari.getText()+"'";
									Connection conn = (Connection)Connector.connector();
									PreparedStatement st = conn.prepareStatement(query);
									st.executeUpdate();
									tampilkanData();
									
									String msg = "<html>Penjualan Berhasil...."+"<br>"+" Stok Mobil "+confirm[1]+" <br>"+"Sudah habis....";
				                    JOptionPane optionpane = new JOptionPane();
				                    optionpane.setMessage(msg);
				                    optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				                    JDialog dialog = optionpane.createDialog(textArea, "Kamu telah melakukan penjualan");
				                    dialog.setVisible(true);
				                    
				                    textArea.setText("");
				                    txtCari.setText("");
				                    txtUnit.setText("");
				                    
				                    if (conn != null) {
				        	        	try {
				        	        		conn.close();
				        				} catch (SQLException e4) {}
				        	        }
				        		 
				        		    if (st != null) {
				        	        	try {
				        	        		st.close();
				        				} catch (SQLException e5) {}
				        	        }
				        		    
				        		    if (rs != null) {
				        	        	try {
				        	        		rs.close();
				        				} catch (SQLException e5) {}
				        	        }
								} catch (Exception e2) {
									System.out.println(e2);
								}
							}
						}
					}
				} catch (Exception e2) {
					System.out.println(e2);
				}
			} 
		});
		konfirmasi.setHorizontalAlignment(SwingConstants.CENTER);
		konfirmasi.setFont(new Font("Stencil", Font.PLAIN, 12));
		konfirmasi.setBounds(0, 0, 179, 32);
		panelKonfir.add(konfirmasi);
		
		JLabel lblNewLabel_2 = new JLabel("Deskripsi");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.ITALIC, 14));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(48, 144, 73, 14);
		panel_2.add(lblNewLabel_2);
		
		JPanel panelCari_1 = new JPanel();
		panelCari_1.setLayout(null);
		panelCari_1.setBounds(234, 131, 75, 23);
		panel_2.add(panelCari_1);
		
		JLabel clear = new JLabel("Clear");
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				clear.setForeground(new Color(255,255,255));
				panelCari_1.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				clear.setForeground(new Color(0, 0, 0));
				panelCari_1.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				clear.setForeground(new Color(255,255,255));
				panelCari_1.setBackground(new Color(0,0,0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("");
				txtCari.setText("");
				txtUnit.setText("");
			}
		});
		clear.setHorizontalAlignment(SwingConstants.CENTER);
		clear.setFont(new Font("Stencil", Font.PLAIN, 15));
		clear.setBounds(0, 0, 75, 27);
		panelCari_1.add(clear);
		
		JLabel mainBackground = new JLabel("");
		mainBackground.setIcon(new ImageIcon(Mobil.class.getResource("/image/background.png")));
		mainBackground.setHorizontalAlignment(SwingConstants.CENTER);
		mainBackground.setBounds(0, 0, 944, 601);
		frmMobil.getContentPane().add(mainBackground);
	}
}
