package br.com;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.opencsv.CSVReader;

public class Conexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "Mysql2@@4";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             CSVReader reader = new CSVReader(new FileReader("src/main/java/br/com/arquivo.csv"))) {

            // Corresponde ao cabeçalho do arquivo
            String[] header = reader.readNext();

            // Corresponde às colunas do arquivo
            String[] line;

            while ((line = reader.readNext()) != null) {

                // Criar um objeto Java com os dados do arquivo CSV
                Dados obj = new Dados(Integer.parseInt(line[0]), line[1], line[2]);

                // Inserir o objeto Java no banco de dados
                String sql = "INSERT INTO csv (id, nome, tipo) VALUES (?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, obj.getId());
                    statement.setString(2, obj.getNome());
                    statement.setString(3, obj.getTipo());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Dados {
    private Integer id;
    private String nome;
    private String tipo;

    public Dados(Integer id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}
