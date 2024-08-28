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

package com.ericsson.oss.services.fls.lookup.util;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.SPACE;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.db.entity.PMRopInfo;
import com.ericsson.oss.services.fls.db.entity.ULSAInfo;
import com.ericsson.oss.services.fls.lookup.annotation.DefaultOrderingField;
import com.ericsson.oss.services.fls.lookup.annotation.DefaultedSelectedField;
import com.ericsson.oss.services.fls.lookup.annotation.MandatoryFilterField;
import com.ericsson.oss.services.fls.lookup.model.PmFileMetadata;
import com.ericsson.oss.services.fls.lookup.model.UlsaFileMetadata;

/**
 * Container with methods
 */
public class FileLookupMetadataUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileLookupMetadataUtil.class);

    private final static Map<Class<?>, LookUpMedataDataEntry> lookUpMedataDataEntries = new HashMap<>();
    private final static Map<Class<?>, Class<?>> classes = new HashMap<Class<?>, Class<?>>();

    static {
        /**
         * Initiate methods of {@link PMRopInfo} and {@link ULSAInfo}
         */
        classes.put(PMRopInfo.class, PmFileMetadata.class);
        classes.put(ULSAInfo.class, UlsaFileMetadata.class);

        for (final Class<?> clazz : classes.keySet()) {
            final LookUpMedataDataEntry lookUpMedataDataEntry = new LookUpMedataDataEntry();
            lookUpMedataDataEntries.put(clazz, lookUpMedataDataEntry);
            for (final Field field : (Field[]) ArrayUtils.addAll(clazz.getSuperclass().getDeclaredFields(), clazz.getDeclaredFields())) {
                final String fieldName = field.getName();
                try {
                    lookUpMedataDataEntry.accessorMethodsMap.put(fieldName, new PropertyDescriptor(fieldName, clazz).getReadMethod());
                } catch (final IntrospectionException exception) {
                    logger.error("Unable to initiate setter method of {}", fieldName);
                }
            }

            for (final Field field : (Field[]) ArrayUtils.addAll(classes.get(clazz).getSuperclass().getDeclaredFields(),
                    classes.get(clazz).getDeclaredFields())) {
                final String fieldName = field.getName();
                /**
                 * Initiate methods of {@link FileMetadataPm} and {@link FileMetadataUlsa}
                 */
                try {
                    lookUpMedataDataEntry.mutatorMethodsMap.put(fieldName, new PropertyDescriptor(fieldName, classes.get(clazz)).getWriteMethod());
                } catch (final IntrospectionException exception) {
                    logger.error("Unable to initiate setter method of {}", fieldName);
                }

                /**
                 * Initiation of default response fields names ; mandatory filter fields names ; default order by field name ; accessors methods ;
                 * mutators
                 * methods
                 */
                if (field.isAnnotationPresent(DefaultedSelectedField.class)) {
                    lookUpMedataDataEntry.defaultSelectedFields.add(fieldName);
                }
                if (field.isAnnotationPresent(MandatoryFilterField.class)) {
                    lookUpMedataDataEntry.mandatoryFilterFields.add(fieldName);
                }
                if (field.isAnnotationPresent(DefaultOrderingField.class)) {
                    lookUpMedataDataEntry.defaultOrderByFields.add(fieldName + SPACE + field.getAnnotation(DefaultOrderingField.class).orderType());
                }
            }
            logger.info(
                    "Default selected fields initialized : {} ; Mandatory filter fields initialized : {} ; Default orderBy fields initialized : {}",
                    lookUpMedataDataEntry.defaultSelectedFields, lookUpMedataDataEntry.mandatoryFilterFields,
                    lookUpMedataDataEntry.defaultOrderByFields);
        }
    }

    /**
     * Check if {@link PMRopInfo} or {@link ULSAInfo} contains field
     *
     * @param fieldName
     *            name of model field
     * @return
     */
    public static boolean isFileMetadataField(final Class<?> clazz, final String fieldName) {
        return getLookUpMedataDataEntry(clazz).accessorMethodsMap.containsKey(fieldName);
    }

    /**
     * Get accessor method of {@link PMRopInfo} and {@link ULSAInfo}
     *
     * @param fieldName
     *            name of model field
     * @return
     */
    public static Method getFileMetadataAccessor(final Class<?> clazz, final String fieldName) {
        return getLookUpMedataDataEntry(clazz).accessorMethodsMap.get(fieldName);
    }

    /**
     * Get mutator method of {@link PmFileMetadata} and {@link UlsaFileMetadata}
     *
     * @param fieldName
     *            name of model field
     * @return
     */
    public static Method getFileMetadataMutator(final Class<?> clazz, final String fieldName) {
        return getLookUpMedataDataEntry(clazz).mutatorMethodsMap.get(fieldName);
    }

    /**
     * Get list of default response fields names
     *
     * @return list of default response fields
     */
    public static List<String> getDefaultSelectedFields(final Class<?> clazz) {
        return Collections.unmodifiableList(getLookUpMedataDataEntry(clazz).defaultSelectedFields);
    }

    /**
     * Get list of mandatory filter fields names
     *
     * @return mandatory filter fields names
     */
    public static List<String> getMandatoryFilterFieldsList(final Class<?> clazz) {
        return Collections.unmodifiableList(getLookUpMedataDataEntry(clazz).mandatoryFilterFields);
    }

    /**
     * Get default order field name
     *
     * @return default orderBy fields with sort type
     */
    public static String[] getDefaultOrderByFieldName(final Class<?> clazz) {
        return getLookUpMedataDataEntry(clazz).defaultOrderByFields.toArray(new String[getLookUpMedataDataEntry(clazz).defaultOrderByFields.size()]);
    }

    public static Class<?> getMetaDataClassFromEntryClass(final Class<?> clazz) {
        return classes.get(clazz);
    }

    private static LookUpMedataDataEntry getLookUpMedataDataEntry(final Class<?> clazz) {
        return lookUpMedataDataEntries.get(clazz);
    }

    static class LookUpMedataDataEntry {
        final List<String> defaultSelectedFields = new ArrayList<>();
        final List<String> mandatoryFilterFields = new ArrayList<>();
        final List<String> defaultOrderByFields = new ArrayList<>();

        final Map<String, Method> accessorMethodsMap = new HashMap<>();
        final Map<String, Method> mutatorMethodsMap = new HashMap<>();
    }
}
