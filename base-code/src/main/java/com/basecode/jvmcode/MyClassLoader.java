package com.basecode.jvmcode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义类加载器
 *
 * @author Jarvis
 * @date 2018/8/20
 */
public class MyClassLoader extends ClassLoader {

    private String fileName;

    public MyClassLoader(String fileName) {
        this.fileName = fileName;
    }

    public MyClassLoader(ClassLoader loader) {
        super(loader);
    }

    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass loadClass} method after checking the
     * parent class loader for the requested class.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting {@code Class} object
     * @throws ClassNotFoundException If the class could not be found
     * @implSpec The default implementation throws {@code ClassNotFoundException}.
     * @since 1.2
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = getClassFile();
        try {
            byte[] bytes = getClassBytes(file);
            Class<?> cls = this.defineClass(name, bytes, 0, bytes.length);
            return cls;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private File getClassFile() {
        File file = new File(this.fileName);
        return file;
    }

    /**
     * 读入.class的字节，因此要使用字节流
     * * 一个Buffer对象是固定数量的数据的容器。其作用是一个存储器,或者分段运输区,在这里数据可被存储并在之后用于检索。
     * * 尽管缓冲区作用于它们存储的原始数据类型,但缓冲区十分倾向于处理字节。
     * NIO里，一个通道（channel）可以表示任何可以读写的对象。它的作用是为文件和套接口提供抽象
     *
     * @param file
     * @return
     * @throws IOException
     */
    private byte[] getClassBytes(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        //通道(Channel)是一种途径,借助该途径,可以用最小的总开销来访问操作系统本身的 I/O 服务。
        //缓冲区(Buffer)则是通道内部用来发送和接收数据的端点。通道channel充当连接I/O服务的导管
        FileChannel channel = inputStream.getChannel();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // WritableByteChannel 接口以提供 write( )方法
        WritableByteChannel byteChannel = Channels.newChannel(outputStream);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            int i = channel.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();  //写模式转换成读模式

            byteChannel.write(buffer);
            buffer.clear();
        }
        inputStream.close();
        return outputStream.toByteArray();
    }


}
