import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SerializeRunner {

    public static void main(String[] args) {
        final String path = "/tmp/SerializableClass.ser";  // for linux

        SerializableClass objIn = new SerializableClass(0b11, "some text", 30, 50L);
        SerializableClass objTemp = null;
        SerializableClass objOut = null;

        Class<?> cls = SerializableClass.class;
        Field[] fields = cls.getDeclaredFields();

        try {
            objTemp = (SerializableClass) cls.getConstructor().newInstance();
            for(Field field : fields) {
                if(field.isAnnotationPresent(Save.class)) {
                    if(field.isAccessible()) {
                       Object value = field.get(objIn);
                        field.set(objTemp, value);
                    } else {
                        field.setAccessible(true);
                        Object value = field.get(objIn);
                        field.set(objTemp, value);
                        field.setAccessible(false);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(objTemp);
         } catch (IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
            objOut = (SerializableClass) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("serialized object: " + objIn.toString());
        System.out.println();
        if(objOut != null) {
            System.out.println("deserialized object: " + objOut.toString());
        }

    }
}
