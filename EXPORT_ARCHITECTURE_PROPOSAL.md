# Modern Export Architecture Proposal

## Current Problems
1. Massive ExportService with 40+ resource-specific methods
2. Legacy JAXB classes (EntryType, ContentType) conflicting with new DTOs
3. Direct Entity → XML marshalling without DTO layer
4. Lombok annotation processing issues causing compilation errors
5. Mixed concerns between export logic and business logic

## Proposed Modern Architecture

### 1. Generic Export Service Pattern
```java
@Service
public class AtomExportService<T> {
    
    private final MapStruct mapper;
    private final Jaxb2Marshaller marshaller;
    
    // Single generic export method instead of 40+ specific methods
    public <E, D> void exportResource(E entity, Class<D> dtoClass, OutputStream stream) {
        D dto = mapper.map(entity, dtoClass);
        AtomEntryDto entry = wrapInAtomEntry(dto);
        marshaller.marshal(entry, stream);
    }
    
    public <E, D> void exportResourceList(List<E> entities, Class<D> dtoClass, OutputStream stream) {
        List<AtomEntryDto> entries = entities.stream()
            .map(entity -> {
                D dto = mapper.map(entity, dtoClass);
                return wrapInAtomEntry(dto);
            })
            .collect(Collectors.toList());
            
        AtomFeedDto feed = new AtomFeedDto(
            generateFeedId(),
            generateFeedTitle(dtoClass),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            generateFeedLinks(),
            entries
        );
        
        marshaller.marshal(feed, stream);
    }
}
```

### 2. Entity-DTO Mapping Strategy
```java
// MapStruct mappers replace manual conversion
@Mapper(componentModel = "spring")
public interface UsagePointMapper {
    UsagePointDto toDto(UsagePointEntity entity);
    UsagePointEntity toEntity(UsagePointDto dto);
}

@Mapper(componentModel = "spring") 
public interface CustomerMapper {
    CustomerDto toDto(CustomerEntity entity);
    CustomerEntity toEntity(CustomerDto dto);
}
```

### 3. Clean Service Layer
```java
@Service
public class UsagePointExportService {
    
    private final AtomExportService<UsagePointDto> atomExportService;
    private final UsagePointService usagePointService;
    private final UsagePointMapper mapper;
    
    public void exportUsagePoint(Long id, OutputStream stream) {
        UsagePointEntity entity = usagePointService.findById(id);
        atomExportService.exportResource(entity, UsagePointDto.class, stream);
    }
    
    public void exportUsagePoints(List<Long> ids, OutputStream stream) {
        List<UsagePointEntity> entities = usagePointService.findAllById(ids);
        atomExportService.exportResourceList(entities, UsagePointDto.class, stream);
    }
}
```

### 4. Controller Layer Simplification
```java
@RestController
@RequestMapping("/espi/1_1/resource")
public class UsagePointController {
    
    private final UsagePointExportService exportService;
    
    @GetMapping("/UsagePoint/{id}")
    public void exportUsagePoint(@PathVariable Long id, HttpServletResponse response) {
        response.setContentType("application/atom+xml");
        exportService.exportUsagePoint(id, response.getOutputStream());
    }
}
```

## Migration Strategy

### Phase 1: Fix Compilation Errors (Current)
1. Fix Lombok annotation processing issues
2. Resolve missing Entity methods
3. Clean up ElectricPowerUsageSummary references
4. Get basic build working

### Phase 2: Create MapStruct Mappers
1. Generate Entity-DTO mappers for all resources
2. Test mapping logic thoroughly
3. Create integration tests

### Phase 3: Replace Legacy Export Logic
1. Create generic AtomExportService
2. Replace specific export methods with generic approach
3. Update controllers to use new export service
4. Remove legacy EntryType/ContentType classes

### Phase 4: Clean Architecture
1. Remove unused legacy export methods
2. Simplify ExportService interface
3. Add comprehensive test coverage
4. Performance optimization

## Benefits of New Architecture

1. **Separation of Concerns**: Entities for persistence, DTOs for API
2. **Type Safety**: MapStruct provides compile-time validation
3. **Maintainability**: Single export pattern vs 40+ methods
4. **Testability**: Easy to unit test mapping and export logic
5. **Performance**: Efficient batch operations
6. **Flexibility**: Easy to add new resource types
7. **Standards Compliance**: Proper Atom protocol implementation

## Immediate Next Steps

1. Fix current compilation errors to get build working
2. Implement MapStruct mappers for key entities
3. Create proof-of-concept generic export service
4. Test with UsagePoint as pilot implementation