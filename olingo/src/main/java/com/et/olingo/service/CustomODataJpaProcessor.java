package com.et.olingo.service;

import com.et.olingo.entity.Child;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.*;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class CustomODataJpaProcessor extends ODataJPADefaultProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CustomODataJpaProcessor(ODataJPAContext oDataJPAContext) {
        super(oDataJPAContext);
    }

    @Override
    public ODataResponse readEntitySet(final GetEntitySetUriInfo uriParserResultView, final String contentType) throws ODataJPAModelException, ODataJPARuntimeException, EdmException {
        logger.info("READ: Entity Set {} called", uriParserResultView.getTargetEntitySet().getName());
        try {
            List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
            return responseBuilder.build(uriParserResultView, jpaEntities, contentType);
        } finally {
            this.close();
        }
    }

    @Override
    public ODataResponse readEntity(final GetEntityUriInfo uriParserResultView, final String contentType) throws ODataJPAModelException, ODataJPARuntimeException, ODataNotFoundException, EdmException {
        ODataResponse response = null;
        if (uriParserResultView.getKeyPredicates().size() > 1) {
            logger.info("READ: Entity {} called with key {} and key {}", uriParserResultView.getTargetEntitySet().getName(), uriParserResultView.getKeyPredicates().get(0).getLiteral(), uriParserResultView.getKeyPredicates().get(1).getLiteral());
        } else {
            logger.info("READ: Entity {} called with key {}", uriParserResultView.getTargetEntitySet().getName(), uriParserResultView.getKeyPredicates().get(0).getLiteral());
        }
        try {
            Object readEntity = jpaProcessor.process(uriParserResultView);
            response = responseBuilder.build(uriParserResultView, readEntity, contentType);
        } finally {
            this.close();
        }
        return response;
    }

    @Override
    public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content, final String requestContentType, final String contentType) throws ODataJPAModelException, ODataJPARuntimeException, ODataNotFoundException, EdmException, EntityProviderException {
        logger.info("POST: Entity {} called", uriParserResultView.getTargetEntitySet().getName());
        ODataResponse response = null;
        try {
            Object createdEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
            if (createdEntity.getClass().equals(Child.class)) {
                response = postProcessCreateChild(createdEntity, uriParserResultView, contentType);
            } else {
                response = responseBuilder.build(uriParserResultView, createdEntity, contentType);
            }
        } finally {
            this.close();
        }
        return response;
    }


    @Override
    public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
                                      final String requestContentType, final boolean merge, final String contentType) throws ODataException, ODataJPAModelException, ODataJPARuntimeException, ODataNotFoundException {
        logger.info("PUT: Entity {} called with key {}", uriParserResultView.getTargetEntitySet().getName(), uriParserResultView.getKeyPredicates().get(0).getLiteral());
        ODataResponse response = null;
        try {
            Object updatedEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
            response = responseBuilder.build(uriParserResultView, updatedEntity);
        } finally {
            this.close();
        }
        return response;
    }

    @Override
    public ODataResponse deleteEntity(DeleteUriInfo uriParserResultView, String contentType) throws ODataException {
        logger.info("DELETE: Entity {} called with key {}", uriParserResultView.getTargetEntitySet().getName(), uriParserResultView.getKeyPredicates().get(0).getLiteral());
        ODataResponse oDataResponse = null;
        try {
            this.oDataJPAContext.setODataContext(this.getContext());
            Object deletedEntity = this.jpaProcessor.process(uriParserResultView, contentType);
            oDataResponse = this.responseBuilder.build(uriParserResultView, deletedEntity);
        } finally {
            this.close();
        }
        return oDataResponse;
    }

    private ODataResponse postProcessCreateChild(Object createdEntity, PostUriInfo uriParserResultView, String contentType) throws ODataJPARuntimeException, ODataNotFoundException {
        Child child = (Child) createdEntity;
        if (child.getSurname() == null || child.getSurname().equalsIgnoreCase("")) {
            if (child.getMother().getSurname() != null && !child.getMother().getSurname().equalsIgnoreCase("")) {
                child.setSurname(child.getMother().getSurname());
            } else if (child.getMother().getSurname() != null && !child.getFather().getSurname().equalsIgnoreCase("")) {
                child.setSurname(child.getFather().getSurname());
            } else {
                child.setSurname("Gashi");
            }
        }
        return responseBuilder.build(uriParserResultView, createdEntity, contentType);
    }

}
