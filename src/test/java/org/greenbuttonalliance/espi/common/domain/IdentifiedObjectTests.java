/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.domain;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IdentifiedObjectTests {
	@Test
	public void mrid() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		identifiedObject
				.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
		assertEquals("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0",
				identifiedObject.getMRID());
		assertEquals(UUID.fromString("E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0"),
				identifiedObject.getUUID());
	}

	@Test
	public void mrid_givenNoUUID() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		assertNull(identifiedObject.getMRID());
	}

	@Test
	public void mrid_isCaseInsensitive() {
		IdentifiedObject identifiedObject = new IdentifiedObject();

		identifiedObject
				.setMRID("urn:uuid:e8e75691-7f9d-49f3-8be2-3a74ebf6bfc0");
		assertEquals("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0",
				identifiedObject.getMRID());
	}

	@Test
	public void UUID() {
		IdentifiedObject identifiedObject = new IdentifiedObject();
		UUID uuid = UUID.randomUUID();

		identifiedObject.setUUID(uuid);
		assertEquals(uuid, identifiedObject.getUUID());
		assertEquals("urn:uuid:" + uuid.toString().toUpperCase(),
				identifiedObject.getMRID());
	}

//	@Test
//	public void description_hasTransientAnnotation() {
//		assertAnnotationPresent(IdentifiedObject.class, "description",
//				XmlTransient.class);
//	}

//	@Test
//	public void id_hasTransientAnnotation() {
//		assertAnnotationPresent(IdentifiedObject.class, "id",
//				XmlTransient.class);
//	}

//	@Test
//	public void created_hasTransientAnnotation() {
//		assertAnnotationPresent(IdentifiedObject.class, "published",
//				XmlTransient.class);
//	}

//	@Test
//	public void updated_hasTransientAnnotation() {
//		assertAnnotationPresent(IdentifiedObject.class, "updated",
//				XmlTransient.class);
//	}

//	@Test
//	public void upLink() {
//		assertAnnotationPresent(IdentifiedObject.class, "upLink",
//				XmlTransient.class);
//		assertAnnotationPresent(IdentifiedObject.class, "upLink",
//				Embedded.class);
//	}
}
