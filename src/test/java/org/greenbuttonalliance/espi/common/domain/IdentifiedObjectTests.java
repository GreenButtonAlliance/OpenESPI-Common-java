/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.support.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.annotation.XmlTransient;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class IdentifiedObjectTests {
	@Test
	public void mrid() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		identifiedObject.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
		Assert.assertThat(identifiedObject.getMRID(),
				is("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0"));
		Assert.assertThat(identifiedObject.getUUID(),
				is(UUID.fromString("E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0")));
	}

	@Test
	public void mrid_givenNoUUID() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		Assert.assertThat(identifiedObject.getMRID(), nullValue());
	}

	@Test
	public void mrid_isCaseInsensitive() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		identifiedObject.setMRID("urn:uuid:e8e75691-7f9d-49f3-8be2-3a74ebf6bfc0");
		Assert.assertThat(identifiedObject.getMRID(),
				is("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0"));
	}

	@Test
	public void UUID() {
		IdentifiedObject identifiedObject = new IdentifiedObject();
		UUID uuid = UUID.randomUUID();

		identifiedObject.setUUID(uuid);
		Assert.assertThat(identifiedObject.getUUID(),
				is(uuid));
		Assert.assertThat(identifiedObject.getMRID(),
				is("urn:uuid:" + uuid.toString().toUpperCase()));
	}

	@Test
	public void description_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(IdentifiedObject.class,
				"description", XmlTransient.class);
	}

	@Test
	public void id_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(IdentifiedObject.class, "id",
				XmlTransient.class);
	}

	@Test
	public void published_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(IdentifiedObject.class, "published",
				XmlTransient.class);
	}

	@Test
	public void updated_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(IdentifiedObject.class, "updated",
				XmlTransient.class);
	}
}
