package org.ihtsdo.otf.terminology.domain;


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

/**
 * class to represent SNOMED description
 */
public class SnomedDescription extends SnomedComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descriptionInactivationIndicator;
	private String term;
	private String conceptId;
	private String typeId;
	private String languageCode;
	private String caseSignificance;
	
	/**
	 * @return the descriptionInactivationIndicator
	 */
	public String getDescriptionInactivationIndicator() {
		return descriptionInactivationIndicator;
	}
	/**
	 * @param descriptionInactivationIndicator the descriptionInactivationIndicator to set
	 */
	public void setDescriptionInactivationIndicator(
			String descriptionInactivationIndicator) {
		this.descriptionInactivationIndicator = descriptionInactivationIndicator;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	/**
	 * @return the conceptId
	 */
	public String getConceptId() {
		return conceptId;
	}
	/**
	 * @param conceptId the conceptId to set
	 */
	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}
	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}
	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	/**
	 * @return the caseSignificance
	 */
	public String getCaseSignificance() {
		return caseSignificance;
	}
	/**
	 * @param caseSignificance the caseSignificance to set
	 */
	public void setCaseSignificance(String caseSignificance) {
		this.caseSignificance = caseSignificance;
	}
}
