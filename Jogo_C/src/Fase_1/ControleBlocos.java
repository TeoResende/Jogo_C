package Fase_1;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ControleBlocos {

	    EstadosBlocos matrizCondicaoBlocos[][]  = new EstadosBlocos[11][6];
//	    EstadosBlocos matrizResposta[][]      = new EstadosBlocos[11][6];

	    String matrizPosicaoBlocos[][]  = new String[11][6];
	    String   matrizResposta[][]     = new String[11][6];
//	    String matrizResposta[][]       = new String[11][6];
	    
		private String nmBloco;
		private Map<JLabel, EstadosBlocos> referenciaBlocos;
		private Map<JLabel, EstadosBlocos> referenciaOpcoes;
	
		public ControleBlocos(){
	//		this.referenciaBotoes = new HashMap<JButton, Boolean>();
			this.referenciaBlocos = new HashMap<>();
			this.referenciaOpcoes = new HashMap<>();
		}
		
		public void executarAcaoBloco(JLabel bloco, EstadosBlocos estado){
			alterarSelecao(bloco, estado);
			alterarCondicaoBloco(bloco);
	    	if(this.isTodasSemBlocos()){
	    	//	alterarEstadoTodosBlocos(EstadosBlocos.POSICAO_CORRETA);
	    	}
	    	else
	    	{
	    //		alterarVisualizacaoBloco(bloco);
	    	}
		}
		
		public void zerarMatrizPosicaoBlocos(){
			for(int i = 0;i<matrizCondicaoBlocos.length;i++)
				for(int j = 0;j<matrizCondicaoBlocos[0].length;j++)
					matrizCondicaoBlocos[i][j] = EstadosBlocos.SEM_BLOCO;
		}
		
		public void salvarMatrizResposta(ResultSet dados_programa){
			
			try {
				dados_programa.first();
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}	
			
			
			String coluna;
			
			for(int i = 0;i<matrizCondicaoBlocos.length;i++)
			{
				for(int j = 0;j<matrizCondicaoBlocos[0].length;j++)
				{
//					coluna = "C"+Integer.toString(j);
				//	dados_programa.getString(coluna);
/*					try {
						matrizResposta[i][j] = dados_programa.getString(coluna);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
				}
				
				try {
					dados_programa.next();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}	
			}		
		}

		public void salvarMatrizPosicaoBlocos(){

		}
		
		public void atualizarBlocos(){
	//		for(int i = 0;i<matrizPosicaoBlocos.length;i++)
	//			for(int j = 0;j<matrizPosicaoBlocos[0].length;i++)
	//				matrizPosicaoBlocos[i][j] = EstadosBlocos.SEM_BLOCO;
			
			for(JLabel bloco: this.referenciaBlocos.keySet()){
				alterarCondicaoBloco(bloco);
			}
			
		}
		
		private void alterarEstadoTodosBlocos(EstadosBlocos estado){
			for(JLabel bloco: this.referenciaBlocos.keySet()){
				alterarSelecao(bloco, estado);
				alterarCondicaoBloco(bloco);
			}
		}
		
		public String getNmBloco(){
			return nmBloco;
		}
		
		public void setNmBotao(String nmBloco){
			this.nmBloco = nmBloco;
		}
		
		public Map<JLabel, EstadosBlocos> getReferenciaBlocos(){
			return referenciaBlocos;
		}
	
		public Map<JLabel, EstadosBlocos> getReferenciaOpcoes(){
			return referenciaOpcoes;
		}
		
		public void setReferenciaBotoes(Map<JLabel, EstadosBlocos> referenciaBlocos){
			this.referenciaBlocos = referenciaBlocos;
		}
		
		public void adicionarBloco(JLabel bloco){
			this.referenciaBlocos.put(bloco, EstadosBlocos.SEM_BLOCO);
		}
	
		public void adicionarOpcoes(JLabel bloco){
			this.referenciaOpcoes.put(bloco, EstadosBlocos.SEM_BLOCO);
		}
		
		public void alterarSelecao(JLabel bloco, EstadosBlocos estado){
			this.referenciaBlocos.put(bloco, estado);
	//		EstadosBotoes b = this.referenciaBotoes.get(botao);
	//		b = selecionado;
	//    	alterarVisualizacaoBotao(botao);
		}
		
		private void alterarCondicaoBloco(JLabel bloco){
			EstadosBlocos selecionado = this.referenciaBlocos.get(bloco);
			switch(selecionado){
				case SEM_BLOCO:
					bloco.setBackground(Color.LIGHT_GRAY);
					bloco.setOpaque(true);
					bloco.setVisible(true);
					break;
				case COM_BLOCO:
	//				bloco.setText(this.nmBloco);
					bloco.setOpaque(false);
					bloco.setVisible(false);
					break;
				case POSICAO_CORRETA:
				
					break;
				case POSICAO_ERRADA:

					break;
			}
		}
		public void alterarCorOpcao(String nome){
			for(JLabel bloco: this.referenciaOpcoes.keySet())
			{
				if(bloco.getName().equals(nome))
				{
					bloco.setBackground(Color.ORANGE);
					bloco.setOpaque(true);
					bloco.setVisible(true);
				}	
			}			
		}
		
		public void alterarCorBloco(String nome){
			for(JLabel bloco: this.referenciaBlocos.keySet())
			{
				if(bloco.getName().equals(nome))
				{
					this.referenciaBlocos.put(bloco, EstadosBlocos.POSICAO_CORRETA);
					bloco.setBackground(Color.ORANGE);
					bloco.setOpaque(true);
					bloco.setVisible(true);
				}	
			}			
		}
		
		public void atualizarEstadoDosBlocos(JLabel label){
			for(JLabel bloco: this.referenciaBlocos.keySet())
			{
			//	EstadosBlocos selecionado = referenciaBlocos.get(bloco);
	
					if((label.getX()+45 > bloco.getX() && label.getX()+30 < bloco.getX()+75) 
						&& (label.getY()+5 > bloco.getY() && label.getY()+10 <  bloco.getY()+25)
						&& bloco.isOpaque())
					{
//						bloco.setOpaque(false);
				//			System.out.println(selecionado);
						label.setBounds(bloco.getX(),bloco.getY(),bloco.getWidth(),bloco.getHeight());
				//			alterarSelecao(bloco, EstadosBlocos.COM_BLOCO);
					}
					else
					{
				//		alterarSelecao(bloco, EstadosBlocos.SEM_BLOCO);
//						bloco.setOpaque(true);
					}
		//			alterarCondicaoBloco(bloco);
				
			}						
		}

		private Map<JLabel, EstadosBlocos> ReferenciaBlocos() {
			// TODO Auto-generated method stub
			return null;
		}

		public void atualizarPosicaoBloco(JLabel bloco){
			
			Point p = new Point();  
			p = MouseInfo.getPointerInfo().getLocation();	
			bloco.setBounds(p.x-49,p.y-35,bloco.getWidth(),bloco.getHeight());
		}
		
		public void retornarBloco(String blocoAtual){
			for(JLabel bloco: this.referenciaOpcoes.keySet())
			{
				if(bloco.getName().equals(blocoAtual))
				{
					int y = 150;
					int x = 30;
					int largura = 147;
					int espaco = 40;
					
					switch(bloco.getName())
					{
						case "B0": bloco.setBounds(x,0*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B1": bloco.setBounds(x,1*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B2": bloco.setBounds(x,2*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B3": bloco.setBounds(x,3*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B4": bloco.setBounds(x,4*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B5": bloco.setBounds(x,5*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B6": bloco.setBounds(x,6*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B7": bloco.setBounds(x,7*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B8": bloco.setBounds(x,8*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						case "B9": bloco.setBounds(x,9*espaco+y,bloco.getWidth(),bloco.getHeight()); break;
						default : break;
					}
					
				}
			}
		}

		public Boolean verificarEncaixe(JLabel blocoAtual)
		{
			for(JLabel bloco: this.referenciaBlocos.keySet())
			{
				if((blocoAtual.getX() == bloco.getX()) && (blocoAtual.getY() == bloco.getY()))
				{
		//			System.out.println(bloco.getName());
					return true;
				}	
			}
			return false;
		}
		
		public JLabel verificarEncaixe1(String opcao)
		{
			for(JLabel opcoes: this.referenciaOpcoes.keySet())
			{
				if(opcoes.getName().equals(opcao)) {
					return opcoes;
				}
			}
			return null;
		}
		
		public JLabel verificarEncaixe2(String bloco)
		{
			for(JLabel blocos: this.referenciaBlocos.keySet())
			{
				if(blocos.getName().equals(bloco)) {
					return blocos;
				}
			}
			return null;
		}
		
	
		public String verificarResposta(JLabel blocoOpcao)
		{
			String resposta = "";
			
			for(JLabel bloco: this.referenciaBlocos.keySet())
			{
				if((blocoOpcao.getX() == bloco.getX()) && (blocoOpcao.getY() == bloco.getY()))
				{
					resposta = bloco.getName();
					return resposta;
				}	
			}
			
			return resposta;
		}

		public Boolean verificarOpcao(JLabel blocoAtual)
		{		
			for(JLabel opcao: this.referenciaOpcoes.keySet())
			{
				
				if((blocoAtual.getX() == opcao.getX()) && (blocoAtual.getY() == opcao.getY())
				   && (blocoAtual.getName() != opcao.getName()))
				{
					return true;
				}	
			}
			return false;
		}
		
		public void zerarSelecoes(){
			alterarEstadoTodosBlocos(EstadosBlocos.SEM_BLOCO);
			
	//	    this.referenciaBotoes.values().stream().forEach((b) -> {
	//	    	b = EstadosBotoes.NORMAL;
	//	    });
/*			
			for (Boolean b : this.referenciaBotoes.values()){
				b = false;
			}
*/
		}
		
		public JLabel opcaoSelecionada(){
			for(JLabel bloco: this.referenciaOpcoes.keySet())
			{
				EstadosBlocos selecionado = referenciaOpcoes.get(bloco);
				if(selecionado == EstadosBlocos.COM_BLOCO)
				{
					return bloco;
				}
			}
			return null;
		}
		
		public Boolean isTodasSemBlocos(){
			for(EstadosBlocos b:this.referenciaBlocos.values()){
				if(b != EstadosBlocos.SEM_BLOCO){
					return false;
				}
			}
			return true;
		}
		
		
		public void desativaOpcoes() {
			for(JLabel bloco: this.referenciaOpcoes.keySet())
			{
				bloco.setVisible(false);
			}
		}
		
		public void ativaOpcoes() {
			for(JLabel bloco: this.referenciaOpcoes.keySet())
			{
				bloco.setVisible(true);
			}
		}
}
