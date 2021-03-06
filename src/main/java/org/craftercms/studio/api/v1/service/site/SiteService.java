/*******************************************************************************
 * Crafter Studio Web-content authoring solution
 *     Copyright (C) 2007-2016 Crafter Software Corporation.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.craftercms.studio.api.v1.service.site;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.craftercms.studio.api.v1.dal.SiteFeed;
import org.craftercms.studio.api.v1.exception.ServiceException;
import org.craftercms.studio.api.v1.to.DeploymentEndpointConfigTO;
import org.craftercms.studio.api.v1.to.PublishingChannelGroupConfigTO;
import org.craftercms.studio.api.v1.to.PublishingTargetTO;
import org.dom4j.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.InputStream;

import org.craftercms.studio.api.v1.to.SiteBlueprintTO;

/**
 * Note: consider renaming
 * A site in Crafter Studio is currently the name for a WEM project being managed.
 * This service provides access to site configuration
 * @author russdanner
 */
public interface SiteService {

	/**
	 * write configuraiton content at the given path
	 * (can be any kind of content)
	 * @param path
	 */
    boolean writeConfiguration(String site, String path, InputStream content) throws ServiceException;

	/**
	 * write configuraiton content at the given path
	 * (can be any kind of content)
	 * @param path
	 */
	boolean writeConfiguration(String path, InputStream content) throws ServiceException;

	/**
	 * given a site ID return the configuration as a document
	 * This method allows extensions to add additional properties to the configuration that
	 * are not made available through the site configuration object
	 * @param site the name of the site
	 * @return a Document containing the entire site configuration
	 */
	Document getSiteConfiguration(String site) throws SiteConfigNotFoundException;

	/**
	 * get configuraiton content as XML string at the given path
	 *
	 * @param site
	 * @param path
	 * @param applyEnv
	 * 			find from the environment overrides location?
	 * @return configuration as XML string
	 */
	Map<String, Object> getConfiguration(String path);

	/**
	 * get configuraiton content as XML string at the given path
	 *
	 * @param site
	 * @param path
	 * @param applyEnv
	 * 			find from the environment overrides location?
	 * @return configuration as XML string
	 */
	Map<String, Object> getConfiguration(String site, String path, boolean applyEnv);

	List<SiteFeed> getUserSites(String user);

    DeploymentEndpointConfigTO getDeploymentEndpoint(String site, String endpoint);

    List<PublishingTargetTO> getPublishingTargetsForSite(String site);

    DeploymentEndpointConfigTO getPreviewDeploymentEndpoint(String site);

    Set<String> getAllAvailableSites();

    /**
     * Create a new site based on an existing blueprint
     * @param blueprintName
     * @param siteName
     * @param siteId
     * @param desc
     */
    boolean createSiteFromBlueprint(String blueprintName, String siteName, String siteId, String desc);

    /**
     * remove a site from the system
     */
   	boolean deleteSite(String siteId);

	/**
	 * Synchronize our internal database with the underlying repository. This is required when a user bypasses the UI
	 * and manipulates the underlying repository directly.
	 *
	 * @param siteId site to sync
	 * @param fromCommitId commit ID to start at and sync up until current commit
	 * @return true if successful, false otherwise
	 */
	boolean syncDatabaseWithRepo(String siteId, String fromCommitId);

   	/**
   	 * get a list of available blueprints
   	 */
   	SiteBlueprintTO[] getAvailableBlueprints();

    String getPreviewServerUrl(String site);

    String getLiveServerUrl(String site);

    String getAuthoringServerUrl(String site);

    String getAdminEmailAddress(String site);

    void reloadSiteConfigurations();

    void reloadSiteConfiguration(String site);

    void reloadSiteConfiguration(String site, boolean triggerEvent);

    void reloadGlobalConfiguration();

    void importSite(String config);

    /**
     * Synchronize Database with repository
     *
     * @param site site id
     */
    void syncRepository(String site);

    /**
     * Rebuild database for site
     *
     * @param site site id
     */
    void rebuildDatabase(String site);

    void updateLastCommitId(String site, String commitId);

    /**
     * Check if site already exists
     *
     * @param site site ID
     * @return true if site exists, false otherwise
     */
    boolean exists(String site);
}
