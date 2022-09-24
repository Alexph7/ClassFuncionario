package data;

import java.sql.*;

public class FuncionarioDao {

    //Usando a variavel de conexão
    Connection conexao;

    //Criando variaveis especiais para conexão com o banco de dados.
    //PreparedStatment e ResultSet são frameworks do pacote java.sql
    //e servem para preparar e executar as instruções sql
    PreparedStatement pst = null;
    ResultSet rs = null;

    public static Connection conectar() {

        Connection conexao;

        //caminho do driver
        String driver = "com.mysql.cj.jdbc.Driver";

        //armazenando informações nas variaveis referentes ao banco
        String url = "jdbc:mysql://localhost:3306/funcionariotab";
        String user = "root";
        String password = "";

        //Estabelecendo a conexão com o banco de dados.
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            //A linha abaixo serve de apoio para esclarecer o erro
            //System.out.println(e);
            return null;
        }

    }

    //Métodos para salvar ou adicionar usuários
    private int salvar(Funcionario funcionario) {
        int status;

        try {
            String sql = "Insert into funcionariotab(matricula,nome,cargo,salario) values(?,?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, funcionario.getMatricula());
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getCargo());
            pst.setDouble(4, funcionario.getSalario());

            status = pst.executeUpdate(); //Retorna 1
            return status;
        } catch (SQLException ex) {
            return ex.getErrorCode();//Retorna codigo do erro.
            //1062 tentaiva de cadastrar usuario já cadastrado.
        }
    }
    
    public void desconectar(){
        try {
            conexao.close();
        } catch (Exception e) {
        }
    }
}
