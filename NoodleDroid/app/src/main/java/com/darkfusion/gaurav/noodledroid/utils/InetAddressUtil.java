package com.darkfusion.gaurav.noodledroid.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InetAddressUtil {

    public static String getInetIPAddress() {
        Enumeration<NetworkInterface> networkInterfaces = null;

        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ignored) {
        }

        if(networkInterfaces == null){
            return null;
        }
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                String hostAddress = inetAddress.getHostAddress();

                if (isValidPrivateInetIPv4Address(hostAddress)) {
                    return hostAddress;
                }
            }
        }

        return null;
    }

    /**
     * RFC1918 name	    IP address range	                Host ID size
     * <p>
     * 24-bit block	    10.0.0.0 – 10.255.255.255	        24 bits
     * 20-bit block	    172.16.0.0 – 172.31.255.255	        20 bits
     * 16-bit block	    192.168.0.0 – 192.168.255.255	    16 bits
     */

    private static boolean isValidPrivateInetIPv4Address(String address) {
        String[] dotSeparatedFields = address.split("\\.");
        if (dotSeparatedFields.length != 4) {
            return false;
        }

        return validate16BitBlock(dotSeparatedFields) || validate20BitBlock(dotSeparatedFields) || validate24BitBlock(dotSeparatedFields);
    }

    private static boolean validate24BitBlock(String[] dotSeparatedFields) {
        if (!dotSeparatedFields[0].equals("10")) {
            return false;
        }

        boolean isValid = true;

        for (int i = 1; i < dotSeparatedFields.length; i++) {
            String field = dotSeparatedFields[i];
            int fieldInt;

            try {
                fieldInt = Integer.parseInt(field);
            } catch (NumberFormatException nfe) {
                return false;
            }
            if (fieldInt < 0 || fieldInt > 255) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    private static boolean validate20BitBlock(String[] dotSeparatedFields) {
        if (!dotSeparatedFields[0].equals("172")) {
            return false;
        }

        boolean isValid = true;

        for (int i = 2; i < dotSeparatedFields.length; i++) {
            String field = dotSeparatedFields[i];

            int fieldInt;
            try {
                fieldInt = Integer.parseInt(field);
            } catch (NumberFormatException nfe) {
                return false;
            }
            if (fieldInt < 0 || fieldInt > 255) {
                isValid = false;
                break;
            }
        }

        return isValid;

    }

    private static boolean validate16BitBlock(String[] dotSeparatedFields) {
        if (dotSeparatedFields.length != 4) {
            return false;
        }
        if (!dotSeparatedFields[0].equals("192")) {
            return false;
        }

        if (!dotSeparatedFields[1].equals("168")) {
            return false;
        }


        boolean isValid = true;

        for (String field : dotSeparatedFields) {
            int fieldInt;
            try {
                fieldInt = Integer.parseInt(field);
            } catch (NumberFormatException nfe) {
                return false;
            }
            if (fieldInt < 0 || fieldInt > 255) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
