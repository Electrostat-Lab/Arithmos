package utils.natives;

import java.lang.reflect.Field;

/**
 * Helper utility for hardcoded paths.
 * 
 * @author pavl_g.
 */
public final class LibraryPath {
    /**
     * Creates a provisional path for the relative native lib path.
     * 
     * @author pavl_g.
     */
    public enum ProvisionalPath {
        /**
         * Creates a custom empty provisional path
         * Chain a call to setCustomPath to set the path manually : 
         * final ProvisionalPath pp = ProvisionalPath.CUSTOM.setCustomPath("/yourpath");
         */
        CUSTOM(""), 
        /**
         * Represents this path : /libs/Arithmos/linux-x86-x64
         */
        LIBS_FOLDER("/libs/Arithmos/linux-x86-x64"), 
        /**
         * Represents the root dir (user.dir) returned from the java system property.
         */
        ROOT_FOLDER("");

        public String path;

        ProvisionalPath(final String path) {
            this.path = path;
        }
        /**
         * Sets a custom provisional path.
         * Chain a call using CUSTOM object.
         * 
         * @param path the new path.
         */
        public final void setCustomPath(final String path) {
            this.path = path;
        }
    }

    private LibraryPath() {

    }

    public static void setLibraryRelativePath(final ProvisionalPath provisionalPath) throws NoSuchFieldException, IllegalAccessException {
        System.setProperty("java.library.path", getProjectRelativePath() + provisionalPath.path);
        final Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        // reload the sys_paths with the new path trick
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
    }

    private static String getProjectRelativePath() {
        return System.getProperty("user.dir");
    }
}