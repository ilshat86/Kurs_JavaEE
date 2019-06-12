package part02.lesson13;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *
 */
public class dbWorker {

    private static Logger logger = LogManager.getLogger(dbWorker.class);
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
            InputStream in = Main.class.getResourceAsStream("/connection.properties");//new FileInputStream("src/part02/lesson13/source/");
            props.load(in);
            in.close();

            String driver = props.getProperty("jdbc.driver");

            if (driver != null) {
                Class.forName(driver);
            }
            logger.info("Properties считался");
            String url = props.getProperty("url");
            logger.info("Устанавливаем соединение с БД");
            Connection connection = DriverManager.getConnection(url, props);
            logger.info("Соединение с БД установлено");
            return connection;
        } catch (IOException ex) {
            logger.error("ошибка доступа к файлу.", ex);
            System.out.println();
            return null;
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver is missing",e);
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
                String changeFileName = "C:\\Users\\ilsha\\IdeaProjects\\Kurs_JavaEE\\resources\\changelog.xml";
                liquibase = new Liquibase(changeFileName, new FileSystemResourceAccessor(), database);
                liquibase.update(new Contexts());
            }
        } catch (DatabaseException e) {
            logger.error(e);
        } catch (LiquibaseException e) {
            logger.error(e);
            ;
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    //nothing to do
                    logger.error(e);
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
        logger.info("Выполнена команда BATCH");
        statement.close();
    } catch (SQLException e) {
            logger.error(e);

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
        logger.info("Выполнена команда Execute");
        statement.close();
        } catch (SQLException e) {
            logger.error(e);

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
            logger.info("выполнен параметризированный запрос");
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(e);

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
            logger.info("перешли на ручное управление транзакциями");
            connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO public.role(\n" +
                    "\tid, name, description)\n" +
                    "\tVALUES (4, 'Просто', 'Полные права');");

            savepoint_0 = connection.setSavepoint("SAVEPOINT_0");
            logger.info("Установлено точка сохранения 0");
            getInsertBatch(connection,false);
            savepoint_1 = connection.setSavepoint("SAVEPOINT_1");
            logger.info("Установлено точка сохранения 1");
            //connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error(e1);
            }
        }

        try {
            getInsert(connection,false);
            savepoint_2 = connection.setSavepoint("SAVEPOINT_2");
            logger.info("Установлено точка сохранения 2");

            //connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            try {
                connection.rollback(savepoint_1);
            } catch (SQLException e1) {
                logger.error(e1);
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
                    "\tVALUES (3, 3, 3);");
            statement.executeBatch();
            savepoint_3 = connection.setSavepoint("SAVEPOINT_3");
            logger.info("Установлено точка сохранения 3");
            statement.close();
            connection.rollback(savepoint_0); // просто взял и передумал записывать в базу
            logger.info("Вернулись к точке сохранения 0");

        } catch (SQLException e) {
            try {
                logger.error(e);
                connection.rollback(savepoint_0);
                logger.info("Вернулись к точке сохранения 0");
            } catch (SQLException e1) {
                logger.error(e1);
            }
        }
        connection.commit();
    }
}