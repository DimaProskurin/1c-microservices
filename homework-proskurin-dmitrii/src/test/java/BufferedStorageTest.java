import ru.mipt1c.homework.task1.KeyValueStorage;
import ru.mipt1c.homework.task1.MalformedDataException;
import ru.mipt1c.homework.tests.task1.AbstractSingleFileStorageTest;
import ru.mipt1c.homework.tests.task1.Student;
import ru.mipt1c.homework.tests.task1.StudentKey;

import java.io.IOException;

public class BufferedStorageTest extends AbstractSingleFileStorageTest {

    @Override
    protected KeyValueStorage<String, String> buildStringsStorage(String path) throws MalformedDataException {
        KeyValueStorage<String, String> bufferedStorage = null;
        try {
            bufferedStorage = new BufferedStorage<>(path);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedStorage;
    }

    @Override
    protected KeyValueStorage<Integer, Double> buildNumbersStorage(String path) throws MalformedDataException {
        KeyValueStorage<Integer, Double> bufferedStorage = null;
        try {
            bufferedStorage = new BufferedStorage<>(path);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedStorage;
    }

    @Override
    protected KeyValueStorage<StudentKey, Student> buildPojoStorage(String path) throws MalformedDataException {
        KeyValueStorage<StudentKey, Student> bufferedStorage = null;
        try {
            bufferedStorage = new BufferedStorage<>(path);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedStorage;
    }
}
