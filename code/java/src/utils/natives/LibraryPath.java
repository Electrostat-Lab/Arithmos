package utils.natives;
import java.lang.reflect.Field;

public final class LibraryPath {
    public static void setLibraryRelativePath(final String libPath) throws NoSuchFieldException, IllegalAccessException {
        System.setProperty("java.library.path", getProjectRelativePath() + libPath);
        final Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        // reload the sys_paths with the new path trick
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
    }

    private static String getProjectRelativePath() {
        return System.getProperty("user.dir");
    }
}