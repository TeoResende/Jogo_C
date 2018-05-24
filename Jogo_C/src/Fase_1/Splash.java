package Fase_1;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class Splash {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Splash window = new Splash();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Splash() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100,500, 300);
		frame.setLocationRelativeTo(null); //faz a janela ser renderizada no centro da tela
		frame.setResizable(false); //desabilita a opção de redimencionar a tela
	    frame.setUndecorated(true); //remove a borda
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ImageIcon icone = new ImageIcon("C:\\Users\\Teo\\Pictures\\parede_vermelha.jpg");
		icone.setImage(icone.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), 100));
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		ImageIcon icone2 = new ImageIcon("C:\\Users\\Teo\\Pictures\\logo.jpg");
		icone2.setImage(icone2.getImage().getScaledInstance(217,80, 100));
		lblLogo.setIcon(icone2);
		lblLogo.setBounds(12, 13, 217, 80);
		frame.getContentPane().add(lblLogo);
		
		JLabel lblProgramandoC = new JLabel("Programando C");
		lblProgramandoC.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblProgramandoC.setBounds(22, 106, 450, 90);
		frame.getContentPane().add(lblProgramandoC);
		
		JLabel lblRobo = new JLabel("");
		ImageIcon icone3 = new ImageIcon("C:\\Users\\Teo\\Pictures\\robo.jpg");
		icone3.setImage(icone3.getImage().getScaledInstance(159,97, 100));
		lblRobo.setIcon(icone3);
		lblRobo.setBounds(329, 190, 159, 97);
		frame.getContentPane().add(lblRobo);
		
		JLabel imagem = new JLabel();
		imagem.setIcon(icone);
		imagem.setBounds(0, 0, 500, 300);
		frame.getContentPane().add(imagem);
		
		new Thread(proximaJanela).start();	//inicia a tarefa paralela
	}

	Runnable proximaJanela = new Runnable() {	
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.dispose();
			//Menu.main(null);
		}
	};
}

