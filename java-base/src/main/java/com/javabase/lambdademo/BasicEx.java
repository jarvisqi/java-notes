package com.javabase.lambdademo;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础
 *
 * @author : Jarvis
 * @date : 2018/5/29
 */
public class BasicEx {

    public static void StringDemo() {

        //分隔字符串
        String r1 = String.join(":", "foobar", "foo", "bar");
        System.out.println(r1);

        // chars从字符串所有字符创建数据流
        String r2 = "foobar:foo:bar"
                .chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted()
                .collect(Collectors.joining());

        System.out.println(r2);

        // 正则模式串可以转换为谓词
        Pattern pattern = Pattern.compile(".*@gmail\\.com");
        Long r3 = Stream.of("bob@gmail.com", "alice@hotmail.com")
                .filter(pattern.asPredicate())
                .count();
        System.out.println(r3);
    }

    private static void NumberDemo() {
        // 2147483647
        System.out.println(Integer.MAX_VALUE);
        // -2147483648
        System.out.println(Integer.MAX_VALUE + 1);
        //2 ** 31 - 1
        long maxUnsignedInt = (1L << 32) - 1;
        System.out.println(maxUnsignedInt);
        String string = String.valueOf(maxUnsignedInt);
        System.out.println(string);
        int unsignedInt = Integer.parseUnsignedInt(string, 10);
        System.out.println(unsignedInt);
        // 转十进制
        String string2 = Integer.toUnsignedString(unsignedInt, 10);
        System.out.println(string2);
        // 转二进制
        int unInt = 100;
        String str2 = Integer.toUnsignedString(unInt, 2);
        System.out.println(str2);

    }

    public static void FileDemo() throws IOException {

        //列出文件
        try (Stream<Path> stream = Files.list(Paths.get(""))) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> !path.startsWith("."))
                    .sorted()
                    .collect(Collectors.joining("; "));
            System.out.println("List: " + joined);
        }

        //查找在目录及其子目录下的文件
        Path start = Paths.get("");
        int maxDepth = 3;
        try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
                String.valueOf(path).endsWith(".js"))) {
            String joined = stream
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining(":"));
            System.out.println("Found: " + joined);
        }

        //读写文件
        //这些方法对内存并不十分高效，因为整个文件都会读进内存。文件越大，所用的堆区也就越大。
        List<String> lines = Files.readAllLines(Paths.get("res/xxx.js"));
        lines.add("xxxx.java");
        Files.write(Paths.get("res/aa.js"), lines);

        //Files.lines方法来作为内存高效的替代
        try (Stream<String> streamLines = Files.lines(Paths.get("res/xxx.js"))) {
            streamLines
                    .filter(lin -> lin.contains("xxx"))
                    .map(String::valueOf)
                    .forEach(System.out::println);
        }

        //写文件
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("res/xxx.js"))) {
            bufferedWriter.write("xxxx");
        }

        //读文件
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("res/xxx.js"))) {
            long countPrints = bufferedReader.lines()
                    .filter(l -> l.contains("xxx"))
                    .count();
            System.out.println(countPrints);
        }
    }


    public static void main(String[] args) {

        //StringDemo();
        NumberDemo();


    }


}
