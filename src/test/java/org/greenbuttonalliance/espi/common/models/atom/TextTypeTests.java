/*
 * Copyright (c) 2013, 2014, 2015, 2016, 2017, 2018 EnergyOS.org
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
 *
 * ********************************************************************************
 *
 *  Copyright (c) 2018 Green Button Alliance, Inc.
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
 */

package org.greenbuttonalliance.espi.common.models.atom;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TextTypeTests {

	@Test
	public void title_returnsContent() {
		TextType textTypeElement = new TextType();
		textTypeElement.getContent().add("Sample text");

		assertTrue(textTypeElement.getTitle().equals("Sample text"));

		textTypeElement.getContent().clear();
		textTypeElement.getContent().add("Different text");

		assertTrue(textTypeElement.getTitle().equals("Different text"));
	}
}
