/*----------------------------------------------------------------------
    FILE        : IpUtil.java
    AUTHOR      : Oğuz Karan
    LAST UPDATE : 13th Mar 2024

    Utility class for IP family

    Copyleft (c) 1993 by C and System Programmers Association (CSD)
    All Rights Free
-----------------------------------------------------------------------*/
package com.karandev.util.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.OptionalInt;

public final class IpUtil {
    private IpUtil() {}

    public static boolean isPortAvailable(int port)
    {
        var result = false;

        try (var socket = new ServerSocket(port)) {
            result = true;
        }
        catch (IOException ignore) {

        }

        return result;
    }

    public static OptionalInt getFirstAvailablePort(int minPort, int maxPort)
    {
        for (var port = minPort; port <= maxPort; ++port)
            if (isPortAvailable(port))
                return OptionalInt.of(port);

        return OptionalInt.empty();
    }


    public static OptionalInt getFirstAvailablePort(int...ports)
    {
        for (var port : ports)
            if (isPortAvailable(port))
                return OptionalInt.of(port);

        return OptionalInt.empty();
    }
}
