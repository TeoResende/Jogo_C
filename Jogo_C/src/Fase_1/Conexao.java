package Fase_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.SingleSelectionModel;

public class Conexao {
	
	public static Connection getConexao(){
		Connection conexao = null;
			try {
				conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/jogoc","root","root");
																  // 127.0.0.1   192.168.10.32
				System.out.println("Banco Conectado!");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao conectar ao banco de dados!");
			}
		return conexao;
	}
	
//----------INSERIR----------------------------------------------------------------	
	
	public boolean cadastrar(String C1,String C2,String C3,String C4,String C5){
		boolean resposta = false;
		String sql = "INSERT INTO questoes (C1,C2,C3,C4,C5) VALUES (?,?,?,?,?)"; 
		try {
			Connection c = Conexao.getConexao();
			
			PreparedStatement cadastro = c.prepareStatement(sql);
			cadastro.setString(2, C1);
			cadastro.setString(3, C2);
			cadastro.setString(4, C3);
			cadastro.setString(5, C4);
			cadastro.setString(6, C5);
			cadastro.execute();
			cadastro.close();
			resposta = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro no cadastro!");
		}		
		return resposta;
	}
	
//----------SELECIONAR----------------------------------------------------------------	
	
	public ResultSet consultar(){
		String sql = "SELECT * FROM programa";
		ResultSet dados = null;
		
		try {
			Connection c = Conexao.getConexao();
			PreparedStatement consulta = c.prepareStatement(sql);
			dados = consulta.executeQuery();
			consulta.execute();
			consulta.close();
			System.out.println("Dados selecionados");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro na consulta!");
			return null;
		}			
		return dados;
	}
	
	
	
	public ResultSet consultar(int id){
		String sql = "SELECT * FROM programa WHERE idprograma>?";
		ResultSet dados = null;
		
		try {
			Connection c = Conexao.getConexao();
			PreparedStatement consulta = c.prepareStatement(sql);
			consulta.setInt(1, id);
			dados = consulta.executeQuery();
			consulta.execute();
			consulta.close();
			System.out.println("Dados selecionados");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro na consulta!");
			return null;
		}			
		return dados;
	}

//---------------Fim main------------------------------------------------	
}

