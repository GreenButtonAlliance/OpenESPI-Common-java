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

import org.greenbuttonalliance.espi.common.domain.legacy.atom.adapters.RetailCustomerAdapter;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
// the above suppress warnings is b/c the serial will get pushed on through the
// the database...
@XmlRootElement(name = "RetailCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetailCustomer")
@Entity
@Table(name = "retail_customers")
@XmlJavaTypeAdapter(RetailCustomerAdapter.class)
@NamedQueries(value = {
		@NamedQuery(name = RetailCustomer.QUERY_FIND_BY_ID, query = "SELECT customer FROM RetailCustomer customer WHERE customer.id = :id"),
		@NamedQuery(name = RetailCustomer.QUERY_FIND_ALL, query = "SELECT customer FROM RetailCustomer customer"),
		@NamedQuery(name = RetailCustomer.QUERY_FIND_BY_USERNAME, query = "SELECT customer FROM RetailCustomer customer WHERE customer.username = :username"),
		@NamedQuery(name = RetailCustomer.QUERY_FIND_ALL_IDS, query = "SELECT retailCustomer.id FROM RetailCustomer retailCustomer"),
		@NamedQuery(name = RetailCustomer.QUERY_FIND_ALL_IDS_BY_XPATH_0, query = "SELECT DISTINCT r.id FROM RetailCustomer r"),
		@NamedQuery(name = RetailCustomer.QUERY_FIND_ID_BY_XPATH, query = "SELECT DISTINCT r.id FROM RetailCustomer r WHERE r.id = :o1Id")

})
public class RetailCustomer extends IdentifiedObject implements UserDetails,
		Principal {

	public static final String QUERY_FIND_BY_ID = "RetailCustomer.findById";
	public static final String QUERY_FIND_ALL = "RetailCustomer.findAll";
	public static final String QUERY_FIND_BY_USERNAME = "RetailCustomer.findByUsername";
	public static final String QUERY_FIND_ALL_IDS = "RetailCustomer.findAllIds";
	public static final String QUERY_FIND_ALL_IDS_BY_XPATH_0 = "RetailCustomer.findAllIdsByXpath0";
	public static final String QUERY_FIND_ID_BY_XPATH = "RetailCustomer.findIdsByXpath";

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_CUSTODIAN = "ROLE_CUSTODIAN";

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @XmlTransient protected Long id;
	 */

	@Column(name = "username")
	@Size(min = 4, max = 30)
	protected String username;

	@Column(name = "first_name")
	@NotEmpty
	@Size(max = 30)
	protected String firstName;

	@Column(name = "last_name")
	@NotEmpty
	@Size(max = 30)
	protected String lastName;

	@Column(name = "password")
	@Size(min = 5, max = 100)
	protected String password;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	@NotNull
	protected Boolean enabled = Boolean.TRUE;

	@Column(name = "role")
	@NotEmpty
	protected String role = ROLE_USER;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getName() {
		return getUsername();
	}

	@Override
	public String getHashedId() {
		return "" + getId();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RetailCustomer that = (RetailCustomer) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (username != null ? !username.equals(that.username)
				: that.username != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		return result;
	}

//	public void merge(RetailCustomer resource) {
	public void merge(IdentifiedObject resource) {
		// TODO needs to inherit from Identified Object
		super.merge(resource);
		this.enabled = ((RetailCustomer)resource).getEnabled();
		this.firstName = ((RetailCustomer)resource).getFirstName();
		this.lastName = ((RetailCustomer)resource).getLastName();
		this.password = ((RetailCustomer)resource).getPassword();
		this.role = ((RetailCustomer)resource).getRole();
		this.username = ((RetailCustomer)resource).getUsername();
	}
}
