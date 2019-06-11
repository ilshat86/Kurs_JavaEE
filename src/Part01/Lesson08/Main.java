package Part01.Lesson08;

import javax.tools.*;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

/**
 * Основной класс
 */
public class Main {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String path = "src/Part01/Lesson08/SomeClass.java";
        Path rootPath = Paths.get("src/Part01/Lesson08");//"out/production/Kurs_JavaEE/Part01/Lesson08");

        int nn =0;
        while (true) {
            nn++;
            if (nn % 3 == 0) // будем просить обновлять код каждые 3 раза
            {
                System.out.println("Введите код: ");
                MyClass.write(path, MyClass.read());
            }
            useCustomClassLoader(rootPath);
            if (nn==10){ //прервем выполнение, т.к. загрузка продемонстрирована
                break;
            }
        }
    }

    /**
     *
     * @param rootPath
     * @throws Exception
     */
    private static void useCustomClassLoader(Path rootPath) throws Exception  {

            JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
            jc.run(null, null, null, "src/Part01/Lesson08/SomeClass.java");

            //URLClassLoader classLoaders = URLClassLoader.newInstance(new URL[]{rootPath.toUri().toURL()});
            ClassLoader classLoaders = new MyClass();
            Class<?> cls = classLoaders.loadClass("Part01.Lesson08.SomeClass");//Class.forName("Part01.Lesson08.SomeClass", true, classLoaders);
            Worker instance = (Worker) cls.newInstance();
            instance.doWork();
        }

}


