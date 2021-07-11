package javautils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具
 */
public final class ClassUtils {

    /**
     * 私有构造防止实例化该工具类，反射创建时也会报错
     */
    private ClassUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param isInitialized 是否执行类的静态代码块，为了提高加载类的性能，isInitialized 设置为 false
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("load class failure", e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            // 获取指定包下面的所有 url
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) {
                    return classSet;
                }

                String protocol = url.getProtocol();
                // 将 file 和 jar 里面的类提取出来并加载
                if (protocol.equals("file")) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if (protocol.equals("jar")) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (jarURLConnection == null) {
                        continue;
                    }

                    JarFile jarFile = jarURLConnection.getJarFile();

                    if (jarFile == null) {
                        continue;
                    }

                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        String jarEntryName = jarEntries.nextElement().getName();
                        if (jarEntryName.endsWith(".class")) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                            doAddClass(classSet, className);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("get class failure", e);
        }
        return classSet;
    }

    /**
     * 将指定包下的所有类添加到 classSet 中
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        // 过滤指定包路径下的文件（返回值为 true 的 file 才会被保留），保留后缀名为 class 的文件和普通文件夹
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            // 判断是文件还是文件夹
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (TextUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (TextUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + File.pathSeparator + subPackagePath;
                }
                String subPackageName = fileName;
                if (TextUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 加载指定类名，并将 Class 添加到 classSet 中
     */
    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
