/**
* Copyright 2014 IHTSDO
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.ihtsdo.otf.snomed.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.ihtsdo.otf.refset.domain.ChangeRecord;
import org.ihtsdo.otf.refset.exception.EntityNotFoundException;
import org.ihtsdo.otf.refset.graph.RefsetGraphAccessException;
import org.ihtsdo.otf.refset.graph.RefsetGraphFactory;
import org.ihtsdo.otf.snomed.domain.Concept;
import org.ihtsdo.otf.snomed.domain.Properties;
import org.ihtsdo.otf.snomed.domain.Relationship;
import org.ihtsdo.otf.snomed.exception.ConceptServiceException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanIndexQuery.Result;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * Service to look up Terminology data.
 *
 */
public class ConceptLookUpServiceImplv1_0 implements ConceptLookupService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConceptLookUpServiceImplv1_0.class);
		
	private RefsetGraphFactory factory;


	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getConcepts(java.util.List)
	 */
	@Override
	@Cacheable(value = { "concepts" })
	public Map<String, Concept> getConcepts(Set<String> conceptIds)
			throws ConceptServiceException {

		LOGGER.debug("getting concepts details for {}", conceptIds);
		
		Map<String, Concept> concepts = new HashMap<String, Concept>();
		
		TitanGraph g = null;
		try {
			
			
			g = factory.getReadOnlyGraph();			
			
			/**/
			List<String> idLst = new ArrayList<String>();
			idLst.addAll(conceptIds);

			int length = idLst.size()/1024;
			int to = idLst.size() > 1024 ? 1024 : conceptIds.size();
			int from = 0;
			for (int i = 0; i < length+1; i++) {
				
				LOGGER.debug("getting concept description from {} to {} ", from, to);
				List<String> subList = idLst.subList(from, to);
				
				String ids = org.apache.commons.lang.StringUtils.join(subList, " OR ");
				Iterable<Result<Vertex>> vs = g.indexQuery("concept","v.sctid:" + ids).vertices();
				for (Result<Vertex> r : vs) {
									
					Vertex v = r.getElement();
					
					Object sctid = v.getProperty(Properties.sctid.toString());
					Object label = v.getProperty(Properties.title.toString());
					if (sctid != null && label != null && idLst.contains(sctid.toString())) {
						
						Concept c = new Concept();
						
						c.setId(sctid.toString());
						
						Long effectiveTime = v.getProperty(Properties.effectiveTime.toString());
						
						if (effectiveTime != null) {
							
							c.setEffectiveTime(new DateTime(effectiveTime));

						}
						
						String status = v.getProperty(Properties.status.toString());
						boolean active = "1".equals(status) ? true : false;
						c.setActive(active);
						
						c.setLabel(label.toString());
						
						Iterable<Edge> es = v.getEdges(Direction.OUT, Relationship.hasModule.toString());
						
						for (Edge edge : es) {

							Vertex vE = edge.getVertex(Direction.IN);
							
							if (vE != null) {
								
								String moduleId = vE.getProperty(Properties.sctid.toString());
								c.setModuleId(moduleId);
								break;

							}

						}
						concepts.put(sctid.toString(), c);
					}
					
				}
				
				//to run next loop if required
				from = to > idLst.size() ? idLst.size() : to;
				to = (to + 1024) > idLst.size() ? idLst.size() : to+1024;


			}

			RefsetGraphFactory.commit(g);
			
		} catch (Exception e) {
			
			LOGGER.error("Error duing concept details for concept map fetch", e);
			RefsetGraphFactory.rollback(g);

			throw new ConceptServiceException(e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);
			
		}
		
		LOGGER.debug("returning total {} concepts ", concepts.size());

		return Collections.unmodifiableMap(concepts);
	}

	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getConcept(java.lang.String)
	 */
	@Override
	@Cacheable(value = { "concept" })
	public Concept getConcept(String conceptId) throws ConceptServiceException,
			EntityNotFoundException {
		
		LOGGER.debug("getting concept details for {} ", conceptId);

		if (StringUtils.isEmpty(conceptId)) {
			
			throw new EntityNotFoundException(String.format("Invalid concept id", conceptId));
		}
		
		TitanGraph g = null;
		
		try {
			
			g = factory.getReadOnlyGraph();
			
			Iterable<Vertex> vs = g.getVertices(Properties.sctid.toString(), conceptId);

			for (Vertex r : vs) {
				
				Concept c = new Concept();

				Vertex v = r;
				
				String sctId = v.getProperty(Properties.sctid.toString());
				c.setId(sctId);
				
				Long effectiveTime = v.getProperty(Properties.effectiveTime.toString());
				
				if (effectiveTime != null) {
					
					c.setEffectiveTime(new DateTime(effectiveTime));

				}
				
				String status = v.getProperty(Properties.status.toString());
				boolean active = "1".equals(status) ? true : false;
				c.setActive(active);
				
				String label = v.getProperty(Properties.title.toString());
				c.setLabel(label);
				
				Iterable<Edge> es = v.getEdges(Direction.OUT, Relationship.hasModule.toString());
				
				for (Edge edge : es) {

					Vertex vE = edge.getVertex(Direction.IN);
					
					if (vE != null) {
						
						String moduleId = vE.getProperty(Properties.sctid.toString());
						c.setModuleId(moduleId);
						break;

					}

				}
				
				RefsetGraphFactory.commit(g);

				return c;

			}

			RefsetGraphFactory.commit(g);
				
		} catch (Exception e) {
			
			LOGGER.error("Error duing concept details fetch", e);
			RefsetGraphFactory.rollback(g);

			throw new ConceptServiceException(e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);
		}

		throw new EntityNotFoundException(String.format("Invalid concept id", conceptId));

	}

	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getConceptIds(int, int)
	 */
	@Override
	@Cacheable(value = { "conceptIds" })
	public Set<String> getConceptIds(int offset, int limit)
			throws ConceptServiceException {
		LOGGER.debug("getting concept ids with offset {} and limit {} ", offset, limit);

		TreeSet<String> conceptIds = new TreeSet<String>();
		
		TitanGraph g = null;
		try {
			
			g = factory.getReadOnlyGraph();

			Iterable<Result<Vertex>> vs = g.indexQuery("concept","v.sctid:*").offset(offset).limit(limit).vertices();

			for (Result<Vertex> v : vs) {
				
				String sctid = v.getElement().getProperty(Properties.sctid.toString());

				if (!StringUtils.isEmpty(sctid)) {
					
					LOGGER.trace("Adding sctid {} to concept id list ", sctid);

					conceptIds.add(sctid);

				}

			}
			
			RefsetGraphFactory.commit(g);
									
		} catch (Exception e) {
			
			LOGGER.error("Error duing concept ids fetch ", e);
			RefsetGraphFactory.rollback(g);

			throw new ConceptServiceException(e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);
			
		}
		LOGGER.debug("returning total {} concept ids ", conceptIds.size());

		return Collections.unmodifiableSortedSet(conceptIds);
	}	
	
	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getTypes(String)
	 */
	@Override
	public Map<String, String> getTypes(String id)
			throws ConceptServiceException {

		LOGGER.debug("getting types for given id {}", id);
		
		Map<String, String> types = new HashMap<String, String>();
		
		TitanGraph g = null;
		try {
			
			g = factory.getReadOnlyGraph();

			    
			
					
			
		
		} catch (Exception e) {
			
			LOGGER.error("Error duing concept details for concept map fetch", e);
			
			throw new ConceptServiceException(e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);
			
		}
		
		LOGGER.debug("returning total {} types ", types.size());

		return Collections.unmodifiableMap(types);
	}
	
	
	/**returns referenceComponentDescription for given referenceComponentId
	 * @param referenceComponentId
	 * @return
	 * @throws RefsetGraphAccessException
	 */
	@Override
	@Cacheable(value = { "referenceComponentDescription" })
	public String getMemberDescription(String referenceComponentId) throws RefsetGraphAccessException  {
		
		LOGGER.debug("getting member description for {} ", referenceComponentId);

		String label = "";

		if (StringUtils.isEmpty(referenceComponentId)) {
			
			return label;
		}

		TitanGraph g = null;
		try {
			
			
				
				g = factory.getReadOnlyGraph();

				Iterable<Result<Vertex>> vs = g.indexQuery("concept","v.sctid:" + referenceComponentId).vertices();
				for (Result<Vertex> r : vs) {
									
					Vertex v = r.getElement();
					
					label = v.getProperty(Properties.title.toString());
					break;
					
				}

				
				RefsetGraphFactory.commit(g);
		
		} catch (Exception e) {
			
			RefsetGraphFactory.rollback(g);

			LOGGER.error("Error duing concept details fetch", e);
			
			throw new RefsetGraphAccessException(e.getMessage(), e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);

		}
		
		return label;
	}

	
	/** Returns {@link Map} of referenceComponentId as key and their description as value
	 * @param referenceComponentId
	 * @return
	 * @throws RefsetGraphAccessException
	 */
	@Override
	@Cacheable(value = { "referenceComponentDescriptions" })
	public Map<String, String> getMembersDescription(List<String> rcIds) throws RefsetGraphAccessException  {
		
		LOGGER.trace("getting members description for {} ", rcIds);

		Map<String, String> descMap = new HashMap<String, String>();
		
		if (rcIds == null || rcIds.isEmpty()) {
			
			return descMap;
		}

		TitanGraph g = null;
		try {
				
				g = factory.getReadOnlyGraph();

				//max OR clause can be 1024 so send in 1024 at max in one call
				int length = rcIds.size()/1024;
				int to = rcIds.size() > 1024 ? 1024 : rcIds.size();
				int from = 0;
				for (int i = 0; i < length+1; i++) {
					
					LOGGER.debug("getting members description from {} to {} ", from, to);

					List<String> subList = rcIds.subList(from, to);
					
					String ids = org.apache.commons.lang.StringUtils.join(subList, " OR ");
					Iterable<Result<Vertex>> vs = g.indexQuery("concept","v.sctid:" + ids).vertices();
					for (Result<Vertex> r : vs) {
										
						Vertex v = r.getElement();
						
						Object sctid = v.getProperty(Properties.sctid.toString());
						Object label = v.getProperty(Properties.title.toString());
						if (sctid != null && label != null && rcIds.contains(sctid.toString())) {
							
							descMap.put(sctid.toString(), label.toString());

						}
						
					}
					
					//to run next loop if required
					from = to > rcIds.size() ? rcIds.size() : to;
					to = (to + 1024) > rcIds.size() ? rcIds.size() : to+1024;


				}
				

				RefsetGraphFactory.commit(g);
		
		} catch (Exception e) {
			
			RefsetGraphFactory.rollback(g);

			LOGGER.error("Error duing concept details fetch", e);
			
			throw new RefsetGraphAccessException(e.getMessage(), e);
			
		} finally {
			
			RefsetGraphFactory.shutdown(g);

		}
		
		return descMap;
	}
	
	/**
	 * @param factory the factory to set
	 */
	@Autowired
	public  void setFactory(RefsetGraphFactory factory) {
		
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getConceptHistory(java.util.Set)
	 */
	@Override
	public Map<String, ChangeRecord<Concept>> getConceptHistory(Set<String> conceptIds)
			throws ConceptServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ihtsdo.otf.snomed.service.ConceptLookupService#getConceptHistory(java.lang.String)
	 */
	@Override
	public ChangeRecord<Concept> getConceptHistory(String conceptId)
			throws ConceptServiceException, EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
