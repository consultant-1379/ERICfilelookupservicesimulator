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

package com.ericsson.oss.services.fls.endpoint.rest;

import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.FILTER;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.FLS_APPLICATION_END_POINT;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.FLS_APPLICATION_PATH;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.HAL_JSON;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.LIMIT;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.OFFSET;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.ORDER_BY;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.SELECT;

//import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.api.exception.FlsDataTypeException;
import com.ericsson.oss.services.fls.endpoint.api.ResponseError;
import com.ericsson.oss.services.fls.endpoint.resources.FlsResource;
import com.ericsson.oss.services.fls.endpoint.resources.FlsResourceSelector;
//import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;



/**
 * End point class for File Lookup Service
 */
//@Path(FLS_APPLICATION_END_POINT)
@Path(FLS_APPLICATION_PATH)
public class FileLookupEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(FileLookupEntryPoint.class);

    @Inject
    private FlsResourceSelector flsResourceSelector;

    /**
     * <pre>
     * GET method to fetch file lookup requests.
     * Example PM URL : http://<hostname>/file/v1/files/?select=id,nodeName&filter=dataType=="PM*"&orderBy=fileCreationTimeInOss desc
     * Example ULSA URL : http://<hostname>/file/v1/files/?select=id,nodeName&filter=dataType=="ULSA"&orderBy=fileCreationTimeInOss desc
     * </pre>
     *
     * @param select
     *            the select request parameter
     * @param filter
     *            the filter request parameter
     * @param limit
     *            the limit request parameter
     * @param orderBy
     *            the orderBy request parameter
     * @return response to a client with results or error code
     */
    @GET
    @Produces({ HAL_JSON })
    public Response getResultByFilters(@QueryParam(SELECT) final String select,
            @QueryParam(FILTER) final String filter, @QueryParam(LIMIT) final String limit,
            @QueryParam(OFFSET) final String offset, @QueryParam(ORDER_BY) final String orderBy)
            throws FileLookupException {

        final long startTime = System.currentTimeMillis();
        final FileLookupRequest fileLookupRequest = new FileLookupRequest(select, filter, limit, offset, orderBy);
        logger.debug("Filter: {} ", fileLookupRequest.getFilter());

        final Response response = getFilesMetadata(fileLookupRequest);
        final long endTime = System.currentTimeMillis();
        logger.debug("FLS Request processing took {} milliseconds.", endTime - startTime);
        return response;
    }

    public Response getFilesMetadata(final FileLookupRequest fileLookupRequest) throws FileLookupException {
        logger.info("New file lookup service request : {}", fileLookupRequest.toString());
        return flsResourceSelector.select(fileLookupRequest).getResponse(fileLookupRequest);
    }

}
