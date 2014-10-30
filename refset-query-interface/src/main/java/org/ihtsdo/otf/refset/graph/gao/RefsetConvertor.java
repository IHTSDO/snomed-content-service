/**
 * 
 */
package org.ihtsdo.otf.refset.graph.gao;

import static org.ihtsdo.otf.refset.domain.RGC.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ihtsdo.otf.refset.domain.Member;
import org.ihtsdo.otf.refset.domain.MetaData;
import org.ihtsdo.otf.refset.domain.Refset;
import org.ihtsdo.otf.refset.graph.schema.GMember;
import org.ihtsdo.otf.refset.graph.schema.GRefset;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Episteme Partners
 *
 */
public class RefsetConvertor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefsetConvertor.class);

	
	/** Extracts {@link Refset} properties in a map to be added in {@link Refset} node
	 * @param r
	 * @return
	 * use frames instead ie {@link GRefset}
	 */
	@Deprecated
	protected static Map<String, Object> getRefsetProperties(Refset r) {
		
		LOGGER.debug("getRefsetProperties {}", r);
		
		Map<String, Object> props = new HashMap<String, Object>();
		
		if (r == null) {
			
			return props;
		}
		DateTime effDate = r.getEffectiveTime();
		
		if( effDate != null )
			props.put(EFFECTIVE_DATE, effDate.getMillis());
		
		DateTime created = r.getCreated();
		if( created != null )
			props.put(CREATED, created.getMillis());
		
		DateTime publishedDate = r.getPublishedDate();
		
		if( publishedDate != null)
			props.put(PUBLISHED_DATE, publishedDate.getMillis());
		
		if(!StringUtils.isEmpty(r.getDescription()))
				props.put(DESC, r.getDescription());
		
		if(!StringUtils.isEmpty(r.getCreatedBy()))
			props.put(CREATED_BY, r.getCreatedBy());
		
		if(!StringUtils.isEmpty(r.getSuperRefsetTypeId()))
			props.put(SUPER_REFSET_TYPE_ID, r.getSuperRefsetTypeId());

		if(!StringUtils.isEmpty(r.getLanguageCode()))
			props.put(LANG_CODE, r.getLanguageCode());
		
		if(!StringUtils.isEmpty(r.getModuleId()))
			props.put(MODULE_ID, r.getModuleId());
		

		if(!StringUtils.isEmpty(r.getTypeId()))
			props.put(TYPE_ID, r.getTypeId());
		
		if(!StringUtils.isEmpty(r.getComponentTypeId()))
			props.put(MEMBER_TYPE_ID, r.getComponentTypeId());
		
		props.put(PUBLISHED, r.isPublished());

		if(!StringUtils.isEmpty(r.getId()))
			props.put(ID, r.getId());//This has to be updated by real refsetId after publishing
		
		LOGGER.info("getRefsetProperties property map size {}", props.size());

		return props;
	}
	
	
	/** Extracts {@link Member} properties in a map to be added in {@link Member} node
	 * @param m
	 * @return
	 * use frames instead ie {@link GMember}

	 */
	@Deprecated
	protected static Map<String, Object> getMemberProperties(Member m) {

		LOGGER.debug("getMemberProperties {}", m);

		Map<String, Object> props = new HashMap<String, Object>();
		if(m == null) {
			
			return props;
		}
		
		DateTime effDate = m.getEffectiveTime();
		if(effDate != null)
			props.put(EFFECTIVE_DATE, effDate.getMillis());
		
		if(!StringUtils.isEmpty(m.getModuleId()))
			props.put(MODULE_ID, m.getModuleId());
	
		if(!StringUtils.isEmpty(m.getReferencedComponentId()))
			props.put(REFERENCE_COMPONENT_ID, m.getReferencedComponentId());

		props.put(ACTIVE, m.isActive());

		if(!StringUtils.isEmpty(m.getId()))
			props.put(ID, m.getId());

		LOGGER.debug("getMemberProperties size {}", props.size());

		return props;
	}


	/**
	 * @param vs.
	 * @return
	 */
	public static List<Refset> getRefsetss(Iterable<GRefset> vs) {

		List<Refset> refsets = new ArrayList<Refset>();

		for (GRefset gr : vs) {
			
			Set<String> keys = gr.asVertex().getPropertyKeys();
			
			if (!StringUtils.isEmpty(gr.getId()) && keys.contains(DESC) ) {
				
				
				Refset r = new Refset();
				
				
				if ( keys.contains(CREATED) ) {
				
					r.setCreated(new DateTime(gr.getCreated()));
				}
				
				if ( keys.contains(CREATED_BY) ) {
					
					r.setCreatedBy(gr.getCreatedBy());
					
				}
				
				
				if ( keys.contains(DESC) ) {
					
					r.setDescription(gr.getDescription());
					
				}
				
				if ( keys.contains(EFFECTIVE_DATE) ) {
					
					r.setEffectiveTime(new DateTime(gr.getEffectiveTime()));

				}
				
				if ( keys.contains(ID) ) {
					
					r.setId(gr.getId());

				}
				
				if ( keys.contains(SCTID) ) {
					
					r.setId(gr.getSctdId());

				}
				
				if ( keys.contains(LANG_CODE) ) {
					
					r.setLanguageCode(gr.getLanguageCode());

					
				}
				
				if ( keys.contains(MODULE_ID) ) {
					
					r.setModuleId(gr.getModuleId());
					
				}
				
				if ( keys.contains(PUBLISHED) ) {
					
					r.setPublished(gr.getPublished() == 1 ? true : false);
					
				}
				
				
				if ( keys.contains(PUBLISHED_DATE) ) {
					
					r.setPublishedDate(new DateTime(gr.getPublishedDate()));

					
				}
				
				
				if ( keys.contains(SUPER_REFSET_TYPE_ID) ) {
					
					r.setSuperRefsetTypeId(gr.getSuperRefsetTypeId());
					
				}
				
				if ( keys.contains(MEMBER_TYPE_ID) ) {
					
					r.setComponentTypeId(gr.getComponentTypeId());
					
				}

				if ( keys.contains(TYPE_ID) ) {
					
					r.setTypeId(gr.getTypeId());
					
				}
				
				if ( keys.contains(ACTIVE) ) {
					
					r.setActive(gr.getActive() == 1 ? true : false);
					
				}
				
				if ( keys.contains(MODIFIED_DATE) ) {
					
					r.setModifiedDate(new DateTime(gr.getModifiedDate()));
				}
				
				if ( keys.contains(MODIFIED_BY) ) {
					
					r.setModifiedBy(gr.getModifiedBy());
					
				}
				
				if ( keys.contains(EXPECTED_RLS_DT) ) {
					
					r.setExpectedReleaseDate(new DateTime(gr.getExpectedReleaseDate()));

				}
				
				r.setTotalNoOfMembers(gr.getNoOfMembers());

				
				refsets.add(r);
				
			}
			
		}
		Collections.sort(refsets);
		Collections.reverse(refsets);

		return Collections.unmodifiableList(refsets);
	}


	/**
	 * @param v
	 * @return
	 */
	public static Refset convert2Refsets(GRefset vR) {


		
		LOGGER.debug("convert2Refsets {}", vR);

		Refset r = getRefset(vR);

		Iterable<Edge> eRs = vR.asVertex().getEdges(Direction.IN, "members");
		r.setMembers(getMembers(eRs));
		
		LOGGER.trace("Returning Refset as {} ", r.toString());

		return r;
		
	
	}

	/**
	 * @param asVertex
	 * @return
	 */
	protected static Refset getRefset(GRefset vR) {
		
		Refset r = new Refset();
		Set<String> keys = vR.asVertex().getPropertyKeys();
		
		if ( keys.contains(CREATED) ) {
		
			r.setCreated(new DateTime(vR.getCreated()));
		}
		
		if ( keys.contains(CREATED_BY) ) {
			
			r.setCreatedBy(vR.getCreatedBy());
			
		}
		
		
		if ( keys.contains(DESC) ) {
			
			r.setDescription(vR.getDescription());
			
		}
		
		if ( keys.contains(EFFECTIVE_DATE) ) {
			
			r.setEffectiveTime(new DateTime(vR.getEffectiveTime()));
			
		}
		
		if ( keys.contains(ID) ) {
			
			r.setId(vR.getId());
		}
		
		if ( keys.contains(LANG_CODE) ) {
			
			r.setLanguageCode(vR.getLanguageCode());
			
		}
		
		if ( keys.contains(MODULE_ID) ) {
			
			r.setModuleId(vR.getModuleId());
			
		}
		
		if ( keys.contains(PUBLISHED) ) {
			
			r.setPublished(vR.getPublished() == 1 ? true : false);
			
		}
		
		
		if ( keys.contains(PUBLISHED_DATE) ) {
			
			r.setPublishedDate(new DateTime(vR.getPublishedDate()));
			
		}
		
		
		if ( keys.contains(SUPER_REFSET_TYPE_ID) ) {
			
			r.setSuperRefsetTypeId(vR.getSuperRefsetTypeId());
			
		}
		

		if ( keys.contains(TYPE_ID) ) {
			
			r.setTypeId(vR.getTypeId());
			
		}

		if ( keys.contains(MEMBER_TYPE_ID) ) {
			
			r.setComponentTypeId(vR.getComponentTypeId());
			
		}
		
		if ( keys.contains(MODIFIED_DATE) ) {
			
			r.setModifiedDate(new DateTime(vR.getModifiedDate()));
		}
		
		if ( keys.contains(MODIFIED_BY) ) {
			
			r.setModifiedBy(vR.getModifiedBy());
			
		}
		
		if ( keys.contains(EXPECTED_RLS_DT) ) {
			
			r.setExpectedReleaseDate(new DateTime(vR.getExpectedReleaseDate()));

		}
		
		if ( keys.contains(SCTID) ) {
			
			r.setId(vR.getSctdId());

		}
		
		return r;
	}


	protected static List<Member> getMembers(Iterable<Edge> eRs ) {
		
		List<Member> members = new ArrayList<Member>();

		if(eRs != null) {
			
			
			for (Edge eR : eRs) {
				
				Vertex vM = eR.getVertex(Direction.OUT);
				
				Set<String> mKeys = vM.getPropertyKeys();
				
				Member m = new Member();
				
				if ( mKeys.contains(ID) ) {
					
					String lId = vM.getProperty(ID);

					m.setId(lId);
					
				}
				
				if ( mKeys.contains(MODULE_ID) ) {
					
					
					String mModuleId = vM.getProperty(MODULE_ID);
					m.setModuleId(mModuleId);
					
				}
				
				if (mKeys.contains(EFFECTIVE_DATE)) {
					

					long effectivetime = vM.getProperty(EFFECTIVE_DATE);
					
					LOGGER.trace("Actual effective date when this member tied to given refset is  {} ", effectivetime);

					m.setEffectiveTime(new DateTime(effectivetime));
					
				}
				
				if (mKeys.contains(PUBLISHED)) {
					

					Integer published = vM.getProperty(PUBLISHED);
					
					m.setPublished(published == 1 ? true : false);;
					
				}


				
				
				if ( mKeys.contains(ACTIVE) ) {
					
					Integer isActive = vM.getProperty(ACTIVE);
					m.setActive(isActive == 1 ? true : false);
					
				}
				
				
				if ( mKeys.contains(EFFECTIVE_DATE) ) {
					
					long effectivetime = vM.getProperty(EFFECTIVE_DATE);
					m.setEffectiveTime(new DateTime(effectivetime));
					
				}
				
				if ( mKeys.contains(MODIFIED_DATE) ) {
					
					long modified = vM.getProperty(MODIFIED_DATE);
					m.setModifiedDate(new DateTime(modified));
				}
				
				if ( mKeys.contains(MODIFIED_BY) ) {
					
					String modifiedby = vM.getProperty(MODIFIED_BY);
					m.setModifiedBy(modifiedby);
					
				}
				
				if ( mKeys.contains(CREATED) ) {
					
					m.setCreated(new DateTime(vM.getProperty(CREATED)));
				}
				
				if ( mKeys.contains(CREATED_BY) ) {
					
					String createdBy = vM.getProperty(CREATED_BY);
					m.setCreatedBy(createdBy);
					
				}
				
				
				
				
				//this has to be edge effective date. //TODO remove above
				Set<String> eKeys = eR.getPropertyKeys();
				if ( eKeys.contains(REFERENCE_COMPONENT_ID) ) {
					
					String referenceComponentId = eR.getProperty(REFERENCE_COMPONENT_ID);
					m.setReferencedComponentId(referenceComponentId);
					
				}

				


				LOGGER.trace("Adding member as {} ", m.toString());

				members.add(m);
			}
		}
		
		Collections.sort(members);
		return members;
	}
			
	
	
	/** Utility method to create {@link MetaData} object from {@link Vertex} 
	 * @param vertex
	 * @return
	 */
	protected static MetaData getMetaData(Vertex v) {
		
		MetaData md = null;
		
		if( v != null) {
			
			md = new MetaData();
			md.setId(v.getId());
			md.setType(v.getClass().getSimpleName());
			//Integer version = e.getProperty("@Version");
			//md.setVersion(version);
			
		}
		
		return md;
	}
}
