package com.progralink.jinout;

public final class JavaVersion {
    public static final String FULL;
    public static final int MAJOR;
    public static final int MINOR;
    public static final int REVISION;

    static {
        String ver = System.getProperty("java.version");
        if (ver.startsWith("1.")) {
            ver = ver.substring(2);
            ver = ver.replace(".", "_");
        }

        FULL = ver;

        String[] parts = ver.split("\\.");
        MAJOR = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
        MINOR = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        REVISION = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
    }

    private JavaVersion() { }

}
