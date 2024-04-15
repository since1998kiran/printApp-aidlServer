// IPrinterService.aidl
package com.imark.tmscall;

// Declare any non-default types here with import statements

interface IPrinterService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     boolean startPrinting( String imageBase64);
     int getPrinterValidWidth();
}