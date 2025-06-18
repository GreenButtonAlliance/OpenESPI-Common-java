-- 
-- Migration to add readingTypeRef columns to SummaryMeasurement fields in UsagePoint
-- Adds readingTypeRef for estimatedLoad, nominalServiceVoltage, ratedCurrent, ratedPower
--

-- Add readingTypeRef column for estimatedLoad SummaryMeasurement
ALTER TABLE usage_points 
ADD COLUMN estimated_load_reading_type_ref VARCHAR(512) AFTER estimated_load_value;

-- Add readingTypeRef column for nominalServiceVoltage SummaryMeasurement
ALTER TABLE usage_points 
ADD COLUMN nominal_voltage_reading_type_ref VARCHAR(512) AFTER nominal_voltage_value;

-- Add readingTypeRef column for ratedCurrent SummaryMeasurement
ALTER TABLE usage_points 
ADD COLUMN rated_current_reading_type_ref VARCHAR(512) AFTER rated_current_value;

-- Add readingTypeRef column for ratedPower SummaryMeasurement
ALTER TABLE usage_points 
ADD COLUMN rated_power_reading_type_ref VARCHAR(512) AFTER rated_power_value;

-- Create indexes for performance on readingTypeRef columns (commonly used for queries)
CREATE INDEX idx_usage_points_estimated_load_reading_type_ref ON usage_points(estimated_load_reading_type_ref);
CREATE INDEX idx_usage_points_nominal_voltage_reading_type_ref ON usage_points(nominal_voltage_reading_type_ref);
CREATE INDEX idx_usage_points_rated_current_reading_type_ref ON usage_points(rated_current_reading_type_ref);
CREATE INDEX idx_usage_points_rated_power_reading_type_ref ON usage_points(rated_power_reading_type_ref);