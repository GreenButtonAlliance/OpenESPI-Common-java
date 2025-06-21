/*
 *
 *     Copyright (c) 2018-2025 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.domain.legacy;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ServiceDeliveryPoint")
@XmlType(name = "ServiceDeliveryPoint", propOrder = { "name", "tariffProfile",
		"customerAgreement" })
@Entity
@Table(name = "service_delivery_points")
public class ServiceDeliveryPoint {
	private String name;
	private String tariffProfile;
	private String customerAgreement;

	private long id;

	@Id
	@XmlTransient
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTariffProfile() {
		return tariffProfile;
	}

	public void setTariffProfile(String tariffProfile) {
		this.tariffProfile = tariffProfile;
	}

	public String getCustomerAgreement() {
		return customerAgreement;
	}

	public void setCustomerAgreement(String customerAgreement) {
		this.customerAgreement = customerAgreement;
	}
}
