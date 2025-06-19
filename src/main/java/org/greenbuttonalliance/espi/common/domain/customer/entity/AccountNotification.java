/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.domain.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.customer.enums.NotificationMethodKind;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * Embeddable class for AccountNotification without JAXB concerns.
 * 
 * [extension] Customer action notification (e.g., delinquency, move in, move out)
 * ESPI compliant with proper UUID identifiers and ATOM feed support.
 */
@Embeddable
@Data
@NoArgsConstructor
@ToString
public class AccountNotification {

    /**
     * Method by which the customer was notified.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "method_kind")
    private NotificationMethodKind methodKind;

    /**
     * Time/date of notification
     */
    @Column(name = "time")
    private OffsetDateTime time;

    /**
     * Annotation of the reason for the notification
     */
    @Column(name = "note", length = 512)
    private String note;

    /**
     * Type of customer notification (delinquency, move in, move out ...)
     */
    @Column(name = "customer_notification_kind", length = 256)
    private String customerNotificationKind;

    /**
     * Customer account this notification belongs to
     * Note: This should be handled at the Entity level, not in an Embeddable
     */
    // @Embedded - Removed as this creates circular reference issues
    // private CustomerAccountEntity customerAccount;
}