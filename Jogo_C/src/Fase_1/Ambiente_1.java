package Fase_1;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import Fase_1.Conexao;
import Fase_1.ControleBlocos;
import Fase_1.EstadosBlocos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.ImageIcon;

public class Ambiente_1 {
	String blocobloco;
	boolean movendo = false;
	ResultSet dados_programa = new Conexao().consultar();
	String   matrizResposta[][]       = new String[16][6];
	int direcao = 0;
	JLabel lblNewLabel = new JLabel("");
	
	ControleBlocos controleDosBlocos =  new ControleBlocos();
	ControleBlocos controleOpcoes    =  new ControleBlocos();
	
	private Thread tarefa;
	int comObjeto = 0;
	
//------------------------------Início classe Ambiente 1-------------------------
	private JFrame frame;
//	static private JLabel atual;

	
//	private List<ControleBlocos> listaControle;
//	private List<ControleBlocos> listaSelecionados;
//	private Map<JLabel, EstadosBlocos> referenciaLabels;
//	private ActionListener acaoBotoes;
	
	MouseAdapter mExited;
	MouseAdapter mRelease;
	MouseAdapter mMoved;
	MouseAdapter mEntered;
	MouseAdapter mClicked;
	MouseMotionListener mDragged;
	
	int pX = 0;
	int pY = 0;
	static String blocoAtual = null;
	
	/**
	 * Launch the application.
	 */
//------------------------------Início main() ----------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ambiente_1 window = new Ambiente_1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
//------------------------------ Fim main() -----------------------------

	/**
	 * Create the application.
	 */

	public Ambiente_1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		
//		listaControle     = new ArrayList<>();
//		listaSelecionados = new ArrayList<>();
//		referenciaLabels  = new HashMap<>();
	
	
		
//---------------------------------------Eventos Blocos--------------------------------------
		mMoved = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
			}
		};
		
		mRelease = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
			}
		}; 
		
		mExited = new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				
				((JLabel)e.getSource()).setBackground(Color.WHITE);
					if(!controleDosBlocos.verificarEncaixe((JLabel)e.getSource())
				  	    || controleOpcoes.verificarOpcao((JLabel)e.getSource()))
					{
						controleOpcoes.retornarBloco(blocoAtual);
					}
					blocoAtual = null;
			}
		};
		
		mEntered = new  MouseAdapter() {		
				public void mouseEntered(MouseEvent e) {
					((JLabel)e.getSource()).setBackground(Color.GREEN);
		//			System.out.println(((JLabel)e.getSource()).getName());
					blocoAtual = ((JLabel)e.getSource()).getName();
				}
		};

		mDragged = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				
				movendo = controleDosBlocos.verificaSeTentouEncaixe(blocobloco,(JLabel)e.getSource());
				
				controleOpcoes.atualizarPosicaoBloco(((JLabel)e.getSource()));
				controleDosBlocos.atualizarEstadoDosBlocos(((JLabel)e.getSource()));
				blocoAtual = ((JLabel)e.getSource()).getName();
			}
		};
				
		mClicked = new  MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}
		};

//---------------------------------------Eventos Blocos--------------------------------------
		
//---------------------Variáveis para formatar tela-------------------------
		Toolkit tk;
	    Dimension dtela;
		tk = Toolkit.getDefaultToolkit();
		dtela = tk.getScreenSize();
		
//		Point p = new Point();                       // cursor mouse
//---------------------Variáveis para formatar tela-------------------------	
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Algerian", Font.PLAIN, 17));
		frame.setBackground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, dtela.width, dtela.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		criarBlocos(75,5);
		
		btnPlay = new JButton("EXECUTAR");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblMensagem2.setVisible(true);
				lblBalao2.setVisible(true);
				testeResposta();
			}
		});
		
//		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				lblNewLabel.setBounds(620, 393, 249, 243);
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if(lblNewLabel.getX()-20 <= 300)
				{
					lblNewLabel.setBounds(300, 393, 249, 243);
				}
				else
				{
					lblNewLabel.setBounds(lblNewLabel.getX()-20, 393, 249, 243);
				}
				
			}
		});
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Rafael\\Documents\\Programas_Java\\Jogo_C\\imagens\\teste.gif"));
		lblNewLabel.setBounds(550, 274, 249, 243);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnVoltar = new JButton("<");
		btnVoltar.setFont(new Font("Algerian", Font.BOLD, 25));
		btnVoltar.setBounds(652, 144, 50, 37);
		frame.getContentPane().add(btnVoltar);
		
		JButton btnAvancar = new JButton(">");
		btnAvancar.setFont(new Font("Algerian", Font.BOLD, 25));
		btnAvancar.setBounds(712, 144, 50, 37);
		frame.getContentPane().add(btnAvancar);
		btnPlay.setFont(new Font("Arial Black", Font.BOLD, 9));
		btnPlay.setBounds(542, 144, 100, 37);
		frame.getContentPane().add(btnPlay);
		
		JPanel comandos = new JPanel();
		comandos.setBackground(Color.GRAY);
		comandos.setBounds(10, 137, 221, 549);
		frame.getContentPane().add(comandos);
		
		JLabel lblFundo = new JLabel("");
		lblFundo.setIcon(new ImageIcon(Ambiente_1.class.getResource("/Fundo_2.png")));
		lblFundo.setOpaque(false);
		lblFundo.setBackground(Color.LIGHT_GRAY);
		lblFundo.setBounds(241, 132, 541, 560);
		frame.getContentPane().add(lblFundo);
		
		JPanel cenario = new JPanel();
		cenario.setBackground(Color.GRAY);
		cenario.setBounds(792, 137, 550, 450);
		frame.getContentPane().add(cenario);
		cenario.setLayout(null);
		
		lblMensagem2 = new JLabel("Mundo SENAI");
		lblMensagem2.setVisible(false);
		lblMensagem2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMensagem2.setBounds(334, 69, 204, 35);
		cenario.add(lblMensagem2);
		
		lblBalao2 = new JLabel("");
		lblBalao2.setVisible(false);
		ImageIcon icone5 = new ImageIcon("C:\\Users\\Teo\\Pictures\\balao.png");
		icone5.setImage(icone5.getImage().getScaledInstance(233,62, 100));
		lblBalao2.setIcon(icone5);
		lblBalao2.setBounds(305, 54, 233, 62);
		cenario.add(lblBalao2);
		
		JLabel lblRobo = new JLabel("");
		lblRobo.setIcon(new ImageIcon("C:\\Users\\Teo\\Pictures\\robozinho.gif"));
		lblRobo.setBounds(12, 13, 526, 424);
		cenario.add(lblRobo);
		
		JPanel erros = new JPanel();
		erros.setBackground(Color.GRAY);
		erros.setBounds(792, 597, 550, 89);
		frame.getContentPane().add(erros);
		
		JLabel lblLogo = new JLabel("");
		ImageIcon icone2 = new ImageIcon("C:\\Users\\Teo\\Pictures\\logo.jpg");
		icone2.setImage(icone2.getImage().getScaledInstance(200,80, 100));
		lblLogo.setIcon(icone2);
		lblLogo.setBounds(30, 30, 200, 80);
		frame.getContentPane().add(lblLogo);
		
		btnOK = new JButton("");
		btnOK.setOpaque(false);
		btnOK.setIcon(new ImageIcon("C:\\Users\\Teo\\Pictures\\icon3.png"));
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnOK.setVisible(false);
				new Thread(msg1).start();
			}
		});
		btnOK.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnOK.setBackground(Color.YELLOW);
		btnOK.setBounds(1220, 30, 87, 80);
		frame.getContentPane().add(btnOK);
		
		lblMensagem = new JLabel("<html>Estou precisando de ajuda. <br>Que bom que voc\u00EA está aqui para me ajudar!</html>");
		lblMensagem.setForeground(Color.WHITE);
		lblMensagem.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblMensagem.setBounds(507, 27, 846, 78);
		frame.getContentPane().add(lblMensagem);
		
		
		JLabel lblRosto = new JLabel("");
		ImageIcon icone3 = new ImageIcon("C:\\Users\\Teo\\Pictures\\robo_rosto.png");
		icone3.setImage(icone3.getImage().getScaledInstance(124,111, 100));
		lblRosto.setIcon(icone3);	
		lblRosto.setBounds(300, 13, 124, 111);
		frame.getContentPane().add(lblRosto);
		
		JLabel lblBalao = new JLabel("");
		ImageIcon icone4 = new ImageIcon("C:\\Users\\Teo\\Pictures\\balao.png");
		icone4.setImage(icone4.getImage().getScaledInstance(905,111, 100));
		lblBalao.setIcon(icone4);	
		lblBalao.setBounds(437, 10, 905, 111);
		frame.getContentPane().add(lblBalao);
		
	//	Image image = new ImageIcon("C:\\Users\\Rafael\\Desktop\\Fundo.png"); // transform it

//		controleDosBlocos.zerarMatrizPosicaoBlocos();
		
		atualizarBlocos(1);
		
		new Thread(tarefa1).start();
		controleOpcoes.desativaOpcoes();
		
//----Campos para inserção-----------------------------------------------
//-----------------------------------------------------------------------		
	}

//---------------------------------------------------Atualizar blocos visíveis--------------
	public void atualizarBlocos(int x){
		
		String coluna = "";
		String nome = "";
		int pos = 0;
		int l =0;
		int c = 0;
		int cont = 1;
		
		if(x == 1)
		{
			try {
					for(JLabel bloco: controleDosBlocos.getReferenciaBlocos().keySet())
					{
						pos = Integer.parseInt((bloco.getName()));
						c = pos%10;
						l = (pos-c)/10;
						
						coluna = "C"+Integer.toString(c);
						dados_programa.absolute(l);
						
						System.out.println("Bloco: "+bloco.getName());
						if(dados_programa.getString(coluna) != null)
						{
							bloco.setOpaque(true);
						}
						else
						{
							bloco.setOpaque(false);
			//				bloco.setEnabled(false);
						}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			for(JLabel bloco: controleDosBlocos.getReferenciaBlocos().keySet())
			{
				bloco.setOpaque(true);	
			}
		}

	
	}
//---------------------------------------------------Atualizar blocos visíveis--------------
	
//===========================================================Teste Resposta==================
	public void testeResposta() {


		for(int l=0;l<=10;l++)
			for(int c=0;c<=5;c++)
				matrizResposta[l][c] = null;
		
		int correto = 1;

		String coluna;
		int linha = 1;
		
		
//			matrizResposta[l][c]  colocar respostas aqui;

//		verificarResposta();
		int l = 0;
		int c = 0;
		int pos = 0;
		for(JLabel opcao: controleOpcoes.getReferenciaOpcoes().keySet())
		{
			for(JLabel bloco: controleDosBlocos.getReferenciaBlocos().keySet())
			{
				if((bloco.getX() == opcao.getX()) && (bloco.getY() == opcao.getY()))
				{
					pos = Integer.parseInt((bloco.getName()));
					c = pos%10;
					l = (pos-c)/10;
					matrizResposta[l][c] = opcao.getText();
				//	System.out.println("posicao = "+(bloco.getName())+" - "+matrizResposta[l][c]);
				}	
			}
		}
	
		
			try {
				dados_programa.first();
				
				do
				{
					for(int k=1;k<=5;k++)
					{
						coluna = "C"+Integer.toString(k);
						System.out.println(
						  "MatrizResp = "+matrizResposta[(dados_programa.getInt("idprograma"))][k]+
						  " - Banco = "+dados_programa.getString(coluna));
						if((matrizResposta[(dados_programa.getInt("idprograma"))][k] == null))
						{
							if(((dados_programa.getString(coluna))!=null))
							{
							    correto = 0;	
							}
						}
						else
						{
							if(((dados_programa.getString(coluna))==null))
							{
								if((matrizResposta[(dados_programa.getInt("idprograma"))][k] != null))
								{
									correto = 0;
								}
							}
							else
							{
								if(!(matrizResposta[(dados_programa.getInt("idprograma"))][k].equals(dados_programa.getString(coluna))))
								{
									correto = 0;
								}
							}
						}
					}
					linha++;
				}while(dados_programa.next());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("  FIM  ");
		if(correto == 1)
		{
			JOptionPane.showMessageDialog(null, " Você acertou!!!");
		}
		else
		{
			JOptionPane.showMessageDialog(null, " Resposta ERRADA.");
		}
		
	}
//===========================================================Teste Resposta==================	

	private void criarBlocos(int qtBlocos,int qtOpcoes){
		
		int i = 0;
		int j = 10;
		int colunas = 5;
		int valorFinal = j+1+colunas;
		
		int posX = 30;
		int posY = 150;
//------------------------------Criar Blocos-----------------------------------------------

		String matrizOpcoes[] = new String[20];
		for(int z=0;z<20;z++) matrizOpcoes[z] = null;
		
		String coluna;
		int k = 0;	
		int indice = 0;
		qtOpcoes = 0;
		
		try {
			dados_programa.first();
			do
			{
				for(k=1;k<=5;k++)
				{
					coluna = "C"+Integer.toString(k);
					if(dados_programa.getString(coluna)!=null)
					{
						matrizOpcoes[indice] = dados_programa.getString(coluna);
						indice++;
						qtOpcoes++;
					}
				}
			}while(dados_programa.next());
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		


				for(i=0; i<qtOpcoes;i++)
				{   
					JLabel label = new JLabel(matrizOpcoes[i]);
					label.setName("B"+i);
					label.setOpaque(true);
					label.setFont(new Font("Courier New", Font.BOLD, 15));
					label.setBackground(Color.WHITE);
					label.setBounds(posX, posY, 180, 28);
					frame.getContentPane().add(label);
					controleOpcoes.adicionarOpcoes(label);
					
					label.addMouseListener(mExited);
					label.addMouseListener(mEntered);
					label.addMouseMotionListener(mDragged);
					label.addMouseListener(mClicked);

					posY += 40; 
				}
//------------------------------Criar Blocos-----------------------------------------------
				
//------------------------------Criar Labels-----------------------------------------------		

		posX = 300;
		posY = 200;
	
		for(i=0; i<qtBlocos;i++)
		{   
			j++;
			if(j>=valorFinal)
			{
				j = (j-colunas)+10;
				valorFinal += 10;
			}
			
			JLabel label = new JLabel("");
			label.setName(""+j);
			label.setOpaque(false);
			label.setFont(new Font("Courier New", Font.BOLD, 15));
			label.setBackground(Color.LIGHT_GRAY);
			label.setBounds(posX, posY, 180, 28);
			frame.getContentPane().add(label);
			controleDosBlocos.adicionarBloco(label);
			
			if((i+1)%colunas == 0){
				posY += 30;
				posX = 300;
			}
			else
			{
				posX += 183;
			}
			
		}
//------------------------------Criar Labels-----------------------------------------------
	}
	
//========================================
	public Runnable tarefa1 = new Runnable() {
		
		@Override
		public void run() {	
		while(true)
		{
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			if(direcao == 0)
			{
				lblNewLabel.setBounds(lblNewLabel.getX()-1, 393, 249, 243);
			}
			else
			{
				lblNewLabel.setBounds(lblNewLabel.getX()+1, 393, 249, 243);
			}
			
			if(lblNewLabel.getX()<= 300)
			{
				direcao = 1;
			}
			
			if(lblNewLabel.getX()>= 550)
			{
				direcao = 0;
			}
			
			if(comObjeto == 10)
			{
//				controleOpcoes.atualizarPosicaoBloco(blocoAtual);
	//		}
	//		else
	//		{
				if(blocoAtual != null)
				{
					controleOpcoes.retornarBloco(blocoAtual);
					blocoAtual = null;
				}
			}

		}
		}
		};
	private JLabel lblMensagem;
	
	public Runnable msg1 = new Runnable() {
		
		@Override
		public void run() {	
			
			try {
				controleOpcoes.ativaOpcoes();
				lblMensagem.setText("<html>Arraste o primeiro bloco<br> para a possição em destaque.</html>");
				Thread.sleep(2000);
				controleOpcoes.alterarCorOpcao("B0");
				controleDosBlocos.alterarCorBloco("11"); //linha um coluna 1 
				
				JLabel opcoes = controleOpcoes.verificarEncaixe1("B0");
				JLabel blocos = controleDosBlocos.verificarEncaixe2("11");
				while(!((opcoes.getX() == blocos.getX()) && (opcoes.getY() == blocos.getY()))){
					Thread.sleep(500);
				}
				//lblMensagem.setText("<html>Dica: o include ....</html>");

				new Thread(msg2).start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
public Runnable msg2 = new Runnable() {
		
		@Override
		public void run() {	
			
			try {
				blocobloco = "12";
				lblMensagem.setText("<html>Parabéns! Agora me ajude a encontrar <br>a cobinação correta deste comando.</html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes = controleOpcoes.verificarEncaixe1("B1");
				JLabel blocos = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes.getX() == blocos.getX()) && (opcoes.getY() == blocos.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Agora me ajude a encontrar <br>a combinação correta deste comando.</html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
//--------------------------------------------------------------------------------------------------
				blocobloco = "21";
				lblMensagem.setText("<html>Parabéns! Nós formamos uma bela dupla.<br>Agora vamos criar a estrutura principal main().</html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes1 = controleOpcoes.verificarEncaixe1("B2");
				JLabel blocos1 = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes1.getX() == blocos1.getX()) && (opcoes1.getY() == blocos1.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Agora vamos criar a estrutura principal main()</html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
//-----------------------------------------------------------------------------------------------------
				blocobloco = "31";
				lblMensagem.setText("<html>Muito bem conseguimos criar o início de nosso programa.<br> Vamos agora apontar onde começa a nossa solução. </html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes2 = controleOpcoes.verificarEncaixe1("B3");
				JLabel blocos2 = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes2.getX() == blocos2.getX()) && (opcoes2.getY() == blocos2.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Vamos agora apontar onde começa a nossa solução. \"{\"</html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
				//-----------------------------------------------------------------------------------------------------
				blocobloco = "41";
				lblMensagem.setText("<html>Agora vamos aprender o comando que fara nosso robo falar</html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes3 = controleOpcoes.verificarEncaixe1("B4");
				JLabel blocos3 = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes3.getX() == blocos3.getX()) && (opcoes3.getY() == blocos3.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Para fazer nosso robo falar \"printf\"<br> print = impressão / f = formatar </html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
				//-----------------------------------------------------------------------------------------------------
				blocobloco = "42";
				lblMensagem.setText("<html>Dentro de um par de parentese () <br> Temos que colocar a mensagem que o robo pronunciara</html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes4 = controleOpcoes.verificarEncaixe1("B5");
				JLabel blocos4 = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes4.getX() == blocos4.getX()) && (opcoes4.getY() == blocos4.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Ajude-me a colocar uma mensagem <br>Para que nosso robo fale \"Mundo SENAI\"</html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
				//-----------------------------------------------------------------------------------------------------
				blocobloco = "51";
				lblMensagem.setText("<html>Muito bem, agora precisamos finalizar nosso bloco \"main()<br>{\"</html>");
				controleDosBlocos.alterarCorBloco(blocobloco);
				JLabel opcoes5 = controleOpcoes.verificarEncaixe1("B6");
				JLabel blocos5 = controleDosBlocos.verificarEncaixe2(blocobloco);
				Thread.sleep(4000);
				while(!((opcoes5.getX() == blocos5.getX()) && (opcoes5.getY() == blocos5.getY()))){
					if(movendo==false) {
						lblMensagem.setText("<html>Agora vamos apontar o fim do nosso main(){ \"}\"</html>");
						Thread.sleep(1000);
					}else {
						lblMensagem.setText("<html>Ops! Testei aqui, mas não funcionou!!!</html>");
						Thread.sleep(1000);
					}
				}
				movendo = false;
				//-----------------------------------------------------------------------------------------------------
				lblMensagem.setText("<html>Muito bem, agora vamos testar nossa solução<br>Clique no botao <b>\"Executar\"</b> em destaque.</html>");
				btnPlay.setOpaque(true);
				btnPlay.setBackground(Color.ORANGE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	private JButton btnOK;
	private JButton btnPlay;
	private JLabel lblBalao2;
	private JLabel lblMensagem2;
}
	
