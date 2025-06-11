# Security Policy

## Supported Versions

Currently supported versions for security updates:

| Version | Supported          | Notes |
| ------- | ------------------ | ----- |
| 1.4.x   | :white_check_mark: | Current Spring Boot 3.5 migration |
| 1.3.x   | :x:                | Legacy version, archived |
| < 1.3   | :x:                | Legacy versions, archived |

## Reporting a Vulnerability

To report a security vulnerability, please follow these steps:

1. **DO NOT** create a public GitHub issue for security vulnerabilities
2. Send an email to: [security@greenbuttonalliance.org](mailto:security@greenbuttonalliance.org)
3. Include:
   - Description of the vulnerability
   - Steps to reproduce
   - Potential impact assessment
   - Any suggested fixes

You can expect:
- Acknowledgment within 48 hours
- Initial assessment within 1 week
- Regular updates on progress
- Credit in security advisories (if desired)

## Security Measures

### Current Implementation

- **GitHub Actions CI/CD**: Automated security scanning on all PRs and commits
- **OWASP Dependency Check**: Automated vulnerability scanning of dependencies
- **Dependabot**: Automated security updates for dependencies
- **Trivy Scanning**: Container and filesystem vulnerability scanning
- **TruffleHog**: Secrets detection in code
- **SonarCloud**: Static code analysis for security issues

### Dependency Management

- Spring Boot BOM manages most dependency versions
- Security patches automatically applied via Dependabot
- OWASP suppressions file documents accepted risks during migration

### Known Security Considerations

#### During Spring Boot 3.5 Migration

**Temporary Security Debt:**
1. **Legacy OAuth2**: `spring-security-oauth2:2.5.2` scheduled for replacement
2. **Xerces XML**: Direct dependency scheduled for removal
3. **Compilation Errors**: Some entity classes have compilation issues

**Migration Timeline:**
- Target completion: Q2 2025
- Security-first approach: Critical vulnerabilities addressed immediately
- All legacy dependencies will be replaced or removed

#### Authentication & Authorization

- OAuth2 implementation follows Green Button Alliance specifications
- JWT token handling for third-party access
- Retail customer data protection via authorization scopes

#### Data Protection

- Energy usage data encryption in transit
- Database connection security via Spring Boot configuration
- No hardcoded credentials in source code (verified by TruffleHog)

## Security Best Practices for Contributors

### Code Review Requirements

1. All security-related changes require 2+ reviewer approval
2. Dependency updates automatically tested for vulnerabilities
3. Static analysis results must be reviewed

### Development Guidelines

1. **Never commit secrets**: Use environment variables or encrypted secrets
2. **Validate all inputs**: Especially XML parsing and database queries
3. **Follow OWASP guidelines**: For web application security
4. **Test security features**: Include security test cases in PRs

### Reporting Security Issues in Dependencies

Run security scans locally:

```bash
# OWASP Dependency Check
mvn org.owasp:dependency-check-maven:check

# Check for outdated dependencies
mvn versions:display-dependency-updates

# Static analysis
mvn spotbugs:check
```

## Security Architecture

### Green Button Specific Security

This library implements security measures specific to the Green Button ecosystem:

1. **ESPI Authorization**: OAuth2-based customer consent management
2. **Data Scope Control**: Fine-grained access control for energy data
3. **Third-Party Isolation**: Secure separation between data custodians and third parties

### Spring Security Integration

- Leverages Spring Security 6.x (post-migration) for authentication
- Role-based access control for retail customers and applications
- Secure REST API endpoints with proper authorization

## Incident Response

In case of a security incident:

1. **Immediate**: Contact [security@greenbuttonalliance.org](mailto:security@greenbuttonalliance.org)
2. **Assessment**: Security team evaluates impact within 24 hours
3. **Mitigation**: Hotfix releases for critical vulnerabilities
4. **Communication**: Security advisories published on GitHub
5. **Post-Incident**: Security review and process improvements

---

For questions about this security policy, contact the Green Button Alliance security team.