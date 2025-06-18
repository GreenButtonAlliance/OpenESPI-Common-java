-- 
-- Migration to embed ServiceDeliveryPoint in UsagePoint table
-- ServiceDeliveryPoint is not a standalone ESPI resource, so it should be embedded
--

-- Add ServiceDeliveryPoint columns to usage_points table
ALTER TABLE usage_points 
ADD COLUMN sdp_mrid VARCHAR(64) AFTER service_delivery_point_id,
ADD COLUMN sdp_description VARCHAR(512) AFTER sdp_mrid,
ADD COLUMN sdp_name VARCHAR(256) AFTER sdp_description,
ADD COLUMN sdp_tariff_profile VARCHAR(256) AFTER sdp_name,
ADD COLUMN sdp_customer_agreement VARCHAR(256) AFTER sdp_tariff_profile;

-- Migrate existing ServiceDeliveryPoint data to embedded columns
UPDATE usage_points up 
JOIN service_delivery_points sdp ON up.service_delivery_point_id = sdp.id
SET 
    up.sdp_mrid = sdp.uuid,
    up.sdp_description = sdp.description,
    up.sdp_name = sdp.name,
    up.sdp_tariff_profile = sdp.tariff_profile,
    up.sdp_customer_agreement = sdp.customer_agreement
WHERE up.service_delivery_point_id IS NOT NULL;

-- Create index on embedded ServiceDeliveryPoint fields for performance
CREATE INDEX idx_usage_points_sdp_mrid ON usage_points(sdp_mrid);
CREATE INDEX idx_usage_points_sdp_name ON usage_points(sdp_name);
CREATE INDEX idx_usage_points_sdp_tariff_profile ON usage_points(sdp_tariff_profile);

-- Drop foreign key constraint and column (after data migration)
ALTER TABLE usage_points DROP FOREIGN KEY fk_usage_points_service_delivery_point;
ALTER TABLE usage_points DROP COLUMN service_delivery_point_id;

-- Drop the separate service_delivery_points table (no longer needed)
DROP TABLE service_delivery_points;