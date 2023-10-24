
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
  public static void main(String[] args) {
    Connection connection = null;
    try {
      // Cria a conex�o com o banco de dados
      connection = DriverManager.getConnection("jdbc:sqlite:base.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // Espera s� por 30 segundos para conectar

      // Roda os comandos para o SQLite
      statement.executeUpdate("DROP TABLE IF EXISTS terminalroot");
      statement.executeUpdate("CREATE TABLE terminalroot (id INTEGER, name STRING)");
      statement.executeUpdate("INSERT INTO terminalroot VALUES(1, 'Marcos Oliveira')");
      statement.executeUpdate("INSERT INTO terminalroot VALUES(2, 'James Gosling')");
      ResultSet rs = statement.executeQuery("SELECT * FROM terminalroot");
      while(rs.next()) {
        // Ler os dados inseridos
        System.out.println("NOME DO CARA  : " + rs.getString("name"));
        System.out.println("IDENTIFICA��O : " + rs.getInt("id"));
      }
    } catch(SQLException e) {
      // Se a mensagem de erro for: "out of memory",
      // Provavelmente erro ao criar(permiss�o) ou caminho do banco de dados
      System.err.println(e.getMessage());
    }

    finally {
      try {
        if(connection != null){
          connection.close();
        }
      } catch(SQLException e) {
        // Falhou tamb�m para fechar o arquivo
        System.err.println(e.getMessage());
      }
    }

  }
}