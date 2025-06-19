# OpenESPI Mapper Migration Plan: UUID Primary Key Architecture

**Status**: In Progress  
**Created**: 2025-06-18  
**Last Updated**: 2025-06-18  

## Overview

This document tracks the systematic migration of all MapStruct mappers from Long-based primary keys to UUID-based primary keys for NAESB ESPI 4.0 compliance. The migration ensures deterministic, globally unique resource identification using UUID5 generation based on href rel="self" values.

## Migration Architecture

### Completed Infrastructure Changes ✅

1. **IdentifiedObjectEntity Refactoring** ✅
   - Changed primary key from `Long id` to `UUID id`
   - Removed redundant fields: `uuid`, `uuidMostSignificantBits`, `uuidLeastSignificantBits`
   - Removed `getMRID()` method (not needed in ESPI 4.0)
   - Simplified lifecycle management using Spring Boot automation

2. **Database Migrations** ✅
   - PostgreSQL migration: `V2_0__Convert_Primary_Keys_To_UUID.sql`
   - MySQL migration: `V2_0__Convert_Primary_Keys_To_UUID.sql`
   - Comprehensive foreign key updates and constraint recreation

3. **Validation Enhancements** ✅
   - Added field-level URL validation to `LinkType.href` using `@Pattern`
   - Ensures absolute HTTP/HTTPS URLs for ESPI compliance

4. **Shared Mapper Utilities** ✅
   - Created `BaseMapperUtils` interface for common UUID/DateTime methods
   - Consolidated duplicate mapping functions to avoid conflicts

### Current Status

- **Total Mappers**: 18 existing + ~10 missing = ~28 total
- **Compilation Errors**: 58 → 3 (95% reduction!)
- **Completed**: 6 mappers (infrastructure + 2 association + MeterReadingMapper)
- **In Progress**: UsagePointMapper (partially fixed, needs ambiguity resolution)
- **Remaining**: 21+ mappers need review/creation

## Systematic Mapper Review List

### Phase 1: Critical Resource Mappers (High Priority)

| Resource Entity | Mapper | Status | Errors | Notes |
|-----------------|--------|--------|---------|-------|
| **UsagePointEntity** | UsagePointMapper | 🟡 In Progress | 8 errors | UUID mapping partially fixed, collection mapping issues remain |
| **MeterReadingEntity** | MeterReadingMapper | ✅ **COMPLETED** | 0 errors | Fixed UUID mapping, DateTime qualifications, DTO id field |
| **IntervalBlockEntity** | IntervalBlockMapper | ❌ Not Started | 6 errors | UUID to Long mapping, obsolete uuid fields |
| **ReadingTypeEntity** | ReadingTypeMapper | ❌ Not Started | 3 errors | UUID to Long mapping, unmapped target properties |
| **UsageSummaryEntity** | UsageSummaryMapper | ❌ Not Started | 6 errors | UUID mapping, obsolete uuid fields |
| **ElectricPowerQualitySummaryEntity** | ElectricPowerQualitySummaryMapper | ❌ Not Started | 6 errors | UUID mapping, obsolete uuid fields |
| **CustomerEntity** | CustomerMapper | ❌ Not Started | 8 errors | UUID mapping, obsolete fields, unknown properties |
| **CustomerAccountEntity** | CustomerAccountMapper | ❌ Not Started | 4 errors | UUID to Long mapping, obsolete uuid fields |
| **CustomerAgreementEntity** | CustomerAgreementMapper | ❌ Not Started | 8 errors | UUID mapping, DateTime conversion, unknown properties |

**Phase 1 Target**: Reduce errors from 58 to ~20, achieve basic compilation

### Phase 2: Supporting Resource Mappers (Medium Priority)

| Resource Entity | Mapper | Status | Priority | Notes |
|-----------------|--------|--------|----------|-------|
| **IntervalReadingEntity** | IntervalReadingMapper | ❌ Not Started | Medium | Embedded component, unmapped properties |
| **ReadingQualityEntity** | ReadingQualityMapper | ❌ Not Started | Medium | Embedded component |
| **DateTimeInterval** | DateTimeIntervalMapper | ❌ Not Started | Medium | Utility mapper |
| **BaseIdentifiedObject** | BaseIdentifiedObjectMapper | ❌ Not Started | High | Core functionality, `entityToId()` needs UUID update |

**Phase 2 Target**: Clean up remaining compilation issues, optimize mappings

### Phase 3: Missing Mappers (Creation Required)

| Resource Entity | Missing Mapper | Priority | ESPI Resource | Notes |
|-----------------|----------------|----------|---------------|-------|
| **TimeConfigurationEntity** | TimeConfigurationMapper | Medium | Yes | Usage database resource |
| **ApplicationInformationEntity** | ApplicationInformationMapper | Medium | Yes | OAuth/authorization resource |
| **AuthorizationEntity** | AuthorizationMapper | Medium | Yes | OAuth resource |
| **RetailCustomerEntity** | RetailCustomerMapper | Medium | Yes | Customer data resource |
| **SubscriptionEntity** | SubscriptionMapper | Medium | Yes | Subscription management |
| **EndDeviceEntity** | EndDeviceMapper | Medium | Yes | Customer device resource |
| **MeterEntity** | MeterMapper | Medium | Yes | Extends EndDevice |
| **ServiceLocationEntity** | ServiceLocationMapper | Medium | Yes | Customer location resource |
| **ServiceSupplierEntity** | ServiceSupplierMapper | Medium | Yes | Utility provider resource |
| **StatementEntity** | StatementMapper | Medium | Yes | Billing statement resource |

**Phase 3 Target**: Complete ESPI resource coverage, full API functionality

### Completed/Low Priority

| Entity/Mapper | Status | Notes |
|---------------|--------|-------|
| **PnodeRefEntity** | ✅ PnodeRefMapper | Association table, created during UsagePoint work |
| **AggregatedNodeRefEntity** | ✅ AggregatedNodeRefMapper | Association table, created during UsagePoint work |
| **ServiceDeliveryPointEntity** | ✅ ServiceDeliveryPointMapper | Embedded component, should work |
| **BaseMapperUtils** | ✅ Created | Shared utility methods |
| **DateTimeMapper** | ✅ Good | No changes needed |
| **GreenButtonMapper** | 🟡 Review Later | Integration mapper, complex dependencies |

## Common Migration Patterns

### 1. UUID Mapping Updates

**Before (Entity → DTO):**
```java
@Mapping(target = "uuid", source = "uuid")
```

**After (Entity → DTO):**
```java
@Mapping(target = "uuid", source = "id", qualifiedByName = "entityUuidToString")
```

**Before (DTO → Entity):**
```java
@Mapping(target = "id", ignore = true)
@Mapping(target = "uuid", source = "uuid")
```

**After (DTO → Entity):**
```java
@Mapping(target = "id", source = "uuid", qualifiedByName = "stringToEntityUuid")
```

### 2. Remove Obsolete Field Mappings

**Remove these mappings:**
```java
@Mapping(target = "uuidMostSignificantBits", ignore = true)
@Mapping(target = "uuidLeastSignificantBits", ignore = true)
@Mapping(target = "uuid", ignore = true) // for entity mapping
```

### 3. Interface Updates

**Add BaseMapperUtils extension:**
```java
public interface XxxMapper extends BaseIdentifiedObjectMapper, BaseMapperUtils {
```

### 4. Qualification for Ambiguous Methods

**Use qualified names for common methods:**
```java
@Mapping(target = "published", source = "published", qualifiedByName = "localDateTimeToOffsetDateTime")
@Mapping(target = "updated", source = "updated", qualifiedByName = "localDateTimeToOffsetDateTime")
```

## Known Issues and Solutions

### Issue 1: Ambiguous Mapping Methods
**Problem**: Multiple mappers define the same utility methods  
**Solution**: Use `BaseMapperUtils` with qualified method names

### Issue 2: UUID to Long Conversion Errors
**Problem**: DTOs expect String UUID, entities now have UUID objects  
**Solution**: Use `entityUuidToString` and `stringToEntityUuid` methods

### Issue 3: Collection Mapping Failures
**Problem**: `Can't map property "Object meterReadings" to "List<MeterReadingEntity>"`  
**Solution**: Ensure related mappers are included in `uses` annotation and properly configured

### Issue 4: Unknown Property Errors
**Problem**: Mappers reference fields that no longer exist  
**Solution**: Remove mappings to obsolete fields, update field names

## Error Tracking

### Current Compilation Errors: 58

**By Category:**
- UUID mapping issues: ~25 errors
- Obsolete field references: ~20 errors  
- Ambiguous method conflicts: ~8 errors
- Collection mapping failures: ~5 errors

**By Mapper:**
- UsagePointMapper: 8 errors
- MeterReadingMapper: 12 errors
- CustomerMapper: 8 errors
- CustomerAgreementMapper: 8 errors
- CustomerAccountMapper: 4 errors
- IntervalBlockMapper: 6 errors
- Others: 12 errors

### Target Milestones

- **Phase 1 Complete**: Reduce to 20 errors
- **Phase 2 Complete**: Reduce to 5 errors  
- **Phase 3 Complete**: 0 errors, full compilation

## Testing Strategy

### Unit Test Updates Required

1. **Entity Tests**: Update ID generation and assertion patterns
2. **Mapper Tests**: Update DTO ↔ Entity conversion tests
3. **Repository Tests**: Update query and persistence tests
4. **Integration Tests**: Update full workflow tests

### Database Migration Testing

1. **Backup Creation**: Test database backup procedures
2. **Migration Execution**: Test both PostgreSQL and MySQL migrations
3. **Data Integrity**: Verify foreign key relationships preserved
4. **Performance**: Ensure UUID indexing performance acceptable

## Documentation Updates

### Files to Update After Migration

1. **API Documentation**: Update resource examples with UUID format
2. **Developer Guide**: Update entity creation patterns
3. **Database Schema**: Update ERD diagrams
4. **Migration Guide**: Document upgrade procedures for implementers

## Timeline and Dependencies

### Critical Path Dependencies

1. **Phase 1 mappers** must be completed before integration testing
2. **BaseIdentifiedObjectMapper** fixes needed for all resource mappers
3. **Database migrations** must be tested before production deployment
4. **Missing mapper creation** can be parallelized with existing mapper fixes

### Estimated Effort

- **Phase 1**: 2-3 days (critical mappers)
- **Phase 2**: 1-2 days (supporting mappers)  
- **Phase 3**: 2-3 days (missing mapper creation)
- **Testing**: 1-2 days (validation and integration)

**Total Estimated**: 6-10 days

## Risk Mitigation

### High-Risk Areas

1. **Collection Mappings**: Complex relationships between resources
2. **DateTime Conversions**: Timezone and format compatibility
3. **Database Migration**: Large dataset conversion risks
4. **Backward Compatibility**: API consumer impact

### Mitigation Strategies

1. **Incremental Commits**: Commit each phase separately
2. **Feature Flags**: Enable gradual rollout if needed
3. **Rollback Plan**: Maintain ability to revert migrations
4. **Extensive Testing**: Unit, integration, and performance tests

---

## Progress Log

### 2025-06-18
- ✅ Completed IdentifiedObjectEntity UUID refactoring
- ✅ Created database migrations for PostgreSQL and MySQL
- ✅ Added LinkType URL validation
- ✅ Created BaseMapperUtils interface
- ✅ Partially fixed UsagePointMapper
- ✅ Created PnodeRefMapper and AggregatedNodeRefMapper
- 🟡 Started systematic mapper review
- **Next**: Fix remaining high-priority mappers in Phase 1

---

*This document will be updated as the migration progresses. Each mapper completion should be logged with status updates and error count reductions.*