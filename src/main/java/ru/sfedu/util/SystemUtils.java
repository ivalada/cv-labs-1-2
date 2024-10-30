package ru.sfedu.util;

import java.util.Locale;

import static ru.sfedu.constant.Constants.*;

public class SystemUtils {

    public static OperatingSystem getOperationSystem() {
        String osName = System.getProperty(OS_NAME_SYSTEM_PROPERTY_NAME, "undefined").toLowerCase(Locale.ENGLISH);

        if ( osName.contains("mac") || osName.contains("darwin") )
            return OperatingSystem.MACOS;

        if ( osName.contains("win") )
            return OperatingSystem.WINDOWS;

        if ( osName.contains("nux") )
            return OperatingSystem.LINUX;

        return OperatingSystem.UNDEFINED;
    }
}
