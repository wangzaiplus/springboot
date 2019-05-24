package com.wangzaiplus.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Serializable工具(JDK)
 */
public class SerializableUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("serializable异常: " + e.getMessage());
            throw new RuntimeException("serializable异常: " + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                LOGGER.error("serializable异常: " + e.getMessage());
                throw new RuntimeException("serializable异常: " + e.getMessage());
            }
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("unserializable异常: " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                LOGGER.error("unserializable异常: " + e.getMessage());
                throw new RuntimeException("unserializable异常: " + e.getMessage());
            }
        }
    }

}
