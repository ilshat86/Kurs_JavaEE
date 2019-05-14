package Part01.Lesson02.Task03;

/**
 * Исключение для одинакового возраста и имени
 */
public class TwinPersonException extends Exception {

    /**
     * Генерирует исключение
     *
     * @param message
     */
    public TwinPersonException(String message) {
        super(message);
    }
}
