-- 
-- Migration to embed ServiceDeliveryPoint in UsagePoint table
-- ServiceDeliveryPoint is not a standalone ESPI resource, so it should be embedded
--

-- Add ServiceDeliveryPoint columns to usage_points table
ALTER TABLE usage_points 
ADD COLUMN sdp_mrid VARCHAR(64),
ADD COLUMN sdp_description VARCHAR(512),
ADD COLUMN sdp_name VARCHAR(256),
ADD COLUMN sdp_tariff_profile VARCHAR(256),
ADD COLUMN sdp_customer_agreement VARCHAR(256);

-- Migrate existing ServiceDeliveryPoint data to embedded columns
UPDATE usage_points 
SET 
    sdp_mrid = sdp.uuid,
    sdp_description = sdp.description,
    sdp_name = sdp.name,
    sdp_tariff_profile = sdp.tariff_profile,
    sdp_customer_agreement = sdp.customer_agreement
FROM service_delivery_points sdp
WHERE usage_points.service_delivery_point_id = sdp.id;

-- Create index on embedded ServiceDeliveryPoint fields for performance
CREATE INDEX idx_usage_points_sdp_mrid ON usage_points(sdp_mrid);
CREATE INDEX idx_usage_points_sdp_name ON usage_points(sdp_name);
CREATE INDEX idx_usage_points_sdp_tariff_profile ON usage_points(sdp_tariff_profile);

-- Drop foreign key constraint and column (after data migration)
ALTER TABLE usage_points DROP CONSTRAINT IF EXISTS fk_usage_points_service_delivery_point;
ALTER TABLE usage_points DROP COLUMN service_delivery_point_id;

-- Drop the separate service_delivery_points table (no longer needed)
DROP TABLE IF EXISTS service_delivery_points;