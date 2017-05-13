package android.si3.unice.polytech.com.example.pierrerainero.firm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Convert an object to a byte array and a byte array to an object
 * Created by PierreRainero on 17/04/2017.
 */

public class Serializer {

    private Serializer(){
    }

    /**
     * Change an object (should implements Serializable) to a byte array
     * @param obj object to convert
     * @return the same data under the form of a byte array
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * Change a byte array to an object (should implements Serializable to be usable)
     * @param data byte array to convert
     * @return the same data as a java object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

}
