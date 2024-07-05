package com.et.olingo.service;

import com.et.olingo.config.JerseyConfig;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

public class CustomODataServiceFactory extends ODataServiceFactory {

    private ODataJPAContext oDataJPAContext;
    private ODataContext oDataContext;

    @Override
    public final ODataService createService(final ODataContext context) throws ODataException {
        oDataContext = context;
        oDataJPAContext = initializeODataJPAContext();

        validatePreConditions();

        ODataJPAFactory factory = ODataJPAFactory.createFactory();
        ODataJPAAccessFactory accessFactory = factory.getODataJPAAccessFactory();

        if (oDataJPAContext.getODataContext() == null) {
            oDataJPAContext.setODataContext(context);
        }

        ODataSingleProcessor oDataSingleProcessor = new CustomODataJpaProcessor(
                oDataJPAContext
        );


        EdmProvider edmProvider = accessFactory.createJPAEdmProvider(oDataJPAContext);
        return createODataSingleProcessorService(edmProvider, oDataSingleProcessor);

    }

    private void validatePreConditions() throws ODataJPARuntimeException {
        if (oDataJPAContext.getEntityManager() == null) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ENTITY_MANAGER_NOT_INITIALIZED, null);
        }
    }

    public final ODataJPAContext getODataJPAContext()
            throws ODataJPARuntimeException {
        if (oDataJPAContext == null) {
            oDataJPAContext = ODataJPAFactory.createFactory()
                    .getODataJPAAccessFactory().createODataJPAContext();
        }
        if (oDataContext != null)
            oDataJPAContext.setODataContext(oDataContext);
        return oDataJPAContext;

    }

    protected ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = this.getODataJPAContext();
        ODataContext oDataContext = oDataJPAContext.getODataContext();


        HttpServletRequest request = (HttpServletRequest) oDataContext.getParameter(
                ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
        EntityManager entityManager = (EntityManager) request
                .getAttribute(JerseyConfig.EntityManagerFilter.EM_REQUEST_ATTRIBUTE);
        oDataJPAContext.setEntityManager(entityManager);


        oDataJPAContext.setPersistenceUnitName("default");
        oDataJPAContext.setContainerManaged(true);
        return oDataJPAContext;
    }
}
