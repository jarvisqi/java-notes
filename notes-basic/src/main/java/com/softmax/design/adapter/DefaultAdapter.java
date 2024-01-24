package com.softmax.design.adapter;

import java.io.File;

/**
 * 接口
 */
interface FileAlterationListener {

    void onDirectoryCreate(final File directory);

    void onDirectoryChange(final File directory);

    void onDirectoryDelete(final File directory);

    void onFileCreate(final File file);

    void onFileChange(final File file);

    void onFileDelete(final File file);

}

/**
 * 简单适配器  默认适配器，最简单的
 * <p>
 * step 1, 接口的一大问题是抽象方法太多了，如果我们要用这个接口,意味着我们要实现每一个抽象方法，如果我们只是想要监控文件夹中的文件创建和文件删除事件，
 * 可是我们还是不得不实现所有的方法，很明显，这不是我们想要的
 * <p>
 * step2, 创建一个适配器 DefaultAdapter ，它用于实现上面的接口，但是所有的方法都是空方法
 * <p>
 * step2, 创建一个类，继承 DefaultAdapter 重写自己想要的方法即可
 *
 * @author Jarvis
 * @date 2018/7/27
 */
public class DefaultAdapter implements FileAlterationListener {

    @Override
    public void onDirectoryCreate(File directory) {

    }

    @Override
    public void onDirectoryChange(File directory) {

    }

    @Override
    public void onDirectoryDelete(File directory) {

    }

    @Override
    public void onFileCreate(File file) {

    }

    @Override
    public void onFileChange(File file) {

    }

    @Override
    public void onFileDelete(File file) {

    }
}

/**
 * 可以定义以下类，仅需要实现我们想实现的方法就可以了：
 */
class FileMonitor extends DefaultAdapter {

    @Override
    public void onFileCreate(File file) {
        System.out.println("File Create");
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("File Delete");
    }
}