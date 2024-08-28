/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.fls.db.util;

import com.ericsson.oss.services.fls.db.entity.PMRopInfo;

import java.util.Comparator;

/**
 * A comparator that compares PMRopInfo based on nodeName.
 *
 * @author EniqTopologyService
 */
public class NodeNameComparator implements Comparator<PMRopInfo> {

    @Override
    public int compare(final PMRopInfo pmRopInfo1, final PMRopInfo pmRopInfo2) {
        if (pmRopInfo1 == null || pmRopInfo2 == null) {
            return -1;
        }
        if (pmRopInfo1 == pmRopInfo2) {
            return 0;
        }
        return pmRopInfo1.getNodeName().compareTo(pmRopInfo2.getNodeName());
    }
}
