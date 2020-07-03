import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IODemo1 {
    public static void main1(String[] args) throws IOException {
        // 文件操作
        File file = new File("d:/test.txt");
        System.out.println("文件是否存在: " + file.exists());
        System.out.println("文件是否是普通文件: " + file.isFile());
        System.out.println("文件是否是目录: " + file.isDirectory());
        file.delete(); // 删除文件
        System.out.println("文件是否存在: " + file.exists());
        file.createNewFile();
        // 当且仅当具有该名称的文件尚不存在时，原子地创建一个由该抽象路径名命名的新的空文件
        System.out.println(File.separator);
        // File.separator: 代表系统目录中的间隔符(比如: /)

        // 目录操作
        File file1 = new File("d:/test_dir/1/2/3/4");
        System.out.println(file1.exists());
        System.out.println(file1.isDirectory());
        System.out.println(file1.getParent());
        // file1.getParent(): 返回此抽象路径名的父 null 的路径名字符串，如果此路径名未命名为父目录，则返回 null

        // listFiles
        // 返回一个抽象路径名数组，表示由该抽象路径名表示的目录中的文件
        File file2 = new File("d:/test_dir");
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f);
        }
        listAllFiles(file);
    }

    // 递归的罗列出一个目录中的所有文件.
    private static void listAllFiles(File f) {
        if (f.isDirectory()) {
            // 如果是目录, 就把目录中包含的文件都罗列出来.
            File[] files = f.listFiles();
            for (File file : files) {
                listAllFiles(file);
            }
        } else {
            // 把这个文件的路径直接打印出来.
            System.out.println(f);
        }
    }

    public static void main(String[] args) throws IOException {
        // 复制文件(需要区分字节流还是字符流)
        copyFile("d:/test_dir/rose.jpg", "d:/test_dir/rose2.jpg");
    }

    private static void copyFile(String srcPath, String destPath) throws IOException {
        // 字节流

        // 0. 先打开文件, 才能够读写. (创建 InputStream / OutputStream 对象的过程)
        FileInputStream fileInputStream = new FileInputStream(srcPath);
        FileOutputStream fileOutputStream = new FileOutputStream(destPath);
        // 1. 需要读取 srcPath 对应的文件内容.
        byte[] buffer = new byte[1024];
        // 单次读取的内容是存在上限(缓冲区的长度), 要想把文件整个都读完, 需要搭配循环来使用.
        int len = -1;
        // 如果读到的 len 是 -1 说明读取结束. 循环就结束了.
        while ((len = fileInputStream.read(buffer)) != -1) {
            // 读取成功, 就把读到的内容写入到另外一个文件中即可.
            // 因为 len 的值不一定就是和缓冲区一样长~
            // 2. 把读取到的内容写入到 destPath 对应的文件中.
            fileOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    private static void copyFile2(String srcPath, String destPath) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 0. 先打开文件, 才能够读写. (创建 InputStream / OutputStream 对象的过程)
            fileInputStream = new FileInputStream(srcPath);
            fileOutputStream = new FileOutputStream(destPath);
            // 1. 需要读取 srcPath 对应的文件内容.
            byte[] buffer = new byte[1024];
            // 单次读取的内容是存在上限(缓冲区的长度), 要想把文件整个都读完, 需要搭配循环来使用.
            int len = -1;
            // 如果读到的 len 是 -1 说明读取结束. 循环就结束了.
            while ((len = fileInputStream.read(buffer)) != -1) {
                // 读取成功, 就把读到的内容写入到另外一个文件中即可.
                // 因为 len 的值不一定就是和缓冲区一样长~
                // 2. 把读取到的内容写入到 destPath 对应的文件中.
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyFile3() {
        // 当代码写成这个样子的时候, 就不需要显式调用 close 了.
        // try 语句会在代码执行完毕后, 自动调用 close 方法. (前提是这个类必须要实现 Closable 接口)
        try (FileInputStream fileInputStream = new FileInputStream("d:/test_dir/rose.jpg");
             FileOutputStream fileOutputStream = new FileOutputStream("d:/test_dir/rose2.jpg")) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
