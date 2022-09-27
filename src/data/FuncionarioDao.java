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

    public boolean conectar() {

        //caminho do driver
        String driver = "com.mysql.cj.jdbc.Driver";

        //armazenando informações nas variaveis referentes ao banco
        String url = "jdbc:mysql://localhost:3306/funcionario";
        String user = "root";
        String password = "";

        //Estabelecendo a conexão com o banco de dados.
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            //A linha abaixo serve de apoio para esclarecer o erro
            //System.out.println(e);
            return false;
        }

    }

    //Métodos para salvar ou adicionar usuários
    public int salvar(Funcionario funcionario) {
        int codigoStatus;

        try {
            String sql = "Insert into funcionariotab(matricula,nome,cargo,salario) values(?,?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, funcionario.getMatricula());
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getCargo());
            pst.setDouble(4, funcionario.getSalario());

            codigoStatus = pst.executeUpdate(); //Retorna 1
            return codigoStatus;
        } catch (SQLException ex) {
            //System.out.println(ex); obter informações sobre o erro
            //System.out.println(ex.getErrorCode());//mostrar o numero do erro no console
            return ex.getErrorCode();//Retorna codigo do erro.
            //1062 tentaiva de cadastrar usuario já cadastrado.
        }
    }

    public Funcionario consultar(String matricula) {//se retornar varias informações o metodo sera diferente.

        Funcionario funcionario = new Funcionario();

        try {
            pst = conexao.prepareStatement("SELECT * FROM funcionariotab WHERE matricula=?");
            //a linha abaixo é o comando que vai setar um parametro no lugar da ? no comando sql, 
            pst.setString(1, matricula);
            //na linha abaixo o rs é a variavel do tipo resultSet que é para guardar o resultado do que virá do banco de dados.
            rs = pst.executeQuery();
            if (rs.next()) {//verifica se achou a matricula informada.
                funcionario.setNome(rs.getString(2));
                funcionario.setCargo(rs.getString(3));
                funcionario.setSalario(rs.getDouble(4));
                return funcionario;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public int alterar(Funcionario funcionario) {
        int statusConexao;
        try {
            pst = conexao.prepareStatement("update funcionariotab set nome=?, cargo=?, salario=? where matricula=?");
            pst.setString(1, funcionario.getNome());
            pst.setString(2, funcionario.getCargo());
            pst.setDouble(3, funcionario.getSalario());
            pst.setString(4, funcionario.getMatricula());

            return statusConexao = pst.executeUpdate();//se retorna 1 então conectou

        } catch (SQLException e) {
            // JOptionPane.showMessageDialog(null, e);
            return e.getErrorCode();
        }
    }

    public boolean deletar(String matricula) {

        try {
            pst = conexao.prepareStatement("delete from funcionariotab where matricula = ?");
            pst.setString(1, matricula);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            //System.out.println(e);
            return false;
        }
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (Exception e) {
        }
    }
}
