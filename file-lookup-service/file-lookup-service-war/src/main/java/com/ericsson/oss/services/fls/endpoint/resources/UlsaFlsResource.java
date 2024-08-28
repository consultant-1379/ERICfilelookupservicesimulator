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

package com.ericsson.oss.services.fls.endpoint.resources;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.AUTHORIZATION_POLICY_ACTION_READ;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.AUTHORIZATION_POLICY_NAME;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.ericsson.oss.services.fls.endpoint.api.ResponseError;
import com.ericsson.oss.services.fls.exceptions.FlsDbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.itpf.sdk.security.accesscontrol.annotation.Authorize;
import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.api.FlsDataType;
import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.dao.interfaces.UlsaInfoDao;
import com.ericsson.oss.services.fls.db.entity.ULSAInfo;
import com.ericsson.oss.services.fls.endpoint.api.ResponseWrapper;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;
import com.ericsson.oss.services.fls.lookup.mapper.FileLookupQueryResultToResponseMapper;
import com.ericsson.oss.services.fls.lookup.mapper.FileLookupRequestToQueryMapper;
import com.ericsson.oss.services.fls.lookup.model.UlsaFileMetadata;
import com.ericsson.oss.services.fls.lookup.validation.FileLookupRequestValidationProcessor;

/**
 * ULSA Fls Resource
 *
 * @author elucbol.
 */
@ApplicationScoped
public class UlsaFlsResource implements FlsResource {

    private static final Logger logger = LoggerFactory.getLogger(UlsaFlsResource.class);

    @Inject
    private FileLookupRequestValidationProcessor validator;

    @Inject
    private FileLookupRequestToQueryMapper<ULSAInfo> requestToQueryMapper;

    @Inject
    private FileLookupQueryResultToResponseMapper<ULSAInfo, UlsaFileMetadata> queryResultToResponseMapper;

    @Inject
    private UlsaInfoDao ulsaInfoDao;

    @Override
    public FlsDataType getFlsDataType() {
        return FlsDataType.ULSA;
    }

    @Override
    @Authorize(resource = AUTHORIZATION_POLICY_NAME, action = AUTHORIZATION_POLICY_ACTION_READ)
    public Response getResponse(final FileLookupRequest fileLookupRequest) throws FileLookupException {
        logger.debug("ULSA Fls Resource is processing the request.");

        validator.processValidation(fileLookupRequest);

        final FLSQuery flsQuery = requestToQueryMapper.mapRequestToQuery(fileLookupRequest);

        logger.debug("File Lookup Service Request: fileLookupRequest: {} FLSQuery : {}", fileLookupRequest, flsQuery);

        final List<ULSAInfo> fileMetaDataList;
        try {
            fileMetaDataList = ulsaInfoDao.findUlsaInfoList(flsQuery);
        } catch (FlsDbException e) {
            return Response.status(SERVICE_UNAVAILABLE).entity(new ResponseError(SERVICE_UNAVAILABLE,
                    "File Lookup Service in currently unavailable. Please try later.")).build();
        }
        final List<UlsaFileMetadata> resultList =
                queryResultToResponseMapper.mapQueryResultToResponse(fileMetaDataList, fileLookupRequest);

        final ResponseWrapper responseWrapper = new ResponseWrapper(resultList);
        return Response.status(Response.Status.OK).entity(responseWrapper).build();
    }
}
