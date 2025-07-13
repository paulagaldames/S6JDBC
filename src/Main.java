
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Scanner sc = new Scanner(System.in);

        try {

            // Establecer conexión
            String url = "jdbc:mysql://localhost:3306/empresa";
            String user = "root";
            String password = "";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa.");

            // Consulta SQL
            String consulta = "SELECT * FROM empleado";
            preparedStatement = connection.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();

            // Mostrar resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("EmpleadoID");
                String nombre = resultSet.getString("Nombre");
                String cargo = resultSet.getString("Cargo");
                Float  salario = resultSet.getFloat("Salario");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Cargo: " + cargo + ", Salario: " + salario);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierro conexión
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sc.close();
    }
}

