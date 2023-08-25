package main;

/**
 * @author Taha Kocer
 */

/* eğer seçilen adet kadar randevu açılmadıysa, randevuyu alana kadar tekrar kontrol et. (cuma günü için)
 * 
 * bunu opsiyonel hale getir.
 * 
 * form doldurma işlemi el ile müdahele edildiği zaman çalışmıyor.
 * */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import component.AppPage;
import component.BasePage;
import component.LoginPage;
import tests.AppTest;
import tests.FormTest;
import tests.LoginTest;
import utilities.BrowserFactory;
import utilities.*;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame {
	private JPanel contentPane;
	private JComboBox comboBox_Adet;
	private JComboBox comboBox;
	private final JLabel lblDay;
	private final JCheckBox chckboxNextMonth;
	private final JButton btnStart;
	private final JButton btnStop;
	private final JCheckBox chckboxGetApp;
	private final JCheckBox chckboxGetForm;
	private final JButton btnFillApp;
	private LoginTest loginTest;
	private AppTest appTest;
	private FormTest formTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		BrowserFactory browserFactory = new BrowserFactory();
		// Günleri tutacak dizi
		Integer[] adet = new Integer[20];
		for (int i = 0; i < 20; i++) {
			adet[i] = i + 1;
		}
		Integer[] days = new Integer[31];
		for (int i = 0; i < 31; i++) {
			days[i] = i + 1;
		}

		// --------------------------icon--------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1017, 680);
		setTitle(" Auto-Visa ");

		ClassLoader classLoader = getClass().getClassLoader();
		ImageIcon icon = new ImageIcon(classLoader.getResource("icon.png"));
		setIconImage(icon.getImage());

		// ---------------------------------------MENU------------------------------------
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnTools = new JMenu("Araçlar");
		menuBar.add(mnTools);

		JMenuItem itemSetPriority = new JMenuItem("Chrome'a öncelik ver (Set Priority)");
		itemSetPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowserFactory.setPriority();
			}
		});
		mnTools.add(itemSetPriority);

		JMenuItem itemTaskKill = new JMenuItem("Tüm driverları durdur (TaskKill)");
		itemTaskKill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowserFactory.taskKill();

			}
		});
		mnTools.add(itemTaskKill);

		JMenuItem itemExit = new JMenuItem("Çıkış");
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnTools.add(itemExit);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setResizable(false);
		// ------------------------------ANA PANEL-----------------------------
		JPanel panel = new JPanel();
		panel.setBounds(0, 90, 420, 530);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Panel"));
		contentPane.add(panel);
		panel.setLayout(null);

		// --------------TEXT AREA SCROLL PANE CONSOLE EKRANI-------------------
		JPanel panel_textarea = new JPanel();
		panel_textarea.setBounds(419, 90, 582, 530);
		panel_textarea.setLayout(null);
		panel_textarea.setBorder(new TitledBorder(new EtchedBorder(), "Console Ekranı"));
		contentPane.add(panel_textarea);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_textarea.setLayout(null);
		panel_textarea.add(scrollPane);
		scrollPane.setBounds(10, 20, 562, 499);

		RedirectedConsole console = new RedirectedConsole(textArea);
		Thread consoleThread = new Thread(() -> {
			console.redirectSystemOutAndErr();
		});
		consoleThread.start();

		// Otomatik kaydırma için caret pozisyonunu en sona ayarlayın
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// ----------------------BROWSERI BAŞLAT BUTONU-------------------------
		JButton btnStartBrowser = new JButton("BROWSER'I\r\n BAŞLAT");
		btnStartBrowser.setBounds(104, 73, 203, 50);
		panel.add(btnStartBrowser);

		comboBox_Adet = new JComboBox(adet);
		comboBox_Adet.setBounds(207, 22, 100, 40);
		panel.add(comboBox_Adet);
		comboBox_Adet.setIgnoreRepaint(true);
		comboBox_Adet.setForeground(new Color(0, 0, 0));

		// ------------------------ADET LABELI---------------------------
		JLabel lblAdet = new JLabel("Adet : ");
		lblAdet.setBounds(107, 22, 90, 40);
		panel.add(lblAdet);
		lblAdet.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdet.setFont(new Font("Tahoma", Font.BOLD, 18));
		// ---------------------------COMBOBOXLAR----------------------------
		comboBox = new JComboBox(days);
		comboBox.setBounds(207, 157, 100, 40);
		panel.add(comboBox);
		comboBox.setEnabled(false);

		// ----------------------------GÜN LABELİ---------------------------
		lblDay = new JLabel("Gün : ");
		lblDay.setBounds(104, 157, 90, 40);
		panel.add(lblDay);
		lblDay.setEnabled(false);
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setFont(new Font("Tahoma", Font.BOLD, 18));

		// ------------------------BİR SONRAKİ AY CHECKBOXI------------------------
		chckboxNextMonth = new JCheckBox("Bir Sonraki Ay");
		chckboxNextMonth.setBounds(204, 212, 103, 23);
		panel.add(chckboxNextMonth);
		chckboxNextMonth.setEnabled(false);
		chckboxNextMonth.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckboxNextMonth.setBackground(new Color(230, 233, 235));
		chckboxNextMonth.setSelected(false);

		// ---------------------------OTOMATİK AL CHECKBOX-------------------------
		chckboxGetApp = new JCheckBox("Randevuyu Otomatik Al");
		chckboxGetApp.setBounds(107, 252, 200, 23);
		panel.add(chckboxGetApp);
		chckboxGetApp.setEnabled(false);
		chckboxGetApp.setBackground(new Color(230, 233, 235));
		chckboxGetApp.setSelected(false);
		chckboxGetApp.setFont(new Font("Tahoma", Font.PLAIN, 10));

		// ------------------------FORMU OTO DOLDUR CHECKBOX-----------------------

		chckboxGetForm = new JCheckBox("Formu Otomatik Doldur");
		chckboxGetForm.setBounds(107, 278, 200, 23);
		panel.add(chckboxGetForm);
		chckboxGetForm.setEnabled(false);
		chckboxGetForm.setBackground(new Color(230, 233, 235));
		chckboxGetForm.setSelected(false);
		chckboxGetForm.setFont(new Font("Tahoma", Font.PLAIN, 10));

		// ---------------------------DURDUR BUTONU----------------------------
		btnStop = new JButton("DURDUR");
		btnStop.setBounds(104, 315, 90, 50);
		panel.add(btnStop);
		btnStop.setEnabled(false);

		// -------------------------BAŞLAT BUTONU-------------------------------
		btnStart = new JButton("BAŞLAT");
		btnStart.setBounds(217, 315, 90, 50);
		panel.add(btnStart);
		btnStart.setEnabled(false);

		// -----------------------FORMU DOLDUR BUTONU----------------------------
		btnFillApp = new JButton("Formu Doldur");
		btnFillApp.setBounds(146, 376, 120, 50);
		panel.add(btnFillApp);
		btnFillApp.setEnabled(false);

		// --------------------BİLGİLENDİRME LABELİ-------------------------
		final JLabel lblInfo = new JLabel(
				"<html> Browser'ı debugging mod'ta başlatmak için <b>\"Browser'ı Başlat\"</b> butonuna tıklayın.<br>(Standart kullandığınız browser'da çalışmaz.) </html>");
		lblInfo.setBounds(39, 448, 352, 60);
		panel.add(lblInfo);
		lblInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setForeground(new Color(204, 0, 0));
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		// --------------------------------BAŞLIK -----------------------------
		JLabel lblOtomatikVizeRandevu = new JLabel("Otomatik Vize Randevu Botu");
		lblOtomatikVizeRandevu.setBounds(0, 0, 1001, 90);
		contentPane.add(lblOtomatikVizeRandevu);
		lblOtomatikVizeRandevu.setOpaque(true);
		lblOtomatikVizeRandevu.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOtomatikVizeRandevu.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtomatikVizeRandevu.setForeground(Color.WHITE);
		lblOtomatikVizeRandevu.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblOtomatikVizeRandevu.setBackground(new Color(14, 16, 120));
		lblOtomatikVizeRandevu.setAlignmentX(0.5f);

		// -----------------------------ACTION LISTENERLAR-------------------------
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BasePage.day = comboBox.getSelectedItem().toString();
				String count = comboBox_Adet.getSelectedItem().toString();
				BasePage.count = Integer.valueOf(count);
				BasePage.running = true;
				System.out.println("BasePage.day = " + BasePage.day);
				System.out.println("BasPage.count = " + BasePage.count);
				System.out.println("BasePage.running = " + BasePage.running);
				appTest = new AppTest();
				appTest.setUp();
				appTest.test01();

			}
		});
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasePage.running = false;
				appTest.tearDown();
				System.out.println("Bot durduruldu.");
				lblInfo.setText("Durduruldu.");
			}
		});
		chckboxNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppPage.next = chckboxNextMonth.isSelected();
				System.out.println("nextMonth = " + AppPage.next);
			}
		});

		chckboxGetApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppPage.autoGet = chckboxGetApp.isSelected();
				System.out.println("autoGet = " + AppPage.autoGet);
			}
		});

		chckboxGetForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppPage.autoFill = chckboxGetForm.isSelected();
				System.out.println("autoFill : " + AppPage.autoFill);
			}
		});

		btnStartBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setClickable(true);
				BrowserFactory.runBrowser(BasePage.profile);
				lblInfo.setText("<html>Çift Faktörlü Doğrulamayı geçiniz.</html>");
				BasePage.count = Integer.parseInt(comboBox_Adet.getSelectedItem().toString());
				System.out.println("BasePage.count = " + BasePage.count);
				loginTest = new LoginTest();
				loginTest.setUp();
				loginTest.test01();
				loginTest.tearDown();

			}
		});

		btnFillApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String count = comboBox_Adet.getSelectedItem().toString();
				AppPage.count = Integer.valueOf(count);
				formTest = new FormTest();
				formTest.setUp();
				formTest.test();
				formTest.tearDown();
				
			}
		});

	}

	private void setClickable(boolean a) {
		comboBox.setEnabled(a);
		chckboxNextMonth.setEnabled(a);
		chckboxGetApp.setEnabled(a);
		chckboxGetForm.setEnabled(a);
		btnStart.setEnabled(a);
		btnStop.setEnabled(a);
		btnFillApp.setEnabled(a);
		lblDay.setEnabled(a);

	}
}
