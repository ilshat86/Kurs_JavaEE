package part02.lesson13;

import liquibase.exception.DatabaseException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class Main {

    /**
     * Основной метод
     * @param args
     * @throws DatabaseException
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main (String[] args) throws DatabaseException, SQLException, IOException, ClassNotFoundException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        // обновим структуру БД
        Connection c = dbWorker.getConnection();
        dbWorker.CreateTables(c);

        c = dbWorker.getConnection(); // создает коннект к базе
        dbWorker.getInsertBatch(c,true); //вызываем Batch запрос
        dbWorker.getInsert(c,true); //вызывваем обычное заполнение
        String query = "Select id, name, birthday, \"login_ID\", city, email, description from \"USER\" where \"login_ID\" = ? and name = ?";
        dbWorker.getQuery(c, query, "Ivan_i", "Иванов Иван Иванович"); //Параметризированная выборка
        dbWorker.manualTransaction(c); // работа с транзакциями и точками сохранения.
        c.close();
    }

}
