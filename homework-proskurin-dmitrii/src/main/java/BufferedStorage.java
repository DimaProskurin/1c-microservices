import ru.mipt1c.homework.task1.KeyValueStorage;
import ru.mipt1c.homework.task1.MalformedDataException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

public class BufferedStorage<K, V> implements KeyValueStorage<K, V> {
    private final HashMap<K, V> buffer;
    private final Path storagePath;
    private boolean is_closed = false;

    public BufferedStorage(String storageDir) throws IOException, ClassNotFoundException {
        if (!Files.exists(Paths.get(storageDir))) {
            throw new IllegalArgumentException(String.format("%s does not exist", storageDir));
        }
        this.storagePath = Paths.get(storageDir, "storage.bin");
        if (!Files.exists(this.storagePath)) {
            Files.createFile(this.storagePath);
            this.buffer = new HashMap<>();
        } else {
            FileInputStream fileInputStream = new FileInputStream(String.valueOf(this.storagePath));
            ObjectInputStream objectInputStream;
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
            } catch (StreamCorruptedException e) {
                throw new MalformedDataException(e);
            }
            this.buffer = (HashMap<K, V>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
    }

    @Override
    public V read(K key) {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        return this.buffer.get(key);
    }

    @Override
    public boolean exists(K key) {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        return this.buffer.containsKey(key);
    }

    @Override
    public void write(K key, V value) {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        this.buffer.put(key, value);
    }

    @Override
    public void delete(K key) {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        this.buffer.remove(key);
    }

    @Override
    public Iterator<K> readKeys() {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        return this.buffer.keySet().iterator();
    }

    @Override
    public int size() {
        if (this.is_closed) {
            throw new ClosedStorageException();
        }
        return this.buffer.size();
    }

    @Override
    public void close() throws IOException {
        this.is_closed = true;
        this.flush();
    }

    @Override
    public void flush() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(this.storagePath));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.buffer);
        objectOutputStream.close();
        fileOutputStream.close();
    }
}
