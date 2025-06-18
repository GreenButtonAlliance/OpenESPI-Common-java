-- 
-- Migration to create PnodeRef and AggregatedNodeRef tables for UsagePoint
-- These tables store the pricing node and aggregated node references for each usage point
--

-- Create pnode_refs table
CREATE TABLE pnode_refs (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    apnode_type VARCHAR(64),
    ref VARCHAR(256) NOT NULL,
    start_effective_date BIGINT,
    end_effective_date BIGINT,
    usage_point_id BIGINT NOT NULL,
    CONSTRAINT fk_pnode_refs_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create aggregated_node_refs table  
CREATE TABLE aggregated_node_refs (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    anode_type VARCHAR(64),
    ref VARCHAR(256) NOT NULL,
    start_effective_date BIGINT,
    end_effective_date BIGINT,
    pnode_ref_id BIGINT,
    usage_point_id BIGINT NOT NULL,
    CONSTRAINT fk_aggregated_node_refs_pnode_ref FOREIGN KEY (pnode_ref_id) REFERENCES pnode_refs(id) ON DELETE SET NULL,
    CONSTRAINT fk_aggregated_node_refs_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for performance
CREATE INDEX idx_pnode_refs_usage_point_id ON pnode_refs(usage_point_id);
CREATE INDEX idx_pnode_refs_apnode_type ON pnode_refs(apnode_type);
CREATE INDEX idx_pnode_refs_ref ON pnode_refs(ref);
CREATE INDEX idx_pnode_refs_effective_dates ON pnode_refs(start_effective_date, end_effective_date);

CREATE INDEX idx_aggregated_node_refs_usage_point_id ON aggregated_node_refs(usage_point_id);
CREATE INDEX idx_aggregated_node_refs_pnode_ref_id ON aggregated_node_refs(pnode_ref_id);
CREATE INDEX idx_aggregated_node_refs_anode_type ON aggregated_node_refs(anode_type);
CREATE INDEX idx_aggregated_node_refs_ref ON aggregated_node_refs(ref);
CREATE INDEX idx_aggregated_node_refs_effective_dates ON aggregated_node_refs(start_effective_date, end_effective_date);

-- Create composite indexes for common queries
CREATE INDEX idx_pnode_refs_usage_point_type_ref ON pnode_refs(usage_point_id, apnode_type, ref);
CREATE INDEX idx_aggregated_node_refs_usage_point_type_ref ON aggregated_node_refs(usage_point_id, anode_type, ref);