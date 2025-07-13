
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
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            String url = "jdbc:mysql://localhost:3306/empresa";
            String user = "root";
            String password = "";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa.");

            //Mostrar el nombre y salario de los empleados que trabajan en el departamento de Desarrollo y que tienen un salario mayor a 60000.00.
            String consulta1 = """
                SELECT e.Nombre, e.Salario
                FROM empleado e
                JOIN departamento d ON e.DepartamentoID = d.DepartamentoID
                WHERE d.Nombre = 'Desarrollo' AND e.Salario > 60000.00
            """;
            preparedStatement = connection.prepareStatement(consulta1);
            resultSet = preparedStatement.executeQuery();
            System.out.println("********** Empleados en Desarrollo con salario > 60000 **********");
            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                float salario = resultSet.getFloat("Salario");
                System.out.println("Nombre: " + nombre + ", Salario: " + salario);
                System.out.println("********** Fin consulta1 **********");
            }
            resultSet.close();
            preparedStatement.close();

            //Agregar un nuevo departamento llamado "Marketing" ubicado en el "Piso 4, Edificio D".
            String consulta2 = "INSERT INTO departamento (DepartamentoID, Nombre, Ubicacion) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(consulta2);
            preparedStatement.setInt(1, 4);
            preparedStatement.setString(2, "Marketing");
            preparedStatement.setString(3,"Piso 4, Edificio D");
            preparedStatement.executeUpdate();
            System.out.println("********** Fin consulta2, registro insertado **********");
            preparedStatement.close();

            //Actualizar el salario de Juan Pérez, quien es un vendedor del departamento de Ventas. Incrementa su salario en un 10%.
            String consulta3 = """
                    UPDATE empleado e
                    JOIN departamento d ON e.DepartamentoID = d.DepartamentoID
                    SET e.Salario = e.salario + (10/100)*e.salario
                    WHERE e.Nombre = 'Juan Pérez' AND e.Cargo = 'Vendedor' AND d.Nombre = 'Ventas'""";

            preparedStatement = connection.prepareStatement(consulta3);
            int filaactualizada = preparedStatement.executeUpdate();
            System.out.println("**********El salario de Juan Peres a sido actualizado.**********");
            preparedStatement.close();

            //Eliminar al empleado con el ID 105, ya que ha dejado la empresa
            String consulta4 = "DELETE FROM empleado WHERE EmpleadoID = ?";
            preparedStatement = connection.prepareStatement(consulta4);
            preparedStatement.setInt(1, 105);
            preparedStatement.executeUpdate();
            System.out.println("**********El empleado con ID: 105 a sido eliminado.**********");
            preparedStatement.close();

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

