package org.gy.framework.util.auth.storage;

import java.util.Map;

import org.gy.framework.util.auth.principal.AuthPrincipal;

public interface StorageAccessor {

    /**
     * get principal from the storage by the sessionIdentifier.
     * 
     * @param sessionIdentifier identifier of that login
     * @return the user information
     */
    AuthPrincipal getPrincipal(String sessionIdentifier);

    /**
     * save the user information to the storage.
     * 
     * @param sessionIdentifier identifier of that login
     * @param principal the user information
     */
    void savePrincipal(String sessionIdentifier,
                       AuthPrincipal principal);

    /**
     * remove the user information.
     * 
     * @param sessionIdentifier identifier of that login
     */
    void removePrincipal(String sessionIdentifier);

    /**
     * save the POST data to the storage.
     * 
     * @param snapshotId the identifier of the POST data.
     * @param paramsSnapshot the map of POST data
     */
    void saveRequestParamsSnapshot(String snapshotId,
                                   Map paramsSnapshot);

    /**
     * get the POST data from the storage.
     * 
     * @param snapshotId the identifier of the POST data.
     * @return the map of POST data
     */
    Map getRequestParamsSnapshot(final String snapshotId);

    /**
     * remove the POST data from the storage.
     * 
     * @param snapshotId the identifier of the POST data.
     */
    void removeRequestParamsSnapshot(String snapshotId);

}
