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

package org.greenbuttonalliance.espi.common.support;

import java.util.UUID;

public class CucumberSession {
	private static String username;
	private static UUID uuid;
	private static String userHashedId;
	private static String usagePointHashedId;
	private static int numberOfSubscriptions;

	private CucumberSession() {
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		CucumberSession.username = username;
	}

	public static UUID getUUID() {
		return uuid;
	}

	public static void setUUID(UUID UUID) {
		CucumberSession.uuid = UUID;
	}

	public static String getUserHashedId() {
		return userHashedId;
	}

	public static void setUserHashedId(String userHashedId) {
		CucumberSession.userHashedId = userHashedId;
	}

	public static void setUsagePointHashedId(String usagePointHashedId) {
		CucumberSession.usagePointHashedId = usagePointHashedId;
	}

	public static String getUsagePointHashedId() {
		return usagePointHashedId;
	}

	public static void setNumberOfSubscriptions(int numberOfSubscriptions) {
		CucumberSession.numberOfSubscriptions = numberOfSubscriptions;
	}

	public static int getNumberOfSubscriptions() {
		return numberOfSubscriptions;
	}
}
