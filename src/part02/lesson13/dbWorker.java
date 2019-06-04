package part02.lesson13;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 *
 */
public class dbWorker {


    /**
     * Создание коннекта к БД
     * @return
     * @throws DatabaseException
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws DatabaseException, SQLException, IOException, ClassNotFoundException {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("src/part02/lesson13/source/connection.properties");
            props.load(in);
            in.close();

            String driver = props.getProperty("jdbc.driver");

            if (driver != null) {
                Class.forName(driver);
            }

            String url = props.getProperty("url");
            Connection connection = DriverManager.getConnection(url, props);
            return connection;
        } catch (IOException ex) {
            System.out.println("ошибка доступа к файлу.");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver is missing");
            return null;
        }
    }


    /**
     * Создание структуры БД
     * используется Liquibase, с поддержкой версионирования и дополнения таблиц
     * @param connection
     * @throws DatabaseException
     */
    public static void CreateTables(Connection connection) throws DatabaseException {
        try {
            if (connection != null) {
                Liquibase liquibase;
                liquibase = null;
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                liquibase = new Liquibase("./src/part02/lesson13/source/changelog.xml", new FileSystemResourceAccessor(), database);
                liquibase.update(new Contexts());
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (LiquibaseException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    //nothing to do
                }
            }
        }
    }


    /**
     * выполнение запросов SQL через Batch
     * @param connection
     * @param truncate
     * @throws SQLException
     */
    public static void getInsertBatch(Connection connection, Boolean truncate) throws SQLException {
        try {
            Statement statement = connection.createStatement();
        if (truncate) {
            statement.addBatch("TRUNCATE TABLE \"USER\"");
        }
        statement.addBatch("INSERT INTO public.\"USER\"(\n" +
                "\tid, name, birthday, \"login_ID\", city, email, description)\n" +
                "\tVALUES (1, 'Иванов Иван Иванович', '12.12.1986', 'Ivan_i', 'Msk', 'i.ivanov@mydomain.com', 'что то дополнительно');");
        statement.addBatch("INSERT INTO public.\"USER\"(\n" +
                "\tid, name, birthday, \"login_ID\", city, email, description)\n" +
                "\tVALUES (2, 'Петров Петр Петрович', '12.12.1956', 'Petr_p', 'Ufa', 'p.petrov@mydomain.com', 'что то дополнительно2');");
        statement.executeBatch();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();

    } }

    /**
     *
     * @param connection
     * @param truncate
     * @throws SQLException
     */
    public static void getInsert(Connection connection, Boolean truncate) throws SQLException {
        try {
        Statement statement = connection.createStatement();
        if (truncate) {
            statement.execute("TRUNCATE TABLE \"role\"");
        }
        statement.execute("INSERT INTO public.role(\n" +
                "\tid, name, description)\n" +
                "\tVALUES (1, 'Администратор', 'Полные права');");
        statement.execute("INSERT INTO public.role(\n" +
                "\tid, name, description)\n" +
                "\tVALUES (2, 'Клиент', 'только свои данные');");
        statement.execute("INSERT INTO public.role(\n" +
                "\tid, name, description)\n" +
                "\tVALUES (3, 'Оператор', 'данные клиентов');");
        statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * выполнение параметризированной выборки
     * @param connection
     * @param query
     * @param login
     * @param name
     * @return
     */
    public static void getQuery(Connection connection, String query, String login, String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, name);

            ResultSet result = preparedStatement.executeQuery();
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     *
     * демонстрация работы с точками сохранения
      * @param connection
     * @throws SQLException
     */
    public static void manualTransaction(Connection connection) throws SQLException {

        Savepoint savepoint_0 = null;
        Savepoint savepoint_1 = null;
        Savepoint savepoint_2 = null;
        Savepoint savepoint_3 = null;

        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO public.role(\n" +
                    "\tid, name, description)\n" +
                    "\tVALUES (4, 'Просто', 'Полные права');");

            savepoint_0 = connection.setSavepoint("SAVEPOINT_0");
            getInsertBatch(connection,false);
            savepoint_1 = connection.setSavepoint("SAVEPOINT_1");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            getInsert(connection,false);
            savepoint_2 = connection.setSavepoint("SAVEPOINT_2");
//          connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint_1);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            Statement statement = connection.createStatement();
            statement.addBatch("INSERT INTO public.\"user_role\"(\n" +
                    "\tid, \"User_id\", \"Role_id\")\n" +
                    "\tVALUES (1, 1, 1);");
            statement.addBatch("INSERT INTO public.\"user_role\"(\n" +
                    "\tid, \"User_id\", \"Role_id\")\n" +
                    "\tVALUES (2, 2, 2);");
            statement.addBatch("INSERT INTO public.\"user_role\"(\n" +
                    "\tid, \"User_id\", \"Role_id\")\n" +
                    "\tVALUES (3, 3, '3');");
            statement.executeBatch();
            savepoint_3 = connection.setSavepoint("SAVEPOINT_3");
            statement.close();
            connection.rollback(savepoint_0); // просто взял и передумал записывать в базу
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint_0);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        connection.commit();

    }
}