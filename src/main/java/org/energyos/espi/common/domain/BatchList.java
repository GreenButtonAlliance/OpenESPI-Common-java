/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@XmlRootElement(name = "BatchList")
@Entity
public class BatchList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long id;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "resources", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "uri")
	@XmlElement(name = "resources")
	private List<String> resources = new ArrayList<>();

	public List<String> getResources() {
		return resources;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
