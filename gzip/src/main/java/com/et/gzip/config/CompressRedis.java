package com.et.gzip.config;

import com.et.gzip.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class CompressRedis extends JdkSerializationRedisSerializer {

    public static final int BUFFER_SIZE = 4096;

    private JacksonRedisSerializer<User>  jacksonRedisSerializer;
    public CompressRedis() {
        this.jacksonRedisSerializer = getValueSerializer();
    }

    @Override
    public byte[] serialize(Object graph) throws SerializationException {
        if (graph == null) {
            return new byte[0];
        }
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            // serialize
            byte[] bytes = jacksonRedisSerializer.serialize(graph);
            log.info("bytes size{}",bytes.length);
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);

            // compress
            gzip.write(bytes);
            gzip.finish();
            byte[] result = bos.toByteArray();

            log.info("result size{}",result.length);
            //return result;
            return new BASE64Encoder().encode(result).getBytes();
        } catch (Exception e) {
            throw new SerializationException("Gzip Serialization Error", e);
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(gzip);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ByteArrayInputStream bis = null;
        GZIPInputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream();
            byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer( new String(bytes));;
            bis = new ByteArrayInputStream(compressed);
            gzip = new GZIPInputStream(bis);
            byte[] buff = new byte[BUFFER_SIZE];
            int n;


            // uncompress
            while ((n = gzip.read(buff, 0, BUFFER_SIZE)) > 0) {
                bos.write(buff, 0, n);
            }
            //deserialize
            Object result = jacksonRedisSerializer.deserialize(bos.toByteArray());
            return result;
        } catch (Exception e) {
            throw new SerializationException("Gzip deserizelie error", e);
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(gzip);
        }
    }

    private static JacksonRedisSerializer<User> getValueSerializer() {
        JacksonRedisSerializer<User> jackson2JsonRedisSerializer = new JacksonRedisSerializer<>(User.class);
        ObjectMapper mapper=new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

}

