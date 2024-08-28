/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.fls.client.utility;

import java.io.*;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utility implements Constant {
    static Logger log = Logger.getLogger(Utility.class);

    public static Properties loadpropertFile() {
        log.info("Loading Properties Files....");
        final Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(System.getProperty(PROPERTYFILE)));
            try {
                prop.load(fis);
                fis.close();
            } catch (final IOException e) {
                log.error(e);
            }

        } catch (final FileNotFoundException e) {
            log.error(e);
        }
        log.info("Loading Properties File finished..");
        return prop;

    }

}
